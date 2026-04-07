package com.patterns.factorymethod.simulation.service;

import com.patterns.factorymethod.simulation.Notification;
import com.patterns.factorymethod.simulation.NotificationService;
import com.patterns.factorymethod.simulation.model.NotificationMessage;
import com.patterns.factorymethod.simulation.notification.EmailNotification;

/**
 * Concrete creator for email notifications.
 * 
 * TODO: Extend NotificationService and override factory method.
 */
public class EmailNotificationService extends NotificationService {
    
    // TODO: Override createNotification() to return new EmailNotification
    @Override
    protected Notification createNotification(NotificationMessage message) {
        throw new UnsupportedOperationException("TODO: Implement createNotification()");
    }
}
