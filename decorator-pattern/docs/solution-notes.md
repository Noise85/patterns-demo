# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation

### Design Approach

**Core Architecture**:
- `Notifier` interface defines the contract
- `BasicNotifier` is the concrete component (no decoration)
- `NotifierDecorator` abstract class implements common delegation
- Concrete decorators extend `NotifierDecorator` and add specific behavior

**Key Implementation Points**:

1. **NotifierDecorator base class**:
   - Holds reference to wrapped `Notifier`
   - Constructor takes wrapped notifier
   - Default `send()` delegates to wrapped component
   - Concrete decorators override to add behavior

2. **Decorator pattern**: Transform → Delegate OR Delegate → Transform
   - `EncryptionDecorator`: Encrypt input, then delegate
   - `CompressionDecorator`: Compress input, then delegate
   - `HtmlDecorator`: Delegate, then wrap result in HTML
   - Order matters!

3. **Encryption logic**: Simple Caesar cipher (shift by 3)
   - 'A' → 'D', 'B' → 'E', 'z' → 'c'
   - Wrap around alphabet boundaries

4. **Compression logic**: Run-length encoding for repeated characters
   - Scan string, count consecutive duplicates
   - Replace with character + count (if count > 1)

### Common Pitfalls

- Forgetting to delegate to wrapped component
- Incorrect transformation order (before vs. after delegation)
- Mutable state shared between calls
- Not maintaining the same interface through the chain

## Exercise 2: Real-World Simulation

### Design Approach

**Core Architecture**:
- `PaymentProcessor` interface defines processing contract
- `CorePaymentProcessor` handles actual payment execution
- Each decorator wraps processor and adds one concern
- `PaymentPipelineFactory` assembles decorator chains

**Immutable Domain Models**:
- Use Java records for `PaymentRequest` (immutable by default)
- `PaymentResult` should be immutable (consider builder for audit trail)
- Add audit entries without mutation (create new lists/collections)

**Decorator Implementations**:

1. **ValidationDecorator**:
   - Check ALL validation rules before processing
   - If any fail, return failed result immediately (don't delegate)
   - If all pass, delegate to wrapped processor

2. **FraudDetectionDecorator**:
   - Evaluate fraud rules against request
   - Add fraud check result to audit trail
   - Block fraudulent requests (return rejection)
   - Delegate legitimate requests

3. **AuditLoggingDecorator**:
   - Log before processing (entry point)
   - Delegate to wrapped processor
   - Log after processing (exit point with result)
   - Calculate processing duration
   - Return result with enhanced audit trail

4. **RetryDecorator**:
   - Attempt initial processing
   - If failed AND message indicates retryable error:
     - Retry up to MAX_RETRIES times
     - Wait between attempts (exponential backoff)
     - Log each retry attempt
   - Return final result (success or last failure)

5. **IdempotencyDecorator**:
   - Maintain in-memory cache: `Map<String, PaymentResult>`
   - Check if transaction ID exists in cache
   - If yes, return cached result
   - If no, delegate, cache result, return

**Pipeline Assembly**:
- Factory method per merchant tier
- Decorators composed from outside-in (outermost first)
- Common pattern: Idempotency → Validation → Fraud → Retry → Audit → Core

### Technical Considerations

**Audit Trail Management**:
- Each decorator adds entries without modifying incoming request
- Consider using immutable list builders or copying
- `PaymentResult` should have `withAuditEntry(String entry)` helper

**Error Handling**:
- Validation failures: descriptive message indicating which rule failed
- Fraud detection: clear rejection reason
- Core processor: simulate both success and failure paths

**Testing Strategy**:
- Test each decorator in isolation (wrap core processor only)
- Test decorator combinations (2-3 decorators)
- Test full pipelines (factory-generated)
- Verify audit trail accuracy at each step
- Test edge cases: duplicate IDs, transient failures, fraudulent requests

### Common Pitfalls

- Mutating `PaymentRequest` or `PaymentResult` (must be immutable)
- Decorators with multiple responsibilities (violates SRP)
- Not delegating when validation/fraud checks pass
- Retrying non-transient failures
- Incorrect decorator order in pipelines
- Losing audit trail entries during decoration

## Design Pattern Insights

### When to Use Decorator

✅ **Use when:**
- Multiple optional behaviors can be combined
- Behaviors are orthogonal (independent)
- Runtime composition is needed
- Subclassing would create class explosion

❌ **Avoid when:**
- Only single enhancement needed (use simple wrapper)
- Behaviors are tightly coupled (consider Strategy or State)
- Performance is critical (decoration adds indirection)

### Decorator vs. Other Patterns

**Decorator vs. Proxy**:
- Decorator: Adds behavior
- Proxy: Controls access, same interface

**Decorator vs. Adapter**:
- Decorator: Same interface in/out
- Adapter: Different interfaces

**Decorator vs. Chain of Responsibility**:
- Decorator: All handlers process request
- Chain: One handler processes request

## Verification Checklist

- [ ] All decorators implement same interface as component
- [ ] Each decorator delegates to wrapped component
- [ ] Decorators can be stacked in any order
- [ ] No mutable shared state
- [ ] Single responsibility per decorator
- [ ] Tests verify both isolation and composition
- [ ] Error cases handled gracefully
- [ ] Audit trail complete and accurate (Exercise 2)
- [ ] Pipeline factory creates correct configurations (Exercise 2)
