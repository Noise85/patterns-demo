package com.patterns.factorymethod.isolation;

/**
 * Concrete product: HTML document.
 * 
 * TODO: Implement the Document interface.
 */
public class HtmlDocument implements Document {
    
    private final String content;
    
    public HtmlDocument(String content) {
        this.content = content;
    }
    
    // TODO: Implement getContent() to return "<html><body>" + content + "</body></html>"
    @Override
    public String getContent() {
        throw new UnsupportedOperationException("TODO: Implement getContent()");
    }
    
    // TODO: Implement getFormat() to return "text/html"
    @Override
    public String getFormat() {
        throw new UnsupportedOperationException("TODO: Implement getFormat()");
    }
}
