package com.patterns.factorymethod.isolation;

/**
 * Product interface for documents.
 * 
 * TODO: Define the contract for all document types.
 */
public interface Document {
    
    // TODO: Declare methods that all documents must implement
    // Hint: You need getContent() and getFormat() methods
    
    String getContent();
    String getFormat();
}
