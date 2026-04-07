package com.patterns.bridge.simulation;

/**
 * SMS delivery channel (simulated).
 */
public class SmsChannel implements MessageChannel {
    private static final int MAX_SMS_LENGTH = 160;

    @Override
    public void send(String recipient, String subject, String body, NotificationPriority priority) {
        // TODO: Format and send SMS
        // Concatenate subject and body: "subject: body"
        // Truncate to 160 characters if needed (add "..." at end)
        // Output format: "Sending SMS to [recipient]: [truncated message]"
        throw new UnsupportedOperationException("TODO: Implement SmsChannel.send()");
    }
}
