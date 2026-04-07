package com.patterns.facade.simulation;

/**
 * Payment method information.
 */
public record PaymentMethod(
    String type,  // "CREDIT_CARD", "DEBIT_CARD", "PAYPAL"
    String token  // tokenized payment details
) {
    public PaymentMethod {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Payment token cannot be blank");
        }
    }
}
