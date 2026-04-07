package com.patterns.mediator.simulation;

import java.util.Map;

/**
 * Thermostat device for temperature control.
 */
public class Thermostat extends SmartDevice {
    
    private int targetTemperature;  // Celsius
    private int currentTemperature;
    private boolean isHeating;
    private boolean isCooling;
    
    public Thermostat(String id, String name, String zone, HomeAutomationMediator hub, int currentTemp) {
        super(id, name, DeviceType.THERMOSTAT, zone, hub);
        this.currentTemperature = currentTemp;
        this.targetTemperature = 20;  // Default comfortable temperature
        this.isHeating = false;
        this.isCooling = false;
    }
    
    /**
     * Sets target temperature.
     *
     * @param temperature target temperature in Celsius
     */
    public void setTargetTemperature(int temperature) {
        // TODO:
        // 1. Set targetTemperature
        // 2. Update heating/cooling state based on current vs target
        // 3. Notify hub with "TEMPERATURE_TARGET_CHANGED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Updates current temperature reading.
     *
     * @param temperature current temperature in Celsius
     */
    public void updateCurrentTemperature(int temperature) {
        // TODO:
        // 1. Set currentTemperature
        // 2. Update heating/cooling state
        // 3. Notify hub with "TEMPERATURE_CHANGED" event
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets to energy-saving mode (lower target temperature).
     */
    public void setEcoMode() {
        // TODO: Set target temperature to 18°C (energy saving)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Sets to comfort mode (normal target temperature).
     */
    public void setComfortMode() {
        // TODO: Set target temperature to 21°C (comfortable)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public int getTargetTemperature() {
        return targetTemperature;
    }
    
    public int getCurrentTemperature() {
        return currentTemperature;
    }
    
    public boolean isHeating() {
        return isHeating;
    }
    
    public boolean isCooling() {
        return isCooling;
    }
    
    @Override
    public Map<String, Object> getState() {
        // TODO: Return map with device state
        // Include: id, name, type, zone, online, currentTemperature, targetTemperature, isHeating, isCooling
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        String mode = isHeating ? "HEATING" : (isCooling ? "COOLING" : "IDLE");
        return String.format("Thermostat '%s' in %s (Current: %d°C, Target: %d°C, %s)",
            name, zone, currentTemperature, targetTemperature, mode);
    }
}
