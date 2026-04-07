package com.patterns.state.isolation;

/**
 * State interface for document workflow.
 * Each state implements this interface to define allowed operations.
 */
public interface DocumentState {
    
    /**
     * Edit the document (modify content).
     *
     * @param document the document being edited
     * @throws IllegalStateException if editing is not allowed in this state
     */
    void edit(Document document);
    
    /**
     * Submit document for review.
     *
     * @param document the document being submitted
     * @throws IllegalStateException if submission is not allowed in this state
     */
    void submit(Document document);
    
    /**
     * Approve the document for publishing.
     *
     * @param document the document being approved
     * @throws IllegalStateException if approval is not allowed in this state
     */
    void approve(Document document);
    
    /**
     * Reject the document and send back for editing.
     *
     * @param document the document being rejected
     * @throws IllegalStateException if rejection is not allowed in this state
     */
    void reject(Document document);
    
    /**
     * Archive the published document.
     *
     * @param document the document being archived
     * @throws IllegalStateException if archiving is not allowed in this state
     */
    void archive(Document document);
    
    /**
     * Returns the name of this state.
     *
     * @return state name
     */
    String getStateName();
}
