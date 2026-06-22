# Android Usage Stats PoC

This proof-of-concept validates whether Jirow Data Insights can collect Android internet consumption data on-device without a backend.

## Objectives

- Request and verify Usage Access permission.
- Read total mobile data usage for a selected period.
- Read total WiFi usage for a selected period.
- List top data-consuming applications by UID/package mapping.
- Prepare compatibility validation across Android 11, 12, 13, 14, and 15.

## Project Type

- Native Android proof of concept.
- Kotlin source.
- No backend.
- No cloud SDKs.
- No authentication.
- No external runtime services.

## Open in Android Studio

1. Open the `poc/android-usage-stats-poc` folder in Android Studio.
2. Let Android Studio sync Gradle.
3. Run the `app` configuration on an Android 11 to Android 15 device or emulator.
4. Tap `Open Usage Access Settings`.
5. Enable access for `Jirow Usage PoC`.
6. Return to the app and tap `Refresh Usage Data`.

## Expected Behavior

- The app shows whether Usage Access is granted.
- The app queries `NetworkStatsManager` for mobile and WiFi totals over the last 30 days.
- The app aggregates mobile and WiFi usage by UID and maps UIDs to installed applications through `PackageManager`.
- The top data-consuming apps are shown in descending order.

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

## Test Checklist

- Install debug build.
- Verify Usage Access permission appears in system settings.
- Verify app detects denied permission.
- Grant Usage Access and return to app.
- Verify mobile total loads.
- Verify WiFi total loads.
- Verify at least one app appears in top consumers after device has usage history.
- Revoke Usage Access and verify error state.
- Reboot device and verify app can refresh again after unlock.
- Test on at least one non-Pixel OEM device.
