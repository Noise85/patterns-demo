# Exercise 2: Real-World Simulation - Order Fulfillment System

## Objective

Build a production-grade order fulfillment system using the State pattern. You'll implement complex state transitions with validation, history tracking, and conditional logic.

## Scenario

You're building an e-commerce order processing system. Orders progress through multiple states with business rules:

1. **Pending**: Order created, awaiting payment
2. **Paid**: Payment received, preparing for shipment
3. **Shipped**: Package in transit
4. **Delivered**: Successfully delivered
5. **Cancelled**: Order cancelled (can happen from Pending or Paid states only)
6. **Refunded**: Payment refunded (only from Delivered state)

Business rules:
- Can only ship after payment confirmed
- Can only cancel before shipping
- Refunds only allowed for delivered orders
- Must track payment amount and shipping address
- Maintain state transition history

## Requirements

### 1. Order State Interface

```java
interface OrderState {
    void pay(Order order, BigDecimal amount);
    void ship(Order order, String trackingNumber);
    void deliver(Order order);
    void cancel(Order order, String reason);
    void refund(Order order);
    
    String getStateName();
    boolean canCancel();
    boolean canShip();
}
```

### 2. Concrete States

#### PendingState
- `pay(amount)`: Validates amount > 0, stores amount, transitions to PaidState
- `cancel(reason)`: Allowed - transitions to CancelledState with reason
- Other operations: Throw `IllegalStateException`

#### PaidState
- `ship(trackingNumber)`: Validates tracking number not empty, stores it, transitions to ShippedState
- `cancel(reason)`: Allowed - transitions to CancelledState, may include restocking fee
- Other operations: Throw `IllegalStateException`

#### ShippedState
- `deliver()`: Transitions to DeliveredState, records delivery timestamp
- `cancel()`: NOT allowed - cannot cancel shipped orders
- Other operations: Throw `IllegalStateException`

#### DeliveredState
- `refund()`: Transitions to RefundedState, processes refund
- Other operations: Throw `IllegalStateException`

#### CancelledState (Terminal)
- All operations: Throw `IllegalStateException`
- Records cancellation reason and timestamp

#### RefundedState (Terminal)
- All operations: Throw `IllegalStateException`
- Records refund timestamp

### 3. Order (Context)

```java
class Order {
    private final String orderId;
    private OrderState currentState;
    private BigDecimal paymentAmount;
    private String shippingAddress;
    private String trackingNumber;
    private String cancellationReason;
    private final List<StateTransition> stateHistory;
    
    public Order(String orderId, String shippingAddress) {
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.currentState = new PendingState();
        this.stateHistory = new ArrayList<>();
        addToHistory(currentState.getStateName());
    }
    
    public void setState(OrderState newState) {
        this.currentState = newState;
        addToHistory(newState.getStateName());
    }
    
    private void addToHistory(String stateName) {
        stateHistory.add(new StateTransition(stateName, LocalDateTime.now()));
    }
}
```

### 4. State Transition Record

```java
record StateTransition(String stateName, LocalDateTime timestamp) {}
```

## Implementation Guidelines

### PendingState Example
```java
public class PendingState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        // TODO: Store payment amount in order
        order.setPaymentAmount(amount);
        
        // TODO: Transition to PaidState
        order.setState(new PaidState());
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Store cancellation reason
        order.setCancellationReason(reason);
        
        // TODO: Transition to CancelledState
        order.setState(new CancelledState());
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        throw new IllegalStateException("Cannot ship unpaid order");
    }
    
    @Override
    public boolean canCancel() {
        return true;
    }
    
    @Override
    public boolean canShip() {
        return false;
    }
}
```

### State Validation
Each state should validate inputs before transitioning:
- Payment amount must be positive
- Tracking number must not be empty
- Cancellation reason should be provided (not null/empty)

### State History
Track all state transitions with timestamps:
```java
List<StateTransition> getStateHistory();
LocalDateTime getEnteredCurrentStateAt();  // Timestamp of current state
```

## Example Usage

