package com.patterns.adapter.simulation;

import com.patterns.adapter.simulation.paypal.*;

/**
 * Adapter for PayPal payment gateway.
 * Converts between PaymentProcessor interface and PayPal SDK.
 */
public class PayPalPaymentAdapter implements PaymentProcessor {
    private final PayPalGateway gateway;

    public PayPalPaymentAdapter(PayPalGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public PaymentResult charge(PaymentRequest request) {
        // TODO: Convert PaymentRequest to PayPalPaymentRequest
        // - Format amount as string (e.g., "123.45")
        // - Map currency to currencyCode
        // - Use cardToken as paymentMethod
        // TODO: Call gateway.executePayment()
        // TODO: Convert PayPalTransaction response to PaymentResult
        // - Map "SUCCESS" status to success=true
        throw new UnsupportedOperationException("TODO: Implement PayPal charge adapter");
    }

    @Override
    public PaymentResult refund(String transactionId, double amount) {
        // TODO: Format amount as string
        // TODO: Call gateway.refundTransaction()
        // TODO: Convert PayPalRefundResponse to PaymentResult
        throw new UnsupportedOperationException("TODO: Implement PayPal refund adapter");
    }

    @Override
    public boolean isAvailable() {
        // TODO: Check if gateway is available
        throw new UnsupportedOperationException("TODO: Implement isAvailable()");
    }
}
