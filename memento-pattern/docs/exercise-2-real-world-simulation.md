# Exercise 2: Real-World Simulation - Order Management with Snapshots

## Objective

Build a **production-grade order management system** that uses Memento pattern for transaction snapshots and rollback. This exercise demonstrates Memento in a realistic trading/order processing context.

---

## Business Context

You're building an **order management system** for a trading platform. Orders go through multiple processing stages:

1. **Submitted** - Initial order received
2. **Risk checked** - Passed risk validations
3. **Pending execution** - Queued for execution
4. **Executed** - Filled by market

At each critical stage, the system takes a **snapshot** so it can rollback if errors occur. For example:
- Risk check fails → rollback to submitted state
- Execution fails → rollback to pending state
- User cancels during processing → restore to last stable state

---

## Domain Model

### Order State

Each order tracks:
- **Order ID**: Unique identifier
- **Symbol**: Instrument (e.g., "AAPL", "GOOGL")
- **Quantity**: Number of shares
- **Price**: Limit price (null for market orders)
- **Status**: Current order status (enum)
- **Filled quantity**: How many shares executed so far
- **Average fill price**: Average execution price
- **Timestamps**: Submission time, last update time

### Order Status (Enum)

```
SUBMITTED → RISK_CHECKED → PENDING_EXECUTION → EXECUTED
     ↓            ↓                ↓              ↓
  REJECTED     REJECTED          FAILED       COMPLETED
```

Possible statuses:
- `SUBMITTED` - Just received
- `RISK_CHECKED` - Passed risk validations
- `PENDING_EXECUTION` - Waiting for market
- `PARTIALLY_FILLED` - Some shares executed
- `EXECUTED` - All shares filled
- `REJECTED` - Failed validation
- `CANCELLED` - User cancelled
- `FAILED` - System error

---

## Requirements

### Classes to Implement

#### 1. `Order` (Originator)

Represents a trading order with snapshotting capabilities.

**State**:
- `orderId: String` - Unique order identifier
- `symbol: String` - Trading symbol
- `quantity: int` - Total order quantity
- `price: BigDecimal` - Limit price (null for market orders)
- `orderStatus: OrderStatus` - Current status
- `filledQuantity: int` - Shares filled so far
- `averageFillPrice: BigDecimal` - Average execution price
- `submittedAt: LocalDateTime` - Submission timestamp
- `lastUpdated: LocalDateTime` - Last modification time

**Operations**:
- `submit()` - Set status to SUBMITTED
- `markRiskChecked()` - Advance to RISK_CHECKED
- `markPendingExecution()` - Advance to PENDING_EXECUTION
- `fillPartial(int quantity, BigDecimal price)` - Record partial fill
- `completeExecution()` - Mark as EXECUTED
- `reject(String reason)` - Set status to REJECTED
- `cancel()` - Set status to CANCELLED
- `snapshot(): Snapshot` - Create memento of current state
- `restore(Snapshot snapshot)` - Restore from snapshot
- `getOrderId()`, `getSymbol()`, `getOrderStatus()`, etc. - Getters for testing

#### 2. `Order.Snapshot` (Memento)

Immutable snapshot of order state.

**State**:
- All order fields captured at snapshot time
- `snapshotTime: LocalDateTime` - When snapshot was taken

**Requirements**:
- Nested class within `Order`
- Private constructor
- Immutable (final fields, defensive copying for mutable objects)
- Package-private getters

#### 3. `OrderStatus` (Enum)

Order status enumeration.

**Values**:
```java
SUBMITTED, RISK_CHECKED, PENDING_EXECUTION, 
PARTIALLY_FILLED, EXECUTED, REJECTED, CANCELLED, FAILED
```

#### 4. `TransactionManager` (Caretaker)

Manages transaction checkpoints and rollback.

**State**:
- `checkpoints: Map<String, Deque<Snapshot>>` - Checkpoints per order ID
- `maxCheckpointsPerOrder: int` - History limit (default 10)

**Operations**:
- `checkpoint(Order order)` - Create checkpoint for order
- `rollback(Order order): boolean` - Rollback to last checkpoint
- `rollbackTo(Order order, int stepsBack): boolean` - Rollback N steps
- `clearCheckpoints(String orderId)` - Remove all checkpoints for order
- `getCheckpointCount(String orderId): int` - Get number of checkpoints
- `hasCheckpoints(String orderId): boolean` - Check if checkpoints exist

---

## Implementation Details

### Order Lifecycle with Snapshots

```java
Order order = new Order("ORD-001", "AAPL", 100, new BigDecimal("150.00"));
TransactionManager txManager = new TransactionManager();

// Submit order
order.submit();
txManager.checkpoint(order); // Checkpoint 1: SUBMITTED

// Risk check
order.markRiskChecked();
txManager.checkpoint(order); // Checkpoint 2: RISK_CHECKED

// If execution fails:
if (executionFailed) {
    txManager.rollback(order); // Back to RISK_CHECKED
}
```

### Partial Fill Handling

```java
order.markPendingExecution();
txManager.checkpoint(order); // Before execution

// Partial fill: 50 shares @ $150.25
order.fillPartial(50, new BigDecimal("150.25"));
txManager.checkpoint(order);

// Another partial fill: 30 shares @ $150.30
order.fillPartial(30, new BigDecimal("150.30"));

// System error - rollback to previous fill
txManager.rollback(order); // Back to 50 shares filled
```

### Multi-step Rollback

