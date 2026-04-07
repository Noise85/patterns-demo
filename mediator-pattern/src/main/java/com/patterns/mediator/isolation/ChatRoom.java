package com.patterns.mediator.isolation;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete mediator that implements chat room coordination logic.
 * Routes messages between users and manages the user registry.
 */
public class ChatRoom implements ChatMediator {
    
    private final Map<String, User> users;
    
    public ChatRoom() {
        this.users = new HashMap<>();
    }
    
    @Override
    public void sendMessage(String message, User sender) {
        // TODO: Broadcast message to all users except sender
        // Format: "[SenderName]: [message]"
        // Iterate through users map and call receive() on each user (except sender)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void sendPrivateMessage(String message, User sender, String recipientName) {
        // TODO: Send private message to specific recipient
        // 1. Look up recipient in users map by name
        // 2. If found, format message as "[SenderName] (private): [message]"
        // 3. Call recipient.receive() with formatted message
        // 4. If not found, send error message back to sender: "System: User [name] not found"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void addUser(User user) {
        // TODO: Add user to chat room
        // 1. First, announce to existing users: "[name] joined the chat"
        // 2. Then add user to users map (key: user name)
        // 3. Check for duplicate names - if exists, handle appropriately
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void removeUser(User user) {
        // TODO: Remove user from chat room
        // 1. First, remove user from users map
        // 2. Then announce to remaining users: "[name] left the chat"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of users currently in the chat room.
     *
     * @return user count
     */
    public int getUserCount() {
        // TODO: Return size of users map
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
