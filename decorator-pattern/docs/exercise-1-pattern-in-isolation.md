# Exercise 1: Pattern in Isolation

## Objective

Implement the Decorator pattern for a notification system that can dynamically compose message processing behaviors (encryption, compression, formatting) without modifying the core notification logic.

## Scenario

Build a flexible notification system where messages can be enhanced with various optional features:

- **Base notification**: Simple message delivery
- **Encryption**: Secure sensitive messages
- **Compression**: Reduce message size for large payloads
- **HTML formatting**: Add markup for rich content
- **Timestamp decoration**: Add send time metadata

These behaviors should be:
- Independently toggleable
- Combinable in any order
- Added/removed at runtime

## Your Tasks

### 1. Define the Component Interface

Create `Notifier` interface with:
- `send(String message)` method

### 2. Implement Concrete Component

Create `BasicNotifier`:
- Implements `Notifier`
- Stores the raw message for verification
- Simple, undecorated message delivery

### 3. Create Abstract Decorator

Create `NotifierDecorator`:
- Implements `Notifier`
- Holds a reference to another `Notifier` (the wrapped component)
- Delegates to the wrapped notifier

### 4. Implement Concrete Decorators

Create the following decorators:

**EncryptionDecorator**:
- Encrypts message before sending (use simple Caesar cipher: shift chars by 3)
- Pattern: `ENCRYPTED[shifted_text]`

**CompressionDecorator**:
- Compresses message (simulate: replace repeated chars with count)
- Example: `"aaa"` → `"a3"`, `"hello"` → `"hel2o"` 
- Pattern: `COMPRESSED[compressed_text]`

**HtmlDecorator**:
- Wraps message in HTML tags
- Pattern: `<html><body>message</body></html>`

**TimestampDecorator**:
- Adds ISO timestamp prefix
- Pattern: `[2024-01-15T10:30:00Z] message`

### 5. Key Requirements

- Each decorator must properly delegate to its wrapped component
- Decorators should be stackable in any order
- The base message should remain accessible through the chain
- All implementations must be immutable (no shared state between sends)

## Example Usage

```java
// Base notifier
Notifier notifier = new BasicNotifier();
notifier.send("Hello");  // → "Hello"

// Single decorator
notifier = new EncryptionDecorator(notifier);
notifier.send("Hello");  // → "ENCRYPTED[Khoor]"

// Stacked decorators
notifier = new CompressionDecorator(
    new EncryptionDecorator(
        new BasicNotifier()
    )
);
notifier.send("AAABBC");  // → "COMPRESSED[ENCRYPTED[DDDEE]]"

// Different order, different result
notifier = new EncryptionDecorator(
    new CompressionDecorator(
        new BasicNotifier()
    )
);
notifier.send("AAABBC");  // → "ENCRYPTED[...]" (encrypts compressed output)
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Base functionality**: `BasicNotifier` works standalone
2. **Individual decorators**: Each decorator works in isolation
3. **Decorator stacking**: Multiple decorators compose correctly
4. **Order independence**: Same decorators, different orders = different results
5. **Immutability**: Multiple calls don't interfere with each other

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] No modifications to test files
- [ ] Clean decorator chain composition
- [ ] Each decorator has single, clear responsibility
- [ ] Code follows Java 21 best practices (records where appropriate, immutability)

## Time Estimate

**45-60 minutes** for a developer familiar with object composition.

## Hints

- Use constructor injection to pass wrapped components
- Each decorator's `send()` should transform and then delegate
- Consider using Java records for immutable data where appropriate
- Think about the order of operations: when to transform vs. when to delegate
- The abstract decorator base class can handle common delegation logic
