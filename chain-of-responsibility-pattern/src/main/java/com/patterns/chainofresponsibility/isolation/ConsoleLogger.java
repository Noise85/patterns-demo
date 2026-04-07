package com.patterns.chainofresponsibility.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Console logger - handles DEBUG level and above.
 * Outputs to simulated console.
 */
public class ConsoleLogger extends LogHandler {
    
    private final List<String> writtenMessages = new ArrayList<>();
    
    public ConsoleLogger() {
        super(LogLevel.DEBUG);
    }
    
    @Override
    protected void write(LogMessage message) {
        // TODO: Implement console logging
        // Format: "[CONSOLE] [LEVEL] message"
        // Example: "[CONSOLE] [DEBUG] Application started"
        // Add formatted message to writtenMessages list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets messages written by this handler (for testing).
     *
     * @return list of written messages
     */
    public List<String> getWrittenMessages() {
        return new ArrayList<>(writtenMessages);
    }
}
