package com.patterns.state.isolation;

/**
 * Initial state when a document is created.
 * Allows editing and submission for review.
 */
public class DraftState implements DocumentState {
    
    @Override
    public void edit(Document document) {
        document.incrementEditCount();
    }
    
    @Override
    public void submit(Document document) {
        document.setState(new ReviewState());
    }
    
    @Override
    public void approve(Document document) {
        throw new IllegalStateException("Cannot approve a document in Draft state");
    }
    
    @Override
    public void reject(Document document) {
        throw new IllegalStateException("Cannot reject a document in Draft state");
    }
    
    @Override
    public void archive(Document document) {
        throw new IllegalStateException("Cannot archive a document in Draft state");
    }
    
    @Override
    public String getStateName() {
        return StateEnum.DRAFT.getDisplayName();
    }
}
