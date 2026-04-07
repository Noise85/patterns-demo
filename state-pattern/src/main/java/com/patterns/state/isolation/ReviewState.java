package com.patterns.state.isolation;

/**
 * State when document is submitted for review.
 * Allows approval (to publish) or rejection (back to draft).
 */
public class ReviewState implements DocumentState {
    
    @Override
    public void edit(Document document) {
        throw new IllegalStateException("Cannot edit a document in Review state");
    }
    
    @Override
    public void submit(Document document) {
        throw new IllegalStateException("Document is already submitted for review");
    }
    
    @Override
    public void approve(Document document) {
        document.setState(new PublishedState());
    }
    
    @Override
    public void reject(Document document) {
        document.setState(new DraftState());
    }
    
    @Override
    public void archive(Document document) {
        throw new IllegalStateException("Cannot archive a document in Review state");
    }
    
    @Override
    public String getStateName() {
        return StateEnum.REVIEW.getDisplayName();
    }
}
