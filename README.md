# Desafio Android — Solução (Jetpack Compose)

> **Resumo:** Arquitetura modular, UDF com ViewModel + StateFlow, offline-first (Room), testes (unit + instrumentado + UI Compose) 

![badge-android](https://img.shields.io/badge/Android-Compose-3DDC84)
![badge-kotlin](https://img.shields.io/badge/Kotlin-2.x-blue)
![badge-ci](https://img.shields.io/badge/CI-Gradle%20%2B%20Detekt-lightgrey)

---

## Como rodar 

```bash
# 1) Clonar
git clone https://github.com/sabinabernardes/Desafio.git
cd Desafio

# 2) Build rápido
./gradlew clean assembleDebug

# 3) Testes unitários
./gradlew test
# (opcional) ./gradlew connectedCheck  # se tiver device/emulador

# 4) Abrir no Android Studio e rodar
```

---

## Índice
1. [Stack](#stack)
2. [Screenshots / GIFs](#screenshots--gifs)
3. [Arquitetura](#arquitetura)
4. [Módulos](#módulos)
5. [Fluxo de Dados](#fluxo-de-dados)
6. [Política de Cache](#política-de-cache)
7. [Como Testar](#como-testar)
8. [Glosário de Branches](#glossário-de-branches)
9. [Testes](#testes)
10. [Trade-offs e Decisões Técnicas](#trade-offs-e-decisões-técnicas)
11. [Coisas legais pra ver aqui](#coisas-legais-pra-ver-aqui)
12. [Próximos Passos](#próximos-passos)

---

## Stack
| Camada | Libs |
| ------ | ---- |
| UI | **Jetpack Compose**, Navigation Compose, Coil |
| DI | Koin |
| Assíncrono | Coroutines + Flow |
| Network | Retrofit |
| Cache | Room |
| Qualidade | Detekt, Ktlint |
| Testes | JUnit5, MockK, Compose UI Testing |

---

## Screenshots / GIFs
<!-- Substituir com GIF curtinho se possível -->
<img width="334" height="734" alt="Screen1" src="https://github.com/user-attachments/assets/ef28131a-6cfb-45c0-9988-5c6a0bbcb5a2" />
<img width="308" height="650" alt="Screen2" src="https://github.com/user-attachments/assets/d80a3253-26b1-4d02-94ce-6cb346023271" />
<img width="310" height="710" alt="Screen3" src="https://github.com/user-attachments/assets/790c3f22-14b0-4e45-8972-157f2cb58c68" />


https://github.com/user-attachments/assets/6654631b-cebf-4160-aff9-a58deb3ce13f

---

## Arquitetura

```mermaid
flowchart TD

  subgraph P["Presentation"]
    UI[Compose] -->|Intents| VM[ViewModel]
    VM -->|StateFlow<UiState>| UI
  end

  subgraph D["Domain"]
    UC[UseCase]
    IRepo["Repository (interface)<br/><code>UserRepository</code>"]
    UC --> IRepo
  end

  subgraph DA["Data"]
    RepoImpl["RepositoryImpl<br/><code>UserRepositoryImpl</code>"]
    ROOM[(Room<br/>Local Cache)]
    RETRO[Retrofit/OkHttp<br/>Remote]
    RepoImpl --> ROOM
    RepoImpl --> RETRO
  end

  VM --> UC
  RepoImpl -.->|implements| IRepo
```

> **Por que assim?**   
> - Evolução sem quebra: UI, Domain e Data desacoplados.  
> - Offline-first: Repository decide entre Room e API.

---

## Módulos

- [`app/`](app) — DI + Navegação  
- [`core/designsystem/`](core/designsystem) — Tema, cores, componentes  
- [`core/navigation`](core/navigation) — Composition root e navegação  
- [`feature/home/`](feature/home) — Tela principal (UI + VM + DI)  

---

## Fluxo de Dados

```kotlin
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<UserDomain>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
```

---

## Política de Cache

1. Room primeiro (`loadFromDb()`)  
2. Se dados velhos, refresh em paralelo (API → Room → UI)  

---
### Como testar
1. Rodar o app e ver a lista (estado Loading → Success).  
2. Ativar **modo avião** e reabrir o app: lista continua disponível (cache local).  
3. Desativar modo avião: dados são atualizados quando a rede volta.

## Glosário de Branches

<details>
<summary>Ver branches</summary>

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

---

## Testes

| Tipo | Ferramentas | Casos principais |
| ---- | ----------- | ---------------- |
| Unit | JUnit, MockK, Turbine | VM emite Loading→Success; Repo acessa cache e API |
| Instrumentado | Room in-memory |
| UI Compose | Compose Test | Estados loading/error/success e ações |

---

##  Trade-offs e Decisões Técnicas

Aqui estão as principais escolhas de arquitetura e por que elas foram feitas neste projeto.  
A ideia não é só listar tecnologias, mas mostrar **o raciocínio** por trás delas.

### **UI e Arquitetura**
- **Jetpack Compose** → Mais rápido pra iterar e testar.  
  _Trade-off_: curva de aprendizado e atenção à recomposição; resolvido com UDF + estados imutáveis.
- **Unidirectional Data Flow (UDF)** com `StateFlow` → Estado único, previsível e fácil de testar.
- **Kotlin Flow** no domínio/repos** → Fluxos reativos pra dados contínuos (ex.: Room emite mudanças automaticamente).  
  _Benefício_: evita callbacks e facilita composição de operações assíncronas.  
  _Trade-off_: exige atenção a escopo/cancelamento; mitigado com `viewModelScope` e operadores como `onStart`/`catch`.
- **ViewModel + UseCases** → Isolamento de regras de negócio da UI.  
  _Custo_: mais arquivos, ganho em clareza e escalabilidade.

### **Injeção de Dependências**
- **Koin** → Setup rápido e simples.  

### **Estratégia de Dados**
- **Offline-first com Room** → Resposta instantânea do cache local, seguido de atualização em segundo plano (*stale-while-revalidate*).

### **Testes e Qualidade**
- **Testes de ViewModel**  (validação de fluxo de estados).
- **CI** com build, lint, testes.
- **ktlintCheck** e **Detekt** para manter o padrão de código.

---

## 📌 Coisas legais pra ver aqui

Quer ver de perto arquitetura bem estruturada, Compose aplicado com boas práticas e atenção aos detalhes?
Aqui estão os destaques do repositório, com links diretos para as partes mais interessantes — tudo organizado de forma modular para facilitar leitura e testes.

### 💻 UI & Compose (módulo Home)
- **[HomeScreen](feature/home/src/main/java/com/bina/home/presentation/screen/HomeScreen.kt)** → Compose com estados claros (Loading/Success/Error) e UI desacoplada da VM.  
- **[Design System](core/designsystem)** *(módulo dedicado)* → Tokens de cor, tipografia, espaçamentos e componentes reutilizáveis com previews.

### 🏗 Arquitetura & Dados (módulo Home)
- **[HomeViewModel](feature/home/src/main/java/com/bina/home/presentation/viewmodel/HomeViewModel.kt)** → UDF com `StateFlow` e estado imutável, resiliente a rotação/process-death.  
- **[UserRepositoryImpl](feature/home/src/main/java/com/bina/home/data/repository/UsersRepositoryImpl.kt)** → Estratégia **offline-first**: lê do Room primeiro e atualiza em segundo plano via API.  
- **[Local Data Source](feature/home/src/main/java/com/bina/home/data/localdatasource/UsersLocalDataSourceImpl.kt)** → Implementação que lê/escreve no Room.  
- **[Remote Data Source](feature/home/src/main/java/com/bina/home/data/remotedatasource/UsersRemoteDataSourceImpl.kt)** → Implementação que consulta a API via Retrofit.

  ### 🧪 Testes
- **[VM Tests](feature/home/src/test/java/com/bina/home/presentation/viewmodel/HomeViewModelTest.kt)** → fluxo de uiState.  
- **[HomeScreenUiTest](feature/home/src/androidTest/java/com/bina/home/presentation/screen/HomeScreenUiTest.kt)** → teste de Ui com compose 

### ⚙️ CI/CD & Qualidade (root do repo)
- **[CI Workflow](.github/workflows/ci.yml)** → Build + lint + testes + **relatório de cobertura Kover **.  
- **[Template de Pull Request](.github/PULL_REQUEST_TEMPLATE.md)** → Checklist de revisão (build, testes, screenshots, trade-offs).  
- **Ktlint & Detekt** → Estilo consistente e regras estáticas.

---

## Próximos Passos
- Mapeamento de erros avançado (4xx/5xx)  
- Snapshot tests (Paparazzi)  
- Feature flags  
- E2E tests  
