package com.patterns.command.simulation;

/**
 * Task execution status.
 */
public enum TaskStatus {
    PENDING,
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELLED,
    RETRYING
}
