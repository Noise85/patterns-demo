package com.patterns.decorator.isolation;

/**
 * Concrete Decorator that compresses messages before sending.
 * Uses run-length encoding: consecutive repeated characters are replaced with char + count.
 * Example: "aaabbc" -> "a3b2c"
 * Wraps compressed content in "COMPRESSED[...]" format.
 */
public class CompressionDecorator extends NotifierDecorator {
    
    public CompressionDecorator(Notifier notifier) {
        super(notifier);
    }
    
    /**
     * Compresses the message using run-length encoding, then delegates.
     *
     * @param message the message to compress and send
     * @return "COMPRESSED[compressed_message]" after delegation
     */
    @Override
    public String send(String message) {
        // TODO: Implement compression logic:
        // 1. Compress message using run-length encoding
        // 2. Wrap in "COMPRESSED[...]" format
        // 3. Delegate to wrapped notifier
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Compresses text using run-length encoding.
     * Consecutive repeated characters are replaced with character + count.
     * Single characters remain unchanged.
     * Example: "aaabbc" -> "a3b2c", "hello" -> "hel2o"
     *
     * @param text the text to compress
     * @return the compressed text
     */
    private String compress(String text) {
        // TODO: Implement run-length encoding
        // - Count consecutive identical characters
        // - Replace with char + count (only if count > 1)
        // - Example: "aaa" -> "a3", "abc" -> "abc"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
