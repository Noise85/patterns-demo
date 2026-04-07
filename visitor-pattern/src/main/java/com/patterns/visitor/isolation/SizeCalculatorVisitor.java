package com.patterns.visitor.isolation;

/**
 * Visitor that calculates the total size of all files in a file system structure.
 */
public class SizeCalculatorVisitor implements FileSystemVisitor {
    
    private long totalSize;
    
    public SizeCalculatorVisitor() {
        this.totalSize = 0;
    }
    
    @Override
    public void visit(File file) {
        totalSize += file.getSize();
    }
    
    @Override
    public void visit(Directory directory) {
        for (FileSystemElement child : directory.getChildren()) {
            child.accept(this);  // Continue calculating size for child elements
        }
    }
    
    /**
     * Returns the total size of all files visited.
     *
     * @return total size in bytes
     */
    public long getTotalSize() {
        return totalSize;
    }
}
