package com.patterns.strategy.isolation;

/**
 * Concrete strategy for processing bank transfer payments.
 * 
 * TODO: Implement the PaymentStrategy interface.
 * Return a message in the format: "Processed $X via Bank Transfer"
 */
public class BankTransferPayment implements PaymentStrategy {
    
    @Override
    public String processPayment(double amount) {
        // TODO: Implement bank transfer payment processing
        // Use String.format() to format the amount with a dollar sign
        // Example: "Processed $200.0 via Bank Transfer"
        return null; // Replace this with your implementation
    }
}
