package com.patterns.factorymethod.simulation;

import com.patterns.factorymethod.simulation.model.DeliveryResult;
import com.patterns.factorymethod.simulation.model.NotificationMessage;
import com.patterns.factorymethod.simulation.notification.EmailNotification;
import com.patterns.factorymethod.simulation.notification.SmsNotification;
import com.patterns.factorymethod.simulation.service.EmailNotificationService;
import com.patterns.factorymethod.simulation.service.SmsNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Multi-Channel Notification System
 */
@DisplayName("Exercise 2: Factory Method - Notification System")
class SimulationExerciseTest {
    
    // ==================== Notification Validation Tests ====================
    
    @Test
    @DisplayName("EmailNotification should validate correct email format")
    void testEmailValidationSuccess() {
        NotificationMessage msg = new NotificationMessage("user@example.com", "Subject", "Body", "NORMAL");
        EmailNotification notification = new EmailNotification(msg);
        
        assertThat(notification.validate()).isTrue();
    }
    
    @Test
    @DisplayName("EmailNotification should reject invalid email format")
    void testEmailValidationFailure() {
        NotificationMessage msg1 = new NotificationMessage("invalid-email", "Subject", "Body", "NORMAL");
        EmailNotification notification1 = new EmailNotification(msg1);
        assertThat(notification1.validate()).isFalse();
        
        NotificationMessage msg2 = new NotificationMessage("user@nodomain", "Subject", "Body", "NORMAL");
        EmailNotification notification2 = new EmailNotification(msg2);
        assertThat(notification2.validate()).isFalse();
    }
    
    @Test
    @DisplayName("SmsNotification should validate correct phone format")
    void testSmsValidationSuccess() {
        NotificationMessage msg = new NotificationMessage("+1234567890123", "Subject", "Body", "NORMAL");
        SmsNotification notification = new SmsNotification(msg);
        
        assertThat(notification.validate()).isTrue();
    }
    
    @Test
    @DisplayName("SmsNotification should reject invalid phone format")
    void testSmsValidationFailure() {
        NotificationMessage msg1 = new NotificationMessage("1234567890", "Subject", "Body", "NORMAL");
        SmsNotification notification1 = new SmsNotification(msg1);
        assertThat(notification1.validate()).isFalse();
        
        NotificationMessage msg2 = new NotificationMessage("+123", "Subject", "Body", "NORMAL");
        SmsNotification notification2 = new SmsNotification(msg2);
        assertThat(notification2.validate()).isFalse();
    }
    
    // ==================== Notification Send Tests ====================
    
    @Test
    @DisplayName("EmailNotification should send successfully with valid data")
    void testEmailSendSuccess() {
        NotificationMessage msg = new NotificationMessage("user@example.com", "Test", "Body", "NORMAL");
        EmailNotification notification = new EmailNotification(msg);
        
        DeliveryResult result = notification.send();
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getDeliveryId()).isNotNull();
        assertThat(result.getChannel()).isEqualTo("EMAIL");
    }
    
    @Test
    @DisplayName("SmsNotification should send successfully with valid data")
    void testSmsSendSuccess() {
        NotificationMessage msg = new NotificationMessage("+1234567890123", "Test", "Body", "NORMAL");
        SmsNotification notification = new SmsNotification(msg);
        
        DeliveryResult result = notification.send();
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getDeliveryId()).isNotNull();
        assertThat(result.getChannel()).isEqualTo("SMS");
    }
    
    // ==================== Service Tests ====================
    
    @Test
    @DisplayName("EmailNotificationService should send valid email")
    void testEmailServiceSuccess() {
        NotificationService service = new EmailNotificationService();
        NotificationMessage msg = new NotificationMessage("user@example.com", "Test", "Body", "NORMAL");
        
        DeliveryResult result = service.sendNotification(msg);
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getChannel()).isEqualTo("EMAIL");
    }
    
    @Test
    @DisplayName("EmailNotificationService should reject invalid email")
    void testEmailServiceValidationFailure() {
        NotificationService service = new EmailNotificationService();
        NotificationMessage msg = new NotificationMessage("invalid-email", "Test", "Body", "NORMAL");
        
        DeliveryResult result = service.sendNotification(msg);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isNotNull();
    }
    
    @Test
    @DisplayName("SmsNotificationService should send valid SMS")
    void testSmsServiceSuccess() {
        NotificationService service = new SmsNotificationService();
        NotificationMessage msg = new NotificationMessage("+1234567890123", "Test", "Body", "NORMAL");
        
        DeliveryResult result = service.sendNotification(msg);
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getChannel()).isEqualTo("SMS");
    }
    
    @Test
    @DisplayName("SmsNotificationService should reject invalid phone number")
    void testSmsServiceValidationFailure() {
        NotificationService service = new SmsNotificationService();
        NotificationMessage msg = new NotificationMessage("123", "Test", "Body", "NORMAL");
        
        DeliveryResult result = service.sendNotification(msg);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isNotNull();
    }
    
    // ==================== Integration Tests ====================
    
    @Test
    @DisplayName("Different services should use different channels")
    void testDifferentServicesDifferentChannels() {
        NotificationMessage emailMsg = new NotificationMessage("user@example.com", "Test", "Body", "NORMAL");
        NotificationMessage smsMsg = new NotificationMessage("+1234567890123", "Test", "Body", "NORMAL");
        
        NotificationService emailService = new EmailNotificationService();
        NotificationService smsService = new SmsNotificationService();
        
        DeliveryResult emailResult = emailService.sendNotification(emailMsg);
        DeliveryResult smsResult = smsService.sendNotification(smsMsg);
        
        assertThat(emailResult.getChannel()).isEqualTo("EMAIL");
        assertThat(smsResult.getChannel()).isEqualTo("SMS");
    }
}
