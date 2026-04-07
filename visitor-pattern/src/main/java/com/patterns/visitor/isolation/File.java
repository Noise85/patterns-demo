package com.patterns.visitor.isolation;

/**
 * Represents a file in the file system.
 */
public class File implements FileSystemElement {
    
    private final String name;
    private final long size;
    private final String extension;
    
    /**
     * Creates a new file.
     *
     * @param name      file name
     * @param size      file size in bytes
     * @param extension file extension (without dot)
     */
    public File(String name, long size, String extension) {
        this.name = name;
        this.size = size;
        this.extension = extension;
    }
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }
    
    public String getName() {
        return name;
    }
    
    public long getSize() {
        return size;
    }
    
    public String getExtension() {
        return extension;
    }
    
    @Override
    public String toString() {
        return String.format("%s.%s (%d bytes)", name, extension, size);
    }
}
