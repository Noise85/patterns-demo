package com.patterns.decorator.simulation;

/**
 * Decorator that validates payment requests before processing.
 * Ensures all business rules are met before delegating to wrapped processor.
 */
public class ValidationDecorator implements PaymentProcessor {
    
    private final PaymentProcessor wrappedProcessor;
    
    private static final long MAX_AMOUNT_CENTS = 1_000_000; // $10,000
    private static final int CURRENCY_CODE_LENGTH = 3;
    
    public ValidationDecorator(PaymentProcessor processor) {
        this.wrappedProcessor = processor;
    }
    
    /**
     * Validates request before processing.
     * Returns failure immediately if validation fails.
     *
     * @param request the payment request
     * @return validation failure or result from wrapped processor
     */
    @Override
    public PaymentResult process(PaymentRequest request) {
        // TODO: Implement validation logic:
        // 1. Check amount is positive
        // 2. Check amount <= MAX_AMOUNT_CENTS ($10,000)
        // 3. Check currency is 3-letter ISO code
        // 4. Check cardToken is not null/empty
        // 5. If ANY validation fails: return PaymentResult.failure with descriptive message
        // 6. If all pass: delegate to wrapped processor
        // 7. Add audit entry: "Validation: Passed" or "Validation: Failed - <reason>"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private boolean isValidCurrency(String currency) {
        // TODO: Validate currency is 3-letter uppercase code (e.g., USD, EUR, GBP)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
