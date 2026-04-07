package com.patterns.command.simulation;

/**
 * Task command interface.
 * Represents a unit of work that can be executed, retried, and tracked.
 */
public interface Task {
    
    /**
     * Gets the unique task identifier.
     *
     * @return task ID
     */
    String getTaskId();
    
    /**
     * Gets the task priority.
     *
     * @return priority level
     */
    TaskPriority getPriority();
    
    /**
     * Checks if the task is idempotent (safe to retry).
     *
     * @return true if task can be safely retried
     */
    boolean isIdempotent();
    
    /**
     * Gets the maximum number of retry attempts.
     *
     * @return max retries
     */
    int getMaxRetries();
    
    /**
     * Gets the current task status.
     *
     * @return task status
     */
    TaskStatus getStatus();
    
    /**
     * Gets the number of execution attempts.
     *
     * @return attempt count
     */
    int getAttemptCount();
    
    /**
     * Executes the task.
     *
     * @return execution result
     */
    TaskResult execute();
    
    /**
     * Cancels the task.
     */
    void cancel();
    
    /**
     * Gets a human-readable description of the task.
     *
     * @return task description
     */
    String getDescription();
}
