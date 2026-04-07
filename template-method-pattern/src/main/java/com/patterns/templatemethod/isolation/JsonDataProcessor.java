package com.patterns.templatemethod.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for processing JSON data.
 * Demonstrates hook method customization (shouldTransform).
 */
public class JsonDataProcessor extends DataProcessor {
    
    @Override
    protected List<String> parse(String input) {
        // TODO: Extract values from JSON array format: ["value1","value2"]
        // 1. Remove opening '[' and closing ']'
        // 2. Split by ','
        // 3. For each element: remove quotes ("), trim whitespace
        // 4. Return list of cleaned values
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected List<String> transform(List<String> records) {
        // TODO: Add "JSON:" prefix to each record
        // Create new list with "JSON:" prepended to each record
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void save(List<String> records) {
        // TODO: Add each record to the database list
        // Use: database.add(record) for each record
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected boolean shouldTransform() {
        // TODO: Override to return false
        // JSON data doesn't need transformation in this implementation
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Note: afterProcessing() not overridden - uses default (does nothing)
}
