package com.patterns.factorymethod.simulation.model;

/**
 * Result of a notification delivery attempt.
 */
public class DeliveryResult {
    private final boolean success;
    private final String deliveryId;
    private final String channel;
    private final String errorMessage;
    
    private DeliveryResult(boolean success, String deliveryId, String channel, String errorMessage) {
        this.success = success;
        this.deliveryId = deliveryId;
        this.channel = channel;
        this.errorMessage = errorMessage;
    }
    
    public static DeliveryResult success(String channel, String deliveryId) {
        return new DeliveryResult(true, deliveryId, channel, null);
    }
    
    public static DeliveryResult failure(String channel, String errorMessage) {
        return new DeliveryResult(false, null, channel, errorMessage);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getDeliveryId() {
        return deliveryId;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
