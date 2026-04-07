# Solution Notes - State Pattern

## Exercise 1: Document Workflow

### Key Implementation Points

#### State Interface
All states implement the same interface, even if they throw exceptions for most methods:
```java
public interface DocumentState {
    void edit(Document document);
    void submit(Document document);
    void approve(Document document);
    void reject(Document document);
    void archive(Document document);
    String getStateName();
}
```

This ensures compile-time safety - all states must handle all operations.

#### State Transitions (States Control)
States decide their successors:
```java
// In DraftState
public void submit(Document document) {
    document.setState(new ReviewState());
}

// In ReviewState
public void approve(Document document) {
    document.setState(new PublishedState());
}

public void reject(Document document) {
    document.setState(new DraftState());  // Back to draft
}
```

**Key**: States reference the context and modify its state directly.

#### Invalid Operations
Clear exception messages:
```java
public void approve(Document document) {
    throw new IllegalStateException(
        "Cannot approve document in Draft state. Submit for review first."
    );
}
```

#### Document Delegation
Document delegates everything to current state:
```java
public void edit() {
    currentState.edit(this);
}

public void submit() {
    currentState.submit(this);
}
```

No conditionals in Document class!

#### Edit Count Tracking
Only DraftState increments:
```java
// In DraftState
public void edit(Document document) {
    document.incrementEditCount();
}

// In Document
public void incrementEditCount() {
    this.editCount++;
}
```

### Design Decisions

1. **States are stateless**: No instance variables in state classes
2. **One instance per transition**: `new ReviewState()` each time (could use singleton)
3. **Context owns data**: Document stores all data, states just define behavior
4. **Terminal state**: ArchivedState has no outgoing transitions

---

## Exercise 2: Order Fulfillment System

### Key Implementation Points

#### State Interface with Guards
```java
public interface OrderState {
    void pay(Order order, BigDecimal amount);
    void ship(Order order, String trackingNumber);
    void deliver(Order order);
    void cancel(Order order, String reason);
    void refund(Order order);
    
    String getStateName();
    
    // Guard methods - query without side effects
    boolean canCancel();
    boolean canShip();
}
```

Guard methods allow checking capabilities without triggering exceptions.

#### Input Validation in States
```java
// In PendingState
public void pay(Order order, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Payment amount must be positive");
    }
    
    order.setPaymentAmount(amount);
    order.setState(new PaidState());
}

// In PaidState
public void ship(Order order, String trackingNumber) {
    if (trackingNumber == null || trackingNumber.trim().isEmpty()) {
        throw new IllegalArgumentException("Tracking number is required");
    }
    
    order.setTrackingNumber(trackingNumber);
    order.setState(new ShippedState());
}
```

**Critical**: Validate before transitioning to prevent invalid states.

#### State History Tracking
```java
// In Order
public void setState(OrderState newState) {
    this.currentState = newState;
    this.stateHistory.add(new StateTransition(
        newState.getStateName(), 
        LocalDateTime.now()
    ));
}

public LocalDateTime getEnteredCurrentStateAt() {
    if (stateHistory.isEmpty()) {
        return null;
    }
    return stateHistory.get(stateHistory.size() - 1).timestamp();
}
```

Every transition is recorded with timestamp.

#### Guard Method Implementation
```java
// In PendingState and PaidState
public boolean canCancel() {
    return true;
}

public boolean canShip() {
    return false;  // Only PaidState returns true
}

// In ShippedState and later
public boolean canCancel() {
    return false;  // Cannot cancel after shipping
}
```

Enables queries: `if (order.canCancel()) { ... }`

#### Terminal States
```java
// In CancelledState
public void pay(Order order, BigDecimal amount) {
    throw new IllegalStateException("Order is cancelled - no operations allowed");
}

public void ship(Order order, String trackingNumber) {
    throw new IllegalStateException("Order is cancelled - no operations allowed");
}

// ... all methods throw for terminal states
```

Terminal states have no valid outgoing transitions.

### Complex Transition Logic

#### Cancellation with Reason
```java
// In PendingState/PaidState
public void cancel(Order order, String reason) {
    if (reason == null || reason.trim().isEmpty()) {
        throw new IllegalArgumentException("Cancellation reason is required");
    }
    
    order.setCancellationReason(reason);
    order.setState(new CancelledState());
}
```

#### Refund Only from Delivered
```java
// In DeliveredState
public void refund(Order order) {
    order.setState(new RefundedState());
}

// In all other states
public void refund(Order order) {
    throw new IllegalStateException(
        "Refund only allowed for delivered orders"
    );
}
```

### Design Decisions

1. **Validation in states**: Each state validates its inputs
2. **Guard methods**: Query capabilities without exceptions
3. **State history**: Complete audit trail with timestamps
4. **Terminal states**: CancelledState and RefundedState
5. **Context owns data**: Order stores all business data
6. **Descriptive errors**: Clear messages for invalid operations

### State Transition Diagram

```
Pending ──pay()──> Paid ──ship()──> Shipped ──deliver()──> Delivered ──refund()──> Refunded
   │                  │                                          
   │                  │                                          
   └──cancel()───────┴──cancel()──> Cancelled
```

