package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Security camera device with recording capabilities.
 */
public class SecurityCamera extends SmartDevice {
    
    private boolean isRecording;
    private boolean isArmed;
    
    public SecurityCamera(String id, String name, String zone, HomeAutomationMediator hub) {
        super(id, name, DeviceType.SECURITY_CAMERA, zone, hub);
        this.isRecording = false;
        this.isArmed = false;
    }
    
    /**
     * Arms the camera (ready to record on events).
     */
    public void arm() {
        // TODO:
        // 1. Set isArmed = true
        // 2. Notify hub with "CAMERA_ARMED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Disarms the camera (standby mode).
     */
    public void disarm() {
        // TODO:
        // 1. Set isArmed = false
        // 2. If recording, stop recording first
        // 3. Notify hub with "CAMERA_DISARMED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Starts recording.
     */
    public void startRecording() {
        // TODO:
        // 1. Set isRecording = true
        // 2. Notify hub with "RECORDING_STARTED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Stops recording.
     */
    public void stopRecording() {
        // TODO:
        // 1. Set isRecording = false
        // 2. Notify hub with "RECORDING_STOPPED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isRecording() {
        return isRecording;
    }
    
    public boolean isArmed() {
        return isArmed;
    }
    
    @Override
    public Map<String, Object> getState() {
        // TODO: Return map with device state
        // Include: id, name, type, zone, online, isArmed, isRecording
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        String status = isRecording ? "RECORDING" : (isArmed ? "ARMED" : "STANDBY");
        return String.format("Security Camera '%s' in %s (%s)",
            name, zone, status);
    }
}
