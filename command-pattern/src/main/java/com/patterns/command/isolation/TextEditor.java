package com.patterns.command.isolation;

/**
 * Text editor that supports basic editing operations.
 * Acts as the Receiver in the Command pattern.
 */
public class TextEditor {
    
    private final StringBuilder content;
    
    public TextEditor() {
        this.content = new StringBuilder();
    }
    
    /**
     * Inserts text at the specified position.
     *
     * @param position position to insert at
     * @param text text to insert
     */
    public void insertText(int position, String text) {
        // TODO: Implement text insertion
        // Use StringBuilder.insert(position, text)
        // Validate position is within bounds [0, content.length()]
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Deletes text at the specified position.
     *
     * @param position starting position
     * @param length number of characters to delete
     */
    public void deleteText(int position, int length) {
        // TODO: Implement text deletion
        // Use StringBuilder.delete(position, position + length)
        // Validate position and length are valid
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Replaces text at the specified position.
     *
     * @param position starting position
     * @param length number of characters to replace
     * @param newText new text
     */
    public void replaceText(int position, int length, String newText) {
        // TODO: Implement text replacement
        // Delete old text, then insert new text
        // Or use StringBuilder.replace(position, position + length, newText)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the current content.
     *
     * @return current text content
     */
    public String getContent() {
        return content.toString();
    }
    
    /**
     * Gets the current content length.
     *
     * @return content length
     */
    public int getLength() {
        return content.length();
    }
}
