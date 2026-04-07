package com.patterns.strategy.isolation;

/**
 * Concrete strategy for processing credit card payments.
 * 
 * TODO: Implement the PaymentStrategy interface.
 * Return a message in the format: "Processed $X via Credit Card"
 */
public class CreditCardPayment implements PaymentStrategy {
    
    @Override
    public String processPayment(double amount) {
        // TODO: Implement credit card payment processing
        // Use String.format() to format the amount with a dollar sign
        // Example: "Processed $100.0 via Credit Card"
        return null; // Replace this with your implementation
    }
}