```java
// Create multiple checkpoints
txManager.checkpoint(order); // State 1
order.someOperation();
txManager.checkpoint(order); // State 2
order.anotherOperation();
txManager.checkpoint(order); // State 3

// Rollback 2 steps
txManager.rollbackTo(order, 2); // Back to State 1
```

---

## Complex Scenarios

### Scenario 1: Risk Check Failure with Rollback

```java
Order order = new Order("ORD-001", "TSLA", 200, new BigDecimal("250.00"));
TransactionManager txManager = new TransactionManager();

order.submit();
txManager.checkpoint(order); // Safe point

order.markRiskChecked();
// Oops, risk check actually failed
if (!passesRiskCheck(order)) {
    txManager.rollback(order); // Back to SUBMITTED
    order.reject("Risk limit exceeded");
}
```

### Scenario 2: Partial Execution Recovery

```java
order.markPendingExecution();
txManager.checkpoint(order); // Before fills

order.fillPartial(50, new BigDecimal("100.00"));
order.fillPartial(30, new BigDecimal("100.10"));
txManager.checkpoint(order); // 80 shares filled

// Market crashes, connection lost
if (connectionLost) {
    txManager.rollback(order); // Back to 0 filled
    order.cancel();
}
```

### Scenario 3: Average Fill Price Calculation

```java
// Order for 100 shares
order.fillPartial(50, new BigDecimal("150.00")); // 50 @ $150.00
// avgFillPrice = 150.00, filledQuantity = 50

order.fillPartial(30, new BigDecimal("151.00")); // 30 @ $151.00
// avgFillPrice = (50*150 + 30*151) / 80 = 150.375, filledQuantity = 80

order.fillPartial(20, new BigDecimal("149.00")); // 20 @ $149.00
// avgFillPrice = (50*150 + 30*151 + 20*149) / 100 = 150.26, filledQuantity = 100

order.completeExecution();
// status = EXECUTED
```

### Scenario 4: History Limit

```java
TransactionManager txManager = new TransactionManager(3); // Max 3 checkpoints

order.submit();
txManager.checkpoint(order); // Checkpoint 1

order.markRiskChecked();
txManager.checkpoint(order); // Checkpoint 2

order.markPendingExecution();
txManager.checkpoint(order); // Checkpoint 3

order.fillPartial(50, price);
txManager.checkpoint(order); // Checkpoint 4 - evicts Checkpoint 1

// Can only rollback 3 times now
```

---

## Key Design Decisions

### 1. Immutability

Snapshots must be deeply immutable:
- Use `final` fields
- Defensive copy for `BigDecimal` (already immutable)
- Copy `LocalDateTime` (already immutable)
- Enums are naturally immutable

### 2. Average Fill Price Calculation

When filling partial orders:
```java
newAvgPrice = (oldAvg * oldQty + newPrice * newQty) / (oldQty + newQty)
```

Use `BigDecimal` with proper scale (2 decimal places, HALF_UP rounding).

### 3. Timestamp Management

- `submittedAt`: Set once on creation, never changes
- `lastUpdated`: Update on every state change
- `snapshotTime`: Set when snapshot is created

### 4. Checkpoint Eviction

When history exceeds limit:
```java
if (checkpoints.size() >= maxCheckpointsPerOrder) {
    checkpoints.removeFirst(); // FIFO eviction
}
```

---

## Success Criteria

Your implementation should:

1. ✅ Create immutable snapshots of complete order state
2. ✅ Restore orders to previous checkpoints accurately
3. ✅ Calculate average fill price correctly for partial fills
4. ✅ Support multi-step rollback
5. ✅ Enforce checkpoint history limits
6. ✅ Handle edge cases (no checkpoints, rollback too far)
7. ✅ Preserve encapsulation (snapshot internals hidden)
8. ✅ Update timestamps correctly

---

## Testing Guidelines

Tests will verify:

- **Snapshot accuracy**: All fields captured correctly
- **Restoration completeness**: All fields restored
- **Partial fills**: Average price calculated properly
- **Multiple checkpoints**: Independent snapshots work
- **Rollback operations**: Single and multi-step rollback
- **History limits**: Old checkpoints evicted
- **Edge cases**: Empty history, invalid rollback
- **Timestamp integrity**: Proper timestamp management
- **Immutability**: Snapshots cannot be modified
- **Encapsulation**: TransactionManager cannot access snapshot internals

---

## Common Pitfalls

❌ **Mutable BigDecimal**: Always create new instances
❌ **Incorrect average price**: Watch calculation for partial fills
❌ **Timestamp confusion**: Don't mix snapshotTime with lastUpdated
❌ **Missing defensive copies**: Even for "immutable" types
❌ **Wrong checkpoint order**: Use Deque properly (push/pop)
❌ **History leak**: Must enforce limits

---

## Extension Ideas (Optional)

1. **Snapshot compression**: Store deltas for large orders
2. **Audit trail**: Log all checkpoints and rollbacks
3. **Snapshot persistence**: Save snapshots to database
4. **Concurrent access**: Thread-safe checkpoint management
5. **Snapshot comparison**: Diff two snapshots
6. **Batch rollback**: Rollback multiple orders atomically

---

## Why This Matters

This exercise simulates real-world order management where:
- Orders transition through complex workflows
- Errors require safe rollback to known-good states
- Audit trails demand accurate state tracking
- Recovery mechanisms prevent data corruption

The Memento pattern provides clean rollback without exposing order internals to the transaction manager.
