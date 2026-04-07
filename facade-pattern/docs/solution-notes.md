# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation

### Design Approach

**Core Architecture**:
- Five independent subsystem classes (Projector, SoundSystem, DvdPlayer, StreamingDevice, Lights)
- Each subsystem maintains its own state
- `HomeTheaterFacade` coordinates subsystems without embedding business logic

**State Management**:
- Each component should track powered on/off state
- Track current settings (volume, input mode, etc.)
- Validate preconditions before operations (e.g., can't play if powered off)

**Facade Implementation**:

1. **Constructor injection**: Accept all subsystems
2. **watchDvdMovie()**: Execute steps in logical order:
   - Lights dim first (ambient setup)
   - Power on devices
   - Configure inputs and modes
   - Start playback
3. **watchStreamingMovie()**: Similar flow, different input source
4. **endMovie()**: Reverse operations:
   - Stop playback
   - Power off devices (order less critical for shutdown)
   - Restore lights

**Error Handling**:
- Subsystems throw `IllegalStateException` for invalid operations
- Example: calling `play()` when device powered off
- Facade can catch and handle or let exceptions propagate

### Common Pitfalls

- Forgetting to track state in subsystems
- Incorrect operation order (e.g., playing before powering on)
- Facade doing too much (embedding subsystem logic)
- Not Making subsystems reusable (tight coupling to facade)

## Exercise 2: Real-World Simulation

### Design Approach

**Core Architecture**:
- Five subsystem services (Inventory, Payment, Shipping, OrderRepository, Notifications)
- `OrderProcessingFacade` orchestrates transaction-like workflow
- Each subsystem is independently testable and reusable

**Transactional Flow**:

1. **Check inventory availability** (read-only, no rollback needed)
2. **Reserve inventory** (CHECKPOINT 1 - can rollback)
3. **Authorize payment** (CHECKPOINT 2 - can refund)
4. **Capture payment** (COMMITTED - must complete)
5. **Create order record**
6. **Generate shipping label**
7. **Schedule pickup**
8. **Commit inventory reservation** (deduct stock)
9. **Send confirmation notification**

**Compensation/Rollback Logic**:

If failure occurs at step N, rollback steps N-1, N-2, etc.:

- **Step 2 fails**: No cleanup needed (read-only check failed)
- **Step 3 fails** (payment authorization): Release inventory reservation
- **Step 4+ fails** (after payment captured): Refund payment + release reservation

Pattern: Try-catch blocks around each critical step, catch exceptions, invoke compensation.

**Idempotency Implementation**:
- Maintain `Map<String, OrderResult>` of processed orders
- Check orderId before processing
- Return cached result if already processed
- Add entry after successful processing

**Subsystem Design**:

1. **InventoryService**:
   - Maintain `Map<String, Integer>` for product stock levels
   - `reserveItems()` creates reservation record (don't deduct yet)
   - `commitReservation()` actually deducts stock
   - `releaseReservation()` returns items to available pool

2. **PaymentService**:
   - Simulate gateway with two-phase commit (authorize → capture)
   - Authorization holds funds without charging
   - Capture completes the transaction
   - `refundPayment()` for rollback

3. **ShippingService**:
   - Generate unique tracking numbers
   - Calculate estimated delivery (e.g., today + 5 days)
   - Create shipping labels with all necessary info

4. **OrderRepository**:
   - Simple in-memory storage: `Map<String, Order>`
   - Track order status: PENDING, PROCESSING, COMPLETED, FAILED

5. **NotificationService**:
   - Simulate sending emails
   - For testing: track sent notifications in a list

**Facade Orchestration**:

```
processOrder(request):
  1. if (alreadyProcessed(orderId)) return cachedResult
  2. reservationId = null, authId = null, transactionId = null
  
  3. try:
       check inventory availability (early fail, no cleanup)
       reservationId = inventory.reserve(items)
       authId = payment.authorize(customerId, total)
       transactionId = payment.capture(authId)
       order = orderRepo.create(...)
       label = shipping.generateLabel(...)
       shipping.schedulePickup(label)
       inventory.commitReservation(reservationId)
       notifications.sendConfirmation(...)
       cache and return success result
       
  4. catch Exception:
       if (transactionId != null) payment.refund(transactionId)
       if (reservationId != null) inventory.releaseReservation(reservationId)
       notifications.sendFailure(...)
       return failure result
```

### Technical Considerations

**Exception Types**:
- `InsufficientStockException`: Inventory unavailable (early exit)
- `PaymentFailedException`: Payment declined (rollback reservation)
- `ShippingException`: Can't generate label (rollback payment + reservation)

**State Consistency**:
- Reservations must be tracked separately from committed stock
- Payment capture is point of no return (after that, must complete or refund)
- Order status should reflect current stage

**Testing Strategy**:
- Test each subsystem in isolation first
- Test facade happy path (all subsystems succeed)
- Test each failure point with proper rollback verification
- Test idempotency by processing same order twice
- Verify notifications sent in all scenarios

### Common Pitfalls

- Not implementing proper rollback/compensation
- Forgetting idempotency check
- Capturing payment before shipping is ready (can't rollback easily)
- Not sending failure notifications
- Tight coupling between facade and subsystems
- Missing state validation in subsystems

## Design Pattern Insights

### When to Use Facade

✅ **Use when:**
- Multiple subsystems need coordination
- Clients shouldn't know subsystem complexity
- Clear transaction boundaries exist
- Want to decouple clients from implementation details

❌ **Avoid when:**
- Only one or two simple classes to wrap
- Clients need fine-grained control over subsystems
- Facade would become too complex (consider multiple facades)

### Facade vs. Other Patterns

**Facade vs. Adapter**:
- Facade: Simplifies interface to multiple classes
- Adapter: Makes one incompatible interface compatible

**Facade vs. Mediator**:
- Facade: One-way (client → facade → subsystems)
- Mediator: Multi-directional (colleagues communicate through mediator)

**Facade vs. Strategy**:
- Facade: Structural pattern (simplify access)
- Strategy: Behavioral pattern (swap algorithms)

## Verification Checklist

- [ ] Subsystems are independently usable
- [ ] Facade doesn't duplicate subsystem logic
- [ ] Clear, simple facade interface
- [ ] Proper error handling and compensation
- [ ] Idempotency where appropriate (Exercise 2)
- [ ] State tracked correctly in subsystems
- [ ] Tests verify orchestration sequence
- [ ] Rollback verified for all failure points (Exercise 2)
