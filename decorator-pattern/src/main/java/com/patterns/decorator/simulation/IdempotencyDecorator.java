package com.patterns.decorator.simulation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Decorator that ensures idempotent payment processing.
 * Caches results by transaction ID to prevent duplicate processing.
 */
public class IdempotencyDecorator implements PaymentProcessor {
    
    private final PaymentProcessor wrappedProcessor;
    private final Map<String, PaymentResult> processedTransactions;
    
    public IdempotencyDecorator(PaymentProcessor processor) {
        this.wrappedProcessor = processor;
        this.processedTransactions = new ConcurrentHashMap<>();
    }
    
    /**
     * Processes payment with idempotency check.
     * Returns cached result for duplicate transaction IDs.
     *
     * @param request the payment request
     * @return cached or newly processed result
     */
    @Override
    public PaymentResult process(PaymentRequest request) {
        // TODO: Implement idempotency logic:
        // 1. Check if transaction ID exists in cache
        // 2. If yes:
        //    a. Retrieve cached result
        //    b. Add audit entry: "Idempotency: Duplicate transaction - returning cached result"
        //    c. Return enhanced cached result
        // 3. If no:
        //    a. Add audit entry: "Idempotency: First occurrence - processing"
        //    b. Delegate to wrapped processor
        //    c. Cache the result by transaction ID
        //    d. Return result with audit entry
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Clears the idempotency cache (for testing).
     */
    void clearCache() {
        processedTransactions.clear();
    }
}
