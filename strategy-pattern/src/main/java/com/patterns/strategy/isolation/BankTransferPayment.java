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
        return String.format("Processed $%.2f via Bank Transfer", amount);
    }
}
