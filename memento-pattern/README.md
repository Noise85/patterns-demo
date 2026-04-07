# Memento Pattern

## Intent

**Capture and externalize an object's internal state without violating encapsulation**, so the object can be restored to this state later.

The Memento pattern provides the ability to **save and restore the previous state of an object** without revealing the details of its implementation.

---

## Problem

You need to implement undo/redo functionality, snapshots, or rollback capabilities in your application, but:

1. **Exposing internal state breaks encapsulation**: Making fields public violates object-oriented principles
2. **Direct state access is fragile**: Clients shouldn't manipulate internal state directly
3. **State persistence is complex**: Capturing complex object graphs is error-prone
4. **Undo/redo requires history**: You need to store multiple states without coupling to implementation

### Real-World Scenarios

- **Text editors**: Undo/redo operations require saving previous document states
- **Transactional systems**: Database transactions need rollback capabilities
- **Game save systems**: Games checkpoint player progress for save/load
- **Version control**: Track changes to documents, configurations, or objects
- **Form wizards**: Navigate back/forth through multi-step forms preserving state
- **Order processing**: Snapshot orders before risk checks or execution

---

## Solution

The Memento pattern involves three key participants:

### Structure

```
┌─────────────┐         creates        ┌─────────────┐
│  Originator │────────────────────────>│   Memento   │
│             │                         │             │
│ - state     │ restores from          │ - state     │
│             │<────────────────────────│             │
│ save()      │                         │ getState()  │
│ restore()   │                         └─────────────┘
└─────────────┘                                ^
       │                                       │
       │                                       │ stores
       v                                       │
┌─────────────┐                         ┌─────────────┐
│   Client    │                         │  Caretaker  │
└─────────────┘                         │             │
                                        │ - mementos  │
                                        │             │
                                        │ undo()      │
                                        │ redo()      │
                                        └─────────────┘
```

### Components

1. **Originator**
   - The object whose state needs to be saved
   - Creates memento containing snapshot of current state
   - Uses memento to restore previous state
   - Has full access to memento's state

2. **Memento**
   - Stores internal state of the Originator
   - Protects against access by objects other than Originator
   - May have two interfaces:
     - **Wide interface** (for Originator): Full state access
     - **Narrow interface** (for Caretaker): Limited/no access
   - Typically immutable

3. **Caretaker**
   - Responsible for memento's safekeeping
   - Never operates on or examines memento contents
   - Manages history (stack for undo/redo)
   - Requests mementos from Originator and passes them back when needed

---

## Key Concepts

### Encapsulation Preservation

The Memento pattern maintains encapsulation boundaries:

```java
// Originator controls state access
public class Editor {
    private String content;
    private int cursorPosition;
    
    // Only Originator creates mementos
    public Memento save() {
        return new Memento(content, cursorPosition);
    }
    
    // Only Originator restores from mementos
    public void restore(Memento memento) {
        this.content = memento.getContent();
        this.cursorPosition = memento.getCursorPosition();
    }
    
    // Memento is immutable and opaque to outsiders
    public static class Memento {
        private final String content;
        private final int cursorPosition;
        
        private Memento(String content, int cursorPosition) {
            this.content = content;
            this.cursorPosition = cursorPosition;
        }
        
        // Package-private or protected access
        String getContent() { return content; }
        int getCursorPosition() { return cursorPosition; }
    }
}
```

**Key points**:
- Memento constructor is private/package-private
- Caretaker only sees opaque Memento type
- Originator has full access to restore state
- No public setters on Memento (immutability)

### Memento Placement

Where should the Memento class live?

1. **Nested class** (most common): `Editor.Memento`
   - ✅ Strong encapsulation
   - ✅ Natural access control
   - ✅ Clear ownership

2. **Separate class**: `EditorMemento`
   - ✅ Cleaner if Memento is large
   - ⚠️ Requires careful access control (package-private)

3. **Record** (Java 14+): `record Memento(String content, int position) {}`
   - ✅ Immutability by default
   - ✅ Concise
   - ⚠️ Public accessors (acceptable if nested)

