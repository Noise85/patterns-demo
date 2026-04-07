package com.patterns.factorymethod.simulation;

import com.patterns.factorymethod.simulation.model.DeliveryResult;
import com.patterns.factorymethod.simulation.model.NotificationMessage;

/**
 * Abstract creator for notifications (Factory Method pattern).
 * 
 * TODO: Complete the implementation.
 */
public abstract class NotificationService {
    
    /**
     * Factory method - subclasses override to create specific notification types.
     * 
     * TODO: Declare this as abstract.
     */
    protected abstract Notification createNotification(NotificationMessage message);
    
    /**
     * Template method that uses the factory method.
     * 
     * TODO: Implement to:
     * 1. Create notification using factory method
     * 2. Validate the notification
     * 3. If invalid, return failure result
     * 4. If valid, send and return result
     */
    public DeliveryResult sendNotification(NotificationMessage message) {
        // TODO: Implement
        return null; // Replace this
    }
}
