package com.patterns.mediator.simulation;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Concrete mediator implementing smart home automation coordination.
 * Manages device registry and executes automation rules.
 */
public class SmartHomeHub implements HomeAutomationMediator {
    
    private final Map<String, SmartDevice> devicesById;
    private final Map<DeviceType, List<SmartDevice>> devicesByType;
    private final Map<String, List<SmartDevice>> devicesByZone;
    private final List<DeviceEvent> eventLog;
    private HomeMode mode;
    
    public SmartHomeHub() {
        this.devicesById = new HashMap<>();
        this.devicesByType = new HashMap<>();
        this.devicesByZone = new HashMap<>();
        this.eventLog = new ArrayList<>();
        this.mode = HomeMode.HOME;
    }
    
    @Override
    public void registerDevice(SmartDevice device) {
        // TODO: Register device in all three maps
        // 1. Add to devicesById map
        // 2. Add to devicesByType map (create list if doesn't exist)
        // 3. Add to devicesByZone map (create list if doesn't exist)
        // 4. Log registration event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void unregisterDevice(SmartDevice device) {
        // TODO: Remove device from all three maps
        // 1. Remove from devicesById
        // 2. Remove from devicesByType list
        // 3. Remove from devicesByZone list
        // 4. Log unregistration event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void notify(SmartDevice sender, String event) {
        // TODO: Route events to appropriate handler methods
        // Log the event first, then handle based on event type
        // Switch on event type and call corresponding handler:
        //   - "MOTION_DETECTED" -> handleMotionDetected(sender)
        //   - "DOOR_UNLOCKED" -> handleDoorUnlocked(sender)
        //   - "DOOR_LOCKED" -> handleDoorLocked(sender)
        //   - "LIGHT_ON", "LIGHT_OFF" -> just log (no cascading action)
        //   - "TEMPERATURE_CHANGED" -> handleTemperatureChanged(sender)
        //   - "CAMERA_ARMED", "CAMERA_DISARMED" -> just log
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void setMode(HomeMode mode) {
        // TODO: Set mode and trigger automation rules
        // 1. Store old mode for comparison
        // 2. Update this.mode to new mode
        // 3. Log mode change event
        // 4. Apply mode-specific automation:
        //    - AWAY: lock all doors, arm all cameras, turn off all lights, set thermostat to eco
        //    - HOME: disarm cameras (stop recording), unlock main door (optional)
        //    - NIGHT: dim lights, arm cameras
        //    - VACATION: same as AWAY but more aggressive energy saving
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public HomeMode getMode() {
        return mode;
    }
    
    @Override
    public List<SmartDevice> getDevicesByType(DeviceType type) {
        // TODO: Return defensive copy of devices list for given type
        // If type not in map, return empty list (not null)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<SmartDevice> getDevicesByZone(String zone) {
        // TODO: Return defensive copy of devices list for given zone
        // If zone not in map, return empty list (not null)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<DeviceEvent> getEventLog() {
        // TODO: Return unmodifiable copy of event log
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Handles motion detection event.
     *
     * @param sensor the motion sensor that detected motion
     */
    private void handleMotionDetected(SmartDevice sensor) {
        // TODO: Implement motion detection automation
        // Get sensor's zone
        // Determine brightness based on mode: AWAY=100, HOME=50, NIGHT=20
        // Turn on all lights in the same zone at appropriate brightness
        // If mode is AWAY, also start recording on all cameras in the zone
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Handles door unlock event.
     *
     * @param door the door lock that was unlocked
     */
    private void handleDoorUnlocked(SmartDevice door) {
        // TODO: Implement door unlock automation
        // If mode was AWAY, switch to HOME mode (returning home)
        // Turn on lights in door's zone at 100% brightness
        // Disarm all cameras (stop recording) if switching from AWAY
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Logs a device event.
     *
     * @param device      the device involved
     * @param eventType   the event type
     * @param description event description
     */
    private void logEvent(SmartDevice device, String eventType, String description) {
        // TODO: Create DeviceEvent and add to eventLog
        // Use LocalDateTime.now() for timestamp
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns device by ID, or null if not found.
     *
     * @param deviceId the device ID
     * @return the device or null
     */
    public SmartDevice getDeviceById(String deviceId) {
        // TODO: Return device from devicesById map
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns count of registered devices.
     *
     * @return device count
     */
    public int getDeviceCount() {
        // TODO: Return size of devicesById map
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
