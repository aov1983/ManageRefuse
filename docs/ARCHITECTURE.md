# Архитектура приложения Refund

## Обзор

Приложение построено по принципам **Clean Architecture** с использованием паттерна **MVVM** (Model-View-ViewModel). Такой подход обеспечивает разделение ответственности, тестируемость кода и независимость от фреймворков.

## Слои архитектуры

### 1. Presentation Layer (UI)
Отвечает за отображение данных и взаимодействие с пользователем.
*   **Компоненты:** Activities, Fragments, Views, Adapters.
*   **Технологии:** Jetpack ViewModel, LiveData/StateFlow, Navigation Component, ViewBinding, Material Design 3.
*   **Задачи:**
    *   Отображение данных пользователю.
    *   Обработка пользовательского ввода.
    *   Отправка событий в ViewModel.
    *   Наблюдение за состоянием UI через LiveData/StateFlow.

### 2. Domain Layer (Бизнес-логика)
Чистый Kotlin модуль, не зависящий от Android SDK.
*   **Компоненты:** Use Cases, Domain Models, Repository Interfaces.
*   **Задачи:**
    *   Инкапсуляция бизнес-правил (например, расчет даты следующего списания).
    *   Определение интерфейсов для работы с данными (`ISubscriptionRepository`).
    *   Оркестрация потоков данных между репозиториями и UI.

### 3. Data Layer (Данные)
Реализует интерфейсы Domain слоя и управляет источниками данных.
*   **Компоненты:** Repositories Implementations, Data Sources (Local/Remote), DTOs, Entities.
*   **Технологии:** Room (SQLite), Retrofit (Network), WorkManager (Background tasks), DataStore/SharedPreferences.
*   **Задачи:**
    *   Получение данных из локальной БД или сети.
    *   Кэширование данных.
    *   Маппинг Entity -> Domain Model.
    *   Выполнение фоновых задач (синхронизация, уведомления).

## Внедрение зависимостей (DI)

Используется библиотека **Dagger Hilt** для автоматического внедрения зависимостей.
*   `@HiltAndroidApp` в классе Application.
*   Модули (`@Module`) для предоставления DAO, Репозиториев, UseCase и ViewModel.
*   Область видимости (`@Singleton`, `@ViewModelScoped`) управляется аннотациями Hilt.

## Схема потока данных

```mermaid
graph TD
    User[Пользователь] --> UI[Fragment/Activity]
    UI --> VM[ViewModel]
    VM --> UC[Use Case]
    UC --> Repo[Repository Interface]
    Repo --> RepoImpl[Repository Implementation]
    RepoImpl --> LocalDS[Local DataSource (Room)]
    RepoImpl --> RemoteDS[Remote DataSource (Retrofit)]
    LocalDS --> DB[(SQLite Database)]
    RemoteDS --> API[External API]
    
    style User fill:#f9f,stroke:#333
    style UI fill:#bbf,stroke:#333
    style VM fill:#bfb,stroke:#333
    style UC fill:#fbb,stroke:#333
```

## Структура пакетов

```
com.refund.app
├── data
│   ├── local           # Room: Entities, DAOs, TypeConverters
│   ├── remote          # Retrofit: DTOs, API Interfaces
│   └── repository      # Реализации репозиториев
├── domain
│   ├── model           # Чистые модели данных
│   ├── repository      # Интерфейсы репозиториев
│   └── usecase         # Бизнес-логика (UseCases)
├── presentation
│   ├── ui              # Fragments, Activities, Adapters
│   ├── viewmodel       # ViewModel'и
│   └── navigation      # Навигационный граф
└── di                  # Hilt Modules
```

## Обработка ошибок

*   **Сеть:** Использование `Result<T>` или sealed классов для обработки состояний успеха/ошибки.
*   **Локальные ошибки:** Перехват исключений Room в репозитории и трансформация в понятные сообщения.
*   **UI:** Отображение Snackbar или специальных экранов ошибок (Empty State, Error State).

## Тестирование

*   **Unit Tests (JUnit + MockK):** Тестирование UseCase и логики репозиториев.
*   **Integration Tests:** Тестирование взаимодействия ViewModel и Repository.
*   **UI Tests (Espresso):** Автоматизация пользовательских сценариев.
