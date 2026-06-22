# Android Data Usage Technical Feasibility

## Executive Summary

The Jirow Data Insights MVP is technically feasible as an Android-first, on-device analytics product, but the data layer must be designed around Android permission friction, API granularity limits, package visibility rules, and OEM-specific behavior.

The recommended MVP approach is:

- Use `NetworkStatsManager` as the primary source for mobile data and WiFi byte usage.
- Use `UsageStatsManager` and the Usage Access app-op to validate user-granted access and support app activity context.
- Store normalized snapshots locally and aggregate them in the app.
- Treat background refresh as best-effort, with refresh-on-open as the reliable baseline.
- Validate behavior on real Android 11, 12, 13, 14, and 15 devices before committing to forecast accuracy claims.

## Proof-of-Concept Location

The native Android proof-of-concept project is stored at:

`poc/android-usage-stats-poc`

The PoC is intentionally small and diagnostic. It requests Usage Access through system settings, reads total mobile and WiFi usage, aggregates app-level usage by UID, maps UIDs to installed packages, and displays the top data-consuming applications.

## APIs Researched

### UsageStatsManager

`UsageStatsManager` provides access to app usage events and aggregated app usage stats. Most methods require `android.permission.PACKAGE_USAGE_STATS`; declaring the permission is not enough, because the user must grant access in Android Settings through Usage Access.

Relevant behavior for Jirow:

- Available from API level 21.
- `queryUsageStats` returns app usage stats for a time range and interval.
- Returned ranges may expand to whole interval boundaries.
- On Android 11 and above, methods can return `null` if the user is not unlocked.
- The API is useful for validating access and adding app activity context, but it does not itself provide byte-level data consumption.

### NetworkStatsManager

`NetworkStatsManager` provides network usage history and statistics in discrete time buckets.

Relevant behavior for Jirow:

- Available from API level 23.
- `querySummaryForDevice` can return total device usage for mobile or WiFi over a time range.
- `querySummary` can return per-UID network usage summaries for the calling user.
- `queryDetailsForUid` can query usage for a specific UID.
- Queries may take several seconds and must not run on the main thread.
- Bucket granularity is coarse, so the API is not appropriate for real-time or minute-level metering.
- Device-wide summaries and access to other apps' usage require `PACKAGE_USAGE_STATS`.

## Permission Requirements

### Required for MVP Feasibility

| Permission or access | Purpose | Grant behavior | MVP recommendation |
| --- | --- | --- | --- |
| `android.permission.PACKAGE_USAGE_STATS` | Allows use of usage and network stats APIs for device/app usage data | Special app-op; user grants through Settings > Usage Access | Required |
| `Settings.ACTION_USAGE_ACCESS_SETTINGS` | Opens the system settings screen where users grant Usage Access | Intent-based settings flow | Required |
| Package visibility declarations | Allows mapping usage UIDs to app names and package metadata | Android 11+ filters installed app visibility by default | Required in some form |

### PoC-Only Permission

| Permission | Purpose | Risk |
| --- | --- | --- |
| `android.permission.QUERY_ALL_PACKAGES` | Lets the PoC map UIDs to installed apps broadly during feasibility testing | Google Play treats installed app lists as sensitive; production use may need a narrower package visibility strategy or policy approval |

### Not Required for Current PoC

| Permission | Reason |
| --- | --- |
| `READ_PHONE_STATE` | The PoC passes `null` subscriber IDs for Android 11+ mobile usage queries instead of reading subscriber identifiers. |
| `INTERNET` | The MVP and PoC are local-only and do not require network calls. |
| Runtime dangerous permission dialog | `PACKAGE_USAGE_STATS` is not granted through normal runtime permission APIs. |

## API Limitations

### Byte-Level App Usage

`UsageStatsManager` does not provide network byte usage. App-level consumption must come from `NetworkStatsManager`, typically grouped by UID.

### UID-to-App Mapping

Network usage is keyed by UID, not always by a single package. Multiple packages can share a UID. Removed apps, system processes, and shared UIDs may appear as aggregate or unknown entries.

### Package Visibility on Android 11+

When an app targets Android 11 or higher, package visibility is filtered by default. This affects calls such as `getInstalledApplications`. The app must declare appropriate package visibility needs. `QUERY_ALL_PACKAGES` is available but should be treated as a sensitive, policy-gated option for production.

### Network Type Split

Mobile and WiFi can be queried separately using `ConnectivityManager.TYPE_MOBILE` and `ConnectivityManager.TYPE_WIFI`, but OEM behavior and historical bucket availability can vary.

### Subscriber ID Restrictions

Starting with Android 10, subscriber IDs are more tightly restricted. For Android 11 to 15, Jirow should pass `null` subscriber IDs for aggregate mobile and WiFi queries unless a future carrier-privileged flow exists.

### Time Granularity

Network stats buckets are not fine-grained. They are suitable for daily, weekly, and monthly analytics, but not for real-time bandwidth monitoring.

### Threading

Network stats queries can take several seconds and must run off the UI thread.

### User Unlock State

On Android 11 and above, usage stats APIs may return `null` while the user is not unlocked. The app should refresh after unlock and avoid relying on boot-time access for complete analytics.

## Android 11-15 Compatibility Assessment

