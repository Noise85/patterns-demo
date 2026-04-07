# Exercise 2: Real-World Simulation

## Objective

Build a production-grade support ticket routing system using Chain of Responsibility with multi-level escalation, SLA tracking, and dynamic handler assignment based on ticket properties.

## Business Context

You're developing a customer support platform where tickets are automatically routed through a chain of support levels:

1. **L1 Support (Tier 1)**: Handles basic issues (<30 complexity)
2. **L2 Support (Tier 2)**: Handles intermediate issues (30-70 complexity)
3. **L3 Support (Tier 3)**: Handles advanced issues (70-90 complexity)
4. **Engineering**: Handles critical bugs (>90 complexity)

The system must:
- Route tickets based on complexity and category
- Track SLA compliance (time limits per tier)
- Support escalation (override routing)
- Maintain audit trail of which handlers processed each ticket
- Handle unprocessable tickets gracefully

## Domain Model

### Ticket
- `String id` (unique ticket ID)
- `String title`
- `String description`
- `TicketCategory category` (TECHNICAL, BILLING, ACCOUNT, GENERAL)
- `int complexityScore` (0-100)
- `Priority priority` (LOW, MEDIUM, HIGH, CRITICAL)
- `LocalDateTime created`
- `TicketStatus status` (OPEN, IN_PROGRESS, RESOLVED, ESCALATED)

### Ticket Categories
- `TECHNICAL`: Technical issues, bugs
- `BILLING`: Payment, invoices
- `ACCOUNT`: Account management
- `GENERAL`: General inquiries

### Priority Levels
- `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

### Support Handler Capabilities
Each handler has:
- Complexity range it can handle
- Specific categories it specializes in
- SLA time limit
- Success/failure tracking

## Your Tasks

### 1. Define Core Types

Create enums:
- `TicketCategory`: TECHNICAL, BILLING, ACCOUNT, GENERAL
- `Priority`: LOW, MEDIUM, HIGH, CRITICAL
- `TicketStatus`: OPEN, IN_PROGRESS, RESOLVED, ESCALATED

Create `Ticket` class:
- All fields listed above
- Method: `void setStatus(TicketStatus status)`
- Method: `void assignTo(String handlerName)`

### 2. Create Handling Result

Create `HandlingResult` record:
- `boolean handled`
- `String handlerName`
- `String resolution` (description of action taken)
- `long processingTimeMillis`

### 3. Implement Abstract Support Handler

Create abstract `SupportHandler`:
- Protected fields: `SupportHandler next`, `String handlerName`, `int minComplexity`, `int maxComplexity`
- Method: `SupportHandler setNext(SupportHandler next)` - fluent chaining
- Method: `HandlingResult handle(Ticket ticket)` - main entry point
  - Check if `canHandle(ticket)`
  - If yes: call `process(ticket)`, return result
  - If no and next exists: delegate to `next.handle(ticket)`
  - If no and no next: return "unhandled" result
- Abstract method: `boolean canHandle(Ticket ticket)` - handler-specific logic
- Abstract method: `HandlingResult process(Ticket ticket)` - handler-specific processing
- Method: `int getHandledCount()`, `int getEscalatedCount()` - statistics

### 4. Create Concrete Handlers

**L1SupportHandler**:
- Handles complexity 0-29
- Specializes in GENERAL and ACCOUNT categories
- SLA: 2 hours
- `canHandle()`: complexity in range OR category is GENERAL/ACCOUNT
- `process()`: Mark ticket IN_PROGRESS, simulate resolution

**L2SupportHandler**:
- Handles complexity 30-69
- Specializes in BILLING and basic TECHNICAL
- SLA: 4 hours
- `canHandle()`: complexity in range OR category is BILLING
- `process()`: Advanced resolution logic

**L3SupportHandler**:
- Handles complexity 70-89
- Specializes in complex TECHNICAL issues
- SLA: 8 hours
- `canHandle()`: complexity in range OR (TECHNICAL && HIGH priority)
- `process()`: Expert-level resolution

**EngineeringHandler**:
- Handles complexity 90-100 OR CRITICAL priority
- No SLA limit (critical issues)
- `canHandle()`: complexity >= 90 OR priority is CRITICAL
- `process()`: Engineering investigation

### 5. Escalation Override

Add to `SupportHandler`:
```java
HandlingResult handleWithEscalation(Ticket ticket, boolean forceEscalate)
```
If `forceEscalate`, skip current handler and go directly to next.

### 6. Handler Chain Builder

Create `SupportChainBuilder`:
```java
static SupportHandler buildDefaultChain()
static SupportHandler buildPriorityChain()  // Different order for high-priority tickets
static SupportHandler buildCustomChain(List<SupportHandler> handlers)
```

### 7. Audit Trail

Create `TicketAudit`:
- Tracks which handlers processed each ticket
- Methods:
  - `void recordHandling(String ticketId, String handlerName, boolean successful)`
  - `List<String> getHandlerHistory(String ticketId)`
  - `Map<String, Integer> getHandlerStatistics()`

### 8. Unhandled Ticket Exception

Create `UnhandledTicketException`:
- Thrown when no handler in chain can process ticket
- Constructor: `UnhandledTicketException(Ticket ticket, String reason)`

## Example Usage

```java
// Build support chain
SupportHandler chain = SupportChainBuilder.buildDefaultChain();
TicketAudit audit = new TicketAudit();

