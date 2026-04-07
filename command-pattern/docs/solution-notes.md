# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation (Text Editor with Undo/Redo)

### Design Approach

**Command Purpose**: Encapsulate text editing operations as objects supporting undo.

**Key Components**:

1. **Command Interface**:
   - `execute()`: Perform the operation
   - `undo()`: Reverse the operation
   - `getDescription()`: Human-readable description

2. **TextEditor (Receiver)**:
   - Uses `StringBuilder` for efficient text manipulation
   - Provides low-level operations: insert, delete, replace
   - Does NOT contain undo logic (that's in commands)

3. **Concrete Commands**:
   - Each command stores parameters and state needed for undo
   - `InsertTextCommand`: Stores position and text
   - `DeleteTextCommand`: Stores position, length, AND deleted text for undo
   - `ReplaceTextCommand`: Stores position, length, new text, AND replaced text for undo

**State Management for Undo**:

```
InsertTextCommand:
  execute(): editor.insertText(position, text)
  undo():    editor.deleteText(position, text.length())
  // No state to save - can reconstruct from parameters

DeleteTextCommand:
  execute(): 
    deletedText = editor.getContent().substring(position, position + length)
    editor.deleteText(position, length)
  undo():
    editor.insertText(position, deletedText)
  // MUST save deleted text during execute()

ReplaceTextCommand:
  execute():
    replacedText = editor.getContent().substring(position, position + length)
    editor.deleteText(position, length)
    editor.insertText(position, newText)
  undo():
    editor.deleteText(position, newText.length())
    editor.insertText(position, replacedText)
  // MUST save replaced text during execute()
```

**Command History (Invoker)**:

Uses two stacks:

```
executeCommand(cmd):
  cmd.execute()
  undoStack.push(cmd)
  redoStack.clear()  // New command creates new timeline

undo():
  if (!undoStack.isEmpty()):
    cmd = undoStack.pop()
    cmd.undo()
    redoStack.push(cmd)

redo():
  if (!redoStack.isEmpty()):
    cmd = redoStack.pop()
    cmd.execute()
    undoStack.push(cmd)
```

**Why Redo Stack Clears**:
When user executes new command after undo, the "future" timeline is invalidated. Can't redo down a path that no longer exists.

**Macro Command**:

Composite pattern + Command pattern:

```
execute():
  for each command:
    command.execute()

undo():
  for i = commands.size() - 1 downto 0:
    commands[i].undo()
  // Undo in REVERSE order
```

**Edge Cases**:

- Insert at invalid position: Validate or clamp to valid range
- Delete beyond end: Adjust length to available text
- Empty undo/redo stack: Check `isEmpty()` before popping
- Empty text operations: Handle gracefully (no-op)

### Common Pitfalls

- Forgetting to save state before executing (DeleteTextCommand, ReplaceTextCommand)
- Not clearing redo stack on new command execution
- Undoing macro commands in wrong order (must be reverse)
- Exposing receiver (TextEditor) directly instead of through commands
- Not validating positions/lengths before operations
- Undo/redo not symmetric (redo should restore exact state)

## Exercise 2: Real-World Simulation (Task Scheduling System)

### Design Approach

**Command Purpose**: Encapsulate background tasks as executable, retryable units with audit trails.

**Task Hierarchy**:

```
Task (interface) extends Command
  ↓
BaseTask (abstract)
  ↓
ConcreteTask (DataImport, ReportGen, Email, ApiCall)
  ↓
BatchTask (composite)
```

**Base Implementation**:

```
BaseTask:
  - Stores: taskId, priority, idempotent flag, maxRetries
  - Tracks: status, attemptCount
  
  execute():
    if (cancelled) throw exception
    status = RUNNING
    startTime = now()
    try:
      result = doExecute()  // Subclass implements
      status = COMPLETED
      return success(result)
    catch Exception:
      status = FAILED
      return failure(exception)
    finally:
      recordDuration()

  doExecute():  // Abstract - subclasses implement
    // Actual work here
```

**Priority Queue Implementation**:

```
PriorityQueue<Task> with Comparator:
  compare(task1, task2):
    priorityDiff = task2.priority.ordinal() - task1.priority.ordinal()
    if (priorityDiff != 0) return priorityDiff
    return task1.scheduledTime - task2.scheduledTime  // FIFO within priority
```

**Retry Logic**:

```
executeWithRetry(task):
  result = task.execute()
  
  if (!result.success):
    task.attemptCount++
    
    if (task.attemptCount < task.maxRetries && task.isIdempotent()):
      task.status = RETRYING
      scheduleTask(task)  // Re-queue for retry
      return result
    else:
      task.status = FAILED
      deadLetterQueue.add(task)
      return result
  
  // Success path
  task.status = COMPLETED
  recordExecution(task, result)
  return result
```

**Why Idempotent Check**:

Non-idempotent tasks (like sending emails) should NOT be retried automatically - might duplicate emails. Only retry tasks that are safe to execute multiple times.

**Batch Task Implementation**:

```
BatchTask.execute():
  results = []
  
  for task in tasks:
    result = task.execute()
    results.add(result)
    
    if (!result.success && stopOnFailure):
      // Mark remaining tasks as skipped
      return combinedFailure(results)
  
  return combinedSuccess(results)

BatchTask.undo():
  // Undo in reverse order (like macro command)
  for i = tasks.size() - 1 downto 0:
    if (tasks[i].status == COMPLETED):
      tasks[i].undo()  // If tasks support undo
```

**Execution History**:

```
TaskExecutionRecord:
  - taskId, description
  - scheduledTime, startTime, endTime
  - status, attemptCount
  - lastResult
  
  getTotalDurationMillis():
    return endTime - scheduledTime
```

Store in `Map<String, TaskExecutionRecord>` for O(1) lookup by taskId.

**Cancellation**:

```
BaseTask:
  volatile boolean cancelled = false
  
  cancel():
    this.cancelled = true
    this.status = CANCELLED
  
  execute():
    if (cancelled) throw new CancellationException()
    // Continue with execution
```

Use `volatile` for visibility across threads if implementing concurrent execution.

**Task Types Design**:

Each concrete task:
1. Defines idempotency based on operation nature
2. Sets appropriate max retries
3. Implements `doExecute()` with actual work
4. Handles task-specific failures

```
DataImportTask:
  - Idempotent: true (re-importing same data is safe)
  - MaxRetries: 3 (network issues common)
  
EmailNotificationTask:
  - Idempotent: false (duplicate emails bad)
  - MaxRetries: 1 (one retry acceptable)
  
ApiCallTask:
  - Idempotent: depends on HTTP method
    - GET, PUT: true
    - POST: false (creates new resources)
  - MaxRetries: 3
```

### Testing Strategy

**Task execution tests**:
- Create task, execute, verify result
- Check status transitions: PENDING → RUNNING → COMPLETED

**Priority tests**:
- Schedule tasks with different priorities
- Verify URGENT executes before LOW
- Within same priority, FIFO order

**Retry tests**:
- Simulate task failure
- Verify idempotent task retries
- Verify non-idempotent task doesn't retry
- Verify max retries limit enforced

**Dead letter queue tests**:
- Task fails max times → appears in DLQ
- Non-idempotent failure → straight to DLQ

**Batch tests**:
- All tasks execute → success
- One task fails with stopOnFailure=true → batch fails, remaining skipped
- One task fails with stopOnFailure=false → batch continues

**Cancellation tests**:
- Cancel before execution → not executed
- Check status becomes CANCELLED

### Common Pitfalls

- Not checking idempotent flag before retry
- Infinite retry loop (missing max retry check)
- Not tracking attempt count correctly
- Priority queue comparator inverted (low priority executes first)
- Not recording execution in history on success
- Forgetting to update status throughout execution
- Dead letter queue missing non-idempotent failures
- Batch undo in wrong order
- Not handling exceptions in task execution
- Cancellation flag not volatile (concurrent execution issues)

## Design Pattern Insights

### Command vs. Strategy

**Command**:
- Encapsulates request as object
- Often stores state (parameters, undo info)
- Can be queued, logged, undone
- Focus: What to do

**Strategy**:
- Encapsulates algorithm
- Usually stateless
- Executed immediately
- Focus: How to do it

### Command vs. Memento

**Command** (for undo):
- Command stores operation details
- Undo by executing inverse operation
- Command knows both forward and backward actions

**Memento** (for undo):
- Memento stores complete object state
- Undo by restoring saved state
- Simpler but more memory intensive

### When to Use Command Pattern

✅ **Use when:**
- Need undo/redo functionality
- Want to queue/schedule operations
- Need audit trail of operations
- Operations can be parameterized
- Want to decouple invoker from receiver
- Need macro/batch operations

❌ **Avoid when:**
- Simple method call suffices
- No need for undo/logging/queuing
- Too many trivial commands (overhead)
- State saving for undo is prohibitively expensive

### Real-World Applications

**Text Editors**:
```
Every keystroke = Command
Undo stack = Command history
Macro = BatchCommand
```

**Database Transactions**:
```
BEGIN = Start tracking commands
COMMIT = Execute all commands
ROLLBACK = Undo all commands
```

**Task Schedulers**:
```
Cron job = Scheduled command
Job queue = Command queue
Dead letter queue = Failed command queue
```

**Event Sourcing**:
```
Every state change = Command stored as event
Replay events = Re-execute commands
Current state = Result of all commands
```

## Verification Checklist

### Isolation Exercise
- [ ] Command interface with execute/undo/getDescription
- [ ] TextEditor receiver with insert/delete/replace
- [ ] Three concrete commands store necessary state
- [ ] DeleteTextCommand saves deleted text
- [ ] ReplaceTextCommand saves replaced text
- [ ] CommandHistory manages undo/redo stacks
- [ ] Redo stack clears on new command
- [ ] Macro command undoes in reverse order
- [ ] Edge cases handled (empty stacks, invalid positions)

### Simulation Exercise
- [ ] Task interface extends Command
- [ ] BaseTask implements common functionality
- [ ] Four concrete task types with appropriate settings
- [ ] TaskScheduler uses priority queue
- [ ] Retry logic checks idempotent flag
- [ ] Max retries limit enforced
- [ ] Dead letter queue captures permanent failures
- [ ] Execution history records all attempts
- [ ] Batch task executes all or stops on failure
- [ ] Cancellation prevents execution
- [ ] Status transitions correct throughout lifecycle
- [ ] Priority scheduling works (URGENT before LOW, FIFO within)
