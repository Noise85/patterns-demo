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
        this.strategy=strategy;
    }
    
    /**
     * Allows changing the payment strategy at runtime.
     * 
     * TODO: Implement this method.
     * Accept a PaymentStrategy parameter and update the field.
     */
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy=strategy;
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
        return strategy.processPayment(amount);
    }
}
