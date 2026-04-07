package com.patterns.command.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Command Pattern - Isolation Exercise (Text Editor with Undo/Redo).
 */
@DisplayName("Command Pattern - Text Editor with Undo/Redo")
class IsolationExerciseTest {
    
    private TextEditor editor;
    private CommandHistory history;
    
    @BeforeEach
    void setUp() {
        editor = new TextEditor();
        history = new CommandHistory();
    }
    
    @Test
    @DisplayName("Should insert text at position")
    void testInsertText() {
        Command insert = new InsertTextCommand(editor, 0, "Hello");
        history.executeCommand(insert);
        
        assertThat(editor.getContent()).isEqualTo("Hello");
        assertThat(editor.getLength()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should undo insert command")
    void testUndoInsert() {
        Command insert = new InsertTextCommand(editor, 0, "Hello");
        history.executeCommand(insert);
        
        history.undo();
        
        assertThat(editor.getContent()).isEmpty();
        assertThat(editor.getLength()).isZero();
    }
    
    @Test
    @DisplayName("Should delete text at position")
    void testDeleteText() {
        editor.insertText(0, "Hello World");
        
        Command delete = new DeleteTextCommand(editor, 6, 5);
        history.executeCommand(delete);
        
        assertThat(editor.getContent()).isEqualTo("Hello ");
    }
    
    @Test
    @DisplayName("Should undo delete command")
    void testUndoDelete() {
        editor.insertText(0, "Hello World");
        
        Command delete = new DeleteTextCommand(editor, 6, 5);
        history.executeCommand(delete);
        assertThat(editor.getContent()).isEqualTo("Hello ");
        
        history.undo();
        assertThat(editor.getContent()).isEqualTo("Hello World");
    }
    
    @Test
    @DisplayName("Should replace text at position")
    void testReplaceText() {
        editor.insertText(0, "Hello World");
        
        Command replace = new ReplaceTextCommand(editor, 0, 5, "Goodbye");
        history.executeCommand(replace);
        
        assertThat(editor.getContent()).isEqualTo("Goodbye World");
    }
    
    @Test
    @DisplayName("Should undo replace command")
    void testUndoReplace() {
        editor.insertText(0, "Hello World");
        
        Command replace = new ReplaceTextCommand(editor, 0, 5, "Goodbye");
        history.executeCommand(replace);
        assertThat(editor.getContent()).isEqualTo("Goodbye World");
        
        history.undo();
        assertThat(editor.getContent()).isEqualTo("Hello World");
    }
    
    @Test
    @DisplayName("Should redo insert command")
    void testRedoInsert() {
        Command insert = new InsertTextCommand(editor, 0, "Hello");
        history.executeCommand(insert);
        history.undo();
        
        history.redo();
        
        assertThat(editor.getContent()).isEqualTo("Hello");
    }
    
    @Test
    @DisplayName("Should redo delete command")
    void testRedoDelete() {
        editor.insertText(0, "Hello World");
        Command delete = new DeleteTextCommand(editor, 6, 5);
        history.executeCommand(delete);
        history.undo();
        
        history.redo();
        
        assertThat(editor.getContent()).isEqualTo("Hello ");
    }
    
    @Test
    @DisplayName("Should clear redo stack on new command")
    void testRedoStackClearing() {
        Command insert1 = new InsertTextCommand(editor, 0, "Hello");
        Command insert2 = new InsertTextCommand(editor, 5, " World");
        
        history.executeCommand(insert1);
        history.executeCommand(insert2);
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        // Undo both
        history.undo();
        history.undo();
        assertThat(editor.getContent()).isEmpty();
        
        // Execute new command - should clear redo stack
        Command insert3 = new InsertTextCommand(editor, 0, "Goodbye");
        history.executeCommand(insert3);
        assertThat(editor.getContent()).isEqualTo("Goodbye");
        
        // Cannot redo " World" anymore
        assertThat(history.canRedo()).isFalse();
    }
    
    @Test
    @DisplayName("Should handle multiple undo operations")
    void testMultipleUndos() {
        history.executeCommand(new InsertTextCommand(editor, 0, "A"));
        history.executeCommand(new InsertTextCommand(editor, 1, "B"));
        history.executeCommand(new InsertTextCommand(editor, 2, "C"));
        assertThat(editor.getContent()).isEqualTo("ABC");
        
        history.undo();
        assertThat(editor.getContent()).isEqualTo("AB");
        
        history.undo();
        assertThat(editor.getContent()).isEqualTo("A");
        
        history.undo();
        assertThat(editor.getContent()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle multiple redo operations")
    void testMultipleRedos() {
        history.executeCommand(new InsertTextCommand(editor, 0, "A"));
        history.executeCommand(new InsertTextCommand(editor, 1, "B"));
        history.executeCommand(new InsertTextCommand(editor, 2, "C"));
        
        history.undo();
        history.undo();
        history.undo();
        assertThat(editor.getContent()).isEmpty();
        
        history.redo();
        assertThat(editor.getContent()).isEqualTo("A");
        
        history.redo();
        assertThat(editor.getContent()).isEqualTo("AB");
        
        history.redo();
        assertThat(editor.getContent()).isEqualTo("ABC");
    }
    
    @Test
    @DisplayName("Should check if undo is possible")
    void testCanUndo() {
        assertThat(history.canUndo()).isFalse();
        
        history.executeCommand(new InsertTextCommand(editor, 0, "Hello"));
        assertThat(history.canUndo()).isTrue();
        
        history.undo();
        assertThat(history.canUndo()).isFalse();
    }
    
    @Test
    @DisplayName("Should check if redo is possible")
    void testCanRedo() {
        assertThat(history.canRedo()).isFalse();
        
        history.executeCommand(new InsertTextCommand(editor, 0, "Hello"));
        assertThat(history.canRedo()).isFalse();
        
        history.undo();
        assertThat(history.canRedo()).isTrue();
        
        history.redo();
        assertThat(history.canRedo()).isFalse();
    }
    
    @Test
    @DisplayName("Should provide command descriptions")
    void testCommandDescriptions() {
        Command insert = new InsertTextCommand(editor, 0, "Hello");
        assertThat(insert.getDescription()).contains("Insert").contains("Hello").contains("0");
        
        editor.insertText(0, "Hello World");
        Command delete = new DeleteTextCommand(editor, 0, 5);
        assertThat(delete.getDescription()).contains("Delete").contains("5");
        
        Command replace = new ReplaceTextCommand(editor, 0, 5, "Goodbye");
        assertThat(replace.getDescription()).contains("Replace").contains("Goodbye");
    }
    
    @Test
    @DisplayName("Should track command history")
    void testCommandHistory() {
        history.executeCommand(new InsertTextCommand(editor, 0, "Hello"));
        history.executeCommand(new InsertTextCommand(editor, 5, " World"));
        history.executeCommand(new DeleteTextCommand(editor, 5, 6));
        
        List<String> historyList = history.getHistory();
        assertThat(historyList).hasSize(3);
        assertThat(historyList.get(0)).contains("Insert").contains("Hello");
        assertThat(historyList.get(1)).contains("Insert").contains(" World");
        assertThat(historyList.get(2)).contains("Delete");
    }
    
    @Test
    @DisplayName("Should execute macro command")
    void testMacroCommand() {
        Command macro = new MacroCommand(List.of(
            new InsertTextCommand(editor, 0, "Hello"),
            new InsertTextCommand(editor, 5, " World"),
            new InsertTextCommand(editor, 11, "!")
        ), "Add greeting");
        
        history.executeCommand(macro);
        
        assertThat(editor.getContent()).isEqualTo("Hello World!");
    }
    
    @Test
    @DisplayName("Should undo macro command in reverse order")
    void testUndoMacroCommand() {
        Command macro = new MacroCommand(List.of(
            new InsertTextCommand(editor, 0, "A"),
            new InsertTextCommand(editor, 1, "B"),
            new InsertTextCommand(editor, 2, "C")
        ), "Add ABC");
        
        history.executeCommand(macro);
        assertThat(editor.getContent()).isEqualTo("ABC");
        
        history.undo();
        assertThat(editor.getContent()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle empty text operations")
    void testEmptyOperations() {
        Command insert = new InsertTextCommand(editor, 0, "");
        history.executeCommand(insert);
        
        assertThat(editor.getContent()).isEmpty();
        assertThat(history.canUndo()).isTrue();
    }
    
    @Test
    @DisplayName("Should handle complex editing sequence")
    void testComplexEditingSequence() {
        // Type "Hello"
        history.executeCommand(new InsertTextCommand(editor, 0, "Hello"));
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        // Type " World"
        history.executeCommand(new InsertTextCommand(editor, 5, " World"));
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        // Delete " World"
        history.executeCommand(new DeleteTextCommand(editor, 5, 6));
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        // Replace "Hello" with "Goodbye"
        history.executeCommand(new ReplaceTextCommand(editor, 0, 5, "Goodbye"));
        assertThat(editor.getContent()).isEqualTo("Goodbye");
        
        // Undo back to "Hello"
        history.undo();
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        // Undo back to "Hello World"
        history.undo();
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        // Redo to "Hello"
        history.redo();
        assertThat(editor.getContent()).isEqualTo("Hello");
    }
    
    @Test
    @DisplayName("Should handle text editor insert at different positions")
    void testTextEditorInsertPositions() {
        editor.insertText(0, "Hello");
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        editor.insertText(5, " World");
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        editor.insertText(5, " Beautiful");
        assertThat(editor.getContent()).isEqualTo("Hello Beautiful World");
    }
    
    @Test
    @DisplayName("Should handle text editor delete at different positions")
    void testTextEditorDeletePositions() {
        editor.insertText(0, "Hello World");
        
        editor.deleteText(5, 6);
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        editor.deleteText(0, 2);
        assertThat(editor.getContent()).isEqualTo("llo");
    }
    
    @Test
    @DisplayName("Should handle text editor replace operation")
    void testTextEditorReplace() {
        editor.insertText(0, "Hello World");
        
        editor.replaceText(0, 5, "Goodbye");
        assertThat(editor.getContent()).isEqualTo("Goodbye World");
        
        editor.replaceText(8, 5, "Friend");
        assertThat(editor.getContent()).isEqualTo("Goodbye Friend");
    }
    
    @Test
    @DisplayName("Should handle macro with mixed operations")
    void testMacroWithMixedOperations() {
        editor.insertText(0, "Hello World");
        
        Command macro = new MacroCommand(List.of(
            new DeleteTextCommand(editor, 5, 6),
            new InsertTextCommand(editor, 5, " Beautiful"),
            new InsertTextCommand(editor, editor.getLength(), "!")
        ), "Beautiful transformation");
        
        history.executeCommand(macro);
        assertThat(editor.getContent()).isEqualTo("Hello Beautiful!");
        
        history.undo();
        assertThat(editor.getContent()).isEqualTo("Hello World");
    }
}