---

## Memento vs. Similar Patterns

### Memento vs. Command

| Aspect | Memento | Command |
|--------|---------|---------|
| **Purpose** | Save/restore state | Encapsulate operations |
| **Undo mechanism** | Restore previous state snapshot | Execute inverse operation |
| **Complexity** | Simpler for complex state | Simpler for simple operations |
| **Memory** | Stores full state copies | Stores operation parameters |
| **Example** | Game save (entire world state) | Text deletion (just deleted text) |

**Use together**: Command for operations, Memento for state snapshots

### Memento vs. Prototype

| Aspect | Memento | Prototype |
|--------|---------|---------|
| **Purpose** | Capture state for restoration | Copy object for duplication |
| **Restoration** | Restore to original object | Create new object |
| **Visibility** | Internal state hidden | Clone may be deep or shallow |
| **Use case** | Undo/redo, rollback | Object creation optimization |

### Memento vs. Serialization

| Aspect | Memento | Serialization |
|--------|---------|---------|
| **Encapsulation** | Preserved (internal access) | Broken (exposes structure) |
| **Coupling** | Low (internal to Originator) | High (tied to field names) |
| **Flexibility** | Can save partial state | Saves all serializable fields |
| **Use case** | In-memory undo/redo | Persistence, network transfer |

---

## Implementation Considerations

### 1. Memento Immutability

Always make mementos immutable:

```java
public static final class Memento {
    private final String state;
    private final LocalDateTime timestamp;
    
    private Memento(String state) {
        this.state = state;
        this.timestamp = LocalDateTime.now();
    }
    
    // No setters - immutable
    String getState() { return state; }
    LocalDateTime getTimestamp() { return timestamp; }
}
```

### 2. Memory Management

Be mindful of memory usage:

```java
public class History {
    private final Deque<Memento> mementos = new ArrayDeque<>();
    private static final int MAX_HISTORY = 50; // Limit history size
    
    public void push(Memento memento) {
        if (mementos.size() >= MAX_HISTORY) {
            mementos.removeFirst(); // Evict oldest
        }
        mementos.push(memento);
    }
}
```

### 3. Incremental Snapshots

For large state, consider **delta snapshots**:

```java
public class DeltaMemento {
    private final Memento baseSnapshot;
    private final List<Change> changes; // Only store changes
}
```

### 4. Access Control

Use nested classes for strong encapsulation:

```java
public class Originator {
    // Nested class has access to Originator's private members
    public static class Memento {
        private final Map<String, Object> state;
        
        // Only Originator can create
        private Memento(Map<String, Object> state) {
            this.state = new HashMap<>(state);
        }
        
        // Package-private access for Originator
        Map<String, Object> getState() {
            return new HashMap<>(state);
        }
    }
}
```

---

## When to Use Memento

✅ **Use Memento when**:
- You need to implement undo/redo functionality
- You need to create snapshots of object state
- You want rollback capabilities (transactions, games, forms)
- You must preserve encapsulation while saving state
- The object's state can be captured as a cohesive snapshot

❌ **Consider alternatives when**:
- Simple operations where Command pattern's inverse operations suffice
- State is mostly immutable (no need to restore)
- Memory constraints are severe (snapshots are expensive)
- You need to persist state across application restarts (use serialization + Memento)

---

## Common Use Cases

### 1. Text Editor Undo/Redo

```java
editor.type("hello");
Memento m1 = editor.save();
editor.type(" world");
editor.restore(m1); // Back to "hello"
```

### 2. Database Transactions

```java
Transaction tx = db.beginTransaction();
Memento snapshot = tx.snapshot();
try {
    tx.execute(operations);
    tx.commit();
} catch (Exception e) {
    tx.restore(snapshot); // Rollback
}
```

### 3. Game Checkpoints

```java
GameState checkpoint = game.saveCheckpoint();
player.continueFrom(dangerousPosition);
if (player.died()) {
    game.loadCheckpoint(checkpoint);
}
```

