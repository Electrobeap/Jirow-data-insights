# Android Usage Stats PoC

This proof-of-concept validates the native Android launcher path for Jirow Data Insights before reintroducing the usage-statistics diagnostic flow. It is a native Android Kotlin project with a simple XML UI.

## Requirements Covered

- Launch through `MainActivity`.
- Use `AppCompatActivity`.
- Render a proper XML activity layout.
- Display `Jirow Data Insights`.
- Display `App Loaded Successfully`.
- Keep compile SDK and target SDK compatible with Android 15 API 35.
- Use Kotlin.
- Use a simple native Android XML UI.
- Do not use Flutter.
- Do not use a backend.
- Do not use authentication.

## Project Type

- Native Android proof of concept.
- Kotlin source.
- XML layout UI; no Compose, Flutter, or server dependency.
- No backend.
- No cloud SDKs.
- No authentication.
- No external runtime services.

## Source Code

Core files:

- `settings.gradle.kts`
- `build.gradle.kts`
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/jirow/usagepoc/MainActivity.kt`
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/values/colors.xml`
- `app/src/main/res/values/dimens.xml`
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values/styles.xml`

Current launcher implementation:

- `MainActivity.kt` extends `AppCompatActivity`.
- `MainActivity.kt` loads `app/src/main/res/layout/activity_main.xml`.
- `activity_main.xml` displays the visible launch smoke-test content:
  - `Jirow Data Insights`
  - `App Loaded Successfully`
- Usage Access and data-query logic will be reintroduced after the launcher/rendering path is stable.

## Build Instructions

### Android Studio

1. Open the `poc/android-usage-stats-poc` folder in Android Studio.
2. Let Android Studio sync Gradle.
3. Confirm Android Studio is using JDK 17.
4. Confirm Android SDK Platform 35 is installed.
5. Select the `app` run configuration.
6. Connect an Android 11 to Android 15 device or start a matching emulator.
7. Run the app.

### Command Line

From the `poc/android-usage-stats-poc` directory:

1. Ensure Android Studio or the Android SDK is installed.
2. Ensure JDK 17 is available.
3. Ensure Android SDK Platform 35 is installed.
4. Ensure a compatible Gradle installation is available on PATH if not building through Android Studio.
5. Run `gradle :app:assembleDebug`.
6. Install the generated debug APK from `app/build/outputs/apk/debug/app-debug.apk`.

## Test Instructions

1. Install and launch `Jirow Usage PoC`.
2. Confirm the app renders `Jirow Data Insights`.
3. Confirm the app renders `App Loaded Successfully`.
4. Confirm there is no blank white screen.
5. Confirm Logcat shows no AndroidRuntime crash for `com.jirow.usagepoc`.

## Expected Behavior

- The app launches through `MainActivity`.
- The app renders a proper XML layout.
- The app displays `Jirow Data Insights`.
- The app displays `App Loaded Successfully`.

## Known PoC Limitations

- This checkpoint verifies launcher rendering only.
- Usage Access, mobile data usage, WiFi usage, and top app consumption queries are not active in the current launcher screen.
- Usage-statistics logic should be reintroduced after this AppCompat/XML rendering path is confirmed on the emulator.

## Compatibility Matrix

| Android version | API level | Expected status |
| --- | ---: | --- |
| Android 11 | 30 | Supported by project min SDK |
| Android 12 | 31/32 | Supported |
| Android 13 | 33 | Supported |
| Android 14 | 34 | Supported |
| Android 15 | 35 | Supported by compile/target SDK |

## Compatibility Test Checklist

- Install debug build.
- Launch on Android 15 API 35 emulator.
- Verify `Jirow Data Insights` is visible.
- Verify `App Loaded Successfully` is visible.
- Verify no blank white screen appears.
- Verify Logcat has no AndroidRuntime crash for `com.jirow.usagepoc`.
