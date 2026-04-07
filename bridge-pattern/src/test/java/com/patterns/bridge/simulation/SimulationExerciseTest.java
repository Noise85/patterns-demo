package com.patterns.bridge.simulation;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Multi-Channel Notification System
 */
class SimulationExerciseTest {

    @Test
    void alertNotification_withEmailChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new EmailChannel();
        Notification alert = new AlertNotification(channel, "user@example.com", "System overload detected");

        alert.send();

        String result = output.toString();
        assertThat(result).contains("Sending email");
        assertThat(result).contains("user@example.com");
        assertThat(result).contains("[ALERT]");
        assertThat(result).contains("HIGH");
    }

    @Test
    void alertNotification_withSmsChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new SmsChannel();
        Notification alert = new AlertNotification(channel, "+1234567890", "Server down");

        alert.send();

        String result = output.toString();
        assertThat(result).contains("Sending SMS");
        assertThat(result).contains("+1234567890");
        assertThat(result).contains("ALERT");
    }

    @Test
    void alertNotification_withPushChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new PushChannel();
        Notification alert = new AlertNotification(channel, "device-123", "Urgent: Security breach");

        alert.send();

        String result = output.toString();
        assertThat(result).contains("Sending push");
        assertThat(result).contains("device-123");
        assertThat(result).contains("ALERT");
        assertThat(result).contains("HIGH");
    }

    @Test
    void reminderNotification_withEmailChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new EmailChannel();
        LocalDateTime time = LocalDateTime.of(2024, 6, 15, 14, 30);
        Notification reminder = new ReminderNotification(
            channel, 
            "user@example.com", 
            "Team meeting",
            time
        );

        reminder.send();

        String result = output.toString();
        assertThat(result).contains("Sending email");
        assertThat(result).contains("Reminder");
        assertThat(result).contains("NORMAL");
    }

    @Test
    void reminderNotification_withSmsChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new SmsChannel();
        LocalDateTime time = LocalDateTime.of(2024, 6, 20, 10, 0);
        Notification reminder = new ReminderNotification(
            channel,
            "+9876543210",
            "Doctor appointment",
            time
        );

        reminder.send();

        String result = output.toString();
        assertThat(result).contains("Sending SMS");
        assertThat(result).contains("+9876543210");
        assertThat(result).contains("Reminder");
    }

    @Test
    void reminderNotification_withPushChannel_sendsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new PushChannel();
        LocalDateTime time = LocalDateTime.of(2024, 7, 1, 9, 0);
        Notification reminder = new ReminderNotification(
            channel,
            "device-456",
            "Morning standup",
            time
        );

        reminder.send();

        String result = output.toString();
        assertThat(result).contains("Sending push");
        assertThat(result).contains("device-456");
        assertThat(result).contains("Reminder");
        assertThat(result).contains("NORMAL");
    }

    @Test
    void smsChannel_truncatesLongMessages() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new SmsChannel();
        
        // Create a very long alert message
        String longMessage = "This is a very long alert message that exceeds the SMS character limit. ".repeat(5);
        Notification alert = new AlertNotification(channel, "+1111111111", longMessage);

        alert.send();

        String result = output.toString();
        // Check that message is truncated (SMS limit is 160 chars)
        assertThat(result).contains("...");
    }

    @Test
    void differentChannels_handleSameNotificationType() {
        MessageChannel emailChannel = new EmailChannel();
        MessageChannel smsChannel = new SmsChannel();
        MessageChannel pushChannel = new PushChannel();

        Notification emailAlert = new AlertNotification(emailChannel, "test1@example.com", "Alert 1");
        Notification smsAlert = new AlertNotification(smsChannel, "+1234567890", "Alert 2");
        Notification pushAlert = new AlertNotification(pushChannel, "device-789", "Alert 3");

        ByteArrayOutputStream output = captureSystemOut();
        emailAlert.send();
        smsAlert.send();
        pushAlert.send();

        String result = output.toString();
        assertThat(result).contains("Sending email");
        assertThat(result).contains("Sending SMS");
        assertThat(result).contains("Sending push");
    }

    @Test
    void sameChannel_handlesDifferentNotificationTypes() {
        MessageChannel channel = new EmailChannel();

        Notification alert = new AlertNotification(channel, "user@example.com", "Critical error");
        LocalDateTime time = LocalDateTime.of(2024, 12, 25, 12, 0);
        Notification reminder = new ReminderNotification(channel, "user@example.com", "Holiday event", time);

        ByteArrayOutputStream output = captureSystemOut();
        alert.send();
        reminder.send();

        String result = output.toString();
        assertThat(result).contains("ALERT");
        assertThat(result).contains("Reminder");
        assertThat(result).contains("HIGH");
        assertThat(result).contains("NORMAL");
    }

    @Test
    void reminderNotification_formatsTimeCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        MessageChannel channel = new EmailChannel();
        LocalDateTime time = LocalDateTime.of(2024, 3, 15, 14, 30);
        Notification reminder = new ReminderNotification(channel, "test@example.com", "Meeting", time);

        reminder.send();

        String result = output.toString();
        assertThat(result).containsAnyOf("2024-03-15", "14:30");
    }

    private ByteArrayOutputStream captureSystemOut() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
