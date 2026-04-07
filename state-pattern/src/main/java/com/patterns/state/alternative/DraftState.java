package com.patterns.state.alternative;

/**
 * Draft state - the initial state where documents can be edited and submitted.
 * Only overrides operations that are valid in this state.
 */
public class DraftState implements DocumentState {
    
    @Override
    public String getStateName() {
        return "Draft";
    }
    
    @Override
    public void edit(Document document) {
        document.incrementEditCount();
    }
    
    @Override
    public void submit(Document document) {
        document.setState(new ReviewState());
    }
    
    // approve(), reject(), archive() use default implementations from interface
    // They will throw IllegalStateException with appropriate message
}