| Android version | API level | Expected feasibility | Notes |
| --- | ---: | --- | --- |
| Android 11 | 30 | Feasible | Package visibility filtering applies. Usage stats may return `null` before user unlock. |
| Android 12 | 31/32 | Feasible | WiFi subscriber-specific query behavior changed, but Jirow should pass `null` for all WiFi networks. |
| Android 13 | 33 | Feasible | No blocker identified for the core PoC path. Notification permission only matters if MVP adds alerts. |
| Android 14 | 34 | Feasible | No blocker identified for foreground refresh. Background work should remain conservative. |
| Android 15 | 35 | Feasible | PoC targets API 35. Real-device testing remains required before claiming production compatibility. |

## Validation Status

This repository now includes a buildable Android Studio PoC configured for API 30 through API 35. Local compile and device execution were not performed in this workspace because `gradle` and `adb` are not available on the current machine PATH.

Compatibility should therefore be treated as researched and implementation-ready, not yet device-certified. The next engineering step is to run the PoC on physical or emulator devices for Android 11, 12, 13, 14, and 15 and record actual results in the compatibility matrix.

## Compatibility Test Plan

The following tests should be run on Android 11, 12, 13, 14, and 15:

1. Install the PoC debug build.
2. Launch the app with Usage Access denied.
3. Confirm the app detects missing access.
4. Tap the settings button and grant Usage Access.
5. Return to the app and refresh usage data.
6. Confirm total mobile usage is returned.
7. Confirm total WiFi usage is returned.
8. Confirm app-level top consumers are returned after the device has sufficient usage history.
9. Confirm results are non-blocking and UI remains responsive during queries.
10. Revoke Usage Access and confirm the app handles the error gracefully.
11. Reboot the device, unlock it, and refresh again.
12. Repeat on at least one Pixel device and one non-Pixel OEM device.

## OEM Restrictions

OEM Android distributions can affect feasibility in several ways:

- Usage Access settings may be renamed, relocated, or wrapped in additional security prompts.
- Background scheduling may be more aggressive than stock Android.
- Battery managers may stop background jobs or restrict app startup.
- Package visibility, system app labels, and UID mappings may differ.
- Dual-SIM, carrier customization, and vendor network accounting can produce different mobile usage totals.

MVP mitigation:

- Make refresh-on-open the primary data freshness path.
- Use background jobs only as best-effort.
- Keep a visible last-updated timestamp.
- Store the last successful data snapshot locally.
- Maintain an OEM compatibility matrix during beta testing.

## Battery Optimization Concerns

Jirow should avoid frequent polling. Android Doze and App Standby defer background CPU, network, jobs, syncs, and alarms when devices are idle. WorkManager uses JobScheduler internally, so scheduled background refreshes can be delayed during Doze.

Recommended MVP behavior:

- Refresh when the user opens the app.
- Use conservative background refresh intervals, such as every 6 to 12 hours.
- Run NetworkStats queries on a worker thread.
- Avoid foreground services for passive analytics.
- Avoid requesting battery optimization exemptions for MVP.
- Treat local notifications as optional and user-controlled.

## Privacy Implications

Data usage analytics are sensitive because they can reveal installed apps, app activity patterns, connectivity habits, work routines, and entertainment behavior.

Privacy requirements for Jirow:

- Explain Usage Access before sending users to Android settings.
- Keep MVP data on-device.
- Do not send usage records to cloud services.
- Avoid third-party analytics SDKs during MVP.
- Do not store raw package usage longer than needed for local analytics.
- Provide a local data reset option.
- Avoid `QUERY_ALL_PACKAGES` in production unless the app has a clearly justified policy-compliant use case.
- Show app names and usage amounts responsibly; avoid implying content inspection.

## Feasibility Decision

The MVP is feasible with important constraints.

### Green

- Total mobile usage is feasible with `NetworkStatsManager`.
- Total WiFi usage is feasible with `NetworkStatsManager`.
- App-level ranking is feasible by aggregating `NetworkStats` buckets by UID.
- Local-only processing is compatible with the Android API model.
- Android 11-15 support is realistic for foreground refresh.

### Yellow

- Permission onboarding is a product risk because Usage Access requires a settings flow.
- UID-to-app mapping is imperfect and must handle shared, removed, system, and unknown UIDs.
- Package visibility may require a careful production strategy.
- OEM background restrictions may affect data freshness.
- Compatibility claims require real-device validation.

### Red

- Real-time per-app data monitoring is not a good MVP promise.
- Cloud-free MVP cannot recover data across devices or reinstalls unless future backup is added.
- Production use of `QUERY_ALL_PACKAGES` could create Play Store policy risk.

## Recommendation

Proceed with the Android-first MVP, but design the data layer around conservative expectations:

- Use `NetworkStatsManager` for byte usage.
- Use `UsageStatsManager` for access validation and usage context.
- Query on app open and periodically in the background as best-effort.
- Store local snapshots and aggregate daily, weekly, and monthly summaries.
- Run a device validation sprint before implementing forecasting and insight confidence labels.

## Official Sources

- Android `UsageStatsManager`: https://developer.android.com/reference/android/app/usage/UsageStatsManager
- Android `NetworkStatsManager`: https://developer.android.com/reference/android/app/usage/NetworkStatsManager
- Android `Manifest.permission.PACKAGE_USAGE_STATS`: https://developer.android.com/reference/android/Manifest.permission#PACKAGE_USAGE_STATS
- Android package visibility: https://developer.android.com/training/package-visibility
- Android `Build.VERSION_CODES`: https://developer.android.com/reference/android/os/Build.VERSION_CODES
- Android Doze and App Standby: https://developer.android.com/training/monitoring-device-state/doze-standby
