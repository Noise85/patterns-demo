package com.patterns.facade.isolation;

/**
 * Lights subsystem component.
 * Manages lighting levels.
 */
public class Lights {
    
    private boolean isOn = false;
    private int brightness = 100; // 0-100 percentage
    
    /**
     * Turns lights on (full brightness).
     */
    public void on() {
        // TODO: Implement turn on logic
        // - Set isOn to true
        // - Set brightness to 100
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Turns lights off.
     */
    public void off() {
        // TODO: Implement turn off logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Dims lights to specified percentage.
     *
     * @param percentage brightness level (0-100)
     * @throws IllegalArgumentException if percentage out of range
     */
    public void dim(int percentage) {
        // TODO: Implement dimming logic
        // - Validate percentage is 0-100
        // - Set brightness
        // - Set isOn based on percentage (on if > 0)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public int getBrightness() {
        return brightness;
    }
}
