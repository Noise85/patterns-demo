# Solution Notes

## Exercise 1: File System Composite

### Architecture

```
FileSystemComponent (interface)
        ↑
        |
    +-------+-------+
    |               |
  File (leaf)   Folder (composite)
                    |
              [FileSystemComponents...]
```

### Key Points

- **Uniform interface**: Both files and folders implement `FileSystemComponent`
- **Recursive operations**: Folder delegates to children
- **Transparency**: Client doesn't know if it's working with file or folder

### Implementation Pattern

```java
public interface FileSystemComponent {
    String getName();
    long getSize();
    void print(int depth);
}

public class File implements FileSystemComponent {
    private final String name;
    private final long sizeInBytes;
    
    @Override
    public long getSize() {
        return sizeInBytes;  // Leaf operation
    }
}

public class Folder implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();
    
    public void add(FileSystemComponent component) {
        children.add(component);
    }
    
    @Override
    public long getSize() {
        // Composite operation: aggregate children
        return children.stream()
            .mapToLong(FileSystemComponent::getSize)
            .sum();
    }
    
    @Override
    public void print(int depth) {
        System.out.println(" ".repeat(depth * 2) + "[" + name + "]");
        children.forEach(child -> child.print(depth + 1));
    }
}
```

## Exercise 2: Organization Chart Composite

### Architecture

```
OrganizationComponent (interface)
        ↑
        |
    +-------+-----------+
    |                   |
Employee (leaf)   Department (composite)
                        |
                  [OrganizationComponents...]
```

### Key Points

- **Multiple aggregations**: salary budget, employee count
- **Recursive search**: findByName traverses tree
- **Nested composites**: Departments contain subdepartments

### Employee Implementation

```java
public class Employee implements OrganizationComponent {
    private final String name;
    private final String role;
    private final double salary;
    
    @Override
    public double getSalaryBudget() {
        return salary;  // Leaf returns own value
    }
    
    @Override
    public int getEmployeeCount() {
        return 1;  // Leaf counts as 1
    }
    
    @Override
    public OrganizationComponent findByName(String name) {
        return this.name.equals(name) ? this : null;
    }
}
```

### Department Implementation

```java
public class Department implements OrganizationComponent {
    private final String name;
    private final List<OrganizationComponent> members = new ArrayList<>();
    
    public void add(OrganizationComponent member) {
        members.add(member);
    }
    
    @Override
    public double getSalaryBudget() {
        // Recursive aggregation
        return members.stream()
            .mapToDouble(OrganizationComponent::getSalaryBudget)
            .sum();
    }
    
    @Override
    public int getEmployeeCount() {
        // Recursive count
        return members.stream()
            .mapToInt(OrganizationComponent::getEmployeeCount)
            .sum();
    }
    
    @Override
    public OrganizationComponent findByName(String searchName) {
        if (name.equals(searchName)) {
            return this;
        }
        // Search children
        for (OrganizationComponent member : members) {
            OrganizationComponent result = member.findByName(searchName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
```

### Benefits Demonstrated

| Aspect | Benefit |
|--------|---------|
| **Uniform Treatment** | Client calls `.getSalaryBudget()` on any component |
| **Recursive Operations** | Folder/Department automatically aggregate children |
| **Extensibility** | Add new component types easily |
| **Simplicity** | No conditionals or type-checking in client |

### Common Patterns

1. **Recursive delegation**: Composite iterates children and calls same operation
2. **Aggregation**: Sum, count, find operations
3. **Depth-first traversal**: print() recursively displays hierarchy
4. **Null checks**: findByName returns null if not found

### Design Decisions

- **Safety**: Component interface vs separate leaf/composite interfaces
- **Transparency**: Should `add()`/`remove()` be in Component or only Composite?
  - Our choice: Only in Composite (safer, more explicit)
- **Caching**: Could cache getSalaryBudget() for performance
- **Thread-safety**: Consider CopyOnWriteArrayList if needed
