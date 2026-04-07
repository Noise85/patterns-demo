package com.patterns.state.simple;

/**
 * Enum-based state machine for document workflow.
 * Each enum constant defines its own state-specific behavior and valid transitions.
 * 
 * <p>This is an alternative to the class-based State pattern shown in the isolation package.
 * The enum approach is simpler and more concise for state machines where:
 * <ul>
 *   <li>States have no instance-specific data</li>
 *   <li>Transitions are straightforward</li>
 *   <li>The set of states is fixed and unlikely to change</li>
 * </ul>
 */
public enum DocumentState {
    
    /**
     * Initial state - document can be edited and submitted for review.
     */
    DRAFT {
        @Override
        public DocumentState submit() {
            return REVIEW;
        }
        
        @Override
        public void edit(Document document) {
            document.incrementEditCount();
        }
    },
    
    /**
     * Under review - can be approved or rejected back to draft.
     */
    REVIEW {
        @Override
        public DocumentState approve() {
            return PUBLISHED;
        }
        
        @Override
        public DocumentState reject() {
            return DRAFT;
        }
    },
    
    /**
     * Published state - document is live and can only be archived.
     */
    PUBLISHED {
        @Override
        public DocumentState archive() {
            return ARCHIVED;
        }
    },
    
    /**
     * Terminal state - no operations allowed.
     */
    ARCHIVED;
    
    // Default implementations throw exceptions for invalid operations
    
    /**
     * Submits document for review.
     * @return next state
     * @throws IllegalStateException if operation not valid in this state
     */
    public DocumentState submit() {
        throw new IllegalStateException("Cannot submit document in " + name() + " state");
    }
    
    /**
     * Approves the document.
     * @return next state
     * @throws IllegalStateException if operation not valid in this state
     */
    public DocumentState approve() {
        throw new IllegalStateException("Cannot approve document in " + name() + " state");
    }
    
    /**
     * Rejects the document.
     * @return next state
     * @throws IllegalStateException if operation not valid in this state
     */
    public DocumentState reject() {
        throw new IllegalStateException("Cannot reject document in " + name() + " state");
    }
    
    /**
     * Archives the document.
     * @return next state
     * @throws IllegalStateException if operation not valid in this state
     */
    public DocumentState archive() {
        throw new IllegalStateException("Cannot archive document in " + name() + " state");
    }
    
    /**
     * Edits the document.
     * @param document the document being edited
     * @throws IllegalStateException if operation not valid in this state
     */
    public void edit(Document document) {
        throw new IllegalStateException("Cannot edit document in " + name() + " state");
    }
}
