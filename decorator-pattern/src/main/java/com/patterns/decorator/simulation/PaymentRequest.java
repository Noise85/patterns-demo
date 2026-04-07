package com.patterns.decorator.simulation;

/**
 * Immutable payment request containing transaction details.
 * Represents a payment to be processed through the pipeline.
 */
public record PaymentRequest(
    String transactionId,
    long amount,          // amount in cents
    String currency,      // ISO currency code (e.g., "USD", "EUR")
    String cardToken,     // tokenized card data
    String merchantId
) {
    
    /**
     * Validates basic structure constraints.
     *
     * @throws IllegalArgumentException if basic constraints violated
     */
    public PaymentRequest {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank");
        }
        if (merchantId == null || merchantId.isBlank()) {
            throw new IllegalArgumentException("Merchant ID cannot be null or blank");
        }
        // Note: Business validation (amount, currency, token) handled by ValidationDecorator
    }
}
