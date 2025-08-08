# Desafio Android — Solução (Jetpack Compose)

> **Resumo :** Arquitetura modular, UDF com ViewModel + StateFlow, offline-first (Room), testes (unit + instrumentado + UI Compose) e resiliência a rotação, process-death e rede ruim.

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
7. [Glosário de Branchs](#glossário-de-branches)
8. [Testes](#testes)
9. [Próximos Passos](#próximos-passos)

---

## Stack
| Camada | Libs |
| ------ | ---- |
| UI | **Jetpack Compose**, Navigation Compose, Coil |
| DI | Koin |
| Assíncrono | Coroutines + Flow |
| Network | Retrofit  |
| Cache | Room |
| Qualidade | Detekt, Ktlint |
| Testes | JUnit5, MockK, Turbine, MockWebServer, Compose UI Testing |

---

## Screenshots / GIFs
<img width="334" height="734" alt="Captura de Tela 2025-08-08 às 20 27 40" src="https://github.com/user-attachments/assets/ef28131a-6cfb-45c0-9988-5c6a0bbcb5a2" />

<img width="308" height="650" alt="Captura de Tela 2025-08-08 às 20 27 48" src="https://github.com/user-attachments/assets/d80a3253-26b1-4d02-94ce-6cb346023271" />

<img width="310" height="710" alt="Captura de Tela 2025-08-08 às 20 27 57" src="https://github.com/user-attachments/assets/790c3f22-14b0-4e45-8972-157f2cb58c68" />


---

## Arquitetura

```mermaid
flowchart TD

  %% Apresentação
  subgraph P["Presentation"]
    UI[Compose] -->|Intents| VM[ViewModel]
    VM -->|StateFlow<UiState>| UI
  end

  %% Domínio
  subgraph D["Domain"]
    UC[UseCase]
    IRepo["Repository (interface)<br/><code>interface UserRepository</code>"]
    UC --> IRepo
  end

  %% Dados 
  subgraph DA["Data"]
    RepoImpl["RepositoryImpl<br/><code>class UserRepositoryImpl</code>"]
    ROOM[(Room<br/>Local Cache)]
    RETRO[Retrofit/OkHttp<br/>Remote]
    RepoImpl --> ROOM
    RepoImpl --> RETRO
  end

  %% Ligações entre camadas
  VM --> UC
  RepoImpl -.->|implements| IRepo

```
> **Por que assim?**  
> 
> - **Resiliência** → ViewModel + `SavedStateHandle` mantêm o estado mesmo em rotação de tela ou process-death, evitando recarregar tudo e perder progresso do usuário.  
> - **Evolução** → Separar o Domain garante que mudanças de regra de negócio não quebrem UI ou Data, facilitando testes e refatorações sem efeito dominó.  
> - **Offline-first** → O Repository decide entre cache local (Room) e remoto (API), mantendo UX consistente e dados disponíveis mesmo sem internet.

---

## Módulos

```
app/                          # Chamada do di+ navegação
core/designsystem/            # Tema, cores, componentes, espaçamentos
core/navigation               # Composition root + navegação
feature/home/                 # Tela principal (UI + VM + DI)
```

---

## Fluxo de Dados

Estado imutável (`StateFlow<UiState>`) na ViewModel; eventos one-shot em `SharedFlow`.

```kotlin
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<UserDomain>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
```

---

## Política de Cache

1. **Room primeiro** (`loadFromDb()`).
2. Se dados estão velhos, faz **refresh** em paralelo (Remote → Room → UI).
3. Offline? Mostra o que tem no DB e sinaliza modo offline.

---
<details>
<summary>📚 Glossário de Branches</summary>

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
| 001 | `core/001-designsystem-foundation` | **core/designsystem** – modulo. |
| 001 | `core/001-designsystem-foundation_pt2` | **core/designsystem** – cores, tipografia, espaçamentos, `Theme.kt`, previews. |
| 002 | `core/002-designsystem-components` | Botões, textos, cards, estados de loading/erro, docs KDoc + stories no Playground. |
| 003 | `core/003-navigation` | **core/navigation** – Prepara para a navegação do app |

---

## ✨ Feature: Home

| Nº  | Branch | Descrição rápida |
|----:|--------|------------------|
| 001 | `feature/001-model-dto-mapper` | Criação dos modelos de domínio, DTOs da API e mapeadores entre eles. |
| 004 | `feature/004-usecase` | Camada opcional de UseCase isolando regras de negócio da UI. |
| 005 | `feature/005-home-viewmodel` | ViewModel e contratos de UI (State + Event), gerenciamento com StateFlow. |
| 006 | `feature/006-adjusting-internal` | Ajusta as classes internal do modulo home pra nao dar acesso a outros modulos . |
| 007 | `feature/007-adjusting-local-data-source` | Ajusta o controle do local data source no projeto  . |
| 008 | `feature/008-adjusting-xp-screen` | Ajusta o a experiência da home . |

---

### 🗝️ Padrão de nomenclatura
<área>/<número-sequencial>-<slug-kebab-case>

* **área** = `infra`, `core`, `feature`, `app`, `docs`  
* **número** = ordem de merge (três dígitos para manter ordenação lexicográfica)  
* **slug** = resumo claro da tarefa/objetivo  

Esse esquema garante histórico linear, PRs focados e fácil rastreabilidade de discussões.

</details>

## Testes

| Tipo | Ferramentas | Cobertura |
| ---- | ----------- | --------- |
| Unit | JUnit, MockK, Turbine | ViewModel, Repo, UseCase |
| Instrumentado | Room (in-memory), MockWebServer | DAO, rede 200/304/404/500 |
| UI Compose | Compose Test | loading/empty/error/success + ações |


---

## Próximos Passos

- Snapshot tests (Papparazzi)   
- Feature flags simples  
- E2E tests
---
