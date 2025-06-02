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
