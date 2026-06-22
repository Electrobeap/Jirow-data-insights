# App Usage UI/UX Wireframe Specification

## Purpose

The App Usage screen helps users identify which applications consume the most internet data. It supports app-level comparison across mobile data, WiFi, and total usage, making it easy for users to spot heavy consumers, background-heavy apps, and usage patterns that affect data bundle depletion.

The screen should feel like a fintech transaction list adapted for internet consumption: searchable, sortable, dense enough for repeated use, and calm enough for non-technical users.

## Layout

### Screen Structure

- Top app bar
- Search and filter area
- Summary strip
- Sort control
- App usage list
- Optional app detail bottom sheet
- Bottom navigation

### Wireframe Order

1. **Top App Bar**
   - Title: "App Usage"
   - Right actions: search icon, filter icon
   - Optional last updated text below title

2. **Search and Filter Area**
   - Search field for app name
   - Time range segmented control:
     - Today
     - 7 days
     - Month
   - Network filter:
     - All
     - Mobile
     - WiFi

3. **Summary Strip**
   - Total apps using data
   - Total usage for selected period
   - Highest consuming app
   - Optional mobile/WiFi split

4. **Sort Control**
   - Default sort: highest total usage
   - Sort options:
     - Total usage
     - Mobile data
     - WiFi
     - App name
   - Display as compact menu, not full-width tabs

5. **App Usage List**
   - Scrollable list of app rows
   - Each row shows app identity, usage amount, usage split, and percentage share

6. **Bottom Navigation**
   - Dashboard
   - Apps
   - Trends
   - Insights
   - Settings

### Visual Direction

- Use a compact financial-ledger feel with clear app rows and strong numeric alignment.
- Keep filters restrained and functional, using Material 3 segmented controls and menus.
- Use app icons as visual anchors; avoid decorative illustrations in the main list.
- Use light cards or row groups with 8dp radius and subtle borders.
- Usage bars should be slim and quiet, with stronger emphasis on the usage number.

## Components

### Top App Bar

- Title: "App Usage"
- Search icon:
  - Expands or focuses search field
- Filter icon:
  - Opens filter bottom sheet
- Refresh indicator:
  - Optional inline timestamp

### Search Field

- Placeholder: "Search apps"
- Leading search icon
- Clear icon when text is entered
- Should preserve active filters while searching

### Time Range Selector

- Options:
  - Today
  - 7 days
  - Month
- Default: Month
- Selected option updates summary strip and app list

### Network Filter

- Options:
  - All
  - Mobile
  - WiFi
- Can appear as chips or inside filter sheet depending on available width
- Active filter must be visible without opening the filter sheet

### Summary Strip

- Compact horizontal metrics:
  - Total usage
  - Apps active
  - Top app
- On small screens, show as a vertically stacked card with two columns.

### App Usage Row

- App icon
- App name
- Optional package name as secondary metadata only in detail view
- Total usage
- Mobile data amount
- WiFi amount
- Share of selected-period usage
- Horizontal usage bar
- Tap target covering full row

### App Detail Bottom Sheet

- Opens when a user taps an app row.
- Displays:
  - App name and icon
  - Selected period usage
  - Mobile/WiFi split
  - Daily mini-trend
  - Usage share
  - Simple insight, if available
- Primary action: "View trend"
- Secondary action: "Close"

### Filter Bottom Sheet

- Time range
- Network type
- Include or exclude system apps
- Sort option
- Reset filters action
- Apply action

## Navigation

### Entry Points

- Bottom navigation Apps tab
- Dashboard "View all" from Top Apps preview
- Dashboard top app row
- Insight related to a specific app

### Exit Points

- Dashboard tab returns to Dashboard
- Trends tab opens Trends screen
- Insights tab opens Insights screen
- App row opens app detail bottom sheet
- "View trend" from app detail opens Trends screen filtered to that app if supported

### Back Behavior

- If search is active, Android back clears search before leaving the screen.
- If filter sheet or app detail sheet is open, Android back closes the sheet.
- Otherwise Android back returns to Dashboard if App Usage was opened from Dashboard, or follows bottom navigation behavior.

## User Interactions

- Search by app name.
- Change time range.
- Filter by mobile data or WiFi.
- Sort by total usage, mobile data, WiFi, or app name.
- Toggle system apps if supported.
- Tap app row to open detail sheet.
- Tap "View trend" to inspect historical usage for a selected app.
- Pull to refresh usage data.
- Long press an app row to reveal advanced details in a future version.

## Data Displayed

### List Data

- App name
- App icon
- Total usage for selected period
- Mobile data usage
- WiFi usage
- Percentage share of total period usage
- Relative usage bar
- Active sort and filter labels

### Summary Data

- Total usage across visible apps
- Number of apps with usage
- Highest consuming app
- Mobile/WiFi split for selected range
- Last updated timestamp

### Detail Data

- Package name
- UID if needed for diagnostics, hidden from normal users
- Daily usage trend
- Highest usage day for the app
- App-specific insight
- Comparison with previous period

### Formatting Rules

- Align usage numbers consistently on the right side of rows.
- Use MB/GB formatting consistent with Dashboard.
- Use app names instead of package names in primary UI.
- Keep package names in secondary or debug-facing areas only.

## Empty States

### No Permission

- Show setup state instead of app list.
- Message: "Enable usage access to see which apps use your data."
- Primary action: "Enable usage access"
- Include privacy note: "Usage data stays on your device."

### No Apps With Usage

- Show a simple empty list state.
- Message: "No app usage found for this period."
- Suggest changing the time range.

### Search No Results

- Message: "No apps match your search."
- Action: "Clear search"

### Filter No Results

- Message: "No apps match these filters."
- Action: "Reset filters"

### Icons Unavailable

- Use a neutral app placeholder icon.
- Do not block the row from appearing.

## Error States

### App Metadata Error

- If app name or icon cannot be resolved, show:
  - App name fallback: "Unknown app"
  - Secondary label: package name if available
- Continue showing usage value.

### Data Collection Failed

- Show banner:
  - "Could not refresh app usage. Showing last available data."
- Keep list visible if cached data exists.
- Provide "Try again" action.

### Network Split Unavailable

- Show total usage.
- Replace mobile/WiFi split with:
  - "Network split unavailable on this device."
- Avoid presenting guessed values.

### Permission Revoked

- Show warning banner.
- Continue showing cached data with stale timestamp.
- Action: "Restore access"

## Dark Mode Considerations

- Use dark neutral surfaces with clear row separation.
- Avoid relying on subtle gray alone for dividers.
- App icons should sit on consistent icon backgrounds if native icons have poor contrast.
- Usage bars should use accessible color contrast against dark surfaces.
- Active filters should remain easy to identify.
- Error and warning banners should be readable without becoming visually aggressive.

## Future Enhancements

- App category grouping, such as social, video, work, messaging, and system.
- Background versus foreground usage if reliable API support is confirmed.
- User-defined app labels, such as Work, Study, Business, or Entertainment.
- App-specific target limits.
- Data-saving recommendations per app.
- Export app usage report.
- Compare two selected apps.
- Detect newly heavy apps after app updates.
- Optional telecom plan optimization recommendations in a later non-MVP phase.
