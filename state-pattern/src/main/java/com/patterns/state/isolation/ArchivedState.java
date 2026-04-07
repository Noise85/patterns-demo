package com.patterns.state.isolation;

/**
 * Terminal state when document is archived.
 * No operations are allowed.
 */
public class ArchivedState implements DocumentState {
    
    @Override
    public void edit(Document document) {
        throw new IllegalStateException("Cannot edit a document in Archived state");
    }
    
    @Override
    public void submit(Document document) {
        throw new IllegalStateException("Cannot submit a document in Archived state");
    }
    
    @Override
    public void approve(Document document) {
        throw new IllegalStateException("Cannot approve a document in Archived state");
    }
    
    @Override
    public void reject(Document document) {
        throw new IllegalStateException("Cannot reject a document in Archived state");
    }
    
    @Override
    public void archive(Document document) {
        throw new IllegalStateException("Document is already archived");
    }
    
    @Override
    public String getStateName() {
        return StateEnum.ARCHIVED.getDisplayName();
    }
}
