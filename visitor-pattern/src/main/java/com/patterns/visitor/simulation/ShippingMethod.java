package com.patterns.visitor.simulation;

/**
 * Available shipping methods.
 */
public enum ShippingMethod {
    /**
     * Standard shipping (5-7 business days).
     */
    STANDARD,
    
    /**
     * Express shipping (2-3 business days).
     */
    EXPRESS,
    
    /**
     * Overnight shipping (next business day).
     */
    OVERNIGHT
}
