package com.patterns.bridge.simulation;

/**
 * Urgent alert notification with high priority.
 */
public class AlertNotification extends Notification {
    private final String alertMessage;

    public AlertNotification(MessageChannel channel, String recipient, String alertMessage) {
        super(channel, recipient);
        this.alertMessage = alertMessage;
    }

    @Override
    public void send() {
        // TODO: Send alert with HIGH priority
        // Subject: "[ALERT] Urgent Notification"
        // Body: alertMessage
        // Call channel.send() with appropriate parameters
        throw new UnsupportedOperationException("TODO: Implement AlertNotification.send()");
    }
}
