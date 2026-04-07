package com.patterns.decorator.isolation;

/**
 * Component interface for the Decorator pattern.
 * Defines the contract for sending notifications.
 */
public interface Notifier {
    
    /**
     * Sends a notification message.
     * Concrete implementations may transform, enhance, or decorate the message
     * before actual delivery.
     *
     * @param message the message to send
     * @return the processed message (after any transformations/decorations)
     */
    String send(String message);
}
