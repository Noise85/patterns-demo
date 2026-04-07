package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Motion sensor device that detects movement.
 */
public class MotionSensor extends SmartDevice {
    
    private boolean motionDetected;
    
    public MotionSensor(String id, String name, String zone, HomeAutomationMediator hub) {
        super(id, name, DeviceType.MOTION_SENSOR, zone, hub);
        this.motionDetected = false;
    }
    
    /**
     * Triggers motion detection event.
     */
    public void detectMotion() {
        // TODO: Set motionDetected to true and notify hub with "MOTION_DETECTED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Clears motion detection state.
     */
    public void clearMotion() {
        // TODO: Set motionDetected to false and notify hub with "MOTION_CLEARED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isMotionDetected() {
        return motionDetected;
    }
    
    @Override
    public Map<String, Object> getState() {
        // TODO: Return map with device state
        // Include: id, name, type, zone, online, motionDetected
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Motion Sensor '%s' in %s", name, zone);
    }
}
