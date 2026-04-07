package com.patterns.adapter.simulation.paypal;

/**
 * PayPal refund response model (third-party).
 */
public class PayPalRefundResponse {
    private final String status;       // "SUCCESS" or "FAILED"
    private final String refundId;
    private final String message;

    public PayPalRefundResponse(String status, String refundId, String message) {
        this.status = status;
        this.refundId = refundId;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getRefundId() {
        return refundId;
    }

    public String getMessage() {
        return message;
    }
}
