package com.patterns.adapter.isolation;

/**
 * Target interface for modern temperature sensors.
 * All sensors should return temperature in Celsius.
 */
public interface TemperatureSensor {
    /**
     * Gets the current temperature in Celsius.
     *
     * @return Temperature in Celsius
     */
    double getTemperature();

    /**
     * Gets the sensor identifier.
     *
     * @return Sensor ID
     */
    String getSensorId();
}
