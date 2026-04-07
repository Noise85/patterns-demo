package com.patterns.adapter.simulation;

import com.patterns.adapter.simulation.paypal.PayPalGateway;
import com.patterns.adapter.simulation.stripe.StripeGateway;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Payment Gateway Adapters
 */
class SimulationExerciseTest {

    @Test
    void stripeAdapter_implementsTargetInterface() {
        StripeGateway stripe = new StripeGateway();
        StripePaymentAdapter adapter = new StripePaymentAdapter(stripe);

        assertThat(adapter).isInstanceOf(PaymentProcessor.class);
    }

    @Test
    void paypalAdapter_implementsTargetInterface() {
        PayPalGateway paypal = new PayPalGateway();
        PayPalPaymentAdapter adapter = new PayPalPaymentAdapter(paypal);

        assertThat(adapter).isInstanceOf(PaymentProcessor.class);
    }

    @Test
    void stripeAdapter_chargesPayment() {
        StripeGateway stripe = new StripeGateway();
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        PaymentRequest request = new PaymentRequest(
                100.00,
                "USD",
                "tok_visa_4242",
                "customer@example.com"
        );

        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getTransactionId()).startsWith("ch_");
        assertThat(result.getMessage()).contains("successful");
    }

    @Test
    void stripeAdapter_refundsPayment() {
        StripeGateway stripe = new StripeGateway();
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        PaymentResult result = processor.refund("ch_123456789", 50.00);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getTransactionId()).startsWith("re_");
        assertThat(result.getMessage()).contains("successful");
    }

    @Test
    void paypalAdapter_chargesPayment() {
        PayPalGateway paypal = new PayPalGateway();
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        PaymentRequest request = new PaymentRequest(
                75.50,
                "USD",
                "card_token_xyz",
                "buyer@example.com"
        );

        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getTransactionId()).startsWith("PAY-");
        assertThat(result.getMessage()).contains("completed");
    }

    @Test
    void paypalAdapter_refundsPayment() {
        PayPalGateway paypal = new PayPalGateway();
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        PaymentResult result = processor.refund("PAY-123456789", 25.00);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getTransactionId()).startsWith("REF-");
        assertThat(result.getMessage()).contains("completed");
    }

    @Test
    void stripeAdapter_convertsAmountToCents() {
        StripeGateway stripe = new StripeGateway();
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        PaymentRequest request = new PaymentRequest(
                123.45,  // Dollars
                "USD",
                "tok_test",
                "test@example.com"
        );

        // Stripe expects 12345 cents internally
        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void paypalAdapter_formatsAmountAsString() {
        PayPalGateway paypal = new PayPalGateway();
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        PaymentRequest request = new PaymentRequest(
                99.99,
                "USD",
                "token_abc",
                "email@test.com"
        );

        // PayPal expects "99.99" string format internally
        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void stripeAdapter_handlesInvalidAmount() {
        StripeGateway stripe = new StripeGateway();
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        PaymentRequest request = new PaymentRequest(
                -10.00,
                "USD",
                "tok_test",
                "test@example.com"
        );

        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).contains("Invalid amount");
    }

    @Test
    void paypalAdapter_handlesInvalidTransactionId() {
        PayPalGateway paypal = new PayPalGateway();
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        PaymentResult result = processor.refund("", 50.00);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).contains("Invalid transaction");
    }

    @Test
    void stripeAdapter_checksAvailability() {
        StripeGateway stripe = new StripeGateway();
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        assertThat(processor.isAvailable()).isTrue();

        stripe.setAvailable(false);

        assertThat(processor.isAvailable()).isFalse();
    }

    @Test
    void paypalAdapter_checksAvailability() {
        PayPalGateway paypal = new PayPalGateway();
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        assertThat(processor.isAvailable()).isTrue();

        paypal.setAvailable(false);

        assertThat(processor.isAvailable()).isFalse();
    }

    @Test
    void stripeAdapter_handlesUnavailableGateway() {
        StripeGateway stripe = new StripeGateway();
        stripe.setAvailable(false);
        PaymentProcessor processor = new StripePaymentAdapter(stripe);

        PaymentRequest request = new PaymentRequest(
                50.00,
                "USD",
                "tok_test",
                "test@example.com"
        );

        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).contains("unavailable");
    }

    @Test
    void paypalAdapter_handlesUnavailableGateway() {
        PayPalGateway paypal = new PayPalGateway();
        paypal.setAvailable(false);
        PaymentProcessor processor = new PayPalPaymentAdapter(paypal);

        PaymentRequest request = new PaymentRequest(
                50.00,
                "USD",
                "tok_test",
                "test@example.com"
        );

        PaymentResult result = processor.charge(request);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).contains("unavailable");
    }

    @Test
    void differentAdapters_provideUnifiedInterface() {
        // Both adapters implement the same interface
        PaymentProcessor stripe = new StripePaymentAdapter(new StripeGateway());
        PaymentProcessor paypal = new PayPalPaymentAdapter(new PayPalGateway());

        PaymentRequest request = new PaymentRequest(
                35.00,
                "USD",
                "tok_card",
                "user@example.com"
        );

        // Client code works with both through the same interface
        PaymentResult stripeResult = stripe.charge(request);
        PaymentResult paypalResult = paypal.charge(request);

        assertThat(stripeResult.isSuccess()).isTrue();
        assertThat(paypalResult.isSuccess()).isTrue();
    }
}
