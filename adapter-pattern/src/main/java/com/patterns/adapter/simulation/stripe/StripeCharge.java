package com.patterns.adapter.simulation.stripe;

/**
 * Stripe charge response model (third-party).
 */
public class StripeCharge {
    private final boolean successful;
    private final String chargeId;
    private final String message;

    public StripeCharge(boolean successful, String chargeId, String message) {
        this.successful = successful;
        this.chargeId = chargeId;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getChargeId() {
        return chargeId;
    }

    public String getMessage() {
        return message;
    }
}
