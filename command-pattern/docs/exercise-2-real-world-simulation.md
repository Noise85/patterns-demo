# Exercise 2: Real-World Simulation

## Objective

Build a production-grade task scheduling and execution system using the Command pattern with job queuing, retry logic, failure handling, and execution audit trails.

## Business Context

You're developing a background job processing system for a SaaS platform. The system needs to:

1. **Execute diverse tasks**: Data imports, reports, emails, API calls
2. **Queue management**: Schedule tasks for immediate or delayed execution
3. **Retry mechanism**: Automatically retry failed tasks with backoff
4. **Failure handling**: Track failures, implement circuit breakers
5. **Audit logging**: Complete execution history for compliance
6. **Priority scheduling**: High-priority tasks execute first
7. **Cancellation**: Support task cancellation before/during execution

Key requirements:
- Tasks can fail and need retry logic
- Some tasks are idempotent (safe to retry), others aren't
- Failed tasks after max retries go to dead-letter queue
- Track execution time, status, and results
- Support batch scheduling (execute multiple tasks as transaction)

## Domain Model

### Task Types
- `DataImportTask`: Import data from external source
- `ReportGenerationTask`: Generate and send reports
- `EmailNotificationTask`: Send email notifications
- `ApiCallTask`: Make external API calls

### Task Status
- `PENDING`: Waiting in queue
- `RUNNING`: Currently executing
- `COMPLETED`: Successfully finished
- `FAILED`: Failed execution
- `CANCELLED`: Cancelled before completion
- `RETRYING`: Failed, waiting for retry

### Task Priority
- `LOW`, `NORMAL`, `HIGH`, `URGENT`

## Your Tasks

### 1. Define Core Types

Create enums:
- `TaskStatus`: PENDING, RUNNING, COMPLETED, FAILED, CANCELLED, RETRYING
- `TaskPriority`: LOW, NORMAL, HIGH, URGENT

Create `TaskResult` record:
- `boolean success`
- `String message`
- `LocalDateTime executionTime`
- `long durationMillis`

### 2. Create Task Command Interface

Create `Task` interface (extends Command):
- Method: `String getTaskId()` - unique task identifier
- Method: `TaskPriority getPriority()` - task priority
- Method: `boolean isIdempotent()` - can safely retry?
- Method: `int getMaxRetries()` - maximum retry attempts
- Method: `TaskResult execute()` - execute task, return result
- Method: `void cancel()` - cancel task if possible
- Method: `String getDescription()` - human-readable description

### 3. Create Abstract Base Task

Create abstract `BaseTask`:
- Fields: `String taskId`, `TaskPriority priority`, `boolean idempotent`, `int maxRetries`
- Field: `TaskStatus status` (default: PENDING)
- Field: `int attemptCount` (tracks retry attempts)
- Constructor with all parameters
- Implement common methods: `getTaskId()`, `getPriority()`, etc.
- Abstract method: `TaskResult doExecute()` - subclasses implement actual work
- `execute()` implementation:
  - Update status to RUNNING
  - Record start time
  - Call `doExecute()`
  - Update status based on result
  - Return TaskResult with timing
  - Handle exceptions

### 4. Create Concrete Task Types

**DataImportTask**:
- Constructor: `DataImportTask(String taskId, String source, int recordCount)`
- Idempotent: true
- Max retries: 3
- `doExecute()`: Simulates importing data (may fail randomly for testing)

**ReportGenerationTask**:
- Constructor: `ReportGenerationTask(String taskId, String reportType, LocalDate period)`
- Idempotent: true
- Max retries: 2
- `doExecute()`: Simulates generating report

