package com.patterns.visitor.simulation;

/**
 * Base interface for order components that can be visited.
 */
public interface OrderComponent {
    
    /**
     * Accepts a visitor to perform operations on this component.
     *
     * @param visitor the visitor to accept
     */
    void accept(OrderVisitor visitor);
}
