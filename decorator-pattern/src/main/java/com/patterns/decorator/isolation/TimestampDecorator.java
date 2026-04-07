package com.patterns.decorator.isolation;

import java.time.Instant;

/**
 * Concrete Decorator that adds a timestamp prefix to messages.
 * Prefixes the result with ISO-8601 timestamp in square brackets.
 */
public class TimestampDecorator extends NotifierDecorator {
    
    private final Instant timestamp;
    
    /**
     * Creates a timestamp decorator with current time.
     *
     * @param notifier the notifier to wrap
     */
    public TimestampDecorator(Notifier notifier) {
        this(notifier, Instant.now());
    }
    
    /**
     * Creates a timestamp decorator with specified time (for testing).
     *
     * @param notifier the notifier to wrap
     * @param timestamp the timestamp to use
     */
    public TimestampDecorator(Notifier notifier, Instant timestamp) {
        super(notifier);
        this.timestamp = timestamp;
    }
    
    /**
     * Delegates to wrapped notifier, then prefixes result with timestamp.
     *
     * @param message the message to send
     * @return "[2024-01-15T10:30:00Z] result"
     */
    @Override
    public String send(String message) {
        // TODO: Implement timestamp decoration:
        // 1. Delegate to wrapped notifier
        // 2. Prefix the RESULT with "[timestamp] "
        // Example: "[2024-01-15T10:30:00Z] Hello"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
