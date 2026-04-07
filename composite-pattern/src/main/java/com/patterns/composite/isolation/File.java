package com.patterns.composite.isolation;

/**
 * Leaf component representing a file.
 */
public class File implements FileSystemComponent {
    private final String name;
    private final long sizeInBytes;

    public File(String name, long sizeInBytes) {
        this.name = name;
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        // TODO: Return the file size
        throw new UnsupportedOperationException("TODO: Implement File.getSize()");
    }

    @Override
    public void print(int depth) {
        // TODO: Print file with indentation
        // Format: "  " repeated depth times, then filename and size
        // Example: "    document.txt (1024 bytes)"
        throw new UnsupportedOperationException("TODO: Implement File.print()");
    }
}
