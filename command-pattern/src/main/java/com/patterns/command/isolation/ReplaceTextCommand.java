package com.patterns.command.isolation;

/**
 * Command to replace text in the editor.
 */
public class ReplaceTextCommand implements Command {
    
    private final TextEditor editor;
    private final int position;
    private final int length;
    private final String newText;
    private String replacedText;  // Stores replaced text for undo
    
    /**
     * Creates a replace text command.
     *
     * @param editor the text editor
     * @param position starting position
     * @param length number of characters to replace
     * @param newText new text
     */
    public ReplaceTextCommand(TextEditor editor, int position, int length, String newText) {
        this.editor = editor;
        this.position = position;
        this.length = length;
        this.newText = newText;
    }
    
    @Override
    public void execute() {
        // TODO: Implement execute
        // 1. Save the text that will be replaced:
        //    replacedText = editor.getContent().substring(position, position + length)
        // 2. Replace the text: editor.replaceText(position, length, newText)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void undo() {
        // TODO: Implement undo
        // 1. Delete the new text: editor.deleteText(position, newText.length())
        // 2. Re-insert the original text: editor.insertText(position, replacedText)
        // OR use replaceText: editor.replaceText(position, newText.length(), replacedText)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        // TODO: Implement getDescription
        // Return format: "Replace [length] characters at position [position] with '[newText]'"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
