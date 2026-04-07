package com.patterns.mediator.simulation;

import java.time.LocalDateTime;

/**
 * Record representing a device event in the audit log.
 *
 * @param timestamp   when the event occurred
 * @param deviceId    unique device identifier
 * @param deviceName  human-readable device name
 * @param eventType   type of event (e.g., "MOTION_DETECTED", "LIGHT_ON")
 * @param description detailed event description
 */
public record DeviceEvent(
    LocalDateTime timestamp,
    String deviceId,
    String deviceName,
    String eventType,
    String description
) {
    
    @Override
    public String toString() {
        return String.format("[%s] %s (%s): %s - %s",
            timestamp, deviceName, deviceId, eventType, description);
    }
}
