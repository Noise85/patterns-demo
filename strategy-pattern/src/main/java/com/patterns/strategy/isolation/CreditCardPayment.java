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
        return String.format("Processed $%.2f via Credit Card", amount);
    }
}
