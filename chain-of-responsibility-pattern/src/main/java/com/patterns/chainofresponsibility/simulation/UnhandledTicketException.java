package com.patterns.chainofresponsibility.simulation;

/**
 * Exception thrown when no handler can process a ticket.
 */
public class UnhandledTicketException extends RuntimeException {
    
    private final Ticket ticket;
    
    public UnhandledTicketException(Ticket ticket, String reason) {
        super(formatMessage(ticket, reason));
        this.ticket = ticket;
    }
    
    private static String formatMessage(Ticket ticket, String reason) {
        return String.format("Ticket %s (%s) could not be handled: %s",
            ticket.getId(), ticket.getTitle(), reason);
    }
    
    public Ticket getTicket() {
        return ticket;
    }
}
