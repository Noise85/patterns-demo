package com.patterns.adapter.simulation;

/**
 * Payment request model for the target interface.
 */
public class PaymentRequest {
    private final double amount;        // Amount in dollars
    private final String currency;
    private final String cardToken;
    private final String customerEmail;

    public PaymentRequest(double amount, String currency, String cardToken, String customerEmail) {
        this.amount = amount;
        this.currency = currency;
        this.cardToken = cardToken;
        this.customerEmail = customerEmail;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCardToken() {
        return cardToken;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
