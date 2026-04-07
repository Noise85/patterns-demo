package com.patterns.chainofresponsibility.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Email logger - handles WARN level and above.
 * Simulates sending emails for warnings.
 */
public class EmailLogger extends LogHandler {
    
    private final String emailAddress;
    private final List<String> writtenMessages = new ArrayList<>();
    
    public EmailLogger(String emailAddress) {
        super(LogLevel.WARN);
        this.emailAddress = emailAddress;
    }
    
    @Override
    protected void write(LogMessage message) {
        // TODO: Implement email logging
        // Format: "[EMAIL: address] [LEVEL] message"
        // Example: "[EMAIL: admin@company.com] [WARN] High memory usage"
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
    
    public String getEmailAddress() {
        return emailAddress;
    }
}
