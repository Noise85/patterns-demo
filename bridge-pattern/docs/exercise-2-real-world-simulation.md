# Exercise 2: Real-World Simulation

## Title
Multi-Channel Notification System

## Learning Objectives

- Apply Bridge pattern to real messaging infrastructure
- Decouple message types from delivery channels
- Design flexible notification system
- Handle platform-specific formatting
- Implement channel-agnostic message abstraction

## Scenario

Your application sends various notification types (Alerts, Reminders, Promotions) through multiple channels (Email, SMS, Push Notifications). Each channel has different APIs and formatting requirements. Use the Bridge pattern to keep message logic separate from delivery channels.

## Functional Requirements

### 1. Implementor Interface (`MessageChannel`)

```java
void send(String recipient, String subject, String body, NotificationPriority priority);
```

### 2. Concrete Implementors

**EmailChannel**:
- Formats as HTML email
- Includes subject line
- Priority affects email headers

**SmsChannel**:
- Truncates to 160 characters
- No subject (concatenates subject + body)
- Priority affects delivery speed

**PushChannel**:
- Formats as push notification
- Subject becomes title
- Priority affects notification badge/sound

### 3. Abstraction (`Notification`)

Abstract class with:
- `MessageChannel channel`
- `String recipient`
- Constructor accepting both
- `abstract void send()` method

### 4. Refined Abstractions

**AlertNotification**:
- High priority
- Urgent formatting
- Includes "[ALERT]" prefix

**ReminderNotification**:
- Normal priority
- Includes reminder time
- Polite tone

## Non-Functional Expectations

- Messages and channels evolve independently
- Adding new channel doesn't change message classes
- Adding new message type works with all channels

## Constraints

- Use composition (Notification has-a MessageChannel)
- NotificationPriority enum: HIGH, NORMAL, LOW

## Starter Code Location

`src/main/java/com/patterns/bridge/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add SlackChannel and DiscordChannel
2. Implement message templating
3. Add delivery confirmation tracking
4. Implement rate limiting per channel

## Hints

<details>
<summary>Click to reveal hints</summary>

- Notification constructor stores channel and recipient
- AlertNotification.send() prepares urgent message, calls channel.send()
- EmailChannel.send() formats as HTML with headers
- SmsChannel truncates long messages
- PushChannel formats for mobile display
</details>
