package com.patterns.proxy.simulation;

/**
 * Document interface - implemented by both real document and proxy.
 */
public interface Document {
    
    /**
     * Gets the document content.
     *
     * @return the content
     */
    String getContent();
    
    /**
     * Edits the document content.
     *
     * @param newContent the new content
     */
    void editContent(String newContent);
    
    /**
     * Deletes the document.
     */
    void delete();
    
    /**
     * Sets the minimum role required to access this document.
     *
     * @param role the required role
     */
    void setRequiredRole(Role role);
    
    /**
     * Gets document metadata.
     *
     * @return formatted metadata string
     */
    String getMetadata();
    
    /**
     * Checks if the document has been deleted.
     *
     * @return true if deleted
     */
    boolean isDeleted();
}
