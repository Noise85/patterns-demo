package com.patterns.strategy.isolation;

/**
 * Concrete strategy for processing PayPal payments.
 * 
 * TODO: Implement the PaymentStrategy interface.
 * Return a message in the format: "Processed $X via PayPal"
 */
public class PayPalPayment implements PaymentStrategy {
    
    @Override
    public String processPayment(double amount) {
        // TODO: Implement PayPal payment processing
        // Use String.format() to format the amount with a dollar sign
        // Example: "Processed $50.0 via PayPal"
        return null; // Replace this with your implementation
    }
}
