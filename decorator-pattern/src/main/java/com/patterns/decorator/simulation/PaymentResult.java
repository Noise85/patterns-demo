package com.patterns.decorator.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable payment processing result.
 * Contains success status, transaction details, and audit trail.
 */
public final class PaymentResult {
    
    private final boolean success;
    private final String transactionId;
    private final String message;
    private final long processingTimeMs;
    private final List<String> auditTrail;
    
    private PaymentResult(boolean success, String transactionId, String message, 
                         long processingTimeMs, List<String> auditTrail) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
        this.processingTimeMs = processingTimeMs;
        this.auditTrail = List.copyOf(auditTrail); // defensive copy for immutability
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getProcessingTimeMs() {
        return processingTimeMs;
    }
    
    public List<String> getAuditTrail() {
        return auditTrail;
    }
    
    /**
     * Creates a new PaymentResult with an additional audit entry.
     * Maintains immutability by creating a new instance.
     *
     * @param entry the audit entry to add
     * @return a new PaymentResult with the entry added
     */
    public PaymentResult withAuditEntry(String entry) {
        List<String> newTrail = new ArrayList<>(this.auditTrail);
        newTrail.add(entry);
        return new PaymentResult(success, transactionId, message, processingTimeMs, newTrail);
    }
    
    /**
     * Creates a new PaymentResult with multiple audit entries added.
     *
     * @param entries the audit entries to add
     * @return a new PaymentResult with entries added
     */
    public PaymentResult withAuditEntries(List<String> entries) {
        List<String> newTrail = new ArrayList<>(this.auditTrail);
        newTrail.addAll(entries);
        return new PaymentResult(success, transactionId, message, processingTimeMs, newTrail);
    }
    
    /**
     * Creates a new PaymentResult with updated processing time.
     *
     * @param timeMs the processing time in milliseconds
     * @return a new PaymentResult with updated processing time
     */
    public PaymentResult withProcessingTime(long timeMs) {
        return new PaymentResult(success, transactionId, message, timeMs, auditTrail);
    }
    
    /**
     * Creates a successful payment result.
     *
     * @param transactionId the transaction ID
     * @param message success message
     * @return a successful PaymentResult
     */
    public static PaymentResult success(String transactionId, String message) {
        return new PaymentResult(true, transactionId, message, 0, new ArrayList<>());
    }
    
    /**
     * Creates a failed payment result.
     *
     * @param transactionId the transaction ID
     * @param message failure reason
     * @return a failed PaymentResult
     */
    public static PaymentResult failure(String transactionId, String message) {
        return new PaymentResult(false, transactionId, message, 0, new ArrayList<>());
    }
}
