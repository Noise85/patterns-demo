package com.patterns.memento.simulation;

/**
 * Order lifecycle status enumeration.
 */
public enum OrderStatus {
    /**
     * Order has been submitted but not yet validated.
     */
    SUBMITTED,
    
    /**
     * Order has passed risk checks.
     */
    RISK_CHECKED,
    
    /**
     * Order is waiting for market execution.
     */
    PENDING_EXECUTION,
    
    /**
     * Order is partially filled.
     */
    PARTIALLY_FILLED,
    
    /**
     * Order is fully executed.
     */
    EXECUTED,
    
    /**
     * Order was rejected during validation.
     */
    REJECTED,
    
    /**
     * Order was cancelled by user or system.
     */
    CANCELLED,
    
    /**
     * Order execution failed due to system error.
     */
    FAILED
}
