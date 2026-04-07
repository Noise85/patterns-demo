package com.patterns.state.alternative;

/**
 * Review state - document under review, can be approved or rejected.
 * Only overrides approve and reject operations.
 */
public class ReviewState implements DocumentState {
    
    @Override
    public String getStateName() {
        return "Review";
    }
    
    @Override
    public void approve(Document document) {
        document.setState(new PublishedState());
    }
    
    @Override
    public void reject(Document document) {
        document.setState(new DraftState());
    }
    
    // edit(), submit(), archive() use default implementations from interface
    // They will throw IllegalStateException with appropriate message
}
