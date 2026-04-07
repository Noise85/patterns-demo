package com.patterns.chainofresponsibility.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * File logger - handles INFO level and above.
 * Simulates writing to a file.
 */
public class FileLogger extends LogHandler {
    
    private final String filename;
    private final List<String> writtenMessages = new ArrayList<>();
    
    public FileLogger(String filename) {
        super(LogLevel.INFO);
        this.filename = filename;
    }
    
    @Override
    protected void write(LogMessage message) {
        // TODO: Implement file logging
        // Format: "[FILE: filename] [LEVEL] message"
        // Example: "[FILE: app.log] [INFO] User logged in"
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
    
    public String getFilename() {
        return filename;
    }
}
