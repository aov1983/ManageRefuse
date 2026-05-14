# План внедрения аналитики

## Цель
Сбор данных о поведении пользователей для улучшения продукта, анализа удержания (retention) и оценки эффективности монетизации.

## Инструменты
Рекомендуется использовать один из следующих инструментов (или оба одновременно):
1.  **Firebase Analytics** (Google) — стандарт де-факто для Android, глубокая интеграция с экосистемой Google.
2.  **AppMetrica** (Yandex) — предпочтительно для рынка РФ, детальная атрибуция и воронки.

## Настройка
1.  Добавить зависимости в `build.gradle.kts` (app):
    ```kotlin
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    
    // AppMetrica
    implementation("com.yandex.android:metrica:6.0.0")
    ```
2.  Инициализация в `RefundApplication.kt`:
    ```kotlin
    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)
        YandexMetrica.activate(applicationContext, "API_KEY")
        YandexMetrica.enableActivityAutoTracking(this)
    }
    ```

## Ключевые события (Events)

| Событие | Параметры | Описание |
| :--- | :--- | :--- |
| `onboarding_completed` | - | Пользователь завершил онбординг. |
| `subscription_added` | `service_name`, `periodicity`, `amount` | Добавлена новая подписка. |
| `subscription_edited` | `service_name`, `field_changed` | Изменена существующая подписка. |
| `subscription_cancelled` | `service_name`, `saved_amount` | Подписка отменена (добавлена в экономию). |
| `reminder_sent` | `service_name`, `days_before` | Отправлено уведомление о списании. |
| `reminder_clicked` | `service_name` | Пользователь нажал на уведомление. |
| `instruction_viewed` | `service_name` | Открыта инструкция по отмене. |
| `partner_link_clicked` | `partner_id`, `service_name` | Клик по партнерской ссылке. |
| `export_data_used` | `format` (csv) | Экспорт данных пользователем. |
| `settings_changed` | `setting_name`, `new_value` | Изменение настроек (уведомления, тема). |

## Воронки и метрики

### 1. Активация
*   Установка → Запуск → Завершение онбординга → Добавление первой подписки.
*   **Цель:** >40% конверсия в добавление первой подписки.

### 2. Удержание (Retention)
*   Day 1, Day 7, Day 30.
*   **Цель:** Day 1 > 35%, Day 7 > 15%.

### 3. Монетизация
*   Показ партнерской ссылки → Клик → Переход на сайт партнера.
*   **CTR кнопок "Найти дешевле"**.

## Дашборды
Необходимо настроить дашборды в **Looker Studio** (для Firebase) или кабинете AppMetrica:
1.  **Обзор:** DAU/MAU, новые пользователи, сессии.
2.  **События:** Топ событий, параметры событий.
3.  **Воронки:** Конверсия по ключевым сценариям.
4.  **Краши:** Crash-free users rate (интеграция с Crashlytics).

## Безопасность и приватность
*   Не передавать персональные данные (PII) в системы аналитики.
*   Использовать анонимные ID устройств.
*   Соблюдать политику конфиденциальности (упомянуть сбор телеметрии).
