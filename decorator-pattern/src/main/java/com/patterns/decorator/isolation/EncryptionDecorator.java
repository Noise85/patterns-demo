package com.patterns.decorator.isolation;

/**
 * Concrete Decorator that encrypts messages before sending.
 * Uses a simple Caesar cipher (shift by 3) for encryption.
 * Wraps encrypted content in "ENCRYPTED[...]" format.
 */
public class EncryptionDecorator extends NotifierDecorator {
    
    private static final int SHIFT = 3;
    
    public EncryptionDecorator(Notifier notifier) {
        super(notifier);
    }
    
    /**
     * Encrypts the message using Caesar cipher, then delegates to wrapped notifier.
     *
     * @param message the message to encrypt and send
     * @return "ENCRYPTED[encrypted_message]" after delegation
     */
    @Override
    public String send(String message) {
        // TODO: Implement encryption logic:
        // 1. Encrypt message using Caesar cipher (shift by 3)
        // 2. Wrap in "ENCRYPTED[...]" format
        // 3. Delegate to wrapped notifier with encrypted message
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Encrypts a string using Caesar cipher with shift of 3.
     * Example: "ABC" -> "DEF", "xyz" -> "abc"
     *
     * @param text the text to encrypt
     * @return the encrypted text
     */
    private String encrypt(String text) {
        // TODO: Implement Caesar cipher
        // - Shift each letter by SHIFT positions
        // - Wrap around: 'Z' -> 'C', 'z' -> 'c'
        // - Preserve non-letters unchanged
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
