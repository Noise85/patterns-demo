package com.patterns.factorymethod.isolation;

/**
 * Concrete product: PDF document.
 * 
 * TODO: Implement the Document interface.
 */
public class PdfDocument implements Document {
    
    private final String content;
    
    public PdfDocument(String content) {
        this.content = content;
    }
    
    // TODO: Implement getContent() to return "PDF: " + content
    @Override
    public String getContent() {
        throw new UnsupportedOperationException("TODO: Implement getContent()");
    }
    
    // TODO: Implement getFormat() to return "application/pdf"
    @Override
    public String getFormat() {
        throw new UnsupportedOperationException("TODO: Implement getFormat()");
    }
}
