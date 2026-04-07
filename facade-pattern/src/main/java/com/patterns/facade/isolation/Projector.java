package com.patterns.facade.isolation;

/**
 * Projector subsystem component.
 * Manages projector power, input selection, and display modes.
 */
public class Projector {
    
    private boolean isPoweredOn = false;
    private String currentInput;
    private String displayMode;
    
    /**
     * Powers on the projector.
     */
    public void powerOn() {
        // TODO: Implement power on logic
        // - Set isPoweredOn to true
        // - Initialize with default values if needed
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Powers off the projector.
     */
    public void powerOff() {
        // TODO: Implement power off logic
        // - Set isPoweredOn to false
        // - Clear current settings
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets the input source.
     *
     * @param input the input source ("DVD", "Streaming", "Cable")
     * @throws IllegalStateException if projector is not powered on
     */
    public void setInput(String input) {
        // TODO: Implement input selection
        // - Check if powered on (throw IllegalStateException if not)
        // - Set currentInput
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets the display mode.
     *
     * @param mode the display mode ("Widescreen", "Standard")
     * @throws IllegalStateException if projector is not powered on
     */
    public void setDisplayMode(String mode) {
        // TODO: Implement display mode setting
        // - Check if powered on
        // - Set displayMode
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isPoweredOn() {
        return isPoweredOn;
    }
    
    public String getCurrentInput() {
        return currentInput;
    }
    
    public String getDisplayMode() {
        return displayMode;
    }
}
