package com.patterns.chainofresponsibility.simulation;

import java.time.LocalDateTime;

/**
 * Support ticket to be processed by handler chain.
 */
public class Ticket {
    
    private final String id;
    private final String title;
    private final String description;
    private final TicketCategory category;
    private final int complexityScore;
    private final Priority priority;
    private final LocalDateTime created;
    private TicketStatus status;
    private String assignedTo;
    
    /**
     * Creates a support ticket.
     *
     * @param id unique ticket ID
     * @param title ticket title
     * @param description ticket description
     * @param category ticket category
     * @param complexityScore complexity (0-100)
     * @param priority priority level
     * @param created creation timestamp
     */
    public Ticket(String id, String title, String description, 
                 TicketCategory category, int complexityScore,
                 Priority priority, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.complexityScore = complexityScore;
        this.priority = priority;
        this.created = created;
        this.status = TicketStatus.OPEN;
    }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    
    public void assignTo(String handlerName) {
        this.assignedTo = handlerName;
    }
    
    // Getters
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public TicketCategory getCategory() {
        return category;
    }
    
    public int getComplexityScore() {
        return complexityScore;
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }
    
    public TicketStatus getStatus() {
        return status;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
}
