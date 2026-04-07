package com.patterns.facade.simulation;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Payment subsystem managing payment authorization and capture.
 */
public class PaymentService {
    
    private final Map<String, AuthorizationRecord> authorizations = new ConcurrentHashMap<>();
    private final Map<String, TransactionRecord> transactions = new ConcurrentHashMap<>();
    
    /**
     * Authorizes a payment without capturing funds.
     *
     * @param customerId the customer identifier
     * @param paymentMethod the payment method
     * @param amountCents the amount in cents
     * @return authorization identifier
     * @throws PaymentFailedException if payment is declined
     */
    public String authorizePayment(String customerId, PaymentMethod paymentMethod, long amountCents) {
        // TODO: Implement authorization logic
        // - Simulate payment gateway call
        // - Decline payments with amounts ending in 13 (fraud simulation)
        // - Create authorization record
        // - Return authorization ID
        // Hint: Check if amountCents % 100 == 13 for decline simulation
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Captures an authorized payment.
     *
     * @param authorizationId the authorization to capture
     * @return transaction identifier
     * @throws IllegalStateException if authorization not found
     */
    public String capturePayment(String authorizationId) {
        // TODO: Implement capture logic
        // - Get authorization record
        // - Create transaction record
        // - Remove authorization (now captured)
        // - Return transaction ID
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Refunds a captured payment.
     *
     * @param transactionId the transaction to refund
     * @param amountCents the amount to refund
     * @return refund identifier
     * @throws IllegalStateException if transaction not found
     */
    public String refundPayment(String transactionId, long amountCents) {
        // TODO: Implement refund logic
        // - Get transaction record
        // - Verify refund amount <= transaction amount
        // - Create refund record
        // - Return refund ID
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private record AuthorizationRecord(String customerId, long amount) {}
    private record TransactionRecord(String customerId, long amount) {}
}
