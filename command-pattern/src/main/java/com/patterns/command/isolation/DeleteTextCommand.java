package com.patterns.command.isolation;

/**
 * Command to delete text from the editor.
 */
public class DeleteTextCommand implements Command {
    
    private final TextEditor editor;
    private final int position;
    private final int length;
    private String deletedText;  // Stores deleted text for undo
    
    /**
     * Creates a delete text command.
     *
     * @param editor the text editor
     * @param position starting position
     * @param length number of characters to delete
     */
    public DeleteTextCommand(TextEditor editor, int position, int length) {
        this.editor = editor;
        this.position = position;
        this.length = length;
    }
    
    @Override
    public void execute() {
        // TODO: Implement execute
        // 1. Save the text that will be deleted:
        //    deletedText = editor.getContent().substring(position, position + length)
        // 2. Delete the text: editor.deleteText(position, length)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void undo() {
        // TODO: Implement undo
        // Re-insert the deleted text: editor.insertText(position, deletedText)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        // TODO: Implement getDescription
        // Return format: "Delete [length] characters at position [position]"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
