# State Pattern

## Intent

The **State** pattern allows an object to alter its behavior when its internal state changes. The object will appear to change its class at runtime.

> **📌 Alternative Implementations**: This module includes multiple variations demonstrating different trade-offs:
> - [`alternative/`](src/main/java/com/patterns/state/alternative/README.md) — **Default interface methods** (Template Method pattern) — 40% less code, modern Java approach
> - [`simple/`](src/main/java/com/patterns/state/simple/README.md) — **Enum-based** state machine — Best for simple, fixed states with no instance data
> - [`isolation/`](src/main/java/com/patterns/state/isolation/) — **Traditional** interface approach — Educational reference

## Problem

Imagine you have an object that behaves differently depending on its state:
- A **document** can be in Draft, Review, or Published state
- An **order** progresses through Pending, Processing, Shipped, Delivered states
- A **media player** transitions between Stopped, Playing, Paused states
- A **vending machine** cycles through Idle, HasMoney, Dispensing states

The naive approach uses conditional logic:
```java
public void publish() {
    if (state == DRAFT) {
        state = REVIEW;
    } else if (state == REVIEW) {
        state = PUBLISHED;
    } else if (state == PUBLISHED) {
        throw new IllegalStateException("Already published");
    }
}
```

This leads to:
- **Complex conditionals**: Every method checks state with if/else or switch
- **Hard to maintain**: Adding a new state requires changing many methods
- **Error-prone**: Easy to miss state checks or handle transitions incorrectly
- **Violates SRP**: Context class handles both core logic and state transitions

## Solution

The State pattern suggests:
1. **Extract state-specific behavior** into separate state classes
2. **Define a common State interface** with methods for all possible actions
3. **Context delegates** to the current state object
4. **States handle transitions** by changing the context's current state

This achieves clean separation: each state class handles its own behavior and knows its valid transitions.

## Structure

```
┌─────────────────┐
│     Context     │
├─────────────────┤
│ - state: State  │────────┐
│ + request()     │        │
│ + setState()    │        │ delegates
└─────────────────┘        │
                           ▼
                    ┌─────────────┐
                    │    State    │
                    ├─────────────┤
                    │ + handle()  │
                    └─────────────┘
                           △
                           │ implements
           ┌───────────────┼───────────────┐
           │               │               │
    ┌──────┴──────┐ ┌──────┴──────┐ ┌──────┴──────┐
    │  StateA     │ │  StateB     │ │  StateC     │
    ├─────────────┤ ├─────────────┤ ├─────────────┤
    │ + handle()  │ │ + handle()  │ │ + handle()  │
    └─────────────┘ └─────────────┘ └─────────────┘
```

## Key Characteristics

### When to Use
- ✅ Object behavior depends on its state
- ✅ Complex conditionals spread across multiple methods
- ✅ States have different sets of valid operations
- ✅ State transitions follow defined rules
- ✅ State-specific behavior is substantial enough to warrant separate classes

### When NOT to Use
- ❌ Only 2-3 simple states with minimal behavior differences
- ❌ State transitions are trivial (simple flag toggling)
- ❌ States share almost all behavior
- ❌ Overhead of multiple classes not justified

## State vs. Strategy

| State | Strategy |
|-------|----------|
| **Context changes** its state object | Context keeps **same strategy** |
| States know about **each other** (transitions) | Strategies are **independent** |
| State determines **what can be done** | Strategy determines **how it's done** |
| States may **reference context** | Strategies usually **stateless** |
| **Internal behavior change** | **External configuration** |

Example:
- **State**: Document transitions Draft → Review → Published (states linked)
- **Strategy**: Choose compression algorithm (Zip, Gzip, Bzip2) - independent

## State vs. Visitor

| State | Visitor |
|-------|---------|
| Changes **behavior** based on state | Adds **operations** to elements |
| Single object changes state | **Multiple objects** in structure |
| About **state transitions** | About **operation separation** |
| Context **holds** current state | Elements **accept** visitors |

## Implementation Approaches

### 1. Context Controls Transitions
Context decides when to change state:
```java
public void submit() {
    if (currentState instanceof DraftState) {
        setState(new ReviewState());
    }
}
```
**Pros**: Centralized transition logic  
**Cons**: Context must know all states

