# Dashboard UI/UX Wireframe Specification

## Purpose

The Dashboard is the primary home screen for Jirow Data Insights. It gives users an immediate understanding of current internet consumption, the split between mobile data and WiFi, top consuming apps, forecast risk, and the most important insight to act on today.

The screen should feel like a modern fintech account overview: calm, trustworthy, data-rich, and easy to scan. The visual language should be inspired by the clarity of Moniepoint, Carbon, Risevest, and Google Material 3 without copying any specific brand.

## Layout

### Screen Structure

- Top app bar
- Usage summary panel
- Period selector
- Mobile data and WiFi breakdown
- Forecast and target status
- Top consuming apps preview
- Priority insights preview
- Bottom navigation

### Wireframe Order

1. **Top App Bar**
   - Left: app name or "Data Insights"
   - Center/left-aligned title depending on final app convention
   - Right: refresh icon and settings icon
   - Secondary row: last updated timestamp

2. **Primary Usage Summary**
   - Large headline metric: total usage for selected period
   - Supporting label: "Used today", "Used this week", or "Used this month"
   - Small comparison indicator: "12% higher than usual"
   - Compact target progress if a monthly target is configured

3. **Period Selector**
   - Segmented control with:
     - Today
     - Week
     - Month
   - Selected segment uses strong contrast and subtle fill

4. **Network Breakdown**
   - Two side-by-side cards:
     - Mobile data
     - WiFi
   - Each card displays usage amount, percentage of total, and small trend indicator

5. **Forecast Card**
   - Shows projected usage outcome for the active period
   - Includes confidence label
   - Displays warning state if projected usage exceeds target

6. **Top Apps Preview**
   - List of top 5 consuming apps
   - App icon, app name, usage amount, and horizontal usage bar
   - "View all" action opens App Usage screen

7. **Priority Insights Preview**
   - One to three insight rows
   - Severity icon, short title, one-line explanation
   - "View insights" action opens Insights screen

8. **Bottom Navigation**
   - Dashboard
   - Apps
   - Trends
   - Insights
   - Settings

### Visual Direction

- Use a neutral fintech base: off-white or light gray background, white surfaces, dark text, and restrained accent colors.
- Use one primary brand accent for positive and active states, preferably a Jirow green or teal.
- Use amber for caution, red for critical usage warnings, and blue for informational states.
- Cards should be compact and purposeful, with radius no larger than 8dp.
- Numeric values should have strong hierarchy and tabular alignment where possible.
- Avoid decorative gradients, oversized illustrations, and marketing-style hero sections.

## Components

### Top App Bar

- Title: "Dashboard" or "Data Insights"
- Refresh action:
  - Icon-only button
  - Tooltip or accessibility label: "Refresh usage"
- Settings action:
  - Icon-only button
  - Opens Settings screen
- Last updated text:
  - Example: "Updated 8:42 AM"
  - Muted text style

### Usage Summary Panel

- Total usage value:
  - Example: "2.84 GB"
  - Largest text on screen
- Period label:
  - Example: "Used today"
- Comparison chip:
  - Examples:
    - "Normal"
    - "18% higher"
    - "Lower than usual"
- Target progress:
  - Linear progress bar for monthly target
  - Only visible when target is configured

### Period Selector

- Three-option segmented control
- Sticky only if testing shows value while scrolling
- Must not resize when labels change

### Mobile Data Card

- Label: "Mobile data"
- Usage value
- Percentage of selected period total
- Trend indicator
- Optional target risk mini-label

### WiFi Card

- Label: "WiFi"
- Usage value
- Percentage of selected period total
- Trend indicator
- Optional note if WiFi usage reduced mobile pressure

### Forecast Card

- Title: "Forecast"
- Forecast message:
  - Example: "At this pace, you may use 11.6 GB this month."
- Confidence label:
  - Low
  - Medium
  - High
- Target status:
  - On track
  - Watch usage
  - Target likely exceeded

### Top Apps Preview

- App row component:
  - App icon
  - App name
  - Network usage amount
  - Usage share bar
  - Optional mobile/WiFi split marker
- Max visible rows: 5
- "View all" action at section header or footer

### Insights Preview