**EmailNotificationTask**:
- Constructor: `EmailNotificationTask(String taskId, String recipient, String subject)`
- Idempotent: false (don't send duplicate emails)
- Max retries: 1
- `doExecute()`: Simulates sending email

**ApiCallTask**:
- Constructor: `ApiCallTask(String taskId, String endpoint, String method)`
- Idempotent: depends on HTTP method (GET/PUT=true, POST=false)
- Max retries: 3
- `doExecute()`: Simulates API call

### 5. Implement Task Scheduler (Invoker)

Create `TaskScheduler`:
- Field: `PriorityQueue<Task>` - tasks sorted by priority
- Field: `Map<String, TaskExecutionRecord>` - execution history
- Field: `Queue<Task>` - dead letter queue (failed after max retries)
- Method: `void scheduleTask(Task task)` - adds task to queue
- Method: `void scheduleDelayed(Task task, Duration delay)` - schedule for later
- Method: `TaskResult executeNext()` - executes highest priority task
- Method: `void executeTask(Task task)` - executes specific task with retry logic
- Method: `List<TaskExecutionRecord> getExecutionHistory()` - returns history
- Method: `List<Task> getDeadLetterQueue()` - returns failed tasks
- Method: `int getPendingTaskCount()` - returns queue size

### 6. Create Execution Record

Create `TaskExecutionRecord`:
- Fields: `String taskId`, `String description`, `TaskStatus status`
- Fields: `LocalDateTime scheduledTime`, `LocalDateTime startTime`, `LocalDateTime endTime`
- Fields: `int attemptCount`, `TaskResult lastResult`
- Method: `long getTotalDurationMillis()` - time from scheduled to completed

### 7. Implement Retry Logic

Add to `TaskScheduler`:
```java
TaskResult executeWithRetry(Task task)
```
- Execute task
- If fails and `attemptCount < maxRetries` and `isIdempotent()`:
  - Update status to RETRYING
  - Increment attempt count
  - Re-queue task
  - Return failure result
- If fails after max retries or not idempotent:
  - Update status to FAILED
  - Add to dead letter queue
  - Return final failure result
- If succeeds:
  - Update status to COMPLETED
  - Record in execution history
  - Return success result

### 8. Implement Batch Task

Create `BatchTask` (macro command):
- Constructor: `BatchTask(String taskId, List<Task> tasks, boolean stopOnFailure)`
- Field: `boolean stopOnFailure` - if true, stop batch on first failure
- `execute()`: Executes all tasks, returns combined result
- If any task fails and `stopOnFailure` is true: stop and mark batch as failed
- Track which tasks succeeded for potential rollback

### 9. Task Cancellation

Implement cancellation:
- Add `volatile boolean cancelled` flag to `BaseTask`
- `cancel()`: Sets cancelled flag, updates status to CANCELLED
- `execute()`: Check cancelled flag before execution
- Scheduler: Support `cancelTask(String taskId)` method

### 10. Priority Queue Management

Implement priority-based scheduling:
- Higher priority tasks execute first
- Within same priority, FIFO order
- Use `PriorityQueue` with custom comparator

## Example Usage

```java
TaskScheduler scheduler = new TaskScheduler();

// Schedule individual tasks
Task import1 = new DataImportTask("T001", "customers.csv", 1000);
Task import2 = new DataImportTask("T002", "orders.csv", 5000);
Task email = new EmailNotificationTask("T003", "admin@company.com", "Import Complete");

scheduler.scheduleTask(import1);
scheduler.scheduleTask(import2);
scheduler.scheduleTask(email);

// Execute tasks in priority order
while (scheduler.getPendingTaskCount() > 0) {
    TaskResult result = scheduler.executeNext();
    System.out.println("Task " + result.success() ? "succeeded" : "failed");
}

// Batch execution
BatchTask batch = new BatchTask("BATCH001", List.of(
    new DataImportTask("T004", "products.csv", 500),
    new ReportGenerationTask("T005", "Sales", LocalDate.now()),
    new EmailNotificationTask("T006", "manager@company.com", "Batch Complete")
), true);  // Stop on first failure

scheduler.executeTask(batch);

// Check execution history
List<TaskExecutionRecord> history = scheduler.getExecutionHistory();
for (TaskExecutionRecord record : history) {
    System.out.println(record.getTaskId() + ": " + record.getStatus() + 
                      " in " + record.getTotalDurationMillis() + "ms");
}

// Check dead letter queue
List<Task> failed = scheduler.getDeadLetterQueue();
System.out.println("Failed tasks after max retries: " + failed.size());
```

## Testing Strategy

Your implementation must handle:

1. **Basic execution**: Tasks execute and return results
2. **Priority scheduling**: High-priority tasks execute first
3. **Retry logic**: Failed idempotent tasks retry up to max attempts
4. **Non-idempotent**: Non-idempotent tasks don't retry
5. **Dead letter queue**: Failed tasks after max retries go to DLQ
6. **Batch execution**: All tasks in batch execute as unit
7. **Batch failure**: Batch stops on first failure if configured
8. **Cancellation**: Tasks can be cancelled before execution
9. **Execution history**: All executions recorded with timing
10. **Status transitions**: Tasks transition through correct statuses
11. **Multiple task types**: Different task implementations work correctly
12. **Concurrent safety**: (Optional) Thread-safe queue operations

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] Tasks properly encapsulate work
- [ ] Scheduler manages queue and execution
- [ ] Retry logic works for idempotent tasks only
- [ ] Priority scheduling functions correctly
- [ ] Batch tasks execute atomically
- [ ] Execution history tracks all operations
- [ ] Dead letter queue captures permanent failures
- [ ] Cancellation prevents execution
- [ ] Production-quality error handling

## Time Estimate

**2-3 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **Exponential backoff**: Increase delay between retries
2. **Rate limiting**: Limit task execution rate per time window
3. **Task dependencies**: Tasks wait for prerequisites
4. **Scheduled execution**: Cron-like scheduling
5. **Monitoring**: Expose metrics (success rate, avg duration)
6. **Persistent queue**: Save queue state to disk
7. **Distributed execution**: Multiple worker instances
8. **Circuit breaker**: Stop retrying after consecutive failures

## Hints

- Use `PriorityQueue` with custom `Comparator<Task>` for priority
- Store attempt count in task, check in retry logic
- Execution history: Use `LinkedHashMap` for insertion order
- Timing: `System.currentTimeMillis()` or `Instant.now()`
- Batch execution: Collect results from all tasks
- Cancellation: Check `cancelled` flag at execution start
- Dead letter queue: Simple `Queue<Task>` for failed tasks
- Status transitions: PENDING → RUNNING → COMPLETED/FAILED/RETRYING
- Idempotent check: Only retry if `task.isIdempotent()` is true
- Test failures: Simulate with random failures or counter
- TaskResult: Include success flag, message, timing
- Priority comparator: Higher priority first, then FIFO within priority
