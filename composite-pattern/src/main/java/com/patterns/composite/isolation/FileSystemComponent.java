package com.patterns.composite.isolation;

/**
 * Component interface for file system elements.
 * Both files and folders implement this interface.
 */
public interface FileSystemComponent {
    /**
     * Gets the name of the component.
     *
     * @return Component name
     */
    String getName();

    /**
     * Gets the size of the component in bytes.
     * For files, returns the file size.
     * For folders, returns the sum of all children's sizes.
     *
     * @return Size in bytes
     */
    long getSize();

    /**
     * Prints the component hierarchy with indentation.
     *
     * @param depth Indentation depth
     */
    void print(int depth);
}
