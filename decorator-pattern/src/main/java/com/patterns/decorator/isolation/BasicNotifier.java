package com.patterns.decorator.isolation;

/**
 * Concrete Component in the Decorator pattern.
 * Provides basic notification functionality without any decoration.
 */
public class BasicNotifier implements Notifier {
    
    /**
     * Sends a message without any transformation or decoration.
     *
     * @param message the message to send
     * @return the message as-is
     */
    @Override
    public String send(String message) {
        // TODO: Implement basic notification - return message unchanged
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
