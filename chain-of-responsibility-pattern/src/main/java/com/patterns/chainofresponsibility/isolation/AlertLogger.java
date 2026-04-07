package com.patterns.chainofresponsibility.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Alert logger - handles ERROR level.
 * Simulates sending critical alerts.
 */
public class AlertLogger extends LogHandler {
    
    private final String alertService;
    private final List<String> writtenMessages = new ArrayList<>();
    
    public AlertLogger(String alertService) {
        super(LogLevel.ERROR);
        this.alertService = alertService;
    }
    
    @Override
    protected void write(LogMessage message) {
        // TODO: Implement alert logging
        // Format: "[ALERT: service] [LEVEL] message"
        // Example: "[ALERT: PagerDuty] [ERROR] Database connection failed"
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
    
    public String getAlertService() {
        return alertService;
    }
}