Cancellation allowed: Pending, Paid  
Cancellation blocked: Shipped, Delivered, Cancelled, Refunded

---

## General State Pattern Insights

### When States Know Each Other
States reference concrete successor states:
```java
document.setState(new ReviewState());  // Draft knows about Review
```

**Trade-off**: Tight coupling vs. encapsulation of transition logic

**Alternative**: Use factory or enum:
```java
document.setState(StateFactory.createState(StateType.REVIEW));
```

### Singleton vs. New Instance
**New instance each time**:
```java
new PendingState()
```
**Pros**: Simple, clear  
**Cons**: More object creation

**Singleton**:
```java
public class PendingState {
    public static final PendingState INSTANCE = new PendingState();
    private PendingState() {}
}

document.setState(PendingState.INSTANCE);
```
**Pros**: Memory efficient  
**Cons**: Must be stateless

**Recommendation**: Use singleton if states have no instance variables.

### Exception vs. No-Op
For invalid operations:

**Option 1 - Throw exception** (recommended):
```java
public void approve(Document document) {
    throw new IllegalStateException("Cannot approve draft");
}
```
**Pros**: Fail fast, clear errors  
**Cons**: Requires try-catch

**Option 2 - Silently ignore**:
```java
public void approve(Document document) {
    // Do nothing
}
```
**Pros**: No exceptions  
**Cons**: Hidden bugs, unclear behavior

**Option 3 - Return status**:
```java
public boolean approve(Document document) {
    return false;  // Operation failed
}
```
**Pros**: Caller can check  
**Cons**: Return type changes

### State Data vs. Context Data

**Bad - Data in state**:
```java
public class DraftState {
    private int editCount;  // WRONG - shared between documents!
}
```

**Good - Data in context**:
```java
public class Document {
    private int editCount;  // RIGHT - per document
}

public class DraftState {
    public void edit(Document document) {
        document.incrementEditCount();  // Modify context
    }
}
```

**Rule**: States are stateless, context owns data.

### Testing Strategy

1. **Test each state independently**:
```java
@Test
void testDraftStateAllowsEdit() {
    Document doc = new Document("Test");
    doc.edit();  // Should work
    assertThat(doc.getEditCount()).isEqualTo(1);
}
```

2. **Test all valid transitions**:
```java
@Test
void testDraftToReviewTransition() {
    Document doc = new Document("Test");
    doc.submit();
    assertThat(doc.getCurrentStateName()).isEqualTo("Review");
}
```

3. **Test invalid operations**:
```java
@Test
void testCannotEditInReview() {
    Document doc = new Document("Test");
    doc.submit();
    
    assertThatThrownBy(() -> doc.edit())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Cannot edit");
}
```

4. **Test transition sequences**:
```java
@Test
void testCompleteWorkflow() {
    Document doc = new Document("Test");
    doc.edit();
    doc.submit();
    doc.approve();
    doc.archive();
    assertThat(doc.getCurrentStateName()).isEqualTo("Archived");
}
```

### Common Mistakes

1. **Context decides transitions**:
```java
// BAD
public void submit() {
    if (currentState instanceof DraftState) {
        currentState = new ReviewState();
    }
}
```
Let states decide transitions!

2. **State has mutable fields**:
```java
// BAD
public class ReviewState {
    private String reviewer;  // Shared across documents!
}
```
Keep states stateless!

3. **Missing state validation**:
```java
// BAD
public void setState(OrderState newState) {
    this.currentState = newState;  // No validation
}
```
Validate valid transitions!

4. **Forgetting history**:
```java
// BAD
public void setState(OrderState newState) {
    this.currentState = newState;
    // Forgot to record history!
}
```

---

## Advanced Patterns

### State with Memento
Save/restore state:
```java
public Memento saveState() {
    return new Memento(currentState.getStateName(), editCount);
}

public void restoreState(Memento memento) {
    this.currentState = StateFactory.create(memento.getStateName());
    this.editCount = memento.getEditCount();
}
```

### State with Observer
Notify on transitions:
```java
public void setState(OrderState newState) {
    OrderState oldState = this.currentState;
    this.currentState = newState;
    notifyStateChanged(oldState, newState);
}
```

### Conditional State Transitions
States can have conditional logic:
```java
public void ship(Order order, String trackingNumber) {
    if (order.getPaymentAmount().compareTo(new BigDecimal("1000")) > 0) {
        // High-value orders need approval
        order.setState(new AwaitingApprovalState());
    } else {
        order.setTrackingNumber(trackingNumber);
        order.setState(new ShippedState());
    }
}
```

---

## Performance Considerations

- **State object creation**: Negligible unless millions of transitions
- **State checking**: Polymorphism is fast (virtual dispatch)
- **Memory**: Context + one state reference per object
- **Optimization**: Use singleton states if stateless

---

## Further Reading

- **Refactoring Guru - State**: https://refactoring.guru/design-patterns/state
- **Martin Fowler - Replace Conditional with Polymorphism**: Classic refactoring using State
- **"Design Patterns" (GoF)**: Chapter on State pattern
- **UML State Diagrams**: Visual representation of state machines
