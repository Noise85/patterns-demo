# Solution Notes - Memento Pattern

**DO NOT** read this until you've attempted the exercises yourself!

These notes provide implementation guidance and common pitfalls. They are NOT complete solutions.

---

## Exercise 1: Text Editor Undo

### Implementation Strategy

#### TextEditor Structure

```java
public class TextEditor {
    private String content = "";
    private int cursorPosition = 0;
    
    // Nested memento class
    public static final class Memento {
        private final String content;
        private final int cursorPosition;
        
        // Private constructor - only TextEditor can create
        private Memento(String content, int cursorPosition) {
            this.content = content;
            this.cursorPosition = cursorPosition;
        }
        
        // Package-private getters - TextEditor can access
        String getContent() { return content; }
        int getCursorPosition() { return cursorPosition; }
    }
}
```

**Key points**:
- Memento is `static final` to prevent subclassing
- Private constructor ensures only TextEditor creates instances
- Package-private getters allow TextEditor to read state
- No setters - immutability enforced

#### Write Operation

When writing text:
1. Insert text at current cursor position
2. Update cursor to end of inserted text
3. Use `StringBuilder` for efficient string manipulation

```java
public void write(String text) {
    // Split content at cursor
    // Insert text
    // Update cursor position
}
```

#### Delete Operation

