package com.patterns.visitor.isolation;

/**
 * Visitor interface for file system operations.
 * Implements the Visitor pattern for file system traversal.
 */
public interface FileSystemVisitor {
    
    /**
     * Visits a file.
     *
     * @param file the file to visit
     */
    void visit(File file);
    
    /**
     * Visits a directory.
     *
     * @param directory the directory to visit
     */
    void visit(Directory directory);
}
