package com.patterns.factorymethod.isolation;

/**
 * Concrete creator for Markdown documents.
 * 
 * TODO: Extend DocumentExporter and override the factory method.
 */
public class MarkdownExporter extends DocumentExporter {
    
    // TODO: Override createDocument() to return a new MarkdownDocument
    @Override
    public Document createDocument(String content) {
        throw new UnsupportedOperationException("TODO: Implement createDocument()");
    }
}
