package com.patterns.factorymethod.simulation.notification;

import com.patterns.factorymethod.simulation.Notification;
import com.patterns.factorymethod.simulation.model.DeliveryResult;
import com.patterns.factorymethod.simulation.model.NotificationMessage;

import java.util.UUID;

/**
 * Email notification implementation.
 * 
 * TODO: Implement the Notification interface.
 * Validates email format and simulates SMTP delivery.
 */
public class EmailNotification implements Notification {
    
    private final NotificationMessage message;
    
    public EmailNotification(NotificationMessage message) {
        this.message = message;
    }
    
    @Override
    public boolean validate() {
        // TODO: Implement email validation
        // Check that recipient contains "@" and "."
        // Return false if invalid
        return false; // Replace this
    }
    
    @Override
    public DeliveryResult send() {
        // TODO: Implement send logic
        // Generate a delivery ID (use UUID.randomUUID().toString())
        // Return DeliveryResult.success(getChannel(), deliveryId)
        return null; // Replace this
    }
    
    @Override
    public String getChannel() {
        return "EMAIL";
    }
}
