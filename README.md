# Shop2Shop Assessment – Transaction Tracker

A prototype Android app that records transactions, flags them for audit, and
syncs to a backend.  
Written in 100 % Kotlin with Jetpack Compose and a clean, test-first
architecture.


## Architectural overview

### Why this structure?
* **SOLID & clean** – each layer knows only *one* direction.
* **Testability** – domain and use-cases have no Android dependencies.
* **Replaceable plugins** – Room ↔ SQLDelight, Koin ↔ Hilt, Retrofit ↔ Ktor
  can be swapped without touching domain/UI.
* **Offline-first** – UI never waits for the network.

## Class-by-class walkthrough
| Layer | Class | Why it exists |

| **Domain / model** | `Transaction.kt` | Immutable POJO representing a ledger entry. Stores epoch-millis `timestamp` (API 24-safe). |
| **Domain / model** | `AuditPolicy.kt` | Business rule object that decides **shouldFlag(tx)**. Encapsulates threshold and keyword logic – keeps rules out of UI. |
| **Domain / repository ports** | `TransactionRepository.kt` | Boundary for CRUD + streaming ledger changes; hides Room / network. |
| **Domain / repository ports** | `AuditPolicyRepository.kt` | Boundary for policy storage; shields DataStore implementation. |
| **Domain / use-cases** | `AddTransactionUseCase.kt` | Single responsibility: validate + flag + persist + push. |
| **Domain / use-cases** | `DeleteTransactionUseCase.kt` | Encapsulates removal logic; keeps UI unaware of DAO. |
| **Domain / use-cases** | `ObserveTransactionsUseCase.kt` | Exposes cold Flow; simplifies ViewModel subscription. |
| **Domain / use-cases** | `UpdateAuditPolicyUseCase.kt` | Persists new rules atomically; no direct DataStore calls elsewhere. |
| **Core util** | `Resource.kt` | Sealed wrapper → `Loading / Success / Error`. Standardises state across screens. |
| **Data / Room** | `TransactionEntity.kt` | SQL schema + primary key. |
| **Data / Room** | `TransactionDao.kt` | All DB access in one place → easier to test and migrate. |
| **Data / Room** | `AppDb.kt` | RoomDatabase with `TypeConverters` registered. |
| **Data / Room** | `Converters.kt` | Handles `BigDecimal` ↔ `String` conversion to keep precision. |
| **Data / Retrofit** | `TransactionDto.kt` | Wire JSON model; isolates network shape from domain. |
| **Data / Retrofit** | `TxApi.kt` | Retrofit interface – one endpoint per REST op. |
| **Data / Retrofit** | `FakeInterceptor.kt` | Injected latency for demos; makes loading states visible. |
| **Data / DataStore** | `AuditPolicySerializer.kt` | Custom Proto serializer – enforces schema & versioning. |
| **Data / DataStore** | `AuditPolicyMapper.kt` | Maps `AuditPolicyProto` ↔ domain model. Keeps protobuf out of business logic. |
| **Data / mapping helpers** | `EntityMapper.kt` | Centralised Room↔Domain↔Dto conversions; avoids duplication. |
| **Repositories (implementation)** | `TransactionRepositoryImpl.kt` | Combines Room + Retrofit → “NetworkBound” pattern: always write local, push remote fire-and-forget. |
| **Repositories (implementation)** | `AuditPolicyRepositoryImpl.kt` | Single-source-of-truth store around DataStore Flow. |
| **DI** | `AppModule.kt` | Koin graph: binds DB, Retrofit, DataStore, repositories, use-cases, VMs. Keeps composition out of code. |
| **ViewModel layer** | `TransactionsViewModel.kt` | State aggregator for list screen; exposes `add`, `delete` intents. |
| **ViewModel layer** | `SettingsViewModel.kt` | Holds current policy & updates; scoped to Audit Policy screen. |
| **UI / Compose** | `TransactionListScreen.kt` | Presents list + FAB; collects `Resource<List<Transaction>>`. |
| **UI / Compose** | `AddTransactionDialog.kt` | Stateless dialog; ViewModel does processing. |
| **UI / Compose** | `AuditPolicyScreen.kt` | Allows editing threshold + keywords; two-way binds to VM. |
| **UI / Compose** | `AppNav.kt` | `NavHostController` routes: *list* ⇄ *settings*. |
| **UI / Compose** | `ScreenPreviews.kt` | IDE-only previews with lightweight fakes – keep runtime code pristine. |
| **Activity** | `MainActivity.kt` | Bootstraps Koin and sets Compose root. Only Android class with logic. |

## Testing approach
| Type | File | Rationale |

| **DAO test** | `TransactionDaoTest` | Verifies CRUD & Flow emission in an in-memory DB. |
| **DataStore test** | `AuditPolicyRepositoryTest` | Confirms Proto serializer round-trip and Flow updates. |
| **Repository test** | `TransactionRepositoryImplTest` | Uses fake DAO + fake API to test local-first + push behaviour. |
| **Business rule test** | `AddTransactionUseCaseTest` | Ensures flags trigger at threshold. |