- Insight row component:
  - Severity icon
  - Insight title
  - Short body
  - Optional dismiss action hidden behind overflow menu
- Severity styles:
  - Info: blue
  - Positive: green
  - Warning: amber
  - Critical: red

## Navigation

### Entry Points

- App launch after successful onboarding
- Bottom navigation Dashboard tab
- Return destination after permission setup

### Exit Points

- Apps tab opens App Usage screen
- Trends tab opens Trends screen
- Insights tab opens Insights screen
- Settings icon opens Settings screen
- Top app row opens app detail screen in a future flow
- Forecast card opens forecast detail view if implemented

### Back Behavior

- Dashboard is the root screen.
- Android back from Dashboard exits the app or returns to the previous Android task state.
- Returning from child screens preserves selected dashboard period.

## User Interactions

- Tap period selector to update all dashboard metrics.
- Pull to refresh or tap refresh icon to fetch latest local usage data.
- Tap "View all" in Top Apps to open App Usage screen.
- Tap an app row to open app-level detail if available.
- Tap forecast card to see more forecast context if the forecast detail view is included.
- Tap an insight to open full insight detail.
- Tap dismiss on an insight to remove it from the preview.
- Tap target prompt to configure monthly target if one is missing.

## Data Displayed

### Primary Data

- Total usage for selected period
- Mobile data usage
- WiFi usage
- Combined usage
- Top 5 apps by total usage
- Period-over-period comparison
- Forecast projection
- Forecast confidence
- Current target progress
- Priority insights
- Last updated timestamp

### Derived Data

- Percentage split between mobile data and WiFi
- Usage trend status
- Target risk status
- Difference from recent average
- Projected month-end usage

### Formatting Rules

- Use MB for values below 1 GB.
- Use GB for values at or above 1 GB.
- Use one decimal place for GB unless precision is important.
- Use compact percentages, such as "24%".
- Use plain-language labels rather than technical API terms.

## Empty States

### No Permission

- Show a permission education panel.
- Primary action: "Enable usage access"
- Secondary action: "Learn why this is needed"
- Explain that data remains on the device.

### No Usage Data Yet

- Show a calm empty state after permission is granted but before data is collected.
- Message: "Your usage summary will appear after the first refresh."
- Primary action: "Refresh now"

### No Monthly Target

- Show dashboard normally.
- Add a small prompt in the forecast card:
  - "Set a monthly target for better forecasts."
- Action: "Set target"

### No Insights Yet

- Replace insights preview with:
  - "Insights will appear as Jirow learns your usage pattern."
- Avoid making this feel like an error.

## Error States

### Permission Revoked

- Display a persistent warning banner near the top.
- Message: "Usage access is off. Turn it on to update your dashboard."
- Action: "Open settings"
- Keep previously collected data visible with stale-data styling.

### Data Collection Failed

- Show non-blocking error banner.
- Message: "Could not refresh usage. Showing last available data."
- Action: "Try again"
- Keep last known data visible.

### Android API Limitation

- If app-level or network split data is unavailable, show:
  - "Some usage details are unavailable on this device."
- Use partial data rather than blocking the entire dashboard.

### Forecast Unavailable

- Show forecast placeholder:
  - "Forecast needs more usage history."
- Include minimum history hint where useful.

## Dark Mode Considerations

- Use a near-black or deep charcoal background, not pure black.
- Keep surfaces slightly lighter than the app background.
- Maintain strong contrast for large numbers and section headings.
- Avoid neon accent colors; use softened green, blue, amber, and red.
- Progress bars must remain visible in low brightness.
- Chart and usage bars should use color plus labels to avoid color-only meaning.
- Dividers should be subtle but visible.
- Empty and error states should keep the same hierarchy as light mode.

## Future Enhancements

- Add spending efficiency once users can enter bundle cost.
- Add bundle depletion countdown.
- Add local notifications for target risk.
- Add quick filters for work, social, streaming, and system usage categories.
- Add forecast detail view with confidence explanation.
- Add smart recommendations for moving heavy usage to WiFi.
- Add weekly summary card.
- Add local report export.
- Add ecosystem entry points for electricity and utility spending modules when the broader Jirow Utility Intelligence Platform is ready.
