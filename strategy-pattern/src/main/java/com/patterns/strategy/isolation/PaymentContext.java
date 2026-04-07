package com.patterns.strategy.isolation;

/**
 * Context class that uses a payment strategy.
 * 
 * This class maintains a reference to a PaymentStrategy and delegates
 * the payment processing work to it.
 * 
 * TODO: Complete the implementation to use the Strategy pattern correctly.
 */
public class PaymentContext {
    
    private PaymentStrategy strategy;
    
    /**
     * Constructor to set the initial payment strategy.
     * 
     * TODO: Implement this constructor.
     * Accept a PaymentStrategy parameter and store it in the field.
     */
    public PaymentContext(PaymentStrategy strategy) {
        // TODO: Store the strategy in the field
        // Remember to save the parameter to the instance variable
    }
    
    /**
     * Allows changing the payment strategy at runtime.
     * 
     * TODO: Implement this method.
     * Accept a PaymentStrategy parameter and update the field.
     */
    public void setStrategy(PaymentStrategy strategy) {
        // TODO: Update the current strategy
        // Remember to save the parameter to the instance variable
    }
    
    /**
     * Processes a payment using the current strategy.
     * 
     * TODO: Implement this method.
     * Delegate to the current strategy's processPayment method.
     * 
     * IMPORTANT: Do NOT use if/else or switch statements here!
     * Just delegate to the strategy object.
     */
    public String executePayment(double amount) {
        // TODO: Delegate to the strategy and return the result
        // Call strategy.processPayment(amount) and return the result
        return null; // Replace this
    }
}
