package com.patterns.factorymethod.isolation;

/**
 * Concrete product: Markdown document.
 * 
 * TODO: Implement the Document interface.
 */
public class MarkdownDocument implements Document {
    
    private final String content;
    
    public MarkdownDocument(String content) {
        this.content = content;
    }
    
    // TODO: Implement getContent() to return content as-is
    @Override
    public String getContent() {
        throw new UnsupportedOperationException("TODO: Implement getContent()");
    }
    
    // TODO: Implement getFormat() to return "text/markdown"
    @Override
    public String getFormat() {
        throw new UnsupportedOperationException("TODO: Implement getFormat()");
    }
}