// Simple ticket → L1 handles
Ticket simple = new Ticket("T001", "Password reset", ..., TicketCategory.ACCOUNT, 15, ...);
HandlingResult result = chain.handle(simple);
assert result.handled();
assert result.handlerName().equals("L1 Support");

// Complex technical ticket → L3 handles
Ticket complex = new Ticket("T002", "API performance issue", ..., TicketCategory.TECHNICAL, 75, ...);
result = chain.handle(complex);
assert result.handlerName().equals("L3 Support");

// Critical bug → Engineering handles immediately
Ticket critical = new Ticket("T003", "Production outage", ..., TicketCategory.TECHNICAL, 95, Priority.CRITICAL, ...);
result = chain.handle(critical);
assert result.handlerName().equals("Engineering");

// Force escalation
result = chain.handleWithEscalation(simple, true);  // Skip L1, go to L2
```

## Testing Strategy

Your implementation must handle:

1. **Basic routing**: Tickets routed to correct handler based on complexity
2. **Category specialization**: Handlers prefer their specialized categories
3. **Priority override**: CRITICAL priority tickets go to Engineering
4. **Chain exhaustion**: Handle case where no handler can process
5. **Escalation**: Force escalation bypasses lower-tier handlers
6. **Statistics tracking**: Count handled vs. escalated tickets
7. **Multiple criteria**: Handlers check both complexity AND category
8. **Audit trail**: Track which handlers saw/processed each ticket
9. **Status updates**: Ticket status changes through processing
10. **Edge cases**: Boundary complexity values, missing categories

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] Tickets correctly routed based on complexity and category
- [ ] Escalation mechanism works properly
- [ ] All handlers track statistics
- [ ] Audit trail maintains complete history
- [ ] Unhandled tickets raise appropriate exceptions
- [ ] Production-quality code with proper error handling

## Time Estimate

**2-3 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **SLA monitoring**: Track if tickets resolved within SLA limits
2. **Smart routing**: Learn from past tickets to optimize routing
3. **Load balancing**: Distribute tickets among multiple handlers at same level
4. **Conditional routing**: Route based on time of day, handler availability
5. **Handler capacity**: Handlers reject tickets when at capacity
6. **Retry mechanism**: Automatic retry with different handlers on failure

## Hints

- Use complexity ranges in `canHandle()`: `complexity >= minComplexity && complexity <= maxComplexity`
- Multiple conditions: complexity OR category OR priority
- Track statistics with simple counters incremented in `process()`
- Escalation: skip current handler by calling `next.handle()` directly
- Chain building: wire handlers together with `setNext()`
- Unhandled case: check if at end of chain (`next == null`) and still can't handle
- Processing time: `System.currentTimeMillis()` before/after processing
- Audit trail: use Map<String, List<String>> for ticket history
- Priority override: check CRITICAL priority first in `canHandle()`
