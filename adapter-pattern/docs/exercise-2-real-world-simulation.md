# Exercise 2: Real-World Simulation

## Title
Multi-Vendor Payment Gateway Adapter

## Learning Objectives

- Adapt multiple third-party APIs to unified interface
- Handle different authentication mechanisms
- Translate domain models between systems
- Implement error handling in adapters
- Design production-grade adapter layer

## Scenario

Your e-commerce platform needs to support multiple payment processors: StripeGateway and PayPalGateway (third-party SDKs). Each has different APIs. You need adapters to present a unified `PaymentProcessor` interface to your application.

## Functional Requirements

### 1. Target Interface (`PaymentProcessor`)

```java
PaymentResult charge(PaymentRequest request);
PaymentResult refund(String transactionId, double amount);
boolean isAvailable();
```

### 2. Domain Models

- `PaymentRequest`: amount, currency, cardToken, customerEmail
- `PaymentResult`: success, transactionId, message

### 3. Third-Party Adaptees

**StripeGateway** (simulated third-party):
- `StripeCharge createCharge(StripeChargeRequest req)`
- `StripeRefund processRefund(String chargeId, int amountCents)`

**PayPalGateway** (simulated third-party):
- `PayPalTransaction executePayment(PayPalPaymentRequest req)`
- `PayPalRefundResponse refundTransaction(String txnId, String amount)`

### 4. Adapters

- `StripePaymentAdapter` - Adapts Stripe to PaymentProcessor
- `PayPalPaymentAdapter` - Adapts PayPal to PaymentProcessor

## Non-Functional Expectations

- Handle amount conversion (dollars ↔ cents for Stripe)
- Map domain models correctly
- Consistent error handling across adapters
- Thread-safe if adaptees are thread-safe

## Constraints

- Do not modify third-party gateway classes
- Adapters must implement `PaymentProcessor`
- All monetary amounts in PaymentRequest are in dollars

## Starter Code Location

`src/main/java/com/patterns/adapter/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add adapter for Square payment gateway
2. Implement retry logic in adapters
3. Add circuit breaker for gateway failures
4. Implement adapter factory pattern

## Hints

<details>
<summary>Click to reveal hints</summary>

- For Stripe: convert dollars to cents (multiply by 100)
- For PayPal: amounts are strings formatted as "123.45"
- Map success/failure from third-party response to PaymentResult
- Extract transaction IDs from third-party responses
- Handle null/error cases gracefully
</details>
