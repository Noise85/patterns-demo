package com.patterns.memento.isolation;

import com.patterns.memento.isolation.TextEditor.Memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Manages undo history for the text editor.
 * Acts as the Caretaker in the Memento pattern.
 */
public class EditorHistory {
    
    private final Deque<Memento> history;
    
    public EditorHistory() {
        this.history = new ArrayDeque<>();
    }
    
    /**
     * Saves a memento to the history.
     *
     * @param memento memento to save
     */
    public void push(Memento memento) {
        // TODO: Add memento to history
        // Use history.push() to add to top of stack
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Retrieves and removes the most recent memento.
     *
     * @return most recent memento, or null if history is empty
     */
    public Memento pop() {
        // TODO: Remove and return most recent memento
        // Return null if history is empty
        // Use history.isEmpty() to check, history.pop() to remove
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Checks if history is empty.
     *
     * @return true if no mementos stored
     */
    public boolean isEmpty() {
        // TODO: Return whether history is empty
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the number of mementos in history.
     *
     * @return size of history
     */
    public int size() {
        // TODO: Return history size
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
