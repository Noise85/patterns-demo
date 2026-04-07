package com.patterns.bridge.simulation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Reminder notification with normal priority.
 */
public class ReminderNotification extends Notification {
    private final String reminderText;
    private final LocalDateTime reminderTime;

    public ReminderNotification(MessageChannel channel, String recipient, 
                               String reminderText, LocalDateTime reminderTime) {
        super(channel, recipient);
        this.reminderText = reminderText;
        this.reminderTime = reminderTime;
    }

    @Override
    public void send() {
        // TODO: Send reminder with NORMAL priority
        // Subject: "Reminder"
        // Body: "You have a reminder at [time]: [reminderText]"
        // Format time as "yyyy-MM-dd HH:mm"
        // Call channel.send() with appropriate parameters
        throw new UnsupportedOperationException("TODO: Implement ReminderNotification.send()");
    }
}
