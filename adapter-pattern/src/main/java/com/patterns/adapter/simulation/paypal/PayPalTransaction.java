package com.patterns.adapter.simulation.paypal;

/**
 * PayPal transaction response model (third-party).
 */
public class PayPalTransaction {
    private final String status;          // "SUCCESS" or "FAILED"
    private final String transactionId;
    private final String description;

    public PayPalTransaction(String status, String transactionId, String description) {
        this.status = status;
        this.transactionId = transactionId;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDescription() {
        return description;
    }
}
