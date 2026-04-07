package com.patterns.command.isolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Manages command execution history and supports undo/redo.
 * Acts as the Invoker in the Command pattern.
 */
public class CommandHistory {
    
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    
    public CommandHistory() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    /**
     * Executes a command and adds it to the undo history.
     * Clears the redo stack (new command creates new timeline).
     *
     * @param command the command to execute
     */
    public void executeCommand(Command command) {
        // TODO: Implement command execution
        // 1. Execute the command: command.execute()
        // 2. Push to undo stack: undoStack.push(command)
        // 3. Clear redo stack: redoStack.clear()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Undoes the last executed command.
     * Moves the command from undo stack to redo stack.
     */
    public void undo() {
        // TODO: Implement undo
        // 1. Check if undo stack is not empty
        // 2. Pop command from undo stack
        // 3. Call command.undo()
        // 4. Push command to redo stack
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Redoes the last undone command.
     * Moves the command from redo stack to undo stack.
     */
    public void redo() {
        // TODO: Implement redo
        // 1. Check if redo stack is not empty
        // 2. Pop command from redo stack
        // 3. Call command.execute()
        // 4. Push command to undo stack
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Checks if undo is possible.
     *
     * @return true if undo stack is not empty
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Checks if redo is possible.
     *
     * @return true if redo stack is not empty
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * Gets the command history (descriptions of executed commands).
     *
     * @return list of command descriptions
     */
    public List<String> getHistory() {
        // TODO: Implement history retrieval
        // Return descriptions of all commands in undo stack
        // Use command.getDescription() for each command
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
