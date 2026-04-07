package com.patterns.adapter.isolation;

/**
 * Legacy temperature sensor that reports in Fahrenheit.
 * This class cannot be modified (simulates third-party library).
 */
public class LegacyFahrenheitSensor {
    private final String deviceId;
    private double currentTemperatureFahrenheit;

    public LegacyFahrenheitSensor(String deviceId, double temperatureFahrenheit) {
        this.deviceId = deviceId;
        this.currentTemperatureFahrenheit = temperatureFahrenheit;
    }

    /**
     * Reads temperature in Fahrenheit.
     *
     * @return Temperature in Fahrenheit
     */
    public double readFahrenheit() {
        return currentTemperatureFahrenheit;
    }

    /**
     * Gets the device identifier.
     *
     * @return Device ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Simulates temperature change.
     *
     * @param newTemperatureFahrenheit New temperature in Fahrenheit
     */
    public void setTemperature(double newTemperatureFahrenheit) {
        this.currentTemperatureFahrenheit = newTemperatureFahrenheit;
    }
}
