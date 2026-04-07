package com.patterns.command.simulation;

import java.time.LocalDateTime;

/**
 * Record of task execution.
 */
public class TaskExecutionRecord {
    
    private final String taskId;
    private final String description;
    private final TaskStatus status;
    private final LocalDateTime scheduledTime;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int attemptCount;
    private final TaskResult lastResult;
    
    /**
     * Creates a task execution record.
     */
    public TaskExecutionRecord(String taskId, String description, TaskStatus status,
                              LocalDateTime scheduledTime, LocalDateTime startTime,
                              LocalDateTime endTime, int attemptCount, TaskResult lastResult) {
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attemptCount = attemptCount;
        this.lastResult = lastResult;
    }
    
    public String getTaskId() {
        return taskId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public int getAttemptCount() {
        return attemptCount;
    }
    
    public TaskResult getLastResult() {
        return lastResult;
    }
    
    /**
     * Calculates total duration from scheduled to completed.
     *
     * @return duration in milliseconds
     */
    public long getTotalDurationMillis() {
        // TODO: Implement duration calculation
        // Calculate difference between endTime and scheduledTime
        // Return duration in milliseconds
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
