package com.patterns.memento.isolation;

import com.patterns.memento.isolation.TextEditor.Memento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Memento Pattern - Isolation Exercise (Text Editor Undo).
 */
@DisplayName("Memento Pattern - Text Editor with Undo")
class IsolationExerciseTest {
    
    private TextEditor editor;
    private EditorHistory history;
    
    @BeforeEach
    void setUp() {
        editor = new TextEditor();
        history = new EditorHistory();
    }
    
    // Text Editor Operations Tests
    
    @Test
    @DisplayName("Should start with empty content")
    void testInitialState() {
        assertThat(editor.getContent()).isEmpty();
        assertThat(editor.getCursorPosition()).isZero();
    }
    
    @Test
    @DisplayName("Should write text at cursor position")
    void testWrite() {
        editor.write("Hello");
        
        assertThat(editor.getContent()).isEqualTo("Hello");
        assertThat(editor.getCursorPosition()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should write text in middle when cursor positioned")
    void testWriteAtCursor() {
        editor.write("Hello");
        editor.setCursor(2); // Position between "He|llo"
        editor.write("X");
        
        assertThat(editor.getContent()).isEqualTo("HeXllo");
        assertThat(editor.getCursorPosition()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should delete character before cursor")
    void testDelete() {
        editor.write("Hello");
        editor.deleteLast();
        
        assertThat(editor.getContent()).isEqualTo("Hell");
        assertThat(editor.getCursorPosition()).isEqualTo(4);
    }
    
    @Test
    @DisplayName("Should not delete when cursor at start")
    void testDeleteAtStart() {
        editor.write("Hello");
        editor.setCursor(0);
        editor.deleteLast();
        
        assertThat(editor.getContent()).isEqualTo("Hello");
        assertThat(editor.getCursorPosition()).isZero();
    }
    
    @Test
    @DisplayName("Should set cursor position")
    void testSetCursor() {
        editor.write("Hello");
        editor.setCursor(2);
        
        assertThat(editor.getCursorPosition()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should reject invalid cursor position")
    void testInvalidCursorPosition() {
        editor.write("Hello");
        
        assertThatThrownBy(() -> editor.setCursor(-1))
            .isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> editor.setCursor(10))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    // Memento Save/Restore Tests
    
    @Test
    @DisplayName("Should save state to memento")
    void testSaveState() {
        editor.write("Test");
        Memento memento = editor.save();
        
        assertThat(memento).isNotNull();
    }
    
    @Test
    @DisplayName("Should restore content from memento")
    void testRestoreContent() {
        editor.write("Initial");
        Memento memento = editor.save();
        
        editor.write(" Changed");
        editor.restore(memento);
        
        assertThat(editor.getContent()).isEqualTo("Initial");
    }
    
    @Test
    @DisplayName("Should restore cursor position from memento")
    void testRestoreCursor() {
        editor.write("Hello");
        editor.setCursor(2);
        Memento memento = editor.save();
        
        editor.setCursor(5);
        editor.restore(memento);
        
        assertThat(editor.getCursorPosition()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should restore both content and cursor")
    void testRestoreCompleteState() {
        editor.write("Hello World");
        editor.setCursor(5);
        Memento memento = editor.save();
        
        editor.write("X");
        assertThat(editor.getContent()).isEqualTo("HelloX World");
        
        editor.restore(memento);
        assertThat(editor.getContent()).isEqualTo("Hello World");
        assertThat(editor.getCursorPosition()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should handle empty state")
    void testSaveEmptyState() {
        Memento memento = editor.save();
        
        editor.write("Something");
        editor.restore(memento);
        
        assertThat(editor.getContent()).isEmpty();
        assertThat(editor.getCursorPosition()).isZero();
    }
    
    // Multiple Snapshots Tests
    
    @Test
    @DisplayName("Should handle multiple independent mementos")
    void testMultipleMementos() {
        editor.write("A");
        Memento m1 = editor.save();
        
        editor.write("B");
        Memento m2 = editor.save();
        
        editor.write("C");
        Memento m3 = editor.save();
        
        editor.restore(m2);
        assertThat(editor.getContent()).isEqualTo("AB");
        
        editor.restore(m1);
        assertThat(editor.getContent()).isEqualTo("A");
        
        editor.restore(m3);
        assertThat(editor.getContent()).isEqualTo("ABC");
    }
    
    @Test
    @DisplayName("Should not affect memento when state changes")
    void testMementoImmutability() {
        editor.write("Original");
        Memento memento = editor.save();
        
        editor.write(" Modified");
        editor.deleteLast();
        editor.deleteLast();
        
        // Memento should still restore original state
        editor.restore(memento);
        assertThat(editor.getContent()).isEqualTo("Original");
    }
    
    // History Tests
    
    @Test
    @DisplayName("History should start empty")
    void testHistoryInitialState() {
        assertThat(history.isEmpty()).isTrue();
        assertThat(history.size()).isZero();
    }
    
    @Test
    @DisplayName("Should push memento to history")
    void testHistoryPush() {
        Memento memento = editor.save();
        history.push(memento);
        
        assertThat(history.isEmpty()).isFalse();
        assertThat(history.size()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should pop memento from history")
    void testHistoryPop() {
        editor.write("Test");
        Memento memento = editor.save();
        history.push(memento);
        
        Memento popped = history.pop();
        
        assertThat(popped).isSameAs(memento);
        assertThat(history.isEmpty()).isTrue();
    }
    
    @Test
    @DisplayName("Should return null when popping empty history")
    void testPopEmptyHistory() {
        Memento memento = history.pop();
        
        assertThat(memento).isNull();
    }
    
    @Test
    @DisplayName("Should handle multiple push/pop operations")
    void testMultiplePushPop() {
        editor.write("A");
        history.push(editor.save());
        
        editor.write("B");
        history.push(editor.save());
        
        editor.write("C");
        history.push(editor.save());
        
        assertThat(history.size()).isEqualTo(3);
        
        Memento m3 = history.pop();
        Memento m2 = history.pop();
        Memento m1 = history.pop();
        
        assertThat(history.isEmpty()).isTrue();
        
        // Verify correct order (LIFO)
        editor.restore(m3);
        assertThat(editor.getContent()).isEqualTo("ABC");
        
        editor.restore(m2);
        assertThat(editor.getContent()).isEqualTo("AB");
        
        editor.restore(m1);
        assertThat(editor.getContent()).isEqualTo("A");
    }
    
    // Complete Workflow Tests
    
    @Test
    @DisplayName("Should support simple undo workflow")
    void testSimpleUndo() {
        editor.write("Hello");
        history.push(editor.save());
        
        editor.write(" World");
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        // Undo
        Memento memento = history.pop();
        editor.restore(memento);
        
        assertThat(editor.getContent()).isEqualTo("Hello");
    }
    
    @Test
    @DisplayName("Should support multiple undo levels")
    void testMultipleUndoLevels() {
        editor.write("First");
        history.push(editor.save());
        
        editor.write(" Second");
        history.push(editor.save());
        
        editor.write(" Third");
        history.push(editor.save());
        
        // Undo twice
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEqualTo("First Second");
        
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEqualTo("First");
    }
    
    @Test
    @DisplayName("Should preserve cursor through undo")
    void testUndoPreservesCursor() {
        editor.write("Hello");
        editor.setCursor(2);
        history.push(editor.save());
        
        editor.write("X");
        editor.setCursor(5);
        
        // Undo
        editor.restore(history.pop());
        
        assertThat(editor.getContent()).isEqualTo("Hello");
        assertThat(editor.getCursorPosition()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should handle complex editing scenario")
    void testComplexEditingScenario() {
        // Type "Hello"
        editor.write("Hello");
        history.push(editor.save());
        
        // Type " World"
        editor.write(" World");
        history.push(editor.save());
        
        // Delete last character
        editor.deleteLast();
        history.push(editor.save());
        
        // Undo delete
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEqualTo("Hello World");
        
        // Undo " World"
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEqualTo("Hello");
        
        // Undo "Hello"
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle insert in middle scenario")
    void testInsertInMiddleScenario() {
        editor.write("HelloWorld");
        editor.setCursor(5);
        history.push(editor.save());
        
        editor.write(" ");
        
        // Undo space insertion
        editor.restore(history.pop());
        assertThat(editor.getContent()).isEqualTo("HelloWorld");
        assertThat(editor.getCursorPosition()).isEqualTo(5);
    }
}
