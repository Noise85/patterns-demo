package com.patterns.adapter.simulation.stripe;

/**
 * Stripe refund response model (third-party).
 */
public class StripeRefund {
    private final boolean successful;
    private final String refundId;
    private final String message;

    public StripeRefund(boolean successful, String refundId, String message) {
        this.successful = successful;
        this.refundId = refundId;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getRefundId() {
        return refundId;
    }

    public String getMessage() {
        return message;
    }
}
