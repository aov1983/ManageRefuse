# Руководство по развертыванию (Deployment)

## 1. Сборка проекта

### Требования
*   **JDK:** Версия 17 или выше.
*   **Android SDK:** API 34 (Target), API 26 (Min).
*   **Gradle:** Версия, указанная в `gradle-wrapper.properties` (обычно 8.x).

### Локальная сборка (Debug)
Для отладки на эмуляторе или устройстве:
```bash
./gradlew assembleDebug
```
APK файл будет создан по пути: `app/build/outputs/apk/debug/app-debug.apk`.

### Сборка релизной версии (Release)
Для публикации в магазин требуется подписанный APK или AAB (Android App Bundle).

#### Шаг 1: Создание ключа подписи
Если у вас еще нет ключа, создайте его с помощью `keytool`:
```bash
keytool -genkey -v -keystore refund-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias refund_key
```
**Важно:** Сохраните файл `.jks` и пароли в надежном месте (менеджер паролей). Потеря ключа сделает невозможным обновление приложения в магазине.

#### Шаг 2: Настройка `gradle.properties`
Добавьте или отредактируйте файл `gradle.properties` в корне проекта (не коммитьте этот файл в git!):
```properties
RELEASE_STORE_FILE=../refund-release-key.jks
RELEASE_KEY_ALIAS=refund_key
RELEASE_STORE_PASSWORD=ваш_пароль_от_хранилища
RELEASE_KEY_PASSWORD=ваш_пароль_от_ключа
```

#### Шаг 3: Сборка AAB
```bash
./gradlew bundleRelease
```
Файл будет создан по пути: `app/build/outputs/bundle/release/app-release.aab`.

#### Шаг 4: Сборка APK (опционально)
```bash
./gradlew assembleRelease
```

## 2. Публикация в RuStore

1.  **Регистрация:** Зарегистрируйтесь в [консоли разработчика RuStore](https://developer.rustore.ru/).
2.  **Создание приложения:** Нажмите "Добавить приложение", заполните название и пакет (`com.refund.app`).
3.  **Загрузка билда:** Загрузите файл `app-release.aab`.
4.  **Оформление страницы:**
    *   **Иконка:** 512x512 px (PNG).
    *   **Скриншоты:** Минимум 2 скриншота для телефона (1920x1080 или пропорционально).
    *   **Описание:** Краткое описание функций (см. README.md).
    *   **Политика конфиденциальности:** Ссылка на документ (можно использовать шаблон из репозитория или сгенерировать).
5.  **Настройки контента:** Укажите категорию "Финансы", возрастное ограничение 0+.
6.  **Отправка на модерацию:** После заполнения всех полей нажмите "Отправить". Модерация занимает обычно 1-3 рабочих дня.

## 3. CI/CD (Автоматизация)

Для автоматической сборки при пуше в репозиторий используется GitHub Actions.

### Настройка секретов
В репозитории GitHub перейдите в **Settings → Secrets and variables → Actions** и добавьте следующие секреты:
*   `KEYSTORE_FILE`: Содержимое файла `refund-release-key.jks` в формате Base64.
    *   Команда для получения: `cat refund-release-key.jks | base64 | pbcopy` (macOS) или используйте онлайн-конвертер.
*   `KEYSTORE_PASSWORD`: Пароль от хранилища.
*   `KEY_ALIAS`: Алиас ключа.
*   `KEY_PASSWORD`: Пароль от ключа.

### Workflow файл
Файл `.github/workflows/build.yml` (необходимо создать) автоматически соберет проект при пуше в ветку `main` или `release`.

Пример структуры workflow:
```yaml
name: Android Build

on:
  push:
    branches: [ main, release ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      
      - name: Decode Keystore
        run: echo $KEYSTORE_FILE | base64 -d > app/refund-release-key.jks
        env:
          KEYSTORE_FILE: ${{ secrets.KEYSTORE_FILE }}
      
      - name: Build Release AAB
        run: ./gradlew bundleRelease
        env:
          RELEASE_STORE_FILE: ../app/refund-release-key.jks
          RELEASE_KEY_ALIAS: ${{ secrets.KEY_ALIAS }}
          RELEASE_STORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
          RELEASE_KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
      
      - name: Upload Artifact
        uses: actions/upload-artifact@v3
        with:
          name: app-release
          path: app/build/outputs/bundle/release/app-release.aab
```

## 4. Обновление приложения

Для выпуска обновления:
1.  Увеличьте `versionCode` и `versionName` в файле `app/build.gradle.kts`.
    ```kotlin
    android {
        defaultConfig {
            versionCode = 2
            versionName = "1.1.0"
        }
    }
    ```
2.  Соберите новый AAB (`./gradlew bundleRelease`).
3.  Загрузите новый файл в консоль RuStore поверх предыдущей версии.

## 5. Мониторинг релиза

После публикации:
*   Следите за отзывами в консоли RuStore.
*   Мониторьте краши через Firebase Crashlytics (если подключен).
*   Отслеживайте метрики установки и удалений.
