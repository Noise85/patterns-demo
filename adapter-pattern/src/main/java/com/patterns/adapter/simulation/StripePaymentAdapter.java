package com.patterns.adapter.simulation;

import com.patterns.adapter.simulation.stripe.*;

/**
 * Adapter for Stripe payment gateway.
 * Converts between PaymentProcessor interface and Stripe SDK.
 */
public class StripePaymentAdapter implements PaymentProcessor {
    private final StripeGateway gateway;

    public StripePaymentAdapter(StripeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public PaymentResult charge(PaymentRequest request) {
        // TODO: Convert PaymentRequest to StripeChargeRequest
        // - Convert amount from dollars to cents (multiply by 100)
        // - Map currency and cardToken
        // TODO: Call gateway.createCharge()
        // TODO: Convert StripeCharge response to PaymentResult
        throw new UnsupportedOperationException("TODO: Implement Stripe charge adapter");
    }

    @Override
    public PaymentResult refund(String transactionId, double amount) {
        // TODO: Convert amount from dollars to cents
        // TODO: Call gateway.processRefund()
        // TODO: Convert StripeRefund response to PaymentResult
        throw new UnsupportedOperationException("TODO: Implement Stripe refund adapter");
    }

    @Override
    public boolean isAvailable() {
        // TODO: Check if gateway is available
        // Hint: Try a test call or maintain availability state
        throw new UnsupportedOperationException("TODO: Implement isAvailable()");
    }
}
