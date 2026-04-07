package com.patterns.factorymethod.isolation;

/**
 * Concrete creator for HTML documents.
 * 
 * TODO: Extend DocumentExporter and override the factory method.
 */
public class HtmlExporter extends DocumentExporter {
    
    // TODO: Override createDocument() to return a new HtmlDocument
    @Override
    public Document createDocument(String content) {
        throw new UnsupportedOperationException("TODO: Implement createDocument()");
    }
}
