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
        return String.format("Processed $%.2f via PayPal", amount);
    }
}
