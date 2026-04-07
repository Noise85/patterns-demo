# Exercise 1: Pattern in Isolation - Text Editor Undo

## Objective

Implement a simple **text editor with undo functionality** using the Memento pattern. This exercise demonstrates the core concepts of capturing and restoring state without breaking encapsulation.

---

## Scenario

You're building a basic text editor that needs undo capability. Users should be able to:
- Type text into the editor
- Undo changes to restore previous content
- Create multiple undo points

The editor maintains both **content** and **cursor position** as internal state.

---

## Requirements

### Classes to Implement

#### 1. `TextEditor` (Originator)

Manages editor state and creates/restores mementos.

**State**:
- `content: String` - Current text content
- `cursorPosition: int` - Current cursor position (0 to content.length)

**Operations**:
- `write(String text)` - Append text at cursor position, move cursor to end
- `deleteLast()` - Delete character before cursor (if any), move cursor back
- `setCursor(int position)` - Move cursor to specified position
- `save(): Memento` - Create memento of current state
- `restore(Memento memento)` - Restore state from memento
- `getContent(): String` - Get current content (for testing)
- `getCursorPosition(): int` - Get cursor position (for testing)

#### 2. `TextEditor.Memento` (Memento)

Immutable snapshot of editor state.

**State**:
- `content: String` - Saved text content
- `cursorPosition: int` - Saved cursor position

**Requirements**:
- Nested class within `TextEditor`
- Private constructor (only `TextEditor` can create)
- Immutable (no setters)
- Package-private getters (accessible to `TextEditor`)

#### 3. `EditorHistory` (Caretaker)

Manages undo history.

**State**:
- `history: Deque<Memento>` - Stack of saved states

**Operations**:
- `push(Memento memento)` - Save a memento
- `pop(): Memento` - Retrieve and remove most recent memento (returns null if empty)
- `isEmpty(): boolean` - Check if history is empty
- `size(): int` - Get history size

---

## Implementation Details

### Text Editor Behavior

**Writing text**:
```
Initial: "Hello" (cursor at 5)
write(" World") → "Hello World" (cursor at 11)
```

**Deleting**:
```
Content: "Hello" (cursor at 5)
deleteLast() → "Hell" (cursor at 4)
```

**Cursor positioning**:
```
Content: "Hello" (cursor at 5)
setCursor(2) → "Hello" (cursor at 2)
write("X") → "HeXllo" (cursor at 3)
```

### Memento Creation

```java
TextEditor editor = new TextEditor();
editor.write("Hello");

Memento m = editor.save(); // Capture: content="Hello", cursor=5

editor.write(" World");
editor.restore(m); // Restore: content="Hello", cursor=5
```

### History Management

```java
EditorHistory history = new EditorHistory();

editor.write("First");
history.push(editor.save());

editor.write(" Second");
history.push(editor.save());

Memento undo = history.pop(); // Get "First Second"
editor.restore(undo);
```

---

## Expected Workflow

### Scenario 1: Simple Undo

```java
TextEditor editor = new TextEditor();
EditorHistory history = new EditorHistory();

// Type "Hello"
editor.write("Hello");
history.push(editor.save()); // Checkpoint

// Type " World"
editor.write(" World");
// Current: "Hello World"

// Undo
Memento memento = history.pop();
editor.restore(memento);
// Restored: "Hello"
```

### Scenario 2: Multiple Undo Levels

```java
editor.write("A");
history.push(editor.save()); // State 1

editor.write("B");
history.push(editor.save()); // State 2

editor.write("C");
history.push(editor.save()); // State 3

// Undo twice
editor.restore(history.pop()); // Back to "AB"
editor.restore(history.pop()); // Back to "A"
```

### Scenario 3: Cursor Preservation

```java
editor.write("Hello");
editor.setCursor(2); // Cursor between "He|llo"
Memento m = editor.save();

editor.write("X"); // "HeXllo"
editor.restore(m); // Restores both content and cursor position
```

---

## Key Concepts to Demonstrate

1. **Encapsulation**: Memento internal state hidden from `EditorHistory`
2. **Immutability**: Mementos cannot be modified after creation
3. **Nested class**: `Memento` is nested in `TextEditor` for access control
4. **Opaque handle**: Caretaker treats mementos as black boxes

---

## Success Criteria

Your implementation should:

1. ✅ Save and restore editor state correctly
2. ✅ Preserve both content and cursor position
3. ✅ Keep memento internals hidden from caretaker
4. ✅ Support multiple undo levels
5. ✅ Handle empty state (no content)
6. ✅ Make mementos immutable
7. ✅ Use proper encapsulation (nested class, private constructor)

---

## Testing Guidelines

Tests will verify:

- **State capture**: Saving captures exact state
- **State restoration**: Restoring returns to saved state
- **Multiple snapshots**: Multiple mementos work independently
- **Cursor handling**: Cursor position is preserved
- **History operations**: Push/pop work correctly
- **Encapsulation**: Memento internals not exposed
- **Edge cases**: Empty editor, empty history

---

## Hints

1. **Defensive copying**: Ensure strings are immutable (Java strings are already immutable)
2. **Cursor validation**: Ensure cursor stays within valid bounds (0 to content.length)
3. **Nested class access**: Inner class can access outer class private members
4. **Deque usage**: `ArrayDeque<>` is perfect for undo history
5. **Null handling**: History should return null when empty, not throw

---

## Common Pitfalls to Avoid

❌ **Public memento fields**: Don't expose state
❌ **Mutable memento**: Don't add setters
❌ **External memento class**: Use nested class for encapsulation
❌ **Forgetting cursor**: Must save both content and cursor
❌ **Shallow copying**: Ensure proper copying (strings are safe)

---

## Extension Ideas (Optional)

Once tests pass, consider:

1. **Redo support**: Add second stack for redo operations
2. **History limit**: Cap history size to prevent memory issues
3. **Timestamps**: Add timestamp to each memento
4. **Named checkpoints**: Allow naming important states
5. **Memento comparison**: Check if current state matches memento

This exercise teaches the fundamentals of Memento pattern with a familiar, intuitive example.
