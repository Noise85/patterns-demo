package com.patterns.facade.isolation;

/**
 * Sound system subsystem component.
 * Manages audio power, volume, and input modes.
 */
public class SoundSystem {
    
    private boolean isPoweredOn = false;
    private int volume = 0;
    private String inputMode;
    
    /**
     * Powers on the sound system.
     */
    public void powerOn() {
        // TODO: Implement power on logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Powers off the sound system.
     */
    public void powerOff() {
        // TODO: Implement power off logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets the volume level.
     *
     * @param level volume level (0-10)
     * @throws IllegalStateException if not powered on
     * @throws IllegalArgumentException if level out of range
     */
    public void setVolume(int level) {
        // TODO: Implement volume setting
        // - Check if powered on
        // - Validate level is between 0 and 10
        // - Set volume
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets the input mode.
     *
     * @param mode the input mode ("DVD", "Streaming", "Cable")
     * @throws IllegalStateException if not powered on
     */
    public void setInputMode(String mode) {
        // TODO: Implement input mode setting
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isPoweredOn() {
        return isPoweredOn;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public String getInputMode() {
        return inputMode;
    }
}
