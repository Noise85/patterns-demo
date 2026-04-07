package com.patterns.factorymethod.simulation.notification;

import com.patterns.factorymethod.simulation.Notification;
import com.patterns.factorymethod.simulation.model.DeliveryResult;
import com.patterns.factorymethod.simulation.model.NotificationMessage;

import java.util.UUID;

/**
 * SMS notification implementation.
 * 
 * TODO: Implement the Notification interface.
 * Validates phone number format and simulates SMS gateway.
 */
public class SmsNotification implements Notification {
    
    private final NotificationMessage message;
    
    public SmsNotification(NotificationMessage message) {
        this.message = message;
    }
    
    @Override
    public boolean validate() {
        // TODO: Implement phone validation
        // Check that recipient starts with "+" and length >= 12
        return false; // Replace this
    }
    
    @Override
    public DeliveryResult send() {
        // TODO: Implement send logic
        return null; // Replace this
    }
    
    @Override
    public String getChannel() {
        return "SMS";
    }
}
