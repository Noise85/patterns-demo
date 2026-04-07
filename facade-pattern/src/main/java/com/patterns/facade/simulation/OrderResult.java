package com.patterns.facade.simulation;

import java.time.LocalDate;

/**
 * Result of order processing operation.
 */
public final class OrderResult {
    
    private final boolean success;
    private final String orderId;
    private final String trackingNumber;
    private final LocalDate estimatedDelivery;
    private final String message;
    
    private OrderResult(boolean success, String orderId, String trackingNumber,
                       LocalDate estimatedDelivery, String message) {
        this.success = success;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.estimatedDelivery = estimatedDelivery;
        this.message = message;
    }
    
    public static OrderResult success(String orderId, String trackingNumber,
                                     LocalDate estimatedDelivery) {
        return new OrderResult(true, orderId, trackingNumber, estimatedDelivery,
            "Order processed successfully");
    }
    
    public static OrderResult failure(String orderId, String message) {
        return new OrderResult(false, orderId, null, null, message);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public LocalDate getEstimatedDelivery() {
        return estimatedDelivery;
    }
    
    public String getMessage() {
        return message;
    }
}
