# Desafio Android — Solução (Jetpack Compose)

> **Resumo :** Arquitetura modular, UDF com ViewModel + StateFlow, offline-first (Room + OkHttp cache), testes (unit + instrumentado + UI Compose) e resiliência a rotação, process-death e rede ruim.

![badge-android](https://img.shields.io/badge/Android-Compose-3DDC84)
![badge-kotlin](https://img.shields.io/badge/Kotlin-2.x-blue)
![badge-ci](https://img.shields.io/badge/CI-Gradle%20%2B%20Detekt-lightgrey)

---

## Índice
1. [Stack](#stack)
2. [Screenshots / GIFs](#screenshots--gifs)
3. [Arquitetura](#arquitetura)
4. [Módulos](#módulos)
5. [Fluxo de Dados](#fluxo-de-dados)
6. [Política de Cache](#política-de-cache)
7. [Tratamento de Erros](#tratamento-de-erros)
8. [Glosário de Branchs](#glossário-de-branches)
9. [Como Rodar](#como-rodar)
10. [Testes](#testes)
11. [Próximos Passos](#próximos-passos)

---

## Stack
| Camada | Libs |
| ------ | ---- |
| UI | **Jetpack Compose**, Navigation Compose, Coil |
| DI | Koin |
| Assíncrono | Coroutines + Flow |
| Network | Retrofit + OkHttp (logging só em *debug*) |
| Cache | Room + OkHttp cache (ETag/Last-Modified) |
| Paginação | Paging 3 |
| Qualidade | Detekt, Ktlint |
| Testes | JUnit5, MockK, Turbine, MockWebServer, Compose UI Testing |

---

## Screenshots / GIFs

> **TODO:** inserir 2–3 GIFs curtos (loading → sucesso; erro → retry; offline).

---

## Arquitetura

```mermaid
flowchart LR
  
  subgraph Presentation["Presentation"]
  subgraph UI_Layer["UI"]
    UI[Compose]
  end
    VM[ViewModel]
  end

  subgraph Domain["Domain"]
    UC["UseCase<br/>"]
  end

  subgraph Data["Data"]
    REPO[Repository<br/>]
    ROOM[(Room<br/>Local Cache)]
    RETRO[Retrofit/OkHttp<br/>Remote]
  end

  %% Fluxo principal
  UI -->|Intents| VM
  VM --> UC
  UC --> REPO

  %% Fontes de dados
  REPO --> ROOM
  REPO --> RETRO

  %% Estado de volta pra UI
  VM -->|StateFlow&lt;UiState&gt;| UI

```
> **Por quê assim ?**  
> Resiliência: ViewModel + SavedStateHandle seguram estado em rotação/process-death.
> Evolução: Domain separado garante mudanças de regra sem mexer UI/Data.
> Offline-first: Repo decide cache versus remoto, mantendo UX consistente.

---

## Módulos

```
app/                          # Chamada do di+ navegação
core/designsystem/            # Tema, cores, componentes, espaçamentos
core/playground               # Teste dos componentes do DS
core/navigation               # Composition root + navegação
core/network/                 # Retrofit/OkHttp + interceptors
core/common/                  # Result, Error, DispatchersProvider
feature/home/                 # Tela principal (UI + VM + DI)
```

---

## Fluxo de Dados

Estado imutável (`StateFlow<UiState>`) na ViewModel; eventos one-shot em `SharedFlow`.

```kotlin
data class UiState(
    val isLoading: Boolean = false,
    val items: List<UiItem> = emptyList(),
    val error: UiError? = null,
    val isEmpty: Boolean = false
)
```

```kotlin
@Composable
fun Screen(state: UiState, onRetry: () -> Unit) {
    when {
        state.isLoading -> Loading()
        state.error != null -> Error(onRetry)
        state.isEmpty -> Empty(onRetry)
        else -> Content(state.items)
    }
}
```

---

## Política de Cache

1. **Room primeiro** (`loadFromDb()`).
2. Se dados estão velhos, faz **refresh** em paralelo (Remote → Room → UI).
3. Offline? Mostra o que tem no DB e sinaliza modo offline.

---

## Tratamento de Erros

| Throwable -> AppError | Como a UI reage |
| --------------------- | --------------- |
| `UnknownHostException` | Sem conexão |
| `SocketTimeoutException` | Timeout |
| `HttpException` 4xx/5xx | `Server(code)` |
| Outro | `Unknown` |

Retry com backoff nos casos que vale a pena e mensagens “Tentar novamente”.

---
## Glossário de Branches

> **Por quê?**  
> Este glossário serve como _guarda-chuva_ de tarefas: cada branch tem nome padronizado (`<área>/<nº>-<slug-descritivo>`), facilitando a discussão nos PRs, a ordem de merge e a leitura do meu raciocínio de construção.

## 📂 Infra

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 001 | `infra/001-project-setup` | Criação do projeto Android, Gradle raiz, README, `.gitignore`. |
| 002 | `infra/002-github-actions` | Workflow CI: `assembleDebug`, |
| 003 | `infra/003-pr-templates` | Templates de Pull Request e Issue na pasta `.github/`. |

---

## 🧱 Core

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 001 | `core/001-designsystem-foundation` | **core/designsystem** – cores, tipografia, espaçamentos, `Theme.kt`, previews. |
| 002 | `core/002-designsystem-components` | Botões, textos, cards, estados de loading/erro, docs KDoc + stories no Playground. |
| 003 | `core/003-common` | **core/common** – Result, AppError, DispatchersProvider + testes unitários. |
| 004 | `core/004-network` | **core/network** – Retrofit, OkHttp, interceptors, MockWebServer configuração base. |
| 005 | `core/005-database` | **core/database** – Room, entidades, DAO + in-memory tests. |

---

## ✨ Features

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 014 | `feature/014-home-data` | Data layer da Home (DTO, mapper, Repository, Paging). |
| 015 | `feature/015-home-ui` | UI Home em Compose + ViewModel + testes de VM. |
| 017 | `feature/017-offline-cache` | Política Room-first + refresh paralelo, banner “offline”. |
| 018 | `feature/018-error-handling` | Mapeio erros → UI, retry com backoff, SnackBar/toasts. |
| 019 | `feature/019-ui-tests` | Compose UI tests: loading, empty, error, success + ações. |
| 020 | `feature/020-perf-accessibility` | Passo final de performance (recomposição) e A11y. |

---

## 📱 App

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 016 | `app/016-navigation-root` | Navigation Compose, injeta VM Home, restaura estado em rotação. |

---

## 📝 Docs

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 021 | `docs/021-readme-gifs` | GIFs, badges finais, cobertura, LICENSE, contatos no README. |

---

### 🗝️ Padrão de nomenclatura
<área>/<número-sequencial>-<slug-kebab-case>

* **área** = `infra`, `core`, `feature`, `app`, `docs`  
* **número** = ordem de merge (três dígitos para manter ordenação lexicográfica)  
* **slug** = resumo claro da tarefa/objetivo  

Esse esquema garante histórico linear, PRs focados e fácil rastreabilidade de discussões.

---
## Como Rodar

> Depende de **JDK 17** e Android Studio **Koala** ou superior.

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPO.git
cd SEU-REPO

./gradlew clean assembleDebug detekt ktlintCheck   # build + análise
./gradlew test                                     # unit tests
./gradlew connectedAndroidTest                     # instrumentados
```

Secrets/API → `local.properties` (fica fora do Git):

```
API_BASE_URL=https://api.exemplo.com/
```

---

## Testes

| Tipo | Ferramentas | Cobertura |
| ---- | ----------- | --------- |
| Unit | JUnit, MockK, Turbine | ViewModel, Repo, UseCase |
| Instrumentado | Room (in-memory), MockWebServer | DAO, rede 200/304/404/500 |
| UI Compose | Compose Test | loading/empty/error/success + ações |

Meta de ≥ 70 % em domain/data.

---

## Próximos Passos

- Snapshot tests (Papparazzi)   
- Feature flags simples  
- E2E tests

---