```java
Order order = new Order("ORD-12345", "123 Main St, City");

// Pending state
System.out.println(order.getCurrentStateName());  // "Pending"
System.out.println(order.canCancel());  // true
System.out.println(order.canShip());    // false

// Pay for order
order.pay(new BigDecimal("99.99"));
System.out.println(order.getCurrentStateName());  // "Paid"
System.out.println(order.getPaymentAmount());     // 99.99

// Ship order
order.ship("TRACK-123456");
System.out.println(order.getTrackingNumber());  // "TRACK-123456"
System.out.println(order.canCancel());  // false

// Deliver order
order.deliver();
System.out.println(order.getCurrentStateName());  // "Delivered"

// Refund
order.refund();
System.out.println(order.getCurrentStateName());  // "Refunded"

// Check history
List<StateTransition> history = order.getStateHistory();
// [Pending, Paid, Shipped, Delivered, Refunded]
```

## Complex Scenarios

### Scenario 1: Cancellation Flow
```java
Order order1 = new Order("ORD-1", "Address");
order1.pay(new BigDecimal("50.00"));
order1.cancel("Customer changed mind");

System.out.println(order1.getCancellationReason());
// "Customer changed mind"
```

### Scenario 2: Invalid Cancellation
```java
Order order2 = new Order("ORD-2", "Address");
order2.pay(new BigDecimal("75.00"));
order2.ship("TRACK-789");

try {
    order2.cancel("Too late");  // IllegalStateException
} catch (IllegalStateException e) {
    System.out.println("Cannot cancel shipped order");
}
```

### Scenario 3: State History Tracking
```java
Order order3 = new Order("ORD-3", "Address");
order3.pay(new BigDecimal("100.00"));
Thread.sleep(1000);
order3.ship("TRACK-ABC");

List<StateTransition> history = order3.getStateHistory();
assertThat(history).hasSize(3);  // Pending, Paid, Shipped

LocalDateTime paidAt = history.get(1).timestamp();
LocalDateTime shippedAt = history.get(2).timestamp();
assertThat(shippedAt).isAfter(paidAt);
```

## Key Learning Points

1. **Complex State Machines**: Multiple states with different allowed operations
2. **Validation in States**: Input validation before state transitions
3. **State History**: Maintaining audit trail of state changes
4. **Conditional Transitions**: Some transitions depend on business rules
5. **Terminal States**: States with no outgoing transitions
6. **Guard Methods**: `canCancel()`, `canShip()` for querying capabilities

## Testing

The test suite verifies:
- ✅ Order starts in Pending state
- ✅ Payment validation (positive amount required)
- ✅ Successful payment transitions to Paid
- ✅ Shipping validation (tracking number required)
- ✅ Cannot ship unpaid order
- ✅ Cannot cancel shipped order
- ✅ Can cancel pending/paid orders
- ✅ Refund only allowed from Delivered state
- ✅ State history tracked correctly
- ✅ Timestamps in history are chronological
- ✅ Cancellation reason stored
- ✅ All data fields populated correctly

## Design Considerations

### Immutable States
States should be stateless (no instance variables). All data belongs to Order context.

### Error Messages
Provide clear error messages:
```java
throw new IllegalStateException(
    "Cannot ship order in " + getStateName() + " state"
);
```

### State Capabilities
Guard methods allow querying without exceptions:
```java
if (order.canCancel()) {
    order.cancel("Reason");
}
```

### Audit Trail
State history provides:
- Compliance tracking
- Customer service insights
- Performance metrics (time in each state)

## Challenge Questions

1. How would you implement partial refunds (refund amount < payment amount)?
2. How would you add a "Return Requested" state after Delivered?
3. Could you implement state timeouts (e.g., auto-cancel after 30 days pending)?
4. How would you support rollback to previous state (undo)?

## Common Pitfalls

- ❌ Storing business data in state objects instead of context
- ❌ Not validating inputs before state transitions
- ❌ Allowing invalid transitions without throwing exceptions
- ❌ Forgetting to update state history
- ❌ Using mutable state objects shared across orders
- ❌ Not handling edge cases (null inputs, negative amounts)
