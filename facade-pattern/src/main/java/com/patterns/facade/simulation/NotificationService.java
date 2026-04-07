package com.patterns.facade.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Notification subsystem for customer communications.
 */
public class NotificationService {
    
    private final List<String> sentNotifications = new ArrayList<>();
    
    /**
     * Sends order confirmation to customer.
     *
     * @param customerId the customer identifier
     * @param orderId the order identifier
     * @param trackingNumber the tracking number
     */
    public void sendOrderConfirmation(String customerId, String orderId, String trackingNumber) {
        // TODO: Implement confirmation notification
        // - Simulate sending email/SMS
        // - Track sent notification for testing
        // Example: "Order confirmation sent to customer {customerId} for order {orderId}"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sends failure notification to customer.
     *
     * @param customerId the customer identifier
     * @param orderId the order identifier
     * @param reason the failure reason
     */
    public void sendFailureNotification(String customerId, String orderId, String reason) {
        // TODO: Implement failure notification
        // - Simulate sending failure email/SMS
        // - Track sent notification
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets list of sent notifications (for testing).
     *
     * @return list of notification descriptions
     */
    public List<String> getSentNotifications() {
        return new ArrayList<>(sentNotifications);
    }
    
    /**
     * Clears notification history (for testing).
     */
    public void clearNotifications() {
        sentNotifications.clear();
    }
}
