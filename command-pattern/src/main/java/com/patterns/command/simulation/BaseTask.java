package com.patterns.command.simulation;

import java.time.LocalDateTime;

/**
 * Abstract base class for tasks.
 * Implements common task functionality.
 */
public abstract class BaseTask implements Task {
    
    protected final String taskId;
    protected final TaskPriority priority;
    protected final boolean idempotent;
    protected final int maxRetries;
    
    protected TaskStatus status;
    protected int attemptCount;
    protected volatile boolean cancelled;
    
    /**
     * Creates a base task.
     *
     * @param taskId unique task identifier
     * @param priority task priority
     * @param idempotent whether task is idempotent
     * @param maxRetries maximum retry attempts
     */
    protected BaseTask(String taskId, TaskPriority priority, boolean idempotent, int maxRetries) {
        this.taskId = taskId;
        this.priority = priority;
        this.idempotent = idempotent;
        this.maxRetries = maxRetries;
        this.status = TaskStatus.PENDING;
        this.attemptCount = 0;
        this.cancelled = false;
    }
    
    @Override
    public String getTaskId() {
        return taskId;
    }
    
    @Override
    public TaskPriority getPriority() {
        return priority;
    }
    
    @Override
    public boolean isIdempotent() {
        return idempotent;
    }
    
    @Override
    public int getMaxRetries() {
        return maxRetries;
    }
    
    @Override
    public TaskStatus getStatus() {
        return status;
    }
    
    @Override
    public int getAttemptCount() {
        return attemptCount;
    }
    
    @Override
    public TaskResult execute() {
        // TODO: Implement execute logic
        // 1. Check if cancelled, throw exception if so
        // 2. Increment attemptCount
        // 3. Set status to RUNNING
        // 4. Record start time
        // 5. Try to execute doExecute() (abstract method)
        // 6. If successful: set status to COMPLETED, return success result
        // 7. If exception: set status to FAILED, return failure result
        // 8. Calculate duration and return TaskResult with timing
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Performs the actual work.
     * Subclasses implement this method.
     *
     * @return execution result message
     * @throws Exception if execution fails
     */
    protected abstract String doExecute() throws Exception;
    
    @Override
    public void cancel() {
        // TODO: Implement cancellation
        // Set cancelled flag to true
        // Set status to CANCELLED
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
