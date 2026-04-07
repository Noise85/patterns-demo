# Alternative State Pattern Implementation
## Template Method at Interface Level Using Default Methods

This package demonstrates a **refined implementation** of the State pattern that eliminates boilerplate by combining it with the Template Method pattern at the interface level using Java 8+ default methods.

---

## The Problem This Solves

Traditional State pattern implementations suffer from excessive boilerplate:

```java
// Traditional approach - LOTS of repetitive code
public class DraftState implements DocumentState {
    @Override public void edit(Document doc) { /* actual logic */ }
    @Override public void submit(Document doc) { /* actual logic */ }
    @Override public void approve(Document doc) { throw new IllegalStateException("..."); }
    @Override public void reject(Document doc) { throw new IllegalStateException("..."); }
    @Override public void archive(Document doc) { throw new IllegalStateException("..."); }
    @Override public String getStateName() { return "Draft"; }
}

// ArchivedState - 5 methods just to throw exceptions!
public class ArchivedState implements DocumentState {
    @Override public void edit(Document doc) { throw new IllegalStateException("..."); }
    @Override public void submit(Document doc) { throw new IllegalStateException("..."); }
    @Override public void approve(Document doc) { throw new IllegalStateException("..."); }
    @Override public void reject(Document doc) { throw new IllegalStateException("..."); }
    @Override public void archive(Document doc) { throw new IllegalStateException("..."); }
    @Override public String getStateName() { return "Archived"; }
}
```

**Problem**: Each state must implement the entire interface, even when most methods just throw exceptions.

---

## The Solution: Default Interface Methods

By leveraging Java 8+ default methods, we move the "template" behavior into the interface itself:

```java
public interface DocumentState {
    String getStateName(); // Only abstract method
    
    // Default implementations - override only when needed
    default void edit(Document doc) {
        throw new IllegalStateException("Cannot edit in " + getStateName());
    }
    
    default void submit(Document doc) {
        throw new IllegalStateException("Cannot submit in " + getStateName());
    }
    
    // ... etc. for approve(), reject(), archive()
}
```

Now concrete states **only override what they support**:

```java
// DraftState - only 3 methods needed!
public class DraftState implements DocumentState {
    @Override public String getStateName() { return "Draft"; }
    @Override public void edit(Document doc) { doc.incrementEditCount(); }
    @Override public void submit(Document doc) { doc.setState(new ReviewState()); }
    // approve(), reject(), archive() use defaults - automatic exceptions!
}

// ArchivedState - just 1 method!
public class ArchivedState implements DocumentState {
    @Override public String getStateName() { return "Archived"; }
    // All operations use defaults - all throw exceptions automatically!
}
```

---

## Benefits

### ✅ Eliminates Boilerplate
- **67% less code**: DraftState goes from ~40 lines to ~12 lines
- **83% less code**: ArchivedState goes from ~30 lines to ~5 lines
- Focus on **what matters**: Only implement valid operations

### ✅ DRY Principle
- Exception-throwing logic in **one place** (interface)
- Consistent error messages across all states
- Change exception handling **once**, affects all states

### ✅ Self-Documenting
- Interface clearly shows default behavior
- States explicitly override only what they support
- Intent immediately clear: "If not overridden, operation is invalid"

### ✅ Easy to Extend
- Add new operation to interface with default implementation
- Existing states **automatically get** sensible default behavior
- No need to update every state class

### ✅ Maintains Interface Flexibility
- Still an interface (not an abstract class)
- States can implement multiple interfaces if needed
- Compatible with Java's multiple interface inheritance

---

## Comparison with Other Approaches

| Approach | Code per State | When to Use |
|----------|---------------|-------------|
| **Traditional Interface** | ~40 lines | When you want explicit implementation everywhere |
| **Abstract Base Class** | ~12 lines | When single inheritance is acceptable |
| **Default Interface Methods** ✅ | ~12 lines | When you need interface flexibility + low boilerplate |
| **Enum** | Variable | When states have no instance data and are fixed |

---

## Design Patterns Combined

This implementation elegantly combines three patterns:

1. **State Pattern**: Encapsulate state-specific behavior in separate classes
2. **Template Method**: Default methods provide template for invalid operations
3. **Strategy Pattern**: Interface defines pluggable state behavior algorithms

