package com.patterns.chainofresponsibility.isolation;

/**
 * Abstract base handler for logging chain.
 */
public abstract class LogHandler {
    
    protected LogHandler next;
    protected LogLevel handlerLevel;
    
    /**
     * Creates a log handler for the specified level.
     *
     * @param handlerLevel the log level this handler processes
     */
    protected LogHandler(LogLevel handlerLevel) {
        this.handlerLevel = handlerLevel;
    }
    
    /**
     * Sets the next handler in the chain.
     * Returns this for fluent chaining.
     *
     * @param next the next handler
     * @return this handler
     */
    public LogHandler setNext(LogHandler next) {
        // TODO: Implement fluent chain building
        // Set this.next = next
        // Return this
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Handles a log message.
     * Processes if appropriate severity, then passes to next handler.
     *
     * @param message the log message
     */
    public void handle(LogMessage message) {
        // TODO: Implement chain handling logic
        // 1. If message severity >= handlerLevel severity: call write(message)
        // 2. If next handler exists: delegate to next.handle(message)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Writes the log message (handler-specific implementation).
     *
     * @param message the log message to write
     */
    protected abstract void write(LogMessage message);
}
