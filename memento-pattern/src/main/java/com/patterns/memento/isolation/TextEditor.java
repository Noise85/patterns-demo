package com.patterns.memento.isolation;

/**
 * A simple text editor supporting undo functionality via the Memento pattern.
 * Manages text content and cursor position.
 */
public class TextEditor {
    
    private String content;
    private int cursorPosition;
    
    public TextEditor() {
        this.content = "";
        this.cursorPosition = 0;
    }
    
    /**
     * Writes text at the current cursor position.
     * Moves cursor to the end of the inserted text.
     *
     * @param text text to write
     */
    public void write(String text) {
        // TODO: Implement write operation
        // 1. Split content into: before cursor + after cursor
        // 2. Reconstruct: before + text + after
        // 3. Update cursor position to end of inserted text
        // Hint: Use substring(0, cursorPosition) and substring(cursorPosition)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Deletes the character before the cursor.
     * Moves cursor back by one position.
     * Does nothing if cursor is at the start.
     */
    public void deleteLast() {
        // TODO: Implement delete operation
        // 1. Check if cursor > 0
        // 2. Remove character before cursor
        // 3. Move cursor back by 1
        // Hint: Use substring to rebuild content without the deleted character
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets the cursor position.
     *
     * @param position new cursor position (0 to content.length())
     * @throws IllegalArgumentException if position is out of bounds
     */
    public void setCursor(int position) {
        // TODO: Implement cursor setting
        // 1. Validate position (0 <= position <= content.length())
        // 2. Set cursorPosition
        // Throw IllegalArgumentException if invalid
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Creates a memento of the current editor state.
     *
     * @return memento capturing current state
     */
    public Memento save() {
        // TODO: Create and return a new Memento
        // Pass current content and cursorPosition to Memento constructor
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Restores the editor state from a memento.
     *
     * @param memento memento to restore from
     */
    public void restore(Memento memento) {
        // TODO: Restore state from memento
        // 1. Get content from memento
        // 2. Get cursor position from memento
        // 3. Assign to instance fields
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the current content (for testing).
     *
     * @return current content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Gets the current cursor position (for testing).
     *
     * @return current cursor position
     */
    public int getCursorPosition() {
        return cursorPosition;
    }
    
    /**
     * Memento class - captures editor state.
     * Nested class with restricted access.
     */
    public static final class Memento {
        
        private final String content;
        private final int cursorPosition;
        
        /**
         * Private constructor - only TextEditor can create mementos.
         *
         * @param content text content to save
         * @param cursorPosition cursor position to save
         */
        private Memento(String content, int cursorPosition) {
            // TODO: Implement memento creation
            // Assign parameters to final fields
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        /**
         * Gets the saved content.
         * Package-private access.
         *
         * @return saved content
         */
        String getContent() {
            // TODO: Return the saved content
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        /**
         * Gets the saved cursor position.
         * Package-private access.
         *
         * @return saved cursor position
         */
        int getCursorPosition() {
            // TODO: Return the saved cursor position
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
