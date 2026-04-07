package com.patterns.adapter.simulation.stripe;

/**
 * Simulated Stripe SDK - Third-party library (do not modify).
 */
public class StripeGateway {
    private boolean available = true;

    public StripeCharge createCharge(StripeChargeRequest request) {
        if (!available) {
            return new StripeCharge(false, null, "Gateway unavailable");
        }

        if (request.getAmountCents() <= 0) {
            return new StripeCharge(false, null, "Invalid amount");
        }

        // Simulate successful charge
        String chargeId = "ch_" + System.currentTimeMillis();
        return new StripeCharge(true, chargeId, "Charge successful");
    }

    public StripeRefund processRefund(String chargeId, int amountCents) {
        if (!available) {
            return new StripeRefund(false, null, "Gateway unavailable");
        }

        if (chargeId == null || chargeId.isEmpty()) {
            return new StripeRefund(false, null, "Invalid charge ID");
        }

        // Simulate successful refund
        String refundId = "re_" + System.currentTimeMillis();
        return new StripeRefund(true, refundId, "Refund successful");
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
