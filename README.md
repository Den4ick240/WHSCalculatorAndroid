# WHS Calculator - Android Application

A native Android application built using **Clean Architecture** and **Android Jetpack** components to calculate and track World Handicap System (WHS) index scores for golfers.

## 🚀 Key Features

* **WHS Index Calculation:** Computes official handicap indexes and course handicaps using dedicated domain use cases.
* **Course & Score Tracking:** Manage, add, and inspect historical golf scores and course ratings using local persistent storage.
* **Multi-Tab Navigation:** Modular UI flow powered by Jetpack Navigation Component with nested navigation graphs.
* **CI/CD Integrated:** Automated build and integration pipeline managed via GitHub Actions.

## 🛠️ Architecture & Tech Stack

* **Architecture:** Clean Architecture (Data, Domain, Presentation) with Use Cases and Repository Pattern
* **Language:** Java
* **Database & Persistence:** Room Persistence Library (DAOs, Entities, Mappers, Type Converters)
* **Dependency Injection:** Hilt / Dagger
* **Jetpack Components:** `ViewModel`, `LiveData`, `Room`, `Navigation Component`, `View Binding`
* **Build & Automation:** Gradle, GitHub Actions CI/CD
