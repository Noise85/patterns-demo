package com.patterns.mediator.isolation;

/**
 * Mediator interface for chat room communication.
 * Defines the contract for coordinating communication between users.
 */
public interface ChatMediator {
    
    /**
     * Sends a message to all users in the chat room except the sender.
     *
     * @param message the message content
     * @param sender  the user sending the message
     */
    void sendMessage(String message, User sender);
    
    /**
     * Sends a private message to a specific user.
     *
     * @param message       the message content
     * @param sender        the user sending the message
     * @param recipientName the name of the user to receive the message
     */
    void sendPrivateMessage(String message, User sender, String recipientName);
    
    /**
     * Adds a user to the chat room and announces their arrival.
     *
     * @param user the user to add
     */
    void addUser(User user);
    
    /**
     * Removes a user from the chat room and announces their departure.
     *
     * @param user the user to remove
     */
    void removeUser(User user);
}
