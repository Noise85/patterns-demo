package com.patterns.templatemethod.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class defining the template method for data processing.
 * Demonstrates the Template Method pattern with a simple data pipeline.
 */
public abstract class DataProcessor {
    
    protected final List<String> database = new ArrayList<>();
    protected final List<String> log = new ArrayList<>();
    
    /**
     * Template method - defines the processing algorithm.
     * Final to prevent subclasses from changing the flow.
     *
     * @param input raw input data
     * @return processed records
     */
    public final List<String> process(String input) {
        // Step 1: Parse raw input
        List<String> records = parse(input);
        
        // Step 2: Validate data
        validate(records);
        
        // Step 3: Transform (if needed)
        if (shouldTransform()) {
            records = transform(records);
        }
        
        // Step 4: Save to database
        save(records);
        
        // Step 5: Post-processing hook
        afterProcessing(records);
        
        return records;
    }
    
    /**
     * Parses raw input into list of records.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param input raw input string
     * @return list of parsed records
     */
    protected abstract List<String> parse(String input);
    
    /**
     * Transforms records to desired format.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param records records to transform
     * @return transformed records
     */
    protected abstract List<String> transform(List<String> records);
    
    /**
     * Saves records to persistent storage.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param records records to save
     */
    protected abstract void save(List<String> records);
    
    /**
     * Hook method - determines if transformation should be applied.
     * Default returns true. Subclasses can override to skip transformation.
     *
     * @return true if records should be transformed
     */
    protected boolean shouldTransform() {
        return true;
    }
    
    /**
     * Hook method - called after processing completes.
     * Default does nothing. Subclasses can override for logging, notifications, etc.
     *
     * @param records the processed records
     */
    protected void afterProcessing(List<String> records) {
        // Default: do nothing
    }
    
    /**
     * Common operation - validates records.
     * Shared by all subclasses, cannot be overridden.
     *
     * @param records records to validate
     * @throws IllegalArgumentException if validation fails
     */
    protected final void validate(List<String> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("No records to process");
        }
        
        for (String record : records) {
            if (record == null || record.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid record: empty or null");
            }
        }
    }
    
    /**
     * Gets the database contents (for testing).
     *
     * @return database records
     */
    public List<String> getDatabase() {
        return new ArrayList<>(database);
    }
    
    /**
     * Gets the log entries (for testing).
     *
     * @return log entries
     */
    public List<String> getLog() {
        return new ArrayList<>(log);
    }
}
