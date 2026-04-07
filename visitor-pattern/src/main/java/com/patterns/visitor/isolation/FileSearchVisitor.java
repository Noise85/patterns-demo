package com.patterns.visitor.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that searches for files matching specific criteria.
 * Can search by name pattern or file extension.
 */
public class FileSearchVisitor implements FileSystemVisitor {
    
    private final String searchPattern;  // null means search by extension only
    private final String extension;      // null means search by name only
    private final List<File> results;
    
    /**
     * Creates a search visitor for files by name pattern.
     *
     * @param searchPattern pattern to match in file name
     */
    public FileSearchVisitor(String searchPattern) {
        this(searchPattern, null);
    }
    
    /**
     * Creates a search visitor for files by name pattern or extension.
     *
     * @param searchPattern pattern to match in file name (null to skip name matching)
     * @param extension     extension to match (null to skip extension matching)
     */
    public FileSearchVisitor(String searchPattern, String extension) {
        this.searchPattern = searchPattern;
        this.extension = extension;
        this.results = new ArrayList<>();
    }
    
    @Override
    public void visit(File file) {
        boolean matchesName = (searchPattern == null) || file.getName().contains(searchPattern);
        boolean matchesExtension = (extension == null) || file.getExtension().equalsIgnoreCase(extension);
        
        if (matchesName && matchesExtension) {
            results.add(file);
        }
    }
    
    @Override
    public void visit(Directory directory) {
        for(FileSystemElement child : directory.getChildren()) {
            child.accept(this);  // Continue searching in child elements
        }
    }
    
    /**
     * Returns the list of files matching the search criteria.
     *
     * @return list of matching files
     */
    public List<File> getResults() {
        return new ArrayList<>(results);  // Defensive copy
    }
    
    /**
     * Returns the number of files found.
     *
     * @return count of matching files
     */
    public int getResultCount() {
        return results.size();
    }
}
