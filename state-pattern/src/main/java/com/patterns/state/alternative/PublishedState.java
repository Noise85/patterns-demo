package com.patterns.state.alternative;

/**
 * Published state - document is live, can only be archived.
 * Only overrides archive operation.
 */
public class PublishedState implements DocumentState {
    
    @Override
    public String getStateName() {
        return "Published";
    }
    
    @Override
    public void archive(Document document) {
        document.setState(new ArchivedState());
    }
    
    // edit(), submit(), approve(), reject() use default implementations
    // They will throw IllegalStateException with appropriate message
}
