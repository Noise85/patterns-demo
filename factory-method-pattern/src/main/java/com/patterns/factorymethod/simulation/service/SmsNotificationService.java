package com.patterns.factorymethod.simulation.service;

import com.patterns.factorymethod.simulation.Notification;
import com.patterns.factorymethod.simulation.NotificationService;
import com.patterns.factorymethod.simulation.model.NotificationMessage;
import com.patterns.factorymethod.simulation.notification.SmsNotification;

/**
 * Concrete creator for SMS notifications.
 * 
 * TODO: Extend NotificationService and override factory method.
 */
public class SmsNotificationService extends NotificationService {
    
    // TODO: Override createNotification() to return new SmsNotification
    @Override
    protected Notification createNotification(NotificationMessage message) {
        throw new UnsupportedOperationException("TODO: Implement createNotification()");
    }
}
