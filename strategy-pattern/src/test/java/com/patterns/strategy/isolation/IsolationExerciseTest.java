package com.patterns.strategy.isolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: Pattern in Isolation
 * 
 * Make these tests pass by implementing the Strategy pattern correctly.
 * All tests should pass when you've completed the exercise.
 */
@DisplayName("Exercise 1: Payment Strategy Pattern")
class IsolationExerciseTest {
    
    @Test
    @DisplayName("CreditCardPayment should process payment correctly")
    void testCreditCardPayment() {
        PaymentStrategy creditCard = new CreditCardPayment();
        String result = creditCard.processPayment(100.0);
        
        assertThat(result)
            .isEqualTo("Processed $100.0 via Credit Card");
    }
    
    @Test
    @DisplayName("PayPalPayment should process payment correctly")
    void testPayPalPayment() {
        PaymentStrategy paypal = new PayPalPayment();
        String result = paypal.processPayment(50.0);
        
        assertThat(result)
            .isEqualTo("Processed $50.0 via PayPal");
    }
    
    @Test
    @DisplayName("BankTransferPayment should process payment correctly")
    void testBankTransferPayment() {
        PaymentStrategy bankTransfer = new BankTransferPayment();
        String result = bankTransfer.processPayment(200.0);
        
        assertThat(result)
            .isEqualTo("Processed $200.0 via Bank Transfer");
    }
    
    @Test
    @DisplayName("PaymentContext should delegate to credit card strategy")
    void testContextWithCreditCard() {
        PaymentContext context = new PaymentContext(new CreditCardPayment());
        String result = context.executePayment(75.0);
        
        assertThat(result)
            .isEqualTo("Processed $75.0 via Credit Card");
    }
    
    @Test
    @DisplayName("PaymentContext should delegate to PayPal strategy")
    void testContextWithPayPal() {
        PaymentContext context = new PaymentContext(new PayPalPayment());
        String result = context.executePayment(150.0);
        
        assertThat(result)
            .isEqualTo("Processed $150.0 via PayPal");
    }
    
    @Test
    @DisplayName("PaymentContext should allow switching strategies at runtime")
    void testSwitchingStrategies() {
        PaymentContext context = new PaymentContext(new CreditCardPayment());
        
        // First payment with credit card
        String result1 = context.executePayment(100.0);
        assertThat(result1)
            .isEqualTo("Processed $100.0 via Credit Card");
        
        // Switch to PayPal
        context.setStrategy(new PayPalPayment());
        String result2 = context.executePayment(100.0);
        assertThat(result2)
            .isEqualTo("Processed $100.0 via PayPal");
        
        // Switch to Bank Transfer
        context.setStrategy(new BankTransferPayment());
        String result3 = context.executePayment(100.0);
        assertThat(result3)
            .isEqualTo("Processed $100.0 via Bank Transfer");
    }
    
    @Test
    @DisplayName("PaymentContext should handle different amounts correctly")
    void testDifferentAmounts() {
        PaymentContext context = new PaymentContext(new CreditCardPayment());
        
        assertThat(context.executePayment(0.0))
            .isEqualTo("Processed $0.0 via Credit Card");
        
        assertThat(context.executePayment(9.99))
            .isEqualTo("Processed $9.99 via Credit Card");
        
        assertThat(context.executePayment(1000.50))
            .isEqualTo("Processed $1000.5 via Credit Card");
    }
}
