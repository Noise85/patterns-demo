package com.patterns.decorator.isolation;

/**
 * Abstract Decorator base class.
 * Maintains a reference to a wrapped Notifier and provides default delegation.
 * Concrete decorators extend this class to add specific behaviors.
 */
public abstract class NotifierDecorator implements Notifier {
    
    private final Notifier wrappedNotifier;
    
    /**
     * Constructs a decorator wrapping the given notifier.
     *
     * @param notifier the notifier to wrap/decorate
     */
    protected NotifierDecorator(Notifier notifier) {
        // TODO: Store the wrapped notifier
        this.wrappedNotifier = null; // FIXME: Replace with proper initialization
    }
    
    /**
     * Default implementation delegates to the wrapped notifier.
     * Concrete decorators override this to add behavior before/after delegation.
     *
     * @param message the message to send
     * @return the result from the wrapped notifier
     */
    @Override
    public String send(String message) {
        // TODO: Delegate to the wrapped notifier
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
