package com.patterns.proxy.simulation;

import java.time.LocalDateTime;

/**
 * Real document implementation - contains business logic only.
 * No security checks (that's the proxy's job).
 */
public class RealDocument implements Document {
    
    private final String title;
    private String content;
    private final String author;
    private final LocalDateTime created;
    private Role requiredRole;
    private boolean deleted;
    
    /**
     * Creates a real document.
     *
     * @param title document title
     * @param content document content
     * @param author document author
     * @param requiredRole minimum role required
     */
    public RealDocument(String title, String content, String author, Role requiredRole) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.requiredRole = requiredRole;
        this.created = LocalDateTime.now();
        this.deleted = false;
    }
    
    @Override
    public String getContent() {
        // TODO: Implement content retrieval
        // Return the content
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void editContent(String newContent) {
        // TODO: Implement content editing
        // Update this.content with newContent
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void delete() {
        // TODO: Implement document deletion
        // Set deleted = true
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void setRequiredRole(Role role) {
        // TODO: Implement role update
        // Update this.requiredRole
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getMetadata() {
        // TODO: Implement metadata formatting
        // Return formatted string with: title, author, created date, required role
        // Example: "Title: Q4 Report | Author: Finance Team | Created: 2026-04-07T21:00:00 | Required Role: VIEWER"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean isDeleted() {
        return deleted;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }
    
    public Role getRequiredRole() {
        return requiredRole;
    }
}
