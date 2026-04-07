package com.patterns.decorator.simulation;

import java.util.Random;

/**
 * Concrete Component - core payment processor.
 * Simulates actual payment processing without any decorations.
 * In production, this would integrate with payment gateways.
 */
public class CorePaymentProcessor implements PaymentProcessor {
    
    private final Random random;
    
    public CorePaymentProcessor() {
        this.random = new Random();
    }
    
    /**
     * For testing with predictable randomness.
     *
     * @param random the random number generator to use
     */
    CorePaymentProcessor(Random random) {
        this.random = random;
    }
    
    /**
     * Processes payment by simulating gateway interaction.
     * 80% success rate for simulation purposes.
     *
     * @param request the payment request
     * @return processing result
     */
    @Override
    public PaymentResult process(PaymentRequest request) {
        // TODO: Implement core payment processing simulation
        // - Simulate random success/failure (80% success rate)
        // - If successful: return PaymentResult.success with appropriate message
        // - If failed: return PaymentResult.failure indicating gateway error
        // - Add basic audit entry: "Core processor: Payment processed"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
