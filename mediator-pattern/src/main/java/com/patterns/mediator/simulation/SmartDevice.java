package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Abstract base class for all smart devices.
 * Provides common functionality and enforces mediator-based communication.
 */
public abstract class SmartDevice {
    
    protected final String id;
    protected final String name;
    protected final DeviceType type;
    protected final String zone;
    protected final HomeAutomationMediator hub;
    protected boolean isOnline;
    
    /**
     * Creates a new smart device.
     *
     * @param id   unique device identifier
     * @param name human-readable device name
     * @param type device type
     * @param zone physical zone/room (e.g., "Living Room")
     * @param hub  the home automation hub (mediator)
     */
    protected SmartDevice(String id, String name, DeviceType type, String zone, HomeAutomationMediator hub) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.zone = zone;
        this.hub = hub;
        this.isOnline = true;
    }
    
    /**
     * Notifies the hub about an event.
     *
     * @param event the event type
     */
    protected void notifyHub(String event) {
        if (hub != null && isOnline) {
            hub.notify(this, event);
        }
    }
    
    /**
     * Returns the current state of the device as a map.
     *
     * @return state map with key-value pairs
     */
    public abstract Map<String, Object> getState();
    
    /**
     * Returns a human-readable description of the device.
     *
     * @return device description
     */
    public abstract String getDescription();
    
    // Getters
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public DeviceType getType() {
        return type;
    }
    
    public String getZone() {
        return zone;
    }
    
    public boolean isOnline() {
        return isOnline;
    }
    
    public void setOnline(boolean online) {
        this.isOnline = online;
    }
}
