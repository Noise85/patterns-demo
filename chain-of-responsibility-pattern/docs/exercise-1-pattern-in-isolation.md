# Exercise 1: Pattern in Isolation

## Objective

Implement the Chain of Responsibility pattern for a logging system where different log handlers process messages based on severity levels.

## Scenario

You're building a logging framework where log messages have different severity levels (DEBUG, INFO, WARN, ERROR). Each handler in the chain:

- Handles messages of a specific severity level
- Passes higher-severity messages to the next handler
- Can output to different destinations (console, file, email)

This demonstrates a **pure chain** where each message is handled by exactly one appropriate handler.

## Your Tasks

### 1. Define Log Level

Create `LogLevel` enum:
- `DEBUG(1)`, `INFO(2)`, `WARN(3)`, `ERROR(4)`
- Each level has a numeric severity value
- Method: `int getSeverity()`

### 2. Create Log Message

Create `LogMessage` record:
- `LogLevel level`
- `String message`
- `LocalDateTime timestamp`

### 3. Implement Abstract Handler

Create abstract `LogHandler`:
- Protected field: `LogHandler next` (next handler in chain)
- Protected field: `LogLevel handlerLevel` (level this handler processes)
- Method: `void setNext(LogHandler next)` - sets next handler, returns `this` for fluent chaining
- Method: `void handle(LogMessage message)` - main entry point
  - If `message.level().getSeverity() >= handlerLevel.getSeverity()`: call `write(message)` then pass to next
  - Else: just pass to next handler
- Abstract method: `void write(LogMessage message)` - implemented by concrete handlers

### 4. Create Concrete Handlers

Create four concrete handlers:

**ConsoleLogger** (handles DEBUG):
- Constructor: `ConsoleLogger()`
- `write()`: Outputs to simulated console (return formatted string for testing)

**FileLogger** (handles INFO):
- Constructor: `FileLogger(String filename)`
- `write()`: Simulates file writing

**EmailLogger** (handles WARN):
- Constructor: `EmailLogger(String emailAddress)`
- `write()`: Simulates email sending

**AlertLogger** (handles ERROR):
- Constructor: `AlertLogger(String alertService)`
- `write()`: Simulates critical alert

### 5. Track Handler Activity

Add to concrete handlers:
```java
List<String> getWrittenMessages()  // For testing verification
```

### 6. Build Chain

Create `LoggerChain` class:
```java
static LogHandler buildDefaultChain()
```
Builds chain: ConsoleLogger → FileLogger → EmailLogger → AlertLogger

## Example Usage

```java
// Build chain
LogHandler chain = LoggerChain.buildDefaultChain();

// Log messages of different severities
chain.handle(new LogMessage(LogLevel.DEBUG, "Debug info", LocalDateTime.now()));
// → ConsoleLogger handles, stops there

chain.handle(new LogMessage(LogLevel.INFO, "User logged in", LocalDateTime.now()));
// → ConsoleLogger passes to FileLogger, FileLogger handles

chain.handle(new LogMessage(LogLevel.WARN, "High memory usage", LocalDateTime.now()));
// → Passes through Console, File, handled by EmailLogger

chain.handle(new LogMessage(LogLevel.ERROR, "Database connection failed", LocalDateTime.now()));
// → Passes through all, handled by AlertLogger
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Level filtering**: Each handler processes only appropriate severity levels
2. **Chain propagation**: Messages pass through chain correctly
3. **Handler execution**: Correct handler processes each message
4. **Multiple handlers**: Higher severity messages processed by multiple handlers (if configured)
5. **Chain building**: Fluent chain construction works
6. **Missing handler**: Messages with no appropriate handler are handled gracefully
7. **Handler tracking**: Can verify which handlers were invoked

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] Abstract handler manages chain traversal
- [ ] Concrete handlers implement specific logging logic
- [ ] Chain can be built fluently
- [ ] Severity-based filtering works correctly
- [ ] Each message processed by correct handler(s)

## Time Estimate

**45-60 minutes** for a developer familiar with behavioral patterns.

## Hints

- Use `LogLevel.getSeverity()` for numeric comparison
- `setNext()` should return `this` for fluent chaining: `return this;`
- In `handle()`: check severity, call `write()` if appropriate, then delegate to `next`
- Store written messages in a `List<String>` for testing verification
- Chain building: `new ConsoleLogger().setNext(new FileLogger().setNext(...))`
- DEBUG messages go to all handlers (if chain configured that way)
- ERROR messages should reach the most critical handler
- Handle `next == null` gracefully (end of chain)
