package com.patterns.bridge.simulation;

/**
 * Push notification channel (simulated).
 */
public class PushChannel implements MessageChannel {
    @Override
    public void send(String recipient, String subject, String body, NotificationPriority priority) {
        // TODO: Format and send push notification
        // Use subject as title
        // Priority affects badge/sound
        // Output format: "Sending push to [recipient]: [subject] - [body] (Priority: [priority])"
        throw new UnsupportedOperationException("TODO: Implement PushChannel.send()");
    }
}
