package com.patterns.bridge.simulation;

/**
 * Email delivery channel (simulated).
 */
public class EmailChannel implements MessageChannel {
    @Override
    public void send(String recipient, String subject, String body, NotificationPriority priority) {
        // TODO: Format and send email
        // Include HTML formatting
        // Add priority to email headers
        // Output format: "Sending email to [recipient]: [subject] (Priority: [priority])"
        throw new UnsupportedOperationException("TODO: Implement EmailChannel.send()");
    }
}
