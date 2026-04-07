package com.patterns.chainofresponsibility.isolation;

import java.time.LocalDateTime;

/**
 * Log message with level, content, and timestamp.
 */
public record LogMessage(
    LogLevel level,
    String message,
    LocalDateTime timestamp
) {
}
