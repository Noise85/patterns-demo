package com.patterns.command.isolation;

/**
 * Command interface for text editing operations.
 */
public interface Command {
    
    /**
     * Executes the command.
     */
    void execute();
    
    /**
     * Undoes the command, reversing its effects.
     */
    void undo();
    
    /**
     * Gets a human-readable description of the command.
     *
     * @return command description
     */
    String getDescription();
}
