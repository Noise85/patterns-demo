package com.patterns.decorator.simulation;

/**
 * Component interface for payment processing.
 * Defines the contract for processing payment transactions.
 */
public interface PaymentProcessor {
    
    /**
     * Processes a payment request.
     *
     * @param request the payment request to process
     * @return the result of payment processing
     */
    PaymentResult process(PaymentRequest request);
}
