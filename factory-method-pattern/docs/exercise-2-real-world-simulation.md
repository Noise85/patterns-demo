# Exercise 2: Real-World Simulation

## Title
Multi-Channel Notification System

## Learning Objectives

By completing this exercise, you will:

- Apply Factory Method pattern to a real-world problem
- Design extensible notification infrastructure
- Handle channel-specific configuration and validation
- Implement delivery tracking and error handling
- Write production-quality notification code
- Follow Open/Closed Principle for adding new channels

## Scenario

You're building a notification service for a SaaS platform. The system must send notifications through multiple channels: Email, SMS, Push Notifications, and Slack. Each channel has:

- Different delivery mechanisms
- Different validation rules (e.g., email format, phone number format)
- Different configuration requirements
- Different delivery tracking

The business wants to easily add new notification channels in the future without modifying existing code.

## Functional Requirements

### 1. Define Notification Model

Create:
- `NotificationMessage` - Contains recipient, subject, body, priority
- `DeliveryResult` - Contains success status, delivery ID, channel used, error message (if any)

### 2. Implement Notification Interface

Define `Notification` interface with:
- `DeliveryResult send()` - sends the notification
- `String getChannel()` - returns channel name
- `boolean validate()` - validates notification can be sent

### 3. Implement Concrete Notifications

At minimum:

- **EmailNotification**: Validates email format, simulates SMTP delivery
- **SmsNotification**: Validates phone number format, simulates SMS gateway
- **PushNotification**: Validates device token, simulates push service
- **SlackNotification**: Validates webhook URL, simulates Slack API

### 4. Implement Notification Factory (Abstract Creator)

Create `NotificationService` abstract class:
- Abstract factory method: `Notification createNotification(NotificationMessage message)`
- Concrete method: `DeliveryResult sendNotification(NotificationMessage message)` - uses factory method
- Error handling for validation failures

### 5. Implement Concrete Notification Services

- `EmailNotificationService`
- `SmsNotificationService`
- `PushNotificationService`
- `SlackNotificationService`

Each service creates its specific notification type.

### 6. Handle Channel-Specific Logic

- Email: Check valid email format (`@` and `.` present)
- SMS: Check valid phone number (starts with `+` and has 10+ digits)
- Push: Check device token is non-empty
- Slack: Check webhook URL starts with `https://hooks.slack.com`

## Non-Functional Expectations

- **Validation**: All channels must validate input before sending
- **Error Handling**: Return failure results instead of throwing exceptions
- **Extensibility**: Adding a new channel shouldn't require changing existing code
- **Clean Architecture**: Separate concerns - validation, creation, delivery
- **Immutability**: Prefer immutable message objects

## Constraints

- No actual network calls (simulate with mock implementations)
- Factory method must be abstract in base service
- Each notification type must implement validation
- Delivery results must include success/failure status
- Use simple string-based validation (regex optional)

## Starter Code Location

- Models: `src/main/java/com/patterns/factorymethod/simulation/model/`
- Notification interface: `src/main/java/com/patterns/factorymethod/simulation/Notification.java`
- Concrete notifications: `src/main/java/com/patterns/factorymethod/simulation/notification/`
- Service creator: `src/main/java/com/patterns/factorymethod/simulation/NotificationService.java`
- Concrete services: `src/main/java/com/patterns/factorymethod/simulation/service/`

Look for `TODO` comments in the code.

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

The tests verify:
- Each notification type validates correctly
- Each service creates the correct notification type
- Validation failures return error results
- Successful sends return success results with delivery IDs
- Invalid inputs are rejected by validation

## Stretch Goals (Optional)

1. **Retry Logic**: Add retry capability for failed deliveries
2. **Rate Limiting**: Track send count and limit per minute
3. **Priority Queuing**: High-priority notifications sent first
4. **Fallback Channels**: If one channel fails, try another
5. **Delivery Tracking**: Store delivery history
6. **Batch Sending**: Send multiple notifications at once
7. **Template Support**: Use message templates with variables

## Hints

<details>
<summary>Click to reveal hints</summary>

**Architecture**:
- Keep models simple (POJOs or records)
- Notification interface defines the contract
- Each notification implementation handles one channel
- Services are factories for notifications

**Validation**:
- Implement validation in each notification class
- Return `false` from `validate()` if invalid
- Check format in the notification, not the service
- Simple validation is fine (contains "@", starts with "+", etc.)

**Factory Method**:
- `NotificationService.createNotification()` is abstract
- Each concrete service overrides it to create its notification type
- `sendNotification()` is concrete and uses the factory method

**Delivery Simulation**:
- Generate a fake delivery ID (UUID or timestamp-based)
- Return success if validation passes
- Return failure with error message if validation fails
- No real network calls needed

**Error Handling**:
- Don't throw exceptions for business failures
- Use `DeliveryResult` to communicate success/failure
- Include descriptive error messages

</details>

## Real-World Considerations

In a production system, you'd also need:

- **Actual APIs**: Integration with real email/SMS/push providers
- **Authentication**: API keys, OAuth tokens for services
- **Async Processing**: Queue-based delivery for scalability
- **Monitoring**: Track delivery rates, failures, latency
- **Cost Tracking**: Monitor API usage and costs
- **Compliance**: GDPR, CAN-SPAM, data retention policies
- **Testing**: Integration tests with sandbox APIs

For this exercise, focus on the Factory Method pattern and clean architecture.

## What You'll Learn

This exercise teaches:

- How Factory Method enables extensibility
- How to design pluggable notification channels
- How to separate object creation from business logic
- How to validate inputs specific to each product type
- How to structure a real-world notification system

Good luck! 🚀
