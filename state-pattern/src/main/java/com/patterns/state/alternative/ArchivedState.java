package com.patterns.state.alternative;

/**
 * Archived state - terminal state where no operations are allowed.
 * Doesn't override any operations - all use default implementations that throw.
 * 
 * <p>This demonstrates the power of default interface methods:
 * a complete state implementation in just 3 lines of code!
 */
public class ArchivedState implements DocumentState {
    
    @Override
    public String getStateName() {
        return "Archived";
    }
    
    // All operations (edit, submit, approve, reject, archive) use defaults
    // They will all throw IllegalStateException with appropriate messages
}
