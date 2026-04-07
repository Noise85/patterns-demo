# Exercise 1: Pattern in Isolation

## Objective

Implement the Command pattern for a text editor supporting undo/redo operations with Insert, Delete, and Replace commands.

## Scenario

You're building a simple text editor where all editing operations must be undoable. Each operation (insert text, delete text, replace text) is encapsulated as a command object that knows how to:

1. Execute the operation
2. Undo the operation (reverse the changes)

The editor maintains a command history for undo/redo functionality.

## Your Tasks

### 1. Create Command Interface

Create `Command` interface:
- Method: `void execute()` - performs the operation
- Method: `void undo()` - reverses the operation
- Method: `String getDescription()` - returns human-readable description

### 2. Implement Text Editor (Receiver)

Create `TextEditor` class:
- Field: `StringBuilder content` - the text content
- Method: `void insertText(int position, String text)` - inserts text at position
- Method: `void deleteText(int position, int length)` - deletes text
- Method: `void replaceText(int position, int length, String newText)` - replaces text
- Method: `String getContent()` - returns current content
- Method: `int getLength()` - returns content length

### 3. Create Insert Command

Create `InsertTextCommand`:
- Constructor: `InsertTextCommand(TextEditor editor, int position, String text)`
- `execute()`: Inserts text at position
- `undo()`: Deletes the inserted text
- `getDescription()`: Returns "Insert '[text]' at position [position]"

### 4. Create Delete Command

Create `DeleteTextCommand`:
- Constructor: `DeleteTextCommand(TextEditor editor, int position, int length)`
- Field: `String deletedText` - stores deleted text for undo
- `execute()`: Deletes text and stores it
- `undo()`: Re-inserts the deleted text
- `getDescription()`: Returns "Delete [length] characters at position [position]"

### 5. Create Replace Command

Create `ReplaceTextCommand`:
- Constructor: `ReplaceTextCommand(TextEditor editor, int position, int length, String newText)`
- Field: `String replacedText` - stores replaced text for undo
- `execute()`: Replaces text and stores old text
- `undo()`: Restores the original text
- `getDescription()`: Returns "Replace [length] characters at position [position] with '[newText]'"

### 6. Implement Command History (Invoker)

Create `CommandHistory`:
- Field: `Stack<Command> undoStack` - commands that can be undone
- Field: `Stack<Command> redoStack` - commands that can be redone
- Method: `void executeCommand(Command command)` - executes and adds to undo stack, clears redo stack
- Method: `void undo()` - undoes last command, moves to redo stack
- Method: `void redo()` - redoes last undone command
- Method: `boolean canUndo()` - returns true if undo stack not empty
- Method: `boolean canRedo()` - returns true if redo stack not empty
- Method: `List<String> getHistory()` - returns list of executed command descriptions

### 7. Implement Macro Command

Create `MacroCommand`:
- Constructor: `MacroCommand(List<Command> commands, String description)`
- `execute()`: Executes all commands in order
- `undo()`: Undoes all commands in reverse order
- `getDescription()`: Returns the macro description

## Example Usage

```java
TextEditor editor = new TextEditor();
CommandHistory history = new CommandHistory();

// Type "Hello"
Command insert1 = new InsertTextCommand(editor, 0, "Hello");
history.executeCommand(insert1);
// Content: "Hello"

// Type " World"
Command insert2 = new InsertTextCommand(editor, 5, " World");
history.executeCommand(insert2);
// Content: "Hello World"

// Delete "World"
Command delete = new DeleteTextCommand(editor, 6, 5);
history.executeCommand(delete);
// Content: "Hello "

// Undo delete (restore "World")
history.undo();
// Content: "Hello World"

// Redo delete
history.redo();
// Content: "Hello "

// Replace "Hello" with "Goodbye"
Command replace = new ReplaceTextCommand(editor, 0, 5, "Goodbye");
history.executeCommand(replace);
// Content: "Goodbye "

// Undo replace
history.undo();
// Content: "Hello "

// Macro: Insert "Dear " at start and "!" at end
Command macro = new MacroCommand(List.of(
    new InsertTextCommand(editor, 0, "Dear "),
    new InsertTextCommand(editor, editor.getLength(), "!")
), "Add greeting");
history.executeCommand(macro);
// Content: "Dear Hello !"

// Undo macro (undoes both inserts)
history.undo();
// Content: "Hello "
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Insert command**: Text inserted at correct position, undo removes it
2. **Delete command**: Text deleted correctly, undo restores it
3. **Replace command**: Text replaced correctly, undo restores original
4. **Command history**: Undo/redo work correctly
5. **Multiple undo**: Can undo multiple operations in reverse order
6. **Redo after undo**: Redo restores undone operations
7. **Redo clearing**: New command clears redo stack
8. **Macro command**: Multiple commands execute/undo as unit
9. **Edge cases**: Empty text, position boundaries, empty redo stack
10. **Command descriptions**: Descriptive text for each command

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] Commands properly encapsulate operations
- [ ] Undo/redo functionality works correctly
- [ ] Command history manages stacks properly
- [ ] Macro commands execute atomically
- [ ] Commands don't expose receiver internals

## Time Estimate

**45-60 minutes** for a developer familiar with behavioral patterns.

## Hints

- Store necessary state in command constructor or during `execute()`
- `DeleteTextCommand.execute()`: Save deleted text before deleting
- `ReplaceTextCommand.execute()`: Save replaced text before replacing
- Undo stacks: `executeCommand()` clears redo stack (new branch)
- Undo/Redo: Move commands between stacks
- Macro undo: Iterate commands in reverse order
- Use `StringBuilder` for efficient text manipulation
- Position validation: Check bounds before operations
- Description format: Be specific and user-friendly
- Command history: Use `Stack<Command>` or `Deque<Command>`
