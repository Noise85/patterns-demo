package com.patterns.state.alternative;

/**
 * State interface with default method implementations (Template Method at interface level).
 * 
 * <p>This approach leverages Java 8+ default interface methods to provide a "template"
 * for invalid operations. Concrete states only override the operations they actually support.
 * 
 * <h3>Key Benefits:</h3>
 * <ul>
 *   <li><b>No boilerplate</b>: States don't implement methods just to throw exceptions</li>
 *   <li><b>DRY principle</b>: Exception-throwing logic centralized in interface</li>
 *   <li><b>Maintains flexibility</b>: Still an interface, allows multiple inheritance</li>
 *   <li><b>Self-documenting</b>: Default behavior explicit in interface contract</li>
 *   <li><b>Easy to extend</b>: New operations get sensible defaults automatically</li>
 * </ul>
 * 
 * <h3>Design Pattern Combination:</h3>
 * This is a blend of:
 * <ul>
 *   <li><b>State Pattern</b>: Encapsulate state-specific behavior</li>
 *   <li><b>Template Method</b>: Default implementations in interface serve as template</li>
 *   <li><b>Strategy Pattern</b>: Interface defines pluggable behaviors</li>
 * </ul>
 * 
 * @see com.patterns.state.isolation For comparison: traditional approach with full implementations
 * @see com.patterns.state.simple For comparison: enum-based approach
 */
public interface DocumentState {
    
    /**
     * Returns the name of this state.
     * Must be implemented by all concrete states.
     *
     * @return state name for display and error messages
     */
    String getStateName();
    
    /**
     * Edits the document.
     * Default implementation throws exception - override to allow editing.
     *
     * @param document the document being edited
     * @throws IllegalStateException if editing not allowed in this state
     */
    default void edit(Document document) {
        throw new IllegalStateException(
            "Cannot edit document in " + getStateName() + " state"
        );
    }
    
    /**
     * Submits document for review.
     * Default implementation throws exception - override to allow submission.
     *
     * @param document the document being submitted
     * @throws IllegalStateException if submission not allowed in this state
     */
    default void submit(Document document) {
        throw new IllegalStateException(
            "Cannot submit document in " + getStateName() + " state"
        );
    }
    
    /**
     * Approves the document.
     * Default implementation throws exception - override to allow approval.
     *
     * @param document the document being approved
     * @throws IllegalStateException if approval not allowed in this state
     */
    default void approve(Document document) {
        throw new IllegalStateException(
            "Cannot approve document in " + getStateName() + " state"
        );
    }
    
    /**
     * Rejects the document.
     * Default implementation throws exception - override to allow rejection.
     *
     * @param document the document being rejected
     * @throws IllegalStateException if rejection not allowed in this state
     */
    default void reject(Document document) {
        throw new IllegalStateException(
            "Cannot reject document in " + getStateName() + " state"
        );
    }
    
    /**
     * Archives the document.
     * Default implementation throws exception - override to allow archiving.
     *
     * @param document the document being archived
     * @throws IllegalStateException if archiving not allowed in this state
     */
    default void archive(Document document) {
        throw new IllegalStateException(
            "Cannot archive document in " + getStateName() + " state"
        );
    }
}
