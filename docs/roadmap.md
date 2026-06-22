# Jirow Data Insights Roadmap

## Roadmap Overview

Jirow Data Insights will evolve from an Android-first, on-device connectivity analytics MVP into the connectivity module of the broader Jirow Utility Intelligence Platform. The roadmap prioritizes product clarity, reliable Android data collection, privacy-preserving local analytics, user validation, and later cloud-enabled ecosystem expansion.

## Phase 1 - Product Definition

**Estimated Timeline:** June 2026

### Objectives

- Define the product vision, user value proposition, and MVP boundaries.
- Clarify the primary target users and priority use cases.
- Document the Android-first technical approach.
- Validate that the MVP can operate without backend infrastructure.
- Establish a foundation for later Jirow Utility Intelligence integration.

### Deliverables

- Product vision document.
- Product requirements document.
- Technical architecture document.
- Product roadmap.
- Initial feature scope and excluded feature list.
- Initial assumptions for Android data collection validation.

### Success Criteria

- Product vision clearly positions Jirow Data Insights as a connectivity analytics module.
- MVP scope is constrained to Android, Flutter, local storage, and on-device analytics.
- Core features are defined: dashboard, app consumption analytics, daily trends, weekly trends, monthly trends, forecasting, and insights.
- Architecture direction is clear enough to begin implementation.
- Excluded features are explicitly documented to prevent MVP scope creep.

### Dependencies

- Agreement on MVP scope and target users.
- Access to Android devices for feasibility testing.
- Confirmation of preferred Flutter state management and storage approach.
- Alignment with the broader Jirow Utility Intelligence strategy.

## Phase 2 - MVP Development

**Estimated Timeline:** July 2026 to August 2026

### Objectives

- Build the Android-first Flutter MVP.
- Implement reliable local data collection for mobile data and WiFi usage.
- Display app-level, daily, weekly, and monthly consumption analytics.
- Generate deterministic local forecasts and rule-based insights.
- Ensure all MVP functionality works offline without accounts, subscriptions, or cloud services.

### Deliverables

- Flutter Android application shell.
- Android native data collection layer using UsageStatsManager and NetworkStatsManager.
- Local storage implementation using Hive or SQLite.
- Dashboard screen.
- App consumption analytics screen.
- Daily, weekly, and monthly trend screens.
- Forecasting engine.
- Rules-based insight engine.
- Permission onboarding flow.
- Settings for data target, renewal day, units, and local data reset.
- Internal test build.

### Success Criteria

- Users can grant required Android access and view data usage in the app.
- Mobile data and WiFi usage are displayed separately where Android APIs support it.
- Top consuming apps are visible for selected periods.
- Daily, weekly, and monthly analytics render correctly from local data.
- Forecasts are generated locally with confidence labels.
- Insights are generated locally in user-friendly language.
- App remains usable offline.
- No backend, authentication, cloud service, payment, or subscription dependency exists in the MVP.

### Dependencies

- Android API behavior validation across target Android versions.
- Flutter project setup and package decisions.
- Native Kotlin bridge between Android APIs and Flutter.
- Local persistence schema.
- Charting library integration.
- Test devices covering common OEM and Android version differences.

## Phase 3 - User Validation

**Estimated Timeline:** September 2026

### Objectives

- Validate that target users understand and trust the product.
- Measure whether dashboards, forecasts, and insights help users manage data consumption.
- Identify gaps in Android data reliability, onboarding, and explanations.
- Prioritize improvements before cloud or ecosystem expansion.

### Deliverables

- Closed beta or pilot build.
- Feedback plan for students, remote workers, content creators, and small business owners.
- Usability test notes.
- Forecast accuracy review.
- Insight usefulness review.
- Permission onboarding improvement backlog.
- MVP iteration plan.

### Success Criteria

- At least 20 representative users complete onboarding and view the dashboard.
- Users can identify their top consuming apps without assistance.
- Users understand the difference between mobile data and WiFi usage.
- Forecasting is perceived as useful for bundle planning.
- Insight text is understandable to non-technical users.
- Day 7 retention and weekly return behavior show meaningful early interest.
- High-priority reliability issues are documented and triaged.

### Dependencies

- Stable MVP build.
- Test user recruitment.
- Feedback collection process.
- Defined privacy messaging for pilot users.
- Basic support channel for pilot issues.

## Phase 4 - Cloud Integration

**Estimated Timeline:** October 2026 to November 2026

### Objectives

- Introduce optional cloud capabilities only after the local MVP has been validated.
- Support account-based backup and synchronization where user value is proven.
- Preserve user trust through explicit consent and privacy controls.
- Prepare the product for multi-device and ecosystem integration.

### Deliverables

- Cloud architecture proposal.
- Optional account model.
- Authentication design using AWS Cognito or selected identity provider.
- Backend API design using AWS Lambda and API Gateway or equivalent services.
- Cloud data model using DynamoDB or selected managed database.
- Optional encrypted cloud backup.
- Sync conflict handling strategy.
- Updated privacy policy and consent flows.

### Success Criteria

- Cloud features are optional and do not break local-first usage.
- Users can choose whether to create an account.
- Local analytics continue to work without internet access.
- Backup and sync preserve data integrity across app reinstalls or devices.
- Privacy controls clearly explain what leaves the device.
- Cloud operating costs are understood before public rollout.

### Dependencies

- MVP validation results showing need for backup, sync, or account features.
- Cloud provider decision.
- Security and privacy review.
- Backend engineering capacity.
- Authentication and data retention policies.
- Updated product requirements for cloud-enabled features.

## Phase 5 - Utility Intelligence Platform

**Estimated Timeline:** December 2026 onward

### Objectives

- Integrate Jirow Data Insights into the broader Jirow Utility Intelligence Platform.
- Expand from internet consumption analytics into multi-utility intelligence.
- Support consumer and business insights across internet, electricity, solar, fuel, and other critical resources.
- Enable future partnerships, research opportunities, and enterprise reporting with explicit consent.

### Deliverables

- Jirow Utility Intelligence integration plan.
- Shared utility data model.
- Cross-module dashboard concepts.
- Connectivity, electricity, solar, fuel, and utility spending modules.
- Optional aggregated analytics layer with consent controls.
- SME and business reporting concepts.
- Telecom and utility partnership exploration.
- Platform-level success metrics.

### Success Criteria

- Jirow Data Insights functions as the connectivity analytics module within the broader platform.
- Users can understand internet consumption alongside other utility consumption signals.
- Platform architecture supports future modules without compromising privacy or MVP learnings.
- Consent-based aggregation is clearly separated from personal on-device analytics.
- Business and partner opportunities are evaluated against user value and trust.

### Dependencies

- Validated Jirow Data Insights MVP.
- Cloud integration readiness if cross-device or ecosystem features are required.
- Product strategy for additional utility modules.
- Shared design system and platform navigation model.
- Privacy, consent, and data governance framework.
- Partnership and research strategy.

