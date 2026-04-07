# Enum-Based State Pattern Implementation

This package demonstrates an **alternative implementation** of the State pattern using Java enums instead of separate state classes.

> **💡 Also See**: The [`alternative/`](../alternative/README.md) package shows how to reduce boilerplate using default interface methods while keeping the class-based approach.

## Comparison: Enum vs Class-Based Approach

### Files Structure

**Enum Approach (this package)**:
- `DocumentState.java` - Single enum with all states and transitions
- `Document.java` - Context class using the enum
- **Total: 2 files**

**Class-Based Approach (isolation package)**:
- `DocumentState.java` - Interface
- `DraftState.java`, `ReviewState.java`, `PublishedState.java`, `ArchivedState.java` - 4 implementations
- `Document.java` - Context class
- **Total: 6 files**

## When to Use Enum-Based State Pattern

### ✅ Best For:
- **Simple state machines** with straightforward transitions
- States with **no instance variables** (all state info in context)
- **Fixed set of states** unlikely to change
- **Performance-critical** code (no object allocation)
- State machines where **visualization is key** (all transitions visible in one file)

### ❌ Not Suitable For:
- States needing **instance data** (e.g., `PaidState(BigDecimal amount, LocalDateTime time)`)
- **Complex state-specific behavior** requiring helper methods or dependencies
- Systems needing **extensibility** (adding states without modifying existing code)
- States requiring **dependency injection** (services, repositories)

## Code Comparison

### Enum Approach
```java
public enum DocumentState {
    DRAFT {
        @Override
        public DocumentState submit() { return REVIEW; }
    },
    REVIEW {
        @Override
        public DocumentState approve() { return PUBLISHED; }
    },
    // ... all states in one file
}
```

**Pros**: Concise, type-safe, no allocation, easy to visualize  
**Cons**: Can't hold instance data, harder to extend, all methods in one place

### Class-Based Approach
```java
public class DraftState implements DocumentState {
    public void submit(Document doc) {
        doc.setState(new ReviewState());
    }
}
```

**Pros**: Rich per-state behavior, extensible, DI-friendly, testable in isolation  
**Cons**: More files, object allocation, requires navigation between classes

## Example Usage

Both approaches have the same API from the client perspective:

```java
Document doc = new Document("My Article");

doc.edit();              // Allowed in DRAFT
doc.submit();            // DRAFT → REVIEW
doc.approve();           // REVIEW → PUBLISHED
doc.archive();           // PUBLISHED → ARCHIVED

// State queries
if (doc.getState() == DocumentState.DRAFT) {
    // Enum equality check
}

// Switch expressions work great with enums
String status = switch (doc.getState()) {
    case DRAFT -> "Being edited";
    case REVIEW -> "Under review";
    case PUBLISHED -> "Live";
    case ARCHIVED -> "Archived";
};
```

## Performance Considerations

**Enum advantages**:
- Zero allocation (enums are singletons)
- Direct reference comparison (`state == DRAFT`)
- Better JVM optimization for switch statements

**Class-based considerations**:
- New object on each transition (unless using Flyweight)
- Can use singleton pattern for stateless states
- Virtual method dispatch overhead (minimal in practice)

## Testing

The enum approach makes it easier to test state transitions exhaustively:

```java
@Test
void testAllTransitions() {
    // Can iterate over all enum values
    for (DocumentState state : DocumentState.values()) {
        // Test each state systematically
    }
}
```

## Recommendation

For the **Document workflow** (this exercise):
- ✅ **Enum approach is better** - simple states, no instance data, clear visualization

For the **Order fulfillment** (simulation exercise):
- ✅ **Class-based approach is better** - complex validation, state-specific data, business logic

The best engineers choose the right tool for the job. Sometimes the classic State pattern is overkill!

## Run the Tests

```bash
# Test enum-based implementation
mvn test -Dtest=SimpleExerciseTest

# Test class-based implementation
mvn test -Dtest=IsolationExerciseTest

# Compare the two approaches
diff src/main/java/com/patterns/state/simple/ \
     src/main/java/com/patterns/state/isolation/
```
