package com.patterns.factorymethod.isolation;

/**
 * Abstract creator with factory method.
 * 
 * This is the key class in the Factory Method pattern.
 * It declares the factory method that subclasses override.
 * 
 * TODO: Complete the implementation.
 */
public abstract class DocumentExporter {
    
    /**
     * Factory method - subclasses override this to create specific document types.
     * 
     * TODO: Declare this as an abstract method.
     * It should accept a String content parameter and return a Document.
     */
    public abstract Document createDocument(String content);
    
    /**
     * Template method that uses the factory method.
     * This method is concrete and should NOT be overridden.
     * 
     * TODO: Implement this method to:
     * 1. Call createDocument(content) to get a document
     * 2. Return document.getContent()
     */
    public String export(String content) {
        // TODO: Use the factory method to create a document
        // TODO: Return the document's content
        return null; // Replace this
    }
}
