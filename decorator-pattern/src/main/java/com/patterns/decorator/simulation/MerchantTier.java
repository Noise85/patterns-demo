package com.patterns.decorator.simulation;

/**
 * Merchant tier enumeration for pipeline configuration.
 * Different tiers receive different levels of payment processing features.
 */
public enum MerchantTier {
    /**
     * Basic tier: Validation only.
     * For small merchants with simple needs.
     */
    BASIC,
    
    /**
     * Standard tier: Validation + Fraud Detection + Audit Logging.
     * For medium-sized merchants requiring fraud protection.
     */
    STANDARD,
    
    /**
     * Premium tier: Full feature set.
     * Idempotency + Validation + Fraud Detection + Retry + Audit Logging.
     * For enterprise merchants requiring maximum reliability.
     */
    PREMIUM
}
