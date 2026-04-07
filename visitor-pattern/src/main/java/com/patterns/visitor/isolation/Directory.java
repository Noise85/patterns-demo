package com.patterns.visitor.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a directory in the file system.
 * Can contain files and other directories.
 */
public class Directory implements FileSystemElement {
    
    private final String name;
    private final List<FileSystemElement> children;
    
    /**
     * Creates a new directory.
     *
     * @param name directory name
     */
    public Directory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }
    
    /**
     * Adds an element (file or directory) to this directory.
     *
     * @param element the element to add
     */
    public void addElement(FileSystemElement element) {
        children.add(element);
    }
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }
    
    public String getName() {
        return name;
    }
    
    public List<FileSystemElement> getChildren() {
        return new ArrayList<>(children);  // Defensive copy
    }
    
    public int getChildCount() {
        return children.size();
    }
    
    @Override
    public String toString() {
        return String.format("%s/ (%d items)", name, children.size());
    }
}
