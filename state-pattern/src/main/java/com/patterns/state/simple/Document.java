package com.patterns.state.simple;

/**
 * Context class for document workflow using enum-based state machine.
 * 
 * <p>This implementation uses an enum instead of separate state classes.
 * Compare this to the isolation package to see the trade-offs:
 * 
 * <h3>Enum Approach (this class):</h3>
 * <ul>
 *   <li>Simpler - one enum file instead of 5 classes</li>
 *   <li>All transitions visible in one place</li>
 *   <li>No object allocation on state transitions</li>
 *   <li>Type-safe - can't create invalid states</li>
 *   <li>Better for simple state machines</li>
 * </ul>
 * 
 * <h3>Class-Based Approach (isolation package):</h3>
 * <ul>
 *   <li>States can have instance variables</li>
 *   <li>Richer behavior per state</li>
 *   <li>Open for extension (new states without modifying existing code)</li>
 *   <li>Better for complex state machines with dependencies</li>
 * </ul>
 */
public class Document {
    
    private final String title;
    private DocumentState state;
    private int editCount;
    
    /**
     * Creates a new document in DRAFT state.
     *
     * @param title document title
     */
    public Document(String title) {
        this.title = title;
        this.state = DocumentState.DRAFT;
        this.editCount = 0;
    }
    
    /**
     * Edits the document.
     *
     * @throws IllegalStateException if editing not allowed in current state
     */
    public void edit() {
        state.edit(this);
    }
    
    /**
     * Submits document for review.
     *
     * @throws IllegalStateException if submission not allowed in current state
     */
    public void submit() {
        state = state.submit();
    }
    
    /**
     * Approves the document.
     *
     * @throws IllegalStateException if approval not allowed in current state
     */
    public void approve() {
        state = state.approve();
    }
    
    /**
     * Rejects the document.
     *
     * @throws IllegalStateException if rejection not allowed in current state
     */
    public void reject() {
        state = state.reject();
    }
    
    /**
     * Archives the document.
     *
     * @throws IllegalStateException if archiving not allowed in current state
     */
    public void archive() {
        state = state.archive();
    }
    
    /**
     * Increments the edit count.
     * Called by DocumentState.DRAFT when edit() is invoked.
     */
    void incrementEditCount() {
        editCount++;
    }
    
    // Getters
    
    public String getTitle() {
        return title;
    }
    
    public DocumentState getState() {
        return state;
    }
    
    public String getCurrentStateName() {
        return state.name();
    }
    
    public int getEditCount() {
        return editCount;
    }
}
