package com.patterns.state.isolation;

/**
 * State when document is approved and published.
 * Only allows archiving.
 */
public class PublishedState implements DocumentState {
    
    @Override
    public void edit(Document document) {
        throw new IllegalStateException("Cannot edit a document in Published state");
    }
    
    @Override
    public void submit(Document document) {
        throw new IllegalStateException("Document is already published");
    }
    
    @Override
    public void approve(Document document) {
        throw new IllegalStateException("Cannot approve a document in Published state");
    }
    
    @Override
    public void reject(Document document) {
        throw new IllegalStateException("Cannot reject a document in Published state");
    }
    
    @Override
    public void archive(Document document) {
        document.setState(new ArchivedState());
    }
    
    @Override
    public String getStateName() {
        return StateEnum.PUBLISHED.getDisplayName();
    }
}
