package com.patterns.adapter.simulation;

/**
 * Target interface for payment processing.
 * All payment adapters must implement this interface.
 */
public interface PaymentProcessor {
    /**
     * Charges a payment.
     *
     * @param request Payment request details
     * @return Payment result with transaction ID
     */
    PaymentResult charge(PaymentRequest request);

    /**
     * Refunds a previous payment.
     *
     * @param transactionId Transaction to refund
     * @param amount        Amount to refund
     * @return Refund result
     */
    PaymentResult refund(String transactionId, double amount);

    /**
     * Checks if the payment gateway is available.
     *
     * @return true if available, false otherwise
     */
    boolean isAvailable();
}
