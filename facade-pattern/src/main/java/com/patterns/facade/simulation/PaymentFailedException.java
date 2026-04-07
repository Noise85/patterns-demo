package com.patterns.facade.simulation;

/**
 * Exception thrown when payment processing fails.
 */
public class PaymentFailedException extends RuntimeException {
    
    private final String reason;
    
    public PaymentFailedException(String reason) {
        super("Payment failed: " + reason);
        this.reason = reason;
    }
    
    public String getReason() {
        return reason;
    }
}
