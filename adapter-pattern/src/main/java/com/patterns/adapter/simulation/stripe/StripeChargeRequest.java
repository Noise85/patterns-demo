package com.patterns.adapter.simulation.stripe;

/**
 * Stripe charge request model (third-party).
 */
public class StripeChargeRequest {
    private int amountCents;
    private String currency;
    private String cardToken;

    public int getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(int amountCents) {
        this.amountCents = amountCents;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }
}
