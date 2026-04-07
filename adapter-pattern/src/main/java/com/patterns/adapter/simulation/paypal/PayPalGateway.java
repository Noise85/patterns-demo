package com.patterns.adapter.simulation.paypal;

/**
 * Simulated PayPal SDK - Third-party library (do not modify).
 */
public class PayPalGateway {
    private boolean available = true;

    public PayPalTransaction executePayment(PayPalPaymentRequest request) {
        if (!available) {
            return new PayPalTransaction("FAILED", null, "Gateway unavailable");
        }

        double amount = Double.parseDouble(request.getAmount());
        if (amount <= 0) {
            return new PayPalTransaction("FAILED", null, "Invalid amount");
        }

        // Simulate successful payment
        String txnId = "PAY-" + System.currentTimeMillis();
        return new PayPalTransaction("SUCCESS", txnId, "Payment completed");
    }

    public PayPalRefundResponse refundTransaction(String transactionId, String amount) {
        if (!available) {
            return new PayPalRefundResponse("FAILED", null, "Gateway unavailable");
        }

        if (transactionId == null || transactionId.isEmpty()) {
            return new PayPalRefundResponse("FAILED", null, "Invalid transaction ID");
        }

        // Simulate successful refund
        String refundId = "REF-" + System.currentTimeMillis();
        return new PayPalRefundResponse("SUCCESS", refundId, "Refund completed");
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