When deleting:
1. Check cursor > 0 (can't delete if at start)
2. Remove character before cursor
3. Move cursor back by 1

#### Cursor Management

Always validate cursor position:
- Minimum: 0
- Maximum: content.length()

#### Creating Mementos

```java
public Memento save() {
    return new Memento(content, cursorPosition);
}
```

Simple! Just capture current state.

#### Restoring from Mementos

```java
public void restore(Memento memento) {
    this.content = memento.getContent();
    this.cursorPosition = memento.getCursorPosition();
}
```

Directly assign fields from memento.

### EditorHistory Implementation

Use `ArrayDeque<Memento>` for stack behavior:

```java
public class EditorHistory {
    private final Deque<Memento> history = new ArrayDeque<>();
    
    public void push(Memento memento) {
        history.push(memento); // Add to top
    }
    
    public Memento pop() {
        return history.isEmpty() ? null : history.pop();
    }
}
```

**Why ArrayDeque**:
- More efficient than Stack (legacy class)
- `push()` adds to front
- `pop()` removes from front
- LIFO behavior perfect for undo

### Common Mistakes

❌ **Forgetting cursor in write()**:
```java
// WRONG: Doesn't update cursor
public void write(String text) {
    content += text; // Cursor still at old position!
}
```

✅ **Correct**:
```java
public void write(String text) {
    // Insert text at cursor
    // Update cursor to end of inserted text
}
```

❌ **Public memento constructor**:
```java
// WRONG: Anyone can create mementos
public Memento(String content, int position) { }
```

✅ **Correct**:
```java
private Memento(String content, int position) { }
```

❌ **Mutable memento**:
```java
// WRONG: Allows modification
public void setContent(String content) {
    this.content = content;
}
```

✅ **Correct**: No setters at all!

### Testing Approach

1. **Basic operations**: Test write, delete, cursor independently
2. **State capture**: Verify save() captures exact state
3. **State restoration**: Verify restore() recovers exact state
4. **Multiple snapshots**: Test independence of mementos
5. **History operations**: Test push/pop behavior
6. **Edge cases**: Empty content, cursor at boundaries

---

## Exercise 2: Order Management with Snapshots

### Implementation Strategy

#### Order State Management

Order has complex state:
```java
public class Order {
    private final String orderId;
    private final String symbol;
    private final int quantity;
    private final BigDecimal price; // Can be null for market orders
    
    private OrderStatus orderStatus;
    private int filledQuantity;
    private BigDecimal averageFillPrice;
    private final LocalDateTime submittedAt;
    private LocalDateTime lastUpdated;
}
```

**Immutable fields** (final): orderId, symbol, quantity, price, submittedAt
**Mutable fields**: orderStatus, filledQuantity, averageFillPrice, lastUpdated

#### Snapshot Structure

The nested Snapshot class must capture ALL state:

```java
public static final class Snapshot {
    private final String orderId;
    private final String symbol;
    private final int quantity;
    private final BigDecimal price; // Can be null
    
    private final OrderStatus orderStatus;
    private final int filledQuantity;
    private final BigDecimal averageFillPrice;
    private final LocalDateTime submittedAt;
    private final LocalDateTime lastUpdated;
    private final LocalDateTime snapshotTime;
    
    private Snapshot(Order order) {
        // Copy all fields
        // Note: BigDecimal is immutable, safe to assign
        // LocalDateTime is immutable, safe to assign
        this.snapshotTime = LocalDateTime.now();
    }
}
```

#### Average Fill Price Calculation

This is the trickiest part!

**Formula**:
```
newAvg = (oldAvg × oldQty + fillPrice × fillQty) / (oldQty + fillQty)
```

**Implementation**:
```java
public void fillPartial(int fillQuantity, BigDecimal fillPrice) {
    // Calculate new average
    if (this.filledQuantity == 0) {
        // First fill
        this.averageFillPrice = fillPrice;
    } else {
        // Weighted average
        BigDecimal oldTotal = averageFillPrice.multiply(
            new BigDecimal(filledQuantity));
        BigDecimal newFill = fillPrice.multiply(
            new BigDecimal(fillQuantity));
        BigDecimal totalAmount = oldTotal.add(newFill);
        int totalQuantity = filledQuantity + fillQuantity;
        
        this.averageFillPrice = totalAmount.divide(
            new BigDecimal(totalQuantity),
            2, // 2 decimal places
            RoundingMode.HALF_UP
        );
    }
    
    this.filledQuantity += fillQuantity;
    this.orderStatus = (filledQuantity == quantity) 
        ? OrderStatus.EXECUTED 
        : OrderStatus.PARTIALLY_FILLED;
    this.lastUpdated = LocalDateTime.now();
}
```

**Key points**:
- Use `BigDecimal.multiply()`, not `*`
- Use `BigDecimal.add()`, not `+`
- Use `BigDecimal.divide()` with scale and rounding mode
- Always set scale to 2 for currency
- Use `RoundingMode.HALF_UP` for standard rounding

#### Restoration Logic

```java
public void restore(Snapshot snapshot) {
    // Restore mutable fields only
    this.orderStatus = snapshot.getOrderStatus();
    this.filledQuantity = snapshot.getFilledQuantity();
    this.averageFillPrice = snapshot.getAverageFillPrice();
    this.lastUpdated = snapshot.getLastUpdated();
    
    // Don't restore immutable fields (orderId, symbol, etc.)
    // They never change!
}
```

#### TransactionManager Implementation

Manage checkpoints per order:

```java
public class TransactionManager {
    private final Map<String, Deque<Snapshot>> checkpoints = new HashMap<>();
    private final int maxCheckpointsPerOrder;
    
    public void checkpoint(Order order) {
        String orderId = order.getOrderId();
        
        // Get or create deque for this order
        Deque<Snapshot> history = checkpoints.computeIfAbsent(
            orderId, k -> new ArrayDeque<>());
        
        // Enforce limit
        if (history.size() >= maxCheckpointsPerOrder) {
            history.removeFirst(); // Evict oldest
        }
        
        // Add new checkpoint
        history.push(order.snapshot());
    }
    
    public boolean rollback(Order order) {
        Deque<Snapshot> history = checkpoints.get(order.getOrderId());
        if (history == null || history.isEmpty()) {
            return false;
        }
        
        Snapshot snapshot = history.pop();
        order.restore(snapshot);
        return true;
    }
}
```

**Key design choices**:
- `Map<String, Deque<Snapshot>>` allows multiple orders
- `computeIfAbsent` creates deque lazily
- `removeFirst()` for FIFO eviction when limit reached
- `push()` adds to front, `pop()` removes from front (LIFO undo)

#### Multi-step Rollback

```java
public boolean rollbackTo(Order order, int stepsBack) {
    if (stepsBack <= 0) return false;
    
    Deque<Snapshot> history = checkpoints.get(order.getOrderId());
    if (history == null) return false;
    
    // Pop stepsBack times
    Snapshot snapshot = null;
    for (int i = 0; i < stepsBack && !history.isEmpty(); i++) {
        snapshot = history.pop();
    }
    
    if (snapshot != null) {
        order.restore(snapshot);
        return true;
    }
    return false;
}
```

### Common Mistakes

❌ **Shallow copy of BigDecimal**:
```java
// WRONG (but actually OK - BigDecimal is immutable)
this.price = order.price; // This is fine!

// But be careful with mutable types
this.someList = order.someList; // WRONG if mutable
this.someList = new ArrayList<>(order.someList); // Correct
```

❌ **Incorrect average calculation**:
```java
// WRONG: Integer division
averageFillPrice = (oldAvg * oldQty + price * qty) / (oldQty + qty);
```

❌ **Forgetting scale on BigDecimal**:
```java
// WRONG: No scale specified
result = amount.divide(quantity); // ArithmeticException!

// CORRECT:
result = amount.divide(quantity, 2, RoundingMode.HALF_UP);
```

❌ **Modifying immutable fields on restore**:
```java
// WRONG: orderId never changes!
public void restore(Snapshot s) {
    this.orderId = s.getOrderId(); // Don't do this
}
```

❌ **Not enforcing history limit**:
```java
// WRONG: Unbounded history
public void checkpoint(Order order) {
    history.push(order.snapshot()); // Grows forever!
}

// CORRECT: Check limit first
```

### Testing Strategy

1. **Snapshot completeness**: Verify all fields captured
2. **Restoration accuracy**: Verify all fields restored
3. **Partial fill math**: Test average price calculation with multiple fills
4. **Checkpoint independence**: Multiple snapshots don't interfere
5. **Rollback operations**: Single and multi-step rollback
6. **History limits**: Old checkpoints evicted properly
7. **Edge cases**: No checkpoints, rollback beyond history
8. **Timestamp validation**: Proper timestamp management
9. **Null handling**: Market orders (null price)

### BigDecimal Best Practices

**Always**:
- Use `new BigDecimal("150.00")` not `new BigDecimal(150.00)` (string constructor is exact)
- Specify scale when dividing: `.divide(divisor, scale, roundingMode)`
- Use `compareTo()` for comparisons, not `equals()`
- Create new instances (immutable anyway)

**Example**:
```java
BigDecimal amount = new BigDecimal("100.00");
BigDecimal quantity = new BigDecimal("3");
BigDecimal avg = amount.divide(quantity, 2, RoundingMode.HALF_UP);
// Result: 33.33
```

---

## Pattern Insights

### Why Nested Class?

Nested memento class provides:
1. **Natural access control**: Outer class can access private constructor
2. **Encapsulation**: Memento internals hidden from outside
3. **Cohesion**: Memento and Originator logically related
4. **Clarity**: Clear ownership relationship

### Memento vs Command for Undo

**Use Memento when**:
- Complex state (many fields)
- State changes are complex
- Need complete rollback

**Use Command when**:
- Simple operations
- Easy to compute inverse
- Memory is tight

**Example**: Text editor
- Memento: Save entire document state
- Command: Store deleted text, position for undo

For Exercise 1, Memento is simpler. For large documents, Command might be more efficient.

### Memory Considerations

Each memento stores full state:
- Order memento: ~200 bytes
- 1000 orders × 10 checkpoints = ~2 MB

Not a problem for most applications, but:
- Limit history per object
- Consider delta snapshots for large state
- Compress old snapshots if needed

### When to Checkpoint

**Good checkpointing strategy**:
- Before risky operations
- After successful state transitions
- At user-defined save points
- Before external system calls

**Example** (Exercise 2):
```
✅ Checkpoint after risk check (before execution)
✅ Checkpoint after each partial fill (in case connection lost)
✅ Checkpoint before cancellation (in case operation fails)
❌ Don't checkpoint on every property change (too many)
```

---

## Common Test Failures

### "Memento not immutable"

**Cause**: Setters on memento class or mutable fields

**Fix**: Remove setters, use `final` fields

### "Average price incorrect"

**Cause**: Integer division or wrong formula

**Fix**: Use BigDecimal with proper scale

### "Cursor position wrong after write"

**Cause**: Forgot to update cursor after inserting text

**Fix**: Set cursor to position after inserted text

### "Checkpoint eviction doesn't work"

**Cause**: Checking size after adding, not before

**Fix**: Check limit before push, remove oldest if needed

---

## Extension Ideas

### Exercise 1 Extensions

1. **Redo support**: Add second stack for redo
2. **Named saves**: Map of named checkpoints
3. **Diff viewer**: Show what changed since last save

### Exercise 2 Extensions

1. **Snapshot metadata**: Add tags, reasons, user who created
2. **Audit trail**: Log all checkpoints and restores
3. **Snapshot compression**: Delta encoding for space savings
4. **Concurrent access**: Thread-safe checkpoint management
5. **Persistence**: Save snapshots to database for recovery

---

## Key Takeaways

1. **Encapsulation is paramount**: Use nested classes with private constructors
2. **Immutability prevents bugs**: No setters on mementos
3. **Memory management matters**: Limit history size
4. **Defensive copying**: Copy mutable state (though BigDecimal/String are immutable)
5. **Clear responsibilities**:
   - Originator: Creates and restores
   - Memento: Stores state
   - Caretaker: Manages history

The Memento pattern provides clean state management without breaking encapsulation - essential for undo/redo and transaction systems.
