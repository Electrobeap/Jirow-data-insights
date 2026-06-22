# Insights UI/UX Wireframe Specification

## Purpose

The Insights screen converts usage analytics into plain-language guidance. It helps users understand what changed, why it matters, and what action they can take to reduce waste, protect a data bundle, or plan future internet usage.

The screen should feel like a trusted financial advisory feed: concise, prioritized, actionable, and calm. It should avoid technical jargon and make local-only privacy obvious without overexplaining.

## Layout

### Screen Structure

- Top app bar
- Insight summary header
- Insight category filters
- Priority insight card
- Insight feed
- Dismissed insights access
- Bottom navigation

### Wireframe Order

1. **Top App Bar**
   - Title: "Insights"
   - Right action: filter icon or settings icon
   - Optional subtitle: "Generated on device"

2. **Insight Summary Header**
   - Short status statement:
     - "3 insights need your attention"
     - "Your usage is on track"
   - Local privacy note:
     - "Processed on this device"
   - Optional refresh timestamp

3. **Category Filters**
   - All
   - Warnings
   - Forecasts
   - Apps
   - Savings
   - Use chips or segmented controls depending on density

4. **Priority Insight Card**
   - Highest-ranked active insight
   - Larger treatment than feed items
   - Includes recommended action

5. **Insight Feed**
   - Scrollable list of insight cards
   - Grouped by recency or importance
   - Each card includes cause, impact, and action

6. **Dismissed Insights Access**
   - Small row near bottom:
     - "View dismissed insights"
   - Hidden if no dismissed insights exist

7. **Bottom Navigation**
   - Dashboard
   - Apps
   - Trends
   - Insights
   - Settings

### Visual Direction

- Use a premium advisory feel rather than a notification inbox.
- Prioritize whitespace, clear typography, and strong action hierarchy.
- Use semantic color sparingly:
  - Green for positive optimization
  - Amber for caution
  - Red for urgent target risk
  - Blue for neutral information
- Use 8dp card radius, subtle borders, and low elevation.
- Avoid alarm-heavy visuals unless a target is clearly at risk.

## Components

### Top App Bar

- Title: "Insights"
- Filter action:
  - Opens insight filter sheet
- Settings action if notification preferences are placed here
- Subtitle or metadata:
  - "On-device analysis"

### Insight Summary Header

- Main statement:
  - Examples:
    - "You are on track this month"
    - "2 apps are driving most of your usage"
    - "Your data target may be exceeded"
- Supporting text:
  - One concise explanation
- Optional status icon

### Category Filters

- Filter chips:
  - All
  - Warnings
  - Forecasts
  - Apps
  - Savings
- Active chip uses primary accent fill.
- Chips must not wrap awkwardly; allow horizontal scrolling if necessary.

### Priority Insight Card

- Severity indicator
- Insight title
- Plain-language body
- Evidence line:
  - Example: "Based on your last 7 days"
- Recommended action:
  - Example: "Use WiFi for video streaming today"
- Related metric:
  - Example: "Projected: 11.6 GB"
- Actions:
  - View details
  - Dismiss

### Insight Feed Card

- Icon
- Category label
- Title
- Body
- Related metric
- Timestamp or generated date
- Optional related app
- Actions:
  - View related data
  - Dismiss

### Insight Detail View or Bottom Sheet

- Opens from any insight card.
- Displays:
  - Full insight title
  - Explanation
  - Supporting metrics
  - Recommended action
  - Related screen shortcut
  - Dismiss action
- Should not include raw technical details unless an advanced diagnostics mode exists.

### Filter Bottom Sheet

- Category
- Severity
- Date generated
- Include dismissed insights
- Reset filters
- Apply filters

## Navigation

### Entry Points

- Bottom navigation Insights tab
- Dashboard insights preview
- Dashboard forecast warning
- Trends key observation
- App Usage app-specific insight
- Local notification tap if notifications are included

### Exit Points

- "View app usage" opens App Usage screen filtered to the related app.
- "View trend" opens Trends screen with relevant period selected.
- "Set target" opens Settings target configuration.
- Dashboard tab returns to Dashboard.
- Apps tab opens App Usage.
- Trends tab opens Trends.

### Back Behavior

- If detail bottom sheet is open, Android back closes it.
- If filters are active and search/filter sheet is open, Android back closes the sheet.
- Otherwise back follows bottom navigation behavior.

## User Interactions

- Filter insights by category.
- Filter insights by severity.
- Tap an insight to view details.
- Dismiss an insight.
- Undo dismiss from snackbar if supported.
- Tap related action to move to Dashboard, App Usage, Trends, or Settings.
- Pull to refresh local insights.
- View dismissed insights.
- Toggle insight notifications if that setting is exposed here.

## Data Displayed

### Insight Data

- Insight type
- Severity
- Title
- Body
- Recommendation
- Related app, if applicable
- Related period
- Related network type
- Supporting metric
- Generated timestamp
- Dismissed state

### Supported Insight Types

- High usage app insight
- Unusual daily spike insight
- Month-end forecast insight
- Target risk insight
- WiFi versus mobile data behavior insight
- Weekly trend insight
- Data-saving recommendation
- Positive usage improvement insight

### Example Insight Formats

- "TikTok used 42% of your mobile data this week."
- "You used 38% more mobile data today than your recent daily average."
- "At this pace, you may pass your 10 GB target by June 24."
- "Your WiFi usage increased this week, reducing pressure on mobile data."

### Formatting Rules

- Use app names, not package names.
- Explain why the insight appears.
- Prefer short, action-oriented text.
- Avoid technical terms like UID, NetworkStatsManager, or package identifier.
- Use dates only when helpful.

## Empty States

### No Permission

- Message: "Enable usage access to get personalized insights."
- Primary action: "Enable usage access"
- Privacy note: "Insights are generated on your device."

### No Insights Yet

- Message: "Insights will appear as Jirow learns your usage pattern."
- Supporting text:
  - "Use the app for a few days to unlock better recommendations."
- Optional action: "Refresh usage"

### All Insights Dismissed

- Message: "You are all caught up."
- Action: "View dismissed insights"

### Filter No Results

- Message: "No insights match these filters."
- Action: "Reset filters"

## Error States

### Insight Generation Failed

- Show non-blocking error state:
  - "Could not update insights."
- Keep previous insights visible.
- Action: "Try again"

### Supporting Data Missing

- Show generic insight if precise supporting metric is unavailable.
- Avoid showing unsupported calculations.
- Example fallback:
  - "Usage changed recently, but detailed comparison is unavailable."

### Permission Revoked

- Show warning banner:
  - "Usage access is off. Insights will not update."
- Keep older insights visible with stale timestamp.
- Action: "Restore access"

### Forecast Unavailable

- Hide forecast-related insights.
- Show empty forecast category state:
  - "Forecast insights need more usage history."

## Dark Mode Considerations

- Insight cards should use dark elevated surfaces with enough separation from the background.
- Severity colors should be accessible and not overly saturated.
- Priority insight card should not rely on bright fill backgrounds.
- Chips need clear selected and unselected contrast.
- Text hierarchy should remain clear for title, explanation, metric, and action.
- Use icons and labels together for severity.

## Future Enhancements

- Personalized insight preferences.
- Local notification scheduling for important insights.
- Saved insights.
- Weekly insight digest.
- Action tracking, such as whether usage improved after a recommendation.
- Cost-saving estimates when bundle pricing is configured.
- Smart category recommendations.
- Consent-based anonymized benchmarking in a future cloud phase.
- Cross-utility insights when electricity, solar, fuel, and utility spending modules are available.
