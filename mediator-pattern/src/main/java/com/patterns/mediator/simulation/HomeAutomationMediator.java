package com.patterns.mediator.simulation;

import java.util.List;

/**
 * Mediator interface for smart home automation.
 * Coordinates communication and behavior between smart devices.
 */
public interface HomeAutomationMediator {
    
    /**
     * Registers a device with the smart home hub.
     *
     * @param device the device to register
     */
    void registerDevice(SmartDevice device);
    
    /**
     * Unregisters a device from the smart home hub.
     *
     * @param device the device to unregister
     */
    void unregisterDevice(SmartDevice device);
    
    /**
     * Receives notification from a device about an event.
     *
     * @param sender the device sending the notification
     * @param event  the event type (e.g., "MOTION_DETECTED", "DOOR_UNLOCKED")
     */
    void notify(SmartDevice sender, String event);
    
    /**
     * Sets the home mode (HOME, AWAY, NIGHT, VACATION).
     *
     * @param mode the mode to activate
     */
    void setMode(HomeMode mode);
    
    /**
     * Returns the current home mode.
     *
     * @return the current mode
     */
    HomeMode getMode();
    
    /**
     * Returns all devices of a specific type.
     *
     * @param type the device type
     * @return list of devices of that type
     */
    List<SmartDevice> getDevicesByType(DeviceType type);
    
    /**
     * Returns all devices in a specific zone.
     *
     * @param zone the zone name (e.g., "Living Room", "Bedroom")
     * @return list of devices in that zone
     */
    List<SmartDevice> getDevicesByZone(String zone);
    
    /**
     * Returns the event log showing all significant actions.
     *
     * @return chronological list of device events
     */
    List<DeviceEvent> getEventLog();
}