### 2. States Control Transitions (Recommended)
States decide their successors:
```java
// In DraftState
public void submit(Document context) {
    context.setState(new ReviewState());
}
```
**Pros**: Encapsulated transitions, follows SRP  
**Cons**: States reference context

### 3. Transition Table
External data structure defines valid transitions:
```java
Map<Pair<State, Action>, State> transitions;
```
**Pros**: Easy to visualize, data-driven  
**Cons**: Additional complexity

## State Object Creation

### Option 1: Create on Transition
```java
context.setState(new ReviewState());  // New instance each time
```
**Use when**: States have instance variables

### Option 2: Flyweight/Singleton
```java
public class ReviewState {
    public static final ReviewState INSTANCE = new ReviewState();
    private ReviewState() {}
}
```
**Use when**: States are stateless (no instance variables)

### Option 3: State Factory
```java
StateFactory.createState(StateType.REVIEW)
```
**Use when**: Complex initialization logic

## Real-World Applications

1. **Document Workflow**: Draft → Review → Approved → Published
2. **Order Processing**: Pending → Paid → Shipped → Delivered → Cancelled
3. **TCP Connection**: Closed → Listen → Established → CloseWait
4. **Game Character**: Idle → Walking → Running → Jumping → Falling
5. **Vending Machine**: Idle → HasMoney → DispensingItem → OutOfStock
6. **Authentication**: Anonymous → Authenticated → Locked → Expired
7. **Traffic Light**: Red → Green → Yellow (with timing rules)

## Practical Considerations

### Thread Safety
- States with mutable fields need synchronization
- Consider immutable states
- Use atomic state transitions (compareAndSet)

### Testing Strategy
- Test each state independently
- Test all valid transitions
- Test invalid transitions (should throw or be no-ops)
- Test state-specific behavior

### Error Handling
Options for invalid operations:
1. **Throw exception**: `throw new IllegalStateException()`
2. **No-op**: Silently ignore
3. **Return status**: `boolean canPublish()`
4. **Guard methods**: Check `if (canPublish()) { publish(); }`

### Logging & Observability
```java
public void setState(State newState) {
    logger.info("Transitioning from {} to {}", state.getClass(), newState.getClass());
    this.state = newState;
}
```

Track:
- State transitions (with timestamp)
- Operations attempted in each state
- Invalid operations blocked

## Common Pitfalls

### 1. Giant State Classes
**Problem**: State classes become too large  
**Solution**: Extract sub-behaviors, use composition

### 2. Unclear Transition Rules
**Problem**: Hard to understand valid state flow  
**Solution**: Document state diagram, use transition table

### 3. Shared State Data
**Problem**: Multiple states need same data  
**Solution**: Keep data in Context, not states

### 4. Circular Dependencies
**Problem**: States reference each other  
**Solution**: Use factory, make states reference Context only

### 5. Missing State Validation
**Problem**: Entering invalid state  
**Solution**: Validate in setState(), use enum for valid states

## Pattern Combinations

### State + Factory
Factory creates appropriate state objects:
```java
StateFactory.createState(OrderStatus.PENDING)
```

### State + Memento
Save/restore state for undo functionality:
```java
Memento saveState() { return new Memento(currentState); }
```

### State + Observer
Notify observers of state changes:
```java
setState(newState);
notifyObservers(StateChangedEvent);
```

## Performance Considerations

- State object creation: Use flyweight for stateless states
- Transition frequency: Cache states vs. create on-demand
- State checking: Avoid instanceof, use polymorphism
- Memory: One state object vs. multiple contexts

---

## Exercises

This module contains two exercises:

1. **Pattern in Isolation** (`docs/exercise-1-pattern-in-isolation.md`)
   - Document workflow with Draft, Review, Published states
   - Focus: Basic state transitions, state-specific operations

2. **Real-World Simulation** (`docs/exercise-2-real-world-simulation.md`)
   - Order fulfillment system with complex state machine
   - Focus: Invalid operation handling, state history, conditional transitions

See individual exercise files for detailed requirements and test cases.

---

## References

- **Refactoring Guru**: https://refactoring.guru/design-patterns/state
- **Gang of Four**: Design Patterns - Elements of Reusable Object-Oriented Software
- **Martin Fowler**: Refactoring (Replace Conditional with Polymorphism)
