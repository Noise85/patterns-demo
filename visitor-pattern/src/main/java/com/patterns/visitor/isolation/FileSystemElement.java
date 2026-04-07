package com.patterns.visitor.isolation;

/**
 * Base interface for file system elements that can be visited.
 */
public interface FileSystemElement {
    
    /**
     * Accepts a visitor to perform operations on this element.
     *
     * @param visitor the visitor to accept
     */
    void accept(FileSystemVisitor visitor);
}
