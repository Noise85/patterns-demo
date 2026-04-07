package com.patterns.visitor.compound;

// Import base components from isolation package
import com.patterns.visitor.isolation.File;
import com.patterns.visitor.isolation.Directory;
import com.patterns.visitor.isolation.FileSystemVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A composite visitor that applies multiple visitors simultaneously during traversal.
 * <p>
 * Implements the Composite pattern for visitors. Allows combining multiple visitor
 * operations into a single tree traversal for efficiency.
 * </p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * // Apply multiple operations in one pass
 * SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
 * FileCounterVisitor counter = new FileCounterVisitor();
 * PrintVisitor printer = new PrintVisitor();
 * 
 * CompositeVisitor composite = new CompositeVisitor(sizeCalc, counter, printer);
 * root.accept(composite);
 * 
 * // All three visitors have processed the tree
 * System.out.println("Size: " + sizeCalc.getTotalSize());
 * System.out.println("Count: " + counter.getFileCount());
 * }</pre>
 * 
 * <h3>Combining with FilteringVisitor:</h3>
 * <pre>{@code
 * // Apply multiple operations only to filtered files
 * CompositeVisitor multiOp = new CompositeVisitor(sizeCalc, counter);
 * FilteringVisitor filter = new FilteringVisitor("report", "pdf", multiOp);
 * 
 * root.accept(filter);
 * }</pre>
 */
public class CompositeVisitor implements FileSystemVisitor {
    
    private final List<FileSystemVisitor> visitors;
    
    /**
     * Creates a composite visitor with the given visitors.
     *
     * @param visitors the visitors to apply (must not be null or empty)
     */
    public CompositeVisitor(FileSystemVisitor... visitors) {
        if (visitors == null || visitors.length == 0) {
            throw new IllegalArgumentException("At least one visitor must be provided");
        }
        
        for (FileSystemVisitor visitor : visitors) {
            if (visitor == null) {
                throw new IllegalArgumentException("Visitor cannot be null");
            }
        }
        
        this.visitors = new ArrayList<>(Arrays.asList(visitors));
    }
    
    /**
     * Creates a composite visitor from a list of visitors.
     *
     * @param visitors list of visitors to apply
     */
    public CompositeVisitor(List<FileSystemVisitor> visitors) {
        if (visitors == null || visitors.isEmpty()) {
            throw new IllegalArgumentException("At least one visitor must be provided");
        }
        
        for (FileSystemVisitor visitor : visitors) {
            if (visitor == null) {
                throw new IllegalArgumentException("Visitor cannot be null");
            }
        }
        
        this.visitors = new ArrayList<>(visitors);
    }
    
    @Override
    public void visit(File file) {
        // Apply all visitors to the file
        for (FileSystemVisitor visitor : visitors) {
            visitor.visit(file);
        }
    }
    
    @Override
    public void visit(Directory directory) {
        // Apply all visitors to the directory
        for (FileSystemVisitor visitor : visitors) {
            visitor.visit(directory);
        }
    }
    
    /**
     * Returns an unmodifiable view of the wrapped visitors.
     *
     * @return list of visitors
     */
    public List<FileSystemVisitor> getVisitors() {
        return Collections.unmodifiableList(visitors);
    }
    
    /**
     * Gets the number of visitors in this composite.
     *
     * @return visitor count
     */
    public int getVisitorCount() {
        return visitors.size();
    }
}
