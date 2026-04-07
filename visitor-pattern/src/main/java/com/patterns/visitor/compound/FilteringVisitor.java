package com.patterns.visitor.compound;

// Import base components from isolation package
import com.patterns.visitor.isolation.File;
import com.patterns.visitor.isolation.Directory;
import com.patterns.visitor.isolation.FileSystemElement;
import com.patterns.visitor.isolation.FileSystemVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A filtering visitor that applies another visitor only to files matching criteria.
 * <p>
 * Demonstrates visitor composition and the decorator pattern.
 * Wraps any FileSystemVisitor and delegates only when elements match filter criteria.
 * </p>
 * 
 * <h3>Architecture:</h3>
 * <pre>
 * FilteringVisitor
 *   ├─ Filtering logic (what to select)
 *   └─ Target visitor (what to do with selected items)
 * </pre>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * // Calculate size of all PDF files containing "report"
 * SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
 * FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);
 * 
 * root.accept(filter);
 * 
 * System.out.println("Matched: " + filter.getMatchCount());
 * System.out.println("Total size: " + sizeVisitor.getTotalSize());
 * }</pre>
 */
public class FilteringVisitor implements FileSystemVisitor {
    
    // Filter criteria
    private final String searchPattern;  // null means match all names
    private final String extension;      // null means match all extensions
    
    // Results tracking
    private final List<File> matchedFiles;
    
    // The visitor to apply to matched files
    private final FileSystemVisitor targetVisitor;
    
    /**
     * Creates a filtering visitor that searches by name pattern.
     *
     * @param searchPattern pattern to match in file names
     * @param targetVisitor visitor to apply to matched files
     */
    public FilteringVisitor(String searchPattern, FileSystemVisitor targetVisitor) {
        this(searchPattern, null, targetVisitor);
    }
    
    /**
     * Creates a filtering visitor with full filter control.
     * <p>
     * Either parameter can be null to match all values for that criterion.
     * </p>
     *
     * @param searchPattern pattern to match in file names (null = match all)
     * @param extension file extension to match (null = match all)
     * @param targetVisitor visitor to apply to matched files (must not be null)
     */
    public FilteringVisitor(String searchPattern, String extension, FileSystemVisitor targetVisitor) {
        if (targetVisitor == null) {
            throw new IllegalArgumentException("Target visitor cannot be null");
        }
        
        this.searchPattern = searchPattern;
        this.extension = extension;
        this.matchedFiles = new ArrayList<>();
        this.targetVisitor = targetVisitor;
    }
    
    @Override
    public void visit(File file) {
        // Step 1: Apply filter criteria
        boolean matchesName = (searchPattern == null) || 
                              file.getName().contains(searchPattern);
        boolean matchesExtension = (extension == null) || 
                                   file.getExtension().equalsIgnoreCase(extension);
        
        // Step 2: If matches, record it and delegate to target visitor
        if (matchesName && matchesExtension) {
            matchedFiles.add(file);
            targetVisitor.visit(file);  // Key: delegate only for matched files
        }
        // Non-matching files are silently skipped (no target visitor call)
    }
    
    @Override
    public void visit(Directory directory) {
        // Recurse through directory structure
        // Note: Directories themselves are not filtered, only files
        for (FileSystemElement child : directory.getChildren()) {
            child.accept(this);
        }
        
        // We don't delegate directory visits to target visitor
        // Most filtering use cases only care about files
        // If needed, this behavior could be made configurable
    }
    
    /**
     * Returns the list of files that matched the filter criteria.
     * <p>
     * This list is populated as the visitor traverses the tree.
     * </p>
     *
     * @return defensive copy of matched files list
     */
    public List<File> getMatchedFiles() {
        return new ArrayList<>(matchedFiles);
    }
    
    /**
     * Gets the count of matched files.
     * <p>
     * More efficient than calling getMatchedFiles().size() if you only need the count.
     * </p>
     *
     * @return number of matched files
     */
    public int getMatchCount() {
        return matchedFiles.size();
    }
    
    /**
     * Gets the search pattern used for filtering.
     *
     * @return search pattern, or null if not filtering by name
     */
    public String getSearchPattern() {
        return searchPattern;
    }
    
    /**
     * Gets the extension used for filtering.
     *
     * @return extension, or null if not filtering by extension
     */
    public String getExtension() {
        return extension;
    }
}
