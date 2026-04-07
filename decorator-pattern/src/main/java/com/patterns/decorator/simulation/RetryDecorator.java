package com.patterns.decorator.simulation;

/**
 * Decorator that retries failed payments with transient errors.
 * Uses exponential backoff between retry attempts.
 */
public class RetryDecorator implements PaymentProcessor {
    
    private final PaymentProcessor wrappedProcessor;
    
    private static final int MAX_RETRIES = 3;
    private static final long BASE_BACKOFF_MS = 100;
    
    public RetryDecorator(PaymentProcessor processor) {
        this.wrappedProcessor = processor;
    }
    
    /**
     * Processes payment with retry logic for transient failures.
     *
     * @param request the payment request
     * @return final result after retries (if applicable)
     */
    @Override
    public PaymentResult process(PaymentRequest request) {
        // TODO: Implement retry logic:
        // 1. Attempt initial processing (delegate to wrapped processor)
        // 2. If successful: return result with audit entry "Retry: Success on attempt 1"
        // 3. If failed AND message contains "TRANSIENT" or "TIMEOUT":
        //    a. Retry up to MAX_RETRIES times
        //    b. Wait between attempts using exponential backoff:
        //       - Attempt 2: wait 100ms
        //       - Attempt 3: wait 200ms  
        //       - Attempt 4: wait 400ms
        //    c. Add audit entry for each retry: "Retry: Attempt <n> after <ms>ms backoff"
        // 4. If still failed after MAX_RETRIES: return last result with audit
        // 5. If non-transient failure: return immediately with audit entry "Retry: Non-retryable error"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private boolean isRetryable(String message) {
        // TODO: Check if error message indicates transient failure
        // Return true if message contains "TRANSIENT" or "TIMEOUT"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private void backoff(int attemptNumber) throws InterruptedException {
        // TODO: Calculate exponential backoff: BASE_BACKOFF_MS * 2^(attemptNumber-1)
        // Attempt 1: 100ms, Attempt 2: 200ms, Attempt 3: 400ms
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
