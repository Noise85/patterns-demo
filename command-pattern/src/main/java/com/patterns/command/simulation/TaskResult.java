package com.patterns.command.simulation;

import java.time.LocalDateTime;

/**
 * Result of task execution.
 */
public record TaskResult(
    boolean success,
    String message,
    LocalDateTime executionTime,
    long durationMillis
) {
}
