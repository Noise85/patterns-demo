package com.patterns.factorymethod.isolation;

/**
 * Concrete creator for PDF documents.
 * 
 * TODO: Extend DocumentExporter and override the factory method.
 */
public class PdfExporter extends DocumentExporter {
    
    // TODO: Override createDocument() to return a new PdfDocument
    @Override
    public Document createDocument(String content) {
        throw new UnsupportedOperationException("TODO: Implement createDocument()");
    }
}