### 4. Form Wizards

```java
Wizard wizard = new Wizard();
wizard.goToStep2();
Memento step2State = wizard.save();
wizard.goToStep3();
wizard.back(); // Uses memento to restore step2State
```

---

## Best Practices

### ✅ DO

1. **Make mementos immutable** - Prevent tampering with saved state
2. **Use nested classes** - Leverage natural access control
3. **Limit history size** - Prevent memory leaks from unbounded history
4. **Timestamp mementos** - Useful for debugging and auditing
5. **Consider compression** - For large state objects
6. **Document cost** - Make memory implications clear

### ❌ DON'T

1. **Don't expose memento internals** - Breaks encapsulation
2. **Don't make mementos mutable** - Defeats the purpose
3. **Don't store unnecessary state** - Only save what's needed for restoration
4. **Don't forget memory limits** - Unbounded history causes OutOfMemoryError
5. **Don't abuse for persistence** - Use proper serialization for disk storage

---

## Common Mistakes

### ❌ Public Memento Internals

```java
// BAD: Exposes internals
public class Memento {
    public String state; // Public field
}
```

**Why bad**: Caretaker can modify saved state

### ❌ Mutable Mementos

```java
// BAD: Allows modification
public class Memento {
    private String state;
    public void setState(String state) { 
        this.state = state; // Mutable
    }
}
```

**Why bad**: Saved state can be corrupted after creation

### ❌ No History Limit

```java
// BAD: Unbounded history
public void save() {
    history.add(createMemento()); // Grows forever
}
```

**Why bad**: Eventually runs out of memory

### ❌ Saving Mutable References

```java
// BAD: Shallow copy
public class Memento {
    private final List<String> items; // Shared reference!
    
    Memento(List<String> items) {
        this.items = items; // No defensive copy
    }
}
```

**Why bad**: Changes to original list affect memento

---

## Testing Strategy

### Test State Capture

```java
@Test
void shouldCaptureCurrentState() {
    originator.setState("initial");
    Memento m = originator.save();
    
    originator.setState("changed");
    originator.restore(m);
    
    assertThat(originator.getState()).isEqualTo("initial");
}
```

### Test Multiple Snapshots

```java
@Test
void shouldHandleMultipleSnapshots() {
    Memento m1 = originator.save(); // state1
    originator.change();
    Memento m2 = originator.save(); // state2
    originator.change();
    Memento m3 = originator.save(); // state3
    
    originator.restore(m2); // Jump to state2
    assertThat(originator.getState()).isEqualTo(state2);
}
```

### Test Encapsulation

```java
@Test
void shouldNotExposeInternals() {
    Memento m = originator.save();
    
    // Memento should be opaque
    // No public methods to modify state
    assertThat(m).hasNoPublicFields();
}
```

### Test Immutability

```java
@Test
void shouldBeImmutable() {
    originator.setState("original");
    Memento m = originator.save();
    
    originator.setState("modified");
    
    // Memento unchanged
    originator.restore(m);
    assertThat(originator.getState()).isEqualTo("original");
}
```

---

## Real-World Examples

1. **IntelliJ IDEA**: Local History uses memento-like snapshots
2. **Git**: Commits are mementos of repository state
3. **Hibernate**: Session state snapshots for transaction rollback
4. **Photoshop**: History panel uses memento for undo/redo
5. **Video games**: Quicksave/checkpoint systems
6. **Browsers**: Back button maintains page state mementos

---

## Summary

The Memento pattern provides a clean way to implement undo/redo and state rollback while maintaining encapsulation:

- **Originator** creates and restores from mementos
- **Memento** is an immutable snapshot of state
- **Caretaker** manages history without accessing state

**Key benefits**:
- Preserves encapsulation
- Simplifies state restoration
- Enables undo/redo functionality
- Supports rollback operations

**Trade-offs**:
- Memory overhead (storing snapshots)
- May be expensive for large state
- Requires careful memory management

Use Memento when you need to capture and restore object state while keeping implementation details hidden from clients.
