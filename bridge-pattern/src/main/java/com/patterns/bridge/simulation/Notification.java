package com.patterns.bridge.simulation;

/**
 * Abstraction for notifications.
 * Delegates delivery to MessageChannel implementor.
 */
public abstract class Notification {
    protected MessageChannel channel;
    protected String recipient;

    protected Notification(MessageChannel channel, String recipient) {
        this.channel = channel;
        this.recipient = recipient;
    }

    /**
     * Sends the notification through the configured channel.
     */
    public abstract void send();
}
