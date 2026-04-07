package com.patterns.state.isolation;

/**
 * Context class representing a document in a workflow system.
 * Delegates all operations to the current state.
 */
public class Document {
    
    private final String title;
    private DocumentState currentState;
    private int editCount;
    
    /**
     * Creates a new document in Draft state.
     *
     * @param title document title
     */
    public Document(String title) {
        this.title = title;
        this.currentState = new DraftState();
        this.editCount = 0;
    }
    
    /**
     * Edits the document (delegates to current state).
     *
     * @throws IllegalStateException if editing not allowed in current state
     */
    public void edit() {
        currentState.edit(this);
    }
    
    /**
     * Submits document for review (delegates to current state).
     *
     * @throws IllegalStateException if submission not allowed in current state
     */
    public void submit() {
        currentState.submit(this);
    }
    
    /**
     * Approves the document (delegates to current state).
     *
     * @throws IllegalStateException if approval not allowed in current state
     */
    public void approve() {
        currentState.approve(this);
    }
    
    /**
     * Rejects the document (delegates to current state).
     *
     * @throws IllegalStateException if rejection not allowed in current state
     */
    public void reject() {
        currentState.reject(this);
    }
    
    /**
     * Archives the document (delegates to current state).
     *
     * @throws IllegalStateException if archiving not allowed in current state
     */
    public void archive() {
        currentState.archive(this);
    }
    
    /**
     * Changes the current state of the document.
     * Called by state objects to transition to new states.
     *
     * @param newState the new state
     */
    public void setState(DocumentState newState) {
        this.currentState = newState;
    }
    
    /**
     * Increments the edit count.
     * Called by DraftState when editing is allowed.
     */
    public void incrementEditCount() {
        editCount++;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getCurrentStateName() {
        return currentState.getStateName();
    }
    
    public int getEditCount() {
        return editCount;
    }
}
