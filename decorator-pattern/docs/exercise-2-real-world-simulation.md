# Exercise 2: Real-World Simulation

## Objective

Build a production-grade payment processing pipeline using the Decorator pattern to compose orthogonal concerns: validation, fraud detection, audit logging, retry logic, and idempotency checking.

## Business Context

You're building a payment gateway for an e-commerce platform. Payment processing has multiple cross-cutting concerns that need to be:

- **Composable**: Different merchants need different processing pipelines
- **Testable**: Each concern should be verifiable in isolation
- **Maintainable**: Adding new concerns shouldn't modify existing code
- **Configurable**: Runtime pipeline assembly based on merchant tier

## Domain Model

### Payment Request
- `transactionId` (unique identifier)
- `amount` (payment amount in cents)
- `currency` (ISO code: USD, EUR, etc.)
- `cardToken` (tokenized card data)
- `merchantId` (merchant identifier)

### Payment Result
- `success` (boolean)
- `transactionId`
- `message` (success/failure reason)
- `processingTimeMs` (duration)
- `auditTrail` (list of processing steps)

## Your Tasks

### 1. Define Component Interface

Create `PaymentProcessor` interface:
- `process(PaymentRequest request)` → `PaymentResult`

### 2. Implement Core Processor

Create `CorePaymentProcessor`:
- The actual payment execution (simulate with random success/failure)
- No validation, logging, or extra concerns
- Just processes the payment transaction

### 3. Implement Processing Decorators

Design and implement the following decorators:

#### ValidationDecorator
Validates payment request before processing:
- Amount must be positive
- Amount must be ≤ $10,000 (1,000,000 cents) 
- Currency must be 3-letter ISO code (USD, EUR, GBP, etc.)
- Card token must not be empty
- If validation fails, return failed `PaymentResult` without delegating

#### FraudDetectionDecorator
Checks for suspicious patterns:
- Flag transactions > $5,000 (500,000 cents) as high-risk
- Simulate fraud check: if amount ends in 666, mark as fraudulent
- Add fraud check to audit trail
- Block fraudulent transactions (don't delegate)
- Allow others to proceed

#### AuditLoggingDecorator
Records processing metadata:
- Log entry point with timestamp
- Delegate to wrapped processor
- Log exit point with result status
- Add all log entries to `PaymentResult.auditTrail`
- Measure and record processing time

#### RetryDecorator
Handles transient failures:
- Retry failed payments up to 3 times
- Only retry if failure message contains "TRANSIENT" or "TIMEOUT"
- Exponential backoff: wait 100ms, 200ms, 400ms between retries
- Update audit trail with retry attempts
- Return last result after max retries

#### IdempotencyDecorator
Prevents duplicate processing:
- Track processed transaction IDs in memory
- If transaction ID already processed, return cached result
- Otherwise, delegate and cache the result
- Include idempotency check in audit trail

### 4. Pipeline Assembly

Create `PaymentPipelineFactory` that builds different pipelines:

**Basic tier** (small merchants):
```
Validation → Core Processor
```

**Standard tier** (medium merchants):
```
Validation → Fraud Detection → Audit Logging → Core Processor
```

**Premium tier** (enterprise):
```
Idempotency → Validation → Fraud Detection → Retry → Audit Logging → Core Processor
```

Factory should have method: `buildPipeline(MerchantTier tier)` → `PaymentProcessor`

### 5. Design Considerations

**Immutability**:
- `PaymentRequest` should be immutable (consider Java record)
- `PaymentResult` should be immutable
- Decorators must not modify shared state

**Single Responsibility**:
- Each decorator handles ONE concern
- Clear separation between business logic and cross-cutting concerns

**Composability**:
- Any decorator can wrap any other decorator
- Order matters (validation before processing, audit around everything)

**Error Handling**:
- Failed validation → immediate failure result
- Fraud detected → immediate rejection
- Core processor failure → return proper error result

## Example Usage

```java
// Basic pipeline
PaymentProcessor basic = pipelineFactory.buildPipeline(MerchantTier.BASIC);
PaymentResult result = basic.process(validRequest);

// Premium pipeline with full decoration
PaymentProcessor premium = pipelineFactory.buildPipeline(MerchantTier.PREMIUM);
PaymentResult result = premium.process(complexRequest);

// Custom pipeline
PaymentProcessor custom = new AuditLoggingDecorator(
    new ValidationDecorator(
        new FraudDetectionDecorator(
            new CorePaymentProcessor()
        )
    )
);
```

## Testing Strategy

Your implementation must handle:

1. **Validation scenarios**: Invalid amount, currency, token
2. **Fraud detection**: High-risk amounts, fraudulent patterns
3. **Audit trail**: All steps logged correctly
4. **Retry logic**: Transient failures retried, others not
5. **Idempotency**: Duplicate transactions handled correctly
6. **Pipeline composition**: Different tiers work as expected
7. **Order sensitivity**: Decorator order affects behavior

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] Each decorator implements single responsibility
- [ ] Decorators are composable in any logical order
- [ ] Audit trail accurately reflects processing steps
- [ ] Immutability maintained throughout
- [ ] Pipeline factory creates correct configurations
- [ ] Error handling is robust and informative

## Time Estimate

**2-3 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **Metrics Decorator**: Track success rate, avg processing time
2. **Circuit Breaker Decorator**: Stop processing after N failures
3. **Rate Limiting Decorator**: Throttle requests per merchant
4. **Notification Decorator**: Send webhooks on payment events
5. **Multi-currency Decorator**: Convert amounts to base currency

## Hints

- Start with the core processor and component interface
- Implement decorators one at a time, testing each in isolation
- Think about decorator order: validation early, audit wrapping everything
- Use builder pattern for complex pipeline assembly if needed
- Consider immutable builders for `PaymentResult` with growing audit trails
- Remember: each decorator enhances, doesn't replace, the core functionality
