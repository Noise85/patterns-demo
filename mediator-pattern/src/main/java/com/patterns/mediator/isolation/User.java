package com.patterns.mediator.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Colleague class representing a chat user.
 * Communicates with other users only through the ChatMediator.
 */
public class User {
    
    private final String name;
    private final ChatMediator mediator;
    private final List<String> messages;
    
    /**
     * Creates a new user with the specified name and mediator.
     *
     * @param name     the user's display name
     * @param mediator the chat mediator for communication
     */
    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        this.messages = new ArrayList<>();
    }
    
    /**
     * Sends a message to all users in the chat room.
     *
     * @param message the message to send
     */
    public void send(String message) {
        // TODO: Delegate to mediator.sendMessage()
        // Pass this message and this user instance to mediator
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sends a private message to a specific user.
     *
     * @param message       the message to send
     * @param recipientName the name of the recipient
     */
    public void sendPrivate(String message, String recipientName) {
        // TODO: Delegate to mediator.sendPrivateMessage()
        // Pass message, this user instance, and recipient name
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Receives a message from the mediator.
     * This method is called by the mediator when messages arrive.
     *
     * @param message the received message
     */
    public void receive(String message) {
        // TODO: Store message in messages list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns all messages received by this user.
     *
     * @return unmodifiable list of messages
     */
    public List<String> getMessages() {
        // TODO: Return unmodifiable copy of messages list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the user's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
