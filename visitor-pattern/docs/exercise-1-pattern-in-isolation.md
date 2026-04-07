# Exercise 1: Visitor Pattern in Isolation

## Objective

Implement the Visitor pattern for a simple file system structure. You'll create visitors that calculate total size and search for files matching criteria.

## Scenario

You're building a file system utility that needs to perform various operations on files and directories:
- Calculate total storage used
- Count files and directories
- Search for specific files

Rather than adding these operations directly to `File` and `Directory` classes, you'll use the Visitor pattern to keep operations separate.

## Requirements

### 1. Element Hierarchy

Create a file system element hierarchy:

```java
interface FileSystemElement {
    void accept(FileSystemVisitor visitor);
}

class File implements FileSystemElement {
    - name: String
    - size: long (bytes)
    - extension: String
}

class Directory implements FileSystemElement {
    - name: String
    - children: List<FileSystemElement>
    
    + addElement(FileSystemElement): void
}
```

### 2. Visitor Interface

Define the visitor interface:

```java
interface FileSystemVisitor {
    void visitFile(File file);
    void visitDirectory(Directory directory);
}
```

### 3. Concrete Visitors

Implement two visitors:

#### SizeCalculatorVisitor
- Calculates total size of all files in the structure
- For files: Add file size
- For directories: Recursively visit all children
- Provides `getTotalSize()` method

#### FileSearchVisitor
- Searches for files by name or extension
- Constructor accepts search criteria (name pattern or extension)
- Collects matching files in a list
- Provides `getResults()` method

## Implementation Guidelines

### File Class
```java
public class File implements FileSystemElement {
    private final String name;
    private final long size;
    private final String extension;
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        // TODO: Call the appropriate visitor method
        // This is the key to double dispatch!
    }
}
```

### Directory Class
```java
public class Directory implements FileSystemElement {
    private final String name;
    private final List<FileSystemElement> children;
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        // TODO: 
        // 1. Let the visitor visit this directory
        // 2. Let the visitor visit all children
    }
}
```

### SizeCalculatorVisitor
```java
public class SizeCalculatorVisitor implements FileSystemVisitor {
    private long totalSize = 0;
    
    @Override
    public void visitFile(File file) {
        // TODO: Add file size to total
    }
    
    @Override
    public void visitDirectory(Directory directory) {
        // TODO: Nothing special needed - children will be visited automatically
        // Or implement traversal logic here if directory controls traversal
    }
    
    public long getTotalSize() {
        return totalSize;
    }
}
```

### FileSearchVisitor
```java
public class FileSearchVisitor implements FileSystemVisitor {
    private final String searchPattern;  // null means search by extension
    private final String extension;      // null means search by name
    private final List<File> results;
    
    @Override
    public void visitFile(File file) {
        // TODO: Check if file matches criteria, add to results if yes
    }
}
```

## Example Usage

```java
// Build file system structure
Directory root = new Directory("root");
Directory documents = new Directory("documents");
Directory photos = new Directory("photos");

documents.addElement(new File("report.pdf", 1024, "pdf"));
documents.addElement(new File("notes.txt", 512, "txt"));
photos.addElement(new File("vacation.jpg", 2048, "jpg"));
photos.addElement(new File("family.jpg", 1536, "jpg"));

root.addElement(documents);
root.addElement(photos);

// Calculate total size
SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
root.accept(sizeCalc);
System.out.println("Total size: " + sizeCalc.getTotalSize()); // 5120 bytes

// Search for all JPG files
FileSearchVisitor jpgSearch = new FileSearchVisitor(null, "jpg");
root.accept(jpgSearch);
System.out.println("Found " + jpgSearch.getResults().size() + " JPG files");
```

## Key Learning Points

1. **Double Dispatch**: `element.accept(visitor)` → `visitor.visitElement(this)`
2. **Separation of Concerns**: File system structure vs. operations on it
3. **Open/Closed**: Add new visitors without modifying File/Directory
4. **Traversal Control**: Who decides traversal order (element or visitor)?

## Testing

The test suite verifies:
- ✅ File accepts visitor correctly
- ✅ Directory accepts visitor and visits children
- ✅ SizeCalculatorVisitor computes correct totals
- ✅ SizeCalculatorVisitor handles nested directories
- ✅ FileSearchVisitor finds files by name
- ✅ FileSearchVisitor finds files by extension
- ✅ Empty directories return correct results

## Challenge Questions

1. What happens if you add a `Symlink` element type?
2. How would you implement a visitor that stops early (e.g., finds first match)?
3. Could you use Java 8 Streams instead of Visitor? What are trade-offs?
4. How would you pass parameters to visitors (e.g., max depth)?

## Common Pitfalls

- ❌ Forgetting to call `visitor.visitDirectory(this)` in Directory
- ❌ Not visiting children in Directory's `accept()` method
- ❌ Accidentally modifying element classes when adding operations
- ❌ Confusing which class controls traversal (element vs visitor)