```
┌─────────────────────────────────────────────┐
│        DocumentState Interface              │
│  (Template Method via default methods)     │
├─────────────────────────────────────────────┤
│ + getStateName(): String                    │
│ + edit(doc): void [default throws]          │
│ + submit(doc): void [default throws]        │
│ + approve(doc): void [default throws]       │
│ + reject(doc): void [default throws]        │
│ + archive(doc): void [default throws]       │
└─────────────────────────────────────────────┘
                     △
                     │ implements
         ┌───────────┼───────────┬───────────┐
         │           │           │           │
    DraftState  ReviewState  PublishedState  ArchivedState
    (override    (override    (override       (overrides
     edit,        approve,     archive        nothing -
     submit)      reject)      only)          all defaults)
```

---

## Real-World Benefits

### Code Maintenance
```java
// Need to add a new operation? Easy!
public interface DocumentState {
    // ... existing methods ...
    
    // New operation with sensible default
    default void comment(Document doc, String comment) {
        throw new IllegalStateException("Cannot comment in " + getStateName());
    }
}

// DraftState can optionally support it
public class DraftState implements DocumentState {
    // ... existing overrides ...
    
    @Override
    public void comment(Document doc, String comment) {
        doc.addComment(comment);  // Only Draft allows comments
    }
}

// All other states automatically throw exception - no changes needed!
```

### Testing
```java
@Test
void verifyArchivedStateRejectsAllOperations() {
    DocumentState archived = new ArchivedState();
    Document doc = new Document("test");
    doc.setState(archived);
    
    // All operations should throw - easy to verify
    assertThatThrownBy(() -> doc.edit()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> doc.submit()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> doc.approve()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> doc.reject()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> doc.archive()).isInstanceOf(IllegalStateException.class);
}
```

---

## When to Use This Approach

### ✅ Use Default Interface Methods When:
- Working with **Java 8+** projects
- Need **interface flexibility** (multiple inheritance)
- Have **5+ operations** per state (more boilerplate to eliminate)
- States are **stateless** or have minimal state
- Want **easy extensibility** without breaking existing code
- Team prefers **composition over inheritance**

### ❌ Consider Abstract Base Class Instead When:
- Need **protected helper methods** states can call
- Want to enforce **single inheritance chain**
- Need **final methods** that states cannot override
- Have **common state** shared across all concrete states

### ❌ Consider Enum Instead When:
- States have **no instance variables** at all
- **Fixed set of states** that won't change
- **Simple transitions** with minimal logic
- **Performance critical** (avoid object allocation)

---

## Code Statistics

### Lines of Code Comparison

**Traditional Approach (isolation package)**:
- DraftState: 38 lines
- ReviewState: 35 lines
- PublishedState: 35 lines
- ArchivedState: 33 lines
- **Total state implementations: 141 lines**

**Alternative Approach (this package)**:
- DocumentState interface: 60 lines (includes defaults + docs)
- DraftState: 12 lines
- ReviewState: 12 lines
- PublishedState: 10 lines
- ArchivedState: 8 lines
- **Total state implementations: 42 lines** (70% reduction!)

---

## Usage Example

```java
// Create document
Document doc = new Document("Design Patterns Guide");

// Edit in Draft state
doc.edit();  // OK - DraftState overrides this
doc.edit();  // OK - can edit multiple times

// Submit for review
doc.submit();  // OK - DraftState overrides this
               // Now in ReviewState

// Try invalid operations
doc.edit();    // IllegalStateException - uses default from interface
doc.submit();  // IllegalStateException - uses default from interface

// Valid operations in Review
doc.approve(); // OK - ReviewState overrides this
               // Now in PublishedState

// Archive
doc.archive(); // OK - PublishedState overrides this
               // Now in ArchivedState

// Terminal state - all operations use defaults
doc.edit();    // IllegalStateException
doc.submit();  // IllegalStateException
doc.approve(); // IllegalStateException
// ... etc. - ArchivedState doesn't override anything!
```

---

## Running the Tests

```bash
# Test alternative implementation
mvn test -Dtest=AlternativeExerciseTest

# Test all state pattern variations
mvn test -Dtest=*ExerciseTest

# Compare implementations
diff -r src/main/java/com/patterns/state/isolation/ \
        src/main/java/com/patterns/state/alternative/
```

---

## Key Takeaways

1. **Default interface methods** are perfect for the Template Method pattern
2. Dramatically reduces boilerplate while maintaining flexibility
3. Makes states **self-documenting** - only show what they support
4. Combines multiple patterns elegantly
5. Easy to extend without breaking existing code

This is how modern Java developers should implement the State pattern! 🎯
