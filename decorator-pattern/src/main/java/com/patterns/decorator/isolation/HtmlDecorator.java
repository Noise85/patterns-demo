package com.patterns.decorator.isolation;

/**
 * Concrete Decorator that wraps messages in HTML markup.
 * Wraps the final result (after delegation) in HTML tags.
 */
public class HtmlDecorator extends NotifierDecorator {
    
    public HtmlDecorator(Notifier notifier) {
        super(notifier);
    }
    
    /**
     * Delegates to wrapped notifier, then wraps result in HTML tags.
     *
     * @param message the message to send
     * @return "<html><body>result</body></html>"
     */
    @Override
    public String send(String message) {
        // TODO: Implement HTML wrapping:
        // 1. Delegate to wrapped notifier
        // 2. Wrap the RESULT in <html><body>...</body></html>
        // Note: This decorates AFTER delegation (different from encrypt/compress)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
