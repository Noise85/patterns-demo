package com.patterns.composite.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite component representing a folder.
 * Can contain files and other folders.
 */
public class Folder implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    /**
     * Adds a component (file or folder) to this folder.
     *
     * @param component Component to add
     */
    public void add(FileSystemComponent component) {
        // TODO: Add component to children list
        throw new UnsupportedOperationException("TODO: Implement Folder.add()");
    }

    /**
     * Removes a component from this folder.
     *
     * @param component Component to remove
     */
    public void remove(FileSystemComponent component) {
        // TODO: Remove component from children list
        throw new UnsupportedOperationException("TODO: Implement Folder.remove()");
    }

    /**
     * Gets all children of this folder.
     *
     * @return List of children
     */
    public List<FileSystemComponent> getChildren() {
        // TODO: Return children list (defensive copy recommended)
        throw new UnsupportedOperationException("TODO: Implement Folder.getChildren()");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        // TODO: Calculate total size by summing all children's sizes
        // Hint: use stream().mapToLong(FileSystemComponent::getSize).sum()
        throw new UnsupportedOperationException("TODO: Implement Folder.getSize()");
    }

    @Override
    public void print(int depth) {
        // TODO: Print folder name with indentation
        // Format: "  " repeated depth times, then "[foldername]"
        // Then recursively print all children with depth + 1
        throw new UnsupportedOperationException("TODO: Implement Folder.print()");
    }
}
