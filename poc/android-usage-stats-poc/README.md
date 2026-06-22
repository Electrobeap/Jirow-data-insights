# Android Usage Stats PoC

This proof-of-concept validates whether Jirow Data Insights can collect Android internet consumption data on-device without a backend. It is a native Android Kotlin project with a simple diagnostic UI.

## Requirements Covered

- Request and verify Usage Access permission.
- Read total mobile data usage for a selected period.
- Read total WiFi usage for a selected period.
- Display the top 10 data-consuming applications by UID/package mapping.
- Prepare compatibility validation across Android 11, 12, 13, 14, and 15.
- Use Kotlin.
- Use a simple native Android UI.
- Do not use Flutter.
- Do not use a backend.
- Do not use authentication.

## Project Type

- Native Android proof of concept.
- Kotlin source.
- Programmatic simple UI; no Compose, Flutter, or server dependency.
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
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values/styles.xml`

Main implementation:

- `MainActivity.kt` checks Usage Access through `AppOpsManager`.
- It opens `Settings.ACTION_USAGE_ACCESS_SETTINGS` so the user can grant access.
- It queries `NetworkStatsManager.querySummaryForDevice` for mobile and WiFi totals.
- It queries `NetworkStatsManager.querySummary` for app-level UID buckets.
- It maps UIDs to app labels through `PackageManager`.
- It displays the top 10 apps by combined mobile and WiFi usage.

## Build Instructions

### Android Studio

1. Open the `poc/android-usage-stats-poc` folder in Android Studio.
2. Let Android Studio sync Gradle.
3. Select the `app` run configuration.
4. Connect an Android 11 to Android 15 device or start a matching emulator.
5. Run the app.

### Command Line

From the `poc/android-usage-stats-poc` directory:

1. Ensure Android Studio or the Android SDK is installed.
2. Ensure a compatible Gradle installation is available on PATH if not building through Android Studio.
3. Run `gradle :app:assembleDebug`.
4. Install the generated debug APK from `app/build/outputs/apk/debug/app-debug.apk`.

## Test Instructions

1. Install and launch `Jirow Usage PoC`.
2. Confirm the app shows `Usage Access: not granted`.
3. Tap `Open Usage Access Settings`.
4. Enable Usage Access for `Jirow Usage PoC`.
5. Return to the app.
6. Tap `Refresh Usage Data`.
7. Confirm mobile data usage is displayed.
8. Confirm WiFi usage is displayed.
9. Confirm the app displays up to 10 top data-consuming applications.
10. Confirm each top app row shows total usage, mobile usage, WiFi usage, UID, and package mapping.
11. Revoke Usage Access from Android settings.
12. Return to the app and confirm it handles the missing permission state.

## Expected Behavior

- The app shows whether Usage Access is granted.
- The app queries `NetworkStatsManager` for mobile and WiFi totals over the last 30 days.
- The app aggregates mobile and WiFi usage by UID and maps UIDs to installed applications through `PackageManager`.
- The top 10 data-consuming apps are shown in descending order.

## Known PoC Limitations

- The project is intentionally diagnostic and not production UI.
- `QUERY_ALL_PACKAGES` is used for feasibility testing and may not be appropriate for Play Store distribution.
- OEM devices may restrict Usage Access settings, background execution, or package visibility differently.
- Mobile subscriber-specific queries are intentionally avoided; the PoC passes `null` subscriber IDs for Android 11+ compatibility.
- Results should be validated on real devices, not only emulators, because modem, dual-SIM, and OEM framework behavior can differ.

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
- Verify Usage Access permission appears in system settings.
- Verify app detects denied permission.
- Grant Usage Access and return to app.
- Verify mobile total loads.
- Verify WiFi total loads.
- Verify up to 10 apps appear in top consumers after device has usage history.
- Revoke Usage Access and verify error state.
- Reboot device and verify app can refresh again after unlock.
- Test on at least one non-Pixel OEM device.
