# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation (Logging Chain)

### Design Approach

**Chain Purpose**: Route log messages to appropriate handlers based on severity.

**Key Components**:

1. **LogLevel Enum**:
   - Each level has numeric severity (DEBUG=1, INFO=2, WARN=3, ERROR=4)
   - Higher number = higher severity
   - Use for comparison in handlers

2. **Abstract LogHandler**:
   - Base class for all handlers
   - Manages chain traversal
   - Template for handling logic

3. **Concrete Handlers**:
   - Each handles specific severity level
   - ConsoleLogger (DEBUG), FileLogger (INFO), EmailLogger (WARN), AlertLogger (ERROR)
   - Implement `write()` with specific output logic

**Handling Logic**:

```
handle(message):
  if (message.level.severity >= handlerLevel.severity):
    write(message)
  
  if (next != null):
    next.handle(message)
```

**Key Decision**: Should handler continue chain after processing?
- **Option A** (Pure Chain): Stop after first handler processes
- **Option B** (Impure Chain): All handlers at or above severity process
- **Recommended**: Option B - allows multiple outputs (console + file + email)

**Severity Propagation**:
- DEBUG message (severity 1): Only ConsoleLogger writes
- INFO message (severity 2): ConsoleLogger + FileLogger write
- WARN message (severity 3): Console + File + Email write
- ERROR message (severity 4): All handlers write

**Fluent Chain Building**:
```
setNext(LogHandler next):
  this.next = next
  return this  // Enable chaining

Usage:
new ConsoleLogger()
  .setNext(new FileLogger(...))
  .setNext(new EmailLogger(...))
  .setNext(new AlertLogger(...))
```

**Testing Strategy**:
- Store written messages in List for verification
- Count how many handlers processed each message
- Verify severity filtering works
- Check chain order matters

### Common Pitfalls

- Forgetting to call `next.handle()` (chain breaks)
- Not checking `next != null` before delegating (NullPointerException)
- Wrong severity comparison (< vs >=)
- Not returning `this` from `setNext()` (breaks fluent chaining)
- Handler processes message it shouldn't (wrong level check)

## Exercise 2: Real-World Simulation (Support Ticket Routing)

### Design Approach

**Chain Purpose**: Route support tickets to appropriate handler based on complexity, category, and priority.

**Handler Decision Logic**:

Each handler's `canHandle()` checks multiple conditions:

```
L1SupportHandler.canHandle(ticket):
  return (complexity < 30) OR 
         (category == GENERAL) OR 
         (category == ACCOUNT)

L2SupportHandler.canHandle(ticket):
  return (complexity >= 30 && complexity < 70) OR 
         (category == BILLING)

L3SupportHandler.canHandle(ticket):
  return (complexity >= 70 && complexity < 90) OR 
         (category == TECHNICAL && priority == HIGH)

EngineeringHandler.canHandle(ticket):
  return (complexity >= 90) OR 
         (priority == CRITICAL)
```

**Multiple Criteria Routing**:
Handlers can handle tickets outside their complexity range if specialized:
- Billing issues always go to L2 (regardless of complexity)
- Critical priority always goes to Engineering (regardless of complexity)
- Technical + High priority goes to L3

**Chain Traversal Flow**:

```
handle(ticket):
  if (canHandle(ticket)):
    result = process(ticket)
    handledCount++
    return result
  else if (next != null):
    escalatedCount++
    return next.handle(ticket)
  else:
    throw UnhandledTicketException(ticket)
```

**Escalation Override**:

```
handleWithEscalation(ticket, forceEscalate):
  if (forceEscalate && next != null):
    return next.handle(ticket)  // Skip current handler
  else:
    return handle(ticket)  // Normal flow
```

**Statistics Tracking**:
- `handledCount`: Incremented when handler processes ticket
- `escalatedCount`: Incremented when handler passes to next
- Per-handler tracking for analytics

**Audit Trail Design**:

```
TicketAudit {
  Map<String, List<String>> handlerHistory
  
  recordHandling(ticketId, handlerName, successful):
    history.computeIfAbsent(ticketId, k -> new ArrayList<>())
           .add(handlerName + ":" + successful)
  
  getHandlerHistory(ticketId):
    return history.getOrDefault(ticketId, emptyList())
}
```

