package com.patterns.command.isolation;

/**
 * Command to insert text into the editor.
 */
public class InsertTextCommand implements Command {
    
    private final TextEditor editor;
    private final int position;
    private final String text;
    
    /**
     * Creates an insert text command.
     *
     * @param editor the text editor
     * @param position position to insert at
     * @param text text to insert
     */
    public InsertTextCommand(TextEditor editor, int position, String text) {
        this.editor = editor;
        this.position = position;
        this.text = text;
    }
    
    @Override
    public void execute() {
        // TODO: Implement execute
        // Call editor.insertText(position, text)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void undo() {
        // TODO: Implement undo
        // Delete the inserted text: editor.deleteText(position, text.length())
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        // TODO: Implement getDescription
        // Return format: "Insert '[text]' at position [position]"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
