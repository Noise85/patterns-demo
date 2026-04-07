package com.patterns.factorymethod.simulation;

import com.patterns.factorymethod.simulation.model.DeliveryResult;

/**
 * Product interface for notifications.
 * 
 * All notification types must implement this interface.
 */
public interface Notification {
    
    /**
     * Send the notification.
     * @return delivery result with success/failure status
     */
    DeliveryResult send();
    
    /**
     * Validate the notification before sending.
     * @return true if valid, false otherwise
     */
    boolean validate();
    
    /**
     * Get the channel name.
     * @return channel identifier
     */
    String getChannel();
}
