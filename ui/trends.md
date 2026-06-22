# Trends UI/UX Wireframe Specification

## Purpose

The Trends screen helps users understand how their internet consumption changes over time. It provides daily, weekly, and monthly views for mobile data, WiFi, and total usage, making it easier to identify spikes, routines, forecast risks, and long-term changes in behavior.

The screen should feel like a modern fintech analytics dashboard: chart-forward, compact, trustworthy, and optimized for practical decision-making rather than visual decoration.

## Layout

### Screen Structure

- Top app bar
- Trend period tabs
- Usage summary panel
- Main chart area
- Network breakdown toggle
- Key observations
- Period comparison section
- Forecast panel
- Bottom navigation

### Wireframe Order

1. **Top App Bar**
   - Title: "Trends"
   - Right action: filter icon
   - Optional date range label

2. **Trend Period Tabs**
   - Daily
   - Weekly
   - Monthly
   - Selected tab controls the chart, summary, and observations

3. **Usage Summary Panel**
   - Total usage for selected trend range
   - Average usage per day/week/month
   - Highest usage period
   - Change versus previous comparable period

4. **Main Chart Area**
   - Daily: vertical bar chart or line chart for the last 30 days
   - Weekly: bar chart by calendar week
   - Monthly: bar or line chart across available months
   - Current period highlighted
   - Highest spike marked with a small label

5. **Network Breakdown Toggle**
   - Total
   - Mobile
   - WiFi
   - Updates chart and summary metrics

6. **Key Observations**
   - Short generated observations from local analytics
   - Examples:
     - "Friday was your highest usage day."
     - "This week is 16% lower than last week."

7. **Period Comparison**
   - Current versus previous period
   - Mobile and WiFi split
   - Difference in MB/GB and percentage

8. **Forecast Panel**
   - Projected month-end or week-end usage
   - Confidence label
   - Target risk if target is set

9. **Bottom Navigation**
   - Dashboard
   - Apps
   - Trends
   - Insights
   - Settings

### Visual Direction

- Use a precise analytics style with clean charts, clear labels, and restrained color.
- Make charts functional before expressive: values, axes, labels, and selected states must be legible.
- Use fintech-style data hierarchy: headline numbers first, trend interpretation second, chart detail third.
- Keep chart containers unframed or lightly framed; avoid nesting cards inside cards.
- Use Material 3 tabs, menus, and bottom sheets for filtering.

## Components

### Top App Bar

- Title: "Trends"
- Filter action:
  - Opens trend filter sheet
- Optional context subtitle:
  - Example: "Last 30 days"

### Trend Period Tabs

- Daily
- Weekly
- Monthly
- Must remain fixed-width or responsive without text overflow
- Selected tab has clear active indicator

### Usage Summary Panel

- Total usage
- Average usage
- Peak period
- Comparison metric
- Uses compact 2x2 metric layout on mobile

### Main Chart

- Chart modes:
  - Bar chart for daily and weekly usage
  - Bar or line chart for monthly usage
- Interactions:
  - Tap a bar or point to select a period
  - Long press for tooltip if supported
  - Horizontal scroll only when needed for dense data
- Tooltip contents:
  - Date or period
  - Total usage
  - Mobile usage
  - WiFi usage
  - Top app for selected period if available

### Network Breakdown Toggle

- Options:
  - Total
  - Mobile
  - WiFi
- Selection changes chart series and summary values.
- Use color plus labels, not color alone.

### Key Observations

- Two to four observation rows.
- Each row includes:
  - Icon
  - Observation title
  - Short explanation
- Should be generated from deterministic analytics rules.

### Period Comparison Section

- Shows current and previous period metrics side by side.
- Includes:
  - Current period usage
  - Previous period usage
  - Absolute difference
  - Percentage change
  - Mobile/WiFi split

### Forecast Panel

- Shows projected end-of-period usage.
- Displays target risk if configured.
- Shows confidence level.
- Opens Insights screen or future forecast detail when tapped.

### Trend Filter Sheet

- Date range
- Network type
- App filter
- Include/exclude system apps
- Reset action
- Apply action

## Navigation

### Entry Points

- Bottom navigation Trends tab
- Dashboard forecast card
- Dashboard trend indicator
- App Usage detail "View trend"
- Insight related to weekly or monthly trend

### Exit Points

- Dashboard tab opens Dashboard
- Apps tab opens App Usage screen
- Insights tab opens Insights screen
- Selecting an app filter can route back to App Usage for app-level details
- Forecast panel can route to Insights or future forecast detail

### Back Behavior

- If filter sheet is open, Android back closes it.
- If a chart tooltip or selected period detail is open, Android back clears the selection.
- Otherwise back follows bottom navigation behavior.

## User Interactions

- Switch between Daily, Weekly, and Monthly tabs.
- Toggle chart data between Total, Mobile, and WiFi.
- Tap chart bars or points to inspect a specific period.
- Open filter sheet to narrow by app, network, or date range.
- Tap a key observation to open a related insight where available.
- Tap forecast panel to see more forecast explanation.
- Pull to refresh latest local usage data.
- Scroll vertically to move from chart to comparison details.

## Data Displayed

### Daily View

- Last 30 days of usage
- Total, mobile, and WiFi usage per day
- Highest usage day
- Today versus yesterday
- Today versus recent daily average
- Top app for selected day where available

### Weekly View

- Usage grouped by calendar week
- Current week usage
- Previous week usage
- Week-over-week change
- Average daily usage for selected week
- Highest usage day in selected week

### Monthly View

- Usage grouped by month
- Current month-to-date usage
- Previous month usage
- Monthly average usage
- Projected month-end usage
- Target progress if configured

### Shared Data

- Network type split
- Selected period label
- Last updated timestamp
- Forecast confidence
- Usage trend status

## Empty States

### No Permission

- Message: "Enable usage access to see your data trends."
- Primary action: "Enable usage access"
- Include local-only privacy note.

### Not Enough History

- Show available current usage.
- Disable or soften unavailable trend comparisons.
- Message: "Trends become clearer after a few days of usage."
- Forecast confidence should be low.

### No Data for Selected Filter

- Message: "No usage data found for this selection."
- Actions:
  - "Reset filters"
  - "Try another period"

### No Monthly Target

- Monthly view remains usable.
- Forecast panel prompts:
  - "Set a monthly target to track usage risk."

## Error States

### Chart Data Failed to Load

- Show chart placeholder with message:
  - "Could not load trend data."
- Provide "Try again" action.
- Keep summary metrics visible if available.

### Partial Network Data

- Display total usage if available.
- Show a small notice:
  - "Mobile and WiFi split may be limited on this device."

### Permission Revoked

- Display stale trend data with warning banner.
- Message: "Usage access is off. Trends will not update."
- Action: "Restore access"

### Forecast Error

- Hide target risk state.
- Show neutral message:
  - "Forecast unavailable for this period."
- Do not block the chart.

## Dark Mode Considerations

- Charts must maintain contrast against dark surfaces.
- Use muted gridlines and strong data series colors.
- Tooltips should use dark elevated surfaces with readable labels.
- Active tab indicators should be clearly visible.
- Red and amber warning states should be toned down to avoid glare.
- Chart labels should not disappear at lower contrast settings.
- Use the same semantic color mapping as light mode.

## Future Enhancements

- Custom date range selection.
- Compare two months or two weeks directly.
- App-specific trend overlays.
- Category-level trends.
- Cost trend when bundle cost is configured.
- Forecast model explanations.
- Export chart images or reports.
- Local anomaly timeline.
- Cross-utility trend comparison when Jirow Utility Intelligence modules are available.
