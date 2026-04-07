package com.patterns.templatemethod.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for processing CSV data.
 * Demonstrates implementing primitive operations and hooks.
 */
public class CsvDataProcessor extends DataProcessor {
    
    @Override
    protected List<String> parse(String input) {
        // TODO: Split input by newlines and trim each line
        // Split by "\n", trim each element, return as list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected List<String> transform(List<String> records) {
        // TODO: Convert each record to UPPERCASE
        // Create new list with uppercase versions of records
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void save(List<String> records) {
        // TODO: Add each record to the database list
        // Use: database.add(record) for each record
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void afterProcessing(List<String> records) {
        // TODO: Log processing completion
        // Add message to log: "Processed N CSV records" where N is record count
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
