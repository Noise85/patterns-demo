package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Smart light device with adjustable brightness.
 */
public class SmartLight extends SmartDevice {
    
    private boolean isOn;
    private int brightness;  // 0-100
    
    public SmartLight(String id, String name, String zone, HomeAutomationMediator hub) {
        super(id, name, DeviceType.LIGHT, zone, hub);
        this.isOn = false;
        this.brightness = 0;
    }
    
    /**
     * Turns on the light at specified brightness.
     *
     * @param brightness brightness level (0-100)
     */
    public void turnOn(int brightness) {
        // TODO: 
        // 1. Set isOn = true
        // 2. Set this.brightness (clamp to 0-100 if needed)
        // 3. Notify hub with "LIGHT_ON" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Turns off the light.
     */
    public void turnOff() {
        // TODO:
        // 1. Set isOn = false
        // 2. Set brightness = 0
        // 3. Notify hub with "LIGHT_OFF" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Adjusts brightness without turning off.
     *
     * @param brightness new brightness level (0-100)
     */
    public void setBrightness(int brightness) {
        // TODO:
        // 1. If brightness > 0, set isOn = true
        // 2. Set this.brightness (clamp to 0-100)
        // 3. Notify hub with "BRIGHTNESS_CHANGED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public int getBrightness() {
        return brightness;
    }
    
    @Override
    public Map<String, Object> getState() {
        // TODO: Return map with device state
        // Include: id, name, type, zone, online, isOn, brightness
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Smart Light '%s' in %s (%s, %d%%)",
            name, zone, isOn ? "ON" : "OFF", brightness);
    }
}
