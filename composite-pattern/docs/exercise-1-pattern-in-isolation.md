# Exercise 1: Pattern in Isolation

## Title
File System Composite

## Learning Objectives

- Model tree structures with Composite pattern
- Treat individual and composite objects uniformly
- Implement recursive operations on hierarchies
- Practice the Component-Leaf-Composite structure
- Understand part-whole relationships

## Scenario

Build a simplified file system where:
- **Files** are leaf nodes (no children, have size)
- **Folders** are composites (contain files and subfolders)
- Both files and folders implement `FileSystemComponent` interface
- Operations like `getSize()` work uniformly on both

## Functional Requirements

### 1. Component Interface (`FileSystemComponent`)

```java
String getName();
long getSize();      // File returns its size, Folder returns sum of children
void print(int depth);  // Display hierarchy
```

### 2. Leaf (`File`)

- Has name and size (bytes)
- `getSize()` returns file size
- `print()` displays file with indentation

### 3. Composite (`Folder`)

- Has name and children (List<FileSystemComponent>)
- Methods: `add(component)`, `remove(component)`, `getChildren()`
- `getSize()` returns sum of all children's sizes (recursive)
- `print()` displays folder name and recursively prints children

## Non-Functional Expectations

- Client treats files and folders uniformly
- Size calculation is recursive for folders
- Hierarchy is displayed properly with indentation

## Constraints

- Use composition (Folder contains List<FileSystemComponent>)
- No type-checking in client code (no `instanceof`)

## Starter Code Location

`src/main/java/com/patterns/composite/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- File stores name and sizeInBytes
- Folder stores List<FileSystemComponent> children
- Folder.getSize() loops through children: `children.stream().mapToLong(FileSystemComponent::getSize).sum()`
- print() uses depth parameter for indentation: `" ".repeat(depth * 2)`
</details>