**Chain Building Strategies**:

Default chain (complexity-based):
```
L1 → L2 → L3 → Engineering
```

Priority chain (priority-first):
```
Engineering → L3 → L2 → L1
```

Custom chain:
```
buildCustomChain(List<SupportHandler> handlers):
  for i = 0 to handlers.size() - 2:
    handlers[i].setNext(handlers[i+1])
  return handlers[0]
```

### Handling Results

**Successful handling**:
```
HandlingResult(
  handled = true,
  handlerName = "L2 Support",
  resolution = "Issue resolved by checking account settings",
  processingTimeMillis = <duration>
)
```

**Unhandled (end of chain)**:
```
HandlingResult(
  handled = false,
  handlerName = "None",
  resolution = "No handler could process ticket",
  processingTimeMillis = 0
)
```

### Testing Strategy

**Routing tests**:
- Low complexity (10) → L1
- Medium complexity (50) → L2
- High complexity (80) → L3
- Critical complexity (95) → Engineering

**Category override tests**:
- Billing (even if complexity 10) → L2
- Technical + High priority → L3

**Priority override tests**:
- CRITICAL priority → Engineering (even if complexity 20)

**Edge cases**:
- Complexity exactly at boundary (30, 70, 90)
- Missing category
- Null ticket
- Empty chain

**Statistics tests**:
- Handler processed 5 tickets → handledCount = 5
- Handler escalated 3 tickets → escalatedCount = 3

### Common Pitfalls

- Not checking multiple conditions in `canHandle()` (OR vs AND)
- Wrong complexity boundaries (< 30 vs <= 30)
- Forgetting priority override (CRITICAL should bypass complexity)
- Not tracking statistics correctly
- Not updating ticket status in `process()`
- Not handling end-of-chain gracefully
- Incorrect escalation logic (still checking canHandle when forcing)
- Not recording audit trail
- Not measuring processing time

## Design Pattern Insights

### Chain of Responsibility vs. Decorator

**Chain of Responsibility**:
- Request handled by ONE handler (typically)
- Handlers can refuse to handle
- Linear flow with potential early termination
- Focus: "Who should handle this?"

**Decorator**:
- All decorators process request
- No refusal, always wraps
- Nested execution (before/after)
- Focus: "Add behavior"

### Chain of Responsibility vs. Command

**Chain of Responsibility**:
- Multiple potential executors
- Runtime selection of executor
- Request routing pattern

**Command**:
- Single executor (receiver)
- Encapsulates request as object
- Request encapsulation pattern

### When to Use Chain of Responsibility

✅ **Use when:**
- Multiple objects can handle a request
- Handler not known in advance
- Want to avoid coupling sender to receiver
- Set of handlers changes dynamically
- Want early termination on first success

❌ **Avoid when:**
- Only one handler exists (use direct call)
- All handlers must process (use Observer or Composite)
- Performance critical (chain traversal has overhead)
- Handler order doesn't matter (use different pattern)

### Real-World Applications

**Servlet Filters** (Java EE):
```
Authentication → Authorization → Logging → Compression → Handler
```

**Express.js Middleware**:
```
app.use(cors())
   .use(auth())
   .use(rateLimit())
   .use(handler)
```

**Event Bubbling** (DOM):
```
Element → Parent → Grandparent → Document → Window
```

## Verification Checklist

### Isolation Exercise
- [ ] LogLevel enum with severity values
- [ ] Abstract LogHandler manages chain
- [ ] Concrete handlers implement specific logging
- [ ] Severity filtering works correctly
- [ ] Chain can be built fluently
- [ ] Messages processed by correct handlers
- [ ] Higher severity messages reach multiple handlers

### Simulation Exercise
- [ ] Multiple routing criteria (complexity, category, priority)
- [ ] Handlers correctly decide if they can handle
- [ ] Chain traverses until handler found
- [ ] Escalation override works
- [ ] Statistics tracked per handler
- [ ] Audit trail records all processing
- [ ] Unhandled tickets raise exception
- [ ] Processing time measured
- [ ] Ticket status updated correctly
