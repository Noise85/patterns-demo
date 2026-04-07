package com.patterns.bridge.simulation;

/**
 * Implementor interface for message delivery channels.
 */
public interface MessageChannel {
    /**
     * Sends a message through this channel.
     *
     * @param recipient Recipient identifier (email, phone, device ID)
     * @param subject   Message subject/title
     * @param body      Message body/content
     * @param priority  Message priority
     */
    void send(String recipient, String subject, String body, NotificationPriority priority);
}
