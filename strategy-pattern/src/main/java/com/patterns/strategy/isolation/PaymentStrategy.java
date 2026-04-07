package com.patterns.strategy.isolation;

/**
 * Strategy interface for payment processing.
 * 
 * TODO: Define the contract for all payment strategies.
 * All payment methods should implement this interface.
 */
public interface PaymentStrategy {
    
    /**
     * TODO: Implement this method in each concrete strategy.
     * Processes a payment and returns a confirmation message.
     * 
     * @param amount The payment amount
     * @return A confirmation message in the format "Processed $X via [PaymentMethod]"
     */
    String processPayment(double amount);
}
