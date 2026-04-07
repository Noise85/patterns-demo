package com.patterns.decorator.simulation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for Exercise 2: Real-World Simulation.
 * Tests payment processing pipeline with various decorator combinations.
 */
@DisplayName("Decorator Pattern - Simulation Exercise Tests")
class SimulationExerciseTest {
    
    private PaymentRequest validRequest;
    private PaymentRequest highAmountRequest;
    private PaymentRequest fraudulentRequest;
    
    @BeforeEach
    void setUp() {
        validRequest = new PaymentRequest(
            "txn-001",
            10000, // $100.00
            "USD",
            "tok_valid_card",
            "merchant-123"
        );
        
        highAmountRequest = new PaymentRequest(
            "txn-002",
            600000, // $6,000 (high-risk)
            "USD",
            "tok_valid_card",
            "merchant-123"
        );
        
        fraudulentRequest = new PaymentRequest(
            "txn-666",
            50666, // ends in 666 (fraudulent pattern)
            "USD",
            "tok_valid_card",
            "merchant-123"
        );
    }
    
    // ===== Core Processor Tests =====
    
    @Test
    @DisplayName("CorePaymentProcessor processes valid request")
    void coreProcessorHandlesValidRequest() {
        PaymentProcessor processor = new CorePaymentProcessor(new Random(42));
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result).isNotNull();
        assertThat(result.getTransactionId()).isEqualTo("txn-001");
        assertThat(result.getAuditTrail()).isNotEmpty();
    }
    
    // ===== Validation Decorator Tests =====
    
    @Test
    @DisplayName("ValidationDecorator allows valid request")
    void validationAllowsValidRequest() {
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation") && entry.contains("Passed"));
    }
    
    @Test
    @DisplayName("ValidationDecorator rejects negative amount")
    void validationRejectsNegativeAmount() {
        PaymentRequest invalidRequest = new PaymentRequest(
            "txn-003", -100, "USD", "tok_card", "merchant-123"
        );
        
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(invalidRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("amount");
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation") && entry.contains("Failed"));
    }
    
    @Test
    @DisplayName("ValidationDecorator rejects zero amount")
    void validationRejectsZeroAmount() {
        PaymentRequest invalidRequest = new PaymentRequest(
            "txn-004", 0, "USD", "tok_card", "merchant-123"
        );
        
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(invalidRequest);
        
        assertThat(result.isSuccess()).isFalse();
    }
    
    @Test
    @DisplayName("ValidationDecorator rejects amount exceeding maximum")
    void validationRejectsExcessiveAmount() {
        PaymentRequest invalidRequest = new PaymentRequest(
            "txn-005", 2_000_000, "USD", "tok_card", "merchant-123"
        );
        
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(invalidRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("amount");
    }
    
    @Test
    @DisplayName("ValidationDecorator rejects invalid currency code")
    void validationRejectsInvalidCurrency() {
        PaymentRequest invalidRequest = new PaymentRequest(
            "txn-006", 10000, "US", "tok_card", "merchant-123"
        );
        
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(invalidRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("currency");
    }
    
    @Test
    @DisplayName("ValidationDecorator rejects empty card token")
    void validationRejectsEmptyCardToken() {
        PaymentRequest invalidRequest = new PaymentRequest(
            "txn-007", 10000, "USD", "", "merchant-123"
        );
        
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(invalidRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("token");
    }
    
    @Test
    @DisplayName("ValidationDecorator accepts various valid currencies")
    void validationAcceptsValidCurrencies() {
        PaymentProcessor processor = new ValidationDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentRequest eurRequest = new PaymentRequest(
            "txn-008", 10000, "EUR", "tok_card", "merchant-123"
        );
        PaymentRequest gbpRequest = new PaymentRequest(
            "txn-009", 10000, "GBP", "tok_card", "merchant-123"
        );
        
        assertThat(processor.process(eurRequest).getAuditTrail())
            .anyMatch(e -> e.contains("Validation") && e.contains("Passed"));
        assertThat(processor.process(gbpRequest).getAuditTrail())
            .anyMatch(e -> e.contains("Validation") && e.contains("Passed"));
    }
    
    // ===== Fraud Detection Decorator Tests =====
    
    @Test
    @DisplayName("FraudDetectionDecorator allows normal transaction")
    void fraudDetectionAllowsNormalTransaction() {
        PaymentProcessor processor = new FraudDetectionDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Fraud check") && entry.contains("Passed"));
    }
    
    @Test
    @DisplayName("FraudDetectionDecorator flags high-risk transaction")
    void fraudDetectionFlagsHighRisk() {
        PaymentProcessor processor = new FraudDetectionDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(highAmountRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("High-risk"));
    }
    
    @Test
    @DisplayName("FraudDetectionDecorator blocks fraudulent pattern")
    void fraudDetectionBlocksFraudulentPattern() {
        PaymentProcessor processor = new FraudDetectionDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(fraudulentRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("fraud");
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Fraud") || entry.contains("fraud"));
    }
    
    @Test
    @DisplayName("FraudDetectionDecorator detects 666 pattern in various amounts")
    void fraudDetectionDetects666Pattern() {
        PaymentProcessor processor = new FraudDetectionDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentRequest fraud1 = new PaymentRequest(
            "txn-010", 666, "USD", "tok_card", "merchant-123"
        );
        PaymentRequest fraud2 = new PaymentRequest(
            "txn-011", 100666, "USD", "tok_card", "merchant-123"
        );
        
        assertThat(processor.process(fraud1).isSuccess()).isFalse();
        assertThat(processor.process(fraud2).isSuccess()).isFalse();
    }
    
    // ===== Audit Logging Decorator Tests =====
    
    @Test
    @DisplayName("AuditLoggingDecorator adds entry and exit logs")
    void auditLoggingAddsEntryExit() {
        PaymentProcessor processor = new AuditLoggingDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Audit") && entry.contains("started"))
            .anyMatch(entry -> entry.contains("Audit") && entry.contains("completed"));
    }
    
    @Test
    @DisplayName("AuditLoggingDecorator measures processing time")
    void auditLoggingMeasuresTime() {
        PaymentProcessor processor = new AuditLoggingDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getProcessingTimeMs()).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    @DisplayName("AuditLoggingDecorator records success status")
    void auditLoggingRecordsSuccessStatus() {
        PaymentProcessor processor = new AuditLoggingDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        boolean hasStatusLog = result.getAuditTrail().stream()
            .anyMatch(entry -> entry.contains("Status:"));
        
        assertThat(hasStatusLog).isTrue();
    }
    
    // ===== Retry Decorator Tests =====
    
    @Test
    @DisplayName("RetryDecorator succeeds on first attempt (no retry needed)")
    void retrySucceedsFirstAttempt() {
        PaymentProcessor processor = new RetryDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Retry") && entry.contains("attempt 1"));
    }
    
    @Test
    @DisplayName("RetryDecorator does not retry non-transient errors")
    void retryDoesNotRetryNonTransient() {
        // Use fraud detection to generate non-transient error
        PaymentProcessor processor = new RetryDecorator(
            new FraudDetectionDecorator(
                new CorePaymentProcessor()
            )
        );
        
        PaymentResult result = processor.process(fraudulentRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Retry") && entry.contains("Non-retryable"));
    }
    
    // ===== Idempotency Decorator Tests =====
    
    @Test
    @DisplayName("IdempotencyDecorator processes first occurrence")
    void idempotencyProcessesFirstOccurrence() {
        PaymentProcessor processor = new IdempotencyDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Idempotency") && entry.contains("First occurrence"));
    }
    
    @Test
    @DisplayName("IdempotencyDecorator returns cached result for duplicate")
    void idempotencyReturnsCachedForDuplicate() {
        PaymentProcessor processor = new IdempotencyDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentResult first = processor.process(validRequest);
        PaymentResult second = processor.process(validRequest); // duplicate
        
        assertThat(second.getAuditTrail())
            .anyMatch(entry -> entry.contains("Idempotency") && entry.contains("Duplicate"));
        
        // Both should have same transaction ID
        assertThat(second.getTransactionId()).isEqualTo(first.getTransactionId());
    }
    
    @Test
    @DisplayName("IdempotencyDecorator handles different transactions independently")
    void idempotencyHandlesDifferentTransactions() {
        PaymentProcessor processor = new IdempotencyDecorator(
            new CorePaymentProcessor(new Random(42))
        );
        
        PaymentRequest request1 = validRequest;
        PaymentRequest request2 = new PaymentRequest(
            "txn-different", 20000, "USD", "tok_card", "merchant-123"
        );
        
        PaymentResult result1 = processor.process(request1);
        PaymentResult result2 = processor.process(request2);
        
        // Both should be "first occurrence" since different transaction IDs
        assertThat(result1.getAuditTrail())
            .anyMatch(e -> e.contains("First occurrence"));
        assertThat(result2.getAuditTrail())
            .anyMatch(e -> e.contains("First occurrence"));
    }
    
    // ===== Pipeline Factory Tests =====
    
    @Test
    @DisplayName("PaymentPipelineFactory builds BASIC tier pipeline")
    void factoryBuildsBasicPipeline() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.BASIC);
        
        PaymentResult result = processor.process(validRequest);
        
        // Should have validation
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation"));
    }
    
    @Test
    @DisplayName("PaymentPipelineFactory builds STANDARD tier pipeline")
    void factoryBuildsStandardPipeline() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.STANDARD);
        
        PaymentResult result = processor.process(validRequest);
        
        // Should have validation, fraud detection, and audit
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation"))
            .anyMatch(entry -> entry.contains("Fraud"))
            .anyMatch(entry -> entry.contains("Audit"));
    }
    
    @Test
    @DisplayName("PaymentPipelineFactory builds PREMIUM tier pipeline")
    void factoryBuildsPremiumPipeline() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.PREMIUM);
        
        PaymentResult result = processor.process(validRequest);
        
        // Should have all decorators
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Idempotency"))
            .anyMatch(entry -> entry.contains("Validation"))
            .anyMatch(entry -> entry.contains("Fraud"))
            .anyMatch(entry -> entry.contains("Retry"))
            .anyMatch(entry -> entry.contains("Audit"));
    }
    
    @Test
    @DisplayName("BASIC tier rejects invalid requests")
    void basicTierRejectsInvalid() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.BASIC);
        
        PaymentRequest invalid = new PaymentRequest(
            "txn-012", -100, "USD", "tok_card", "merchant-123"
        );
        
        PaymentResult result = processor.process(invalid);
        
        assertThat(result.isSuccess()).isFalse();
    }
    
    @Test
    @DisplayName("STANDARD tier blocks fraudulent transactions")
    void standardTierBlocksFraud() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.STANDARD);
        
        PaymentResult result = processor.process(fraudulentRequest);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("fraud");
    }
    
    @Test
    @DisplayName("PREMIUM tier prevents duplicate processing")
    void premiumTierPreventsDuplicates() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.PREMIUM);
        
        PaymentResult first = processor.process(validRequest);
        PaymentResult second = processor.process(validRequest);
        
        assertThat(second.getAuditTrail())
            .anyMatch(entry -> entry.contains("Duplicate"));
    }
    
    // ===== Integration Tests =====
    
    @Test
    @DisplayName("Full pipeline processes valid request successfully")
    void fullPipelineProcessesValidRequest() {
        PaymentProcessor processor = new IdempotencyDecorator(
            new ValidationDecorator(
                new FraudDetectionDecorator(
                    new RetryDecorator(
                        new AuditLoggingDecorator(
                            new CorePaymentProcessor(new Random(42))
                        )
                    )
                )
            )
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result).isNotNull();
        assertThat(result.getTransactionId()).isEqualTo("txn-001");
        assertThat(result.getAuditTrail()).hasSizeGreaterThan(5);
    }
    
    @Test
    @DisplayName("Validation failure stops pipeline early")
    void validationFailureStopsPipeline() {
        PaymentProcessor processor = new ValidationDecorator(
            new FraudDetectionDecorator(
                new CorePaymentProcessor()
            )
        );
        
        PaymentRequest invalid = new PaymentRequest(
            "txn-013", -100, "USD", "tok_card", "merchant-123"
        );
        
        PaymentResult result = processor.process(invalid);
        
        assertThat(result.isSuccess()).isFalse();
        // Should NOT reach fraud detection or core processor
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation"))
            .noneMatch(entry -> entry.contains("Core processor"));
    }
    
    @Test
    @DisplayName("Fraud detection stops pipeline before core processor")
    void fraudDetectionStopsPipeline() {
        PaymentProcessor processor = new FraudDetectionDecorator(
            new CorePaymentProcessor()
        );
        
        PaymentResult result = processor.process(fraudulentRequest);
        
        assertThat(result.isSuccess()).isFalse();
        // Should NOT reach core processor
        assertThat(result.getAuditTrail())
            .noneMatch(entry -> entry.contains("Core processor"));
    }
    
    @Test
    @DisplayName("Audit trail reflects complete processing journey")
    void auditTrailReflectsCompleteJourney() {
        PaymentPipelineFactory factory = new PaymentPipelineFactory();
        PaymentProcessor processor = factory.buildPipeline(MerchantTier.PREMIUM);
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail()).isNotEmpty();
        
        // Verify order: Idempotency first, then Validation, etc.
        int idempotencyIndex = findAuditIndex(result, "Idempotency");
        int validationIndex = findAuditIndex(result, "Validation");
        
        assertThat(idempotencyIndex).isLessThan(validationIndex);
    }
    
    @Test
    @DisplayName("Custom pipeline can be assembled manually")
    void customPipelineAssembly() {
        // Custom: Only validation and audit, no fraud detection
        PaymentProcessor processor = new ValidationDecorator(
            new AuditLoggingDecorator(
                new CorePaymentProcessor(new Random(42))
            )
        );
        
        PaymentResult result = processor.process(validRequest);
        
        assertThat(result.getAuditTrail())
            .anyMatch(entry -> entry.contains("Validation"))
            .anyMatch(entry -> entry.contains("Audit"))
            .noneMatch(entry -> entry.contains("Fraud"));
    }
    
    // ===== Helper Methods =====
    
    private int findAuditIndex(PaymentResult result, String keyword) {
        for (int i = 0; i < result.getAuditTrail().size(); i++) {
            if (result.getAuditTrail().get(i).contains(keyword)) {
                return i;
            }
        }
        return -1;
    }
}
