package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Door lock device for security.
 */
public class DoorLock extends SmartDevice {
    
    private boolean isLocked;
    
    public DoorLock(String id, String name, String zone, HomeAutomationMediator hub) {
        super(id, name, DeviceType.DOOR_LOCK, zone, hub);
        this.isLocked = true;  // Default to locked for security
    }
    
    /**
     * Locks the door.
     */
    public void lock() {
        // TODO:
        // 1. Set isLocked = true
        // 2. Notify hub with "DOOR_LOCKED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Unlocks the door.
     */
    public void unlock() {
        // TODO:
        // 1. Set isLocked = false
        // 2. Notify hub with "DOOR_UNLOCKED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isLocked() {
        return isLocked;
    }
    
    @Override
    public Map<String, Object> getState() {
        // TODO: Return map with device state
        // Include: id, name, type, zone, online, isLocked
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Door Lock '%s' in %s (%s)",
            name, zone, isLocked ? "LOCKED" : "UNLOCKED");
    }
}
