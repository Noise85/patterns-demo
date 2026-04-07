package com.patterns.observer.isolation;

/**
 * Observer interface for weather data updates.
 * Observers implement this interface to receive notifications when weather conditions change.
 */
public interface WeatherObserver {
    
    /**
     * Called when weather measurements are updated.
     *
     * @param temperature temperature in Celsius
     * @param humidity    humidity percentage (0-100)
     * @param pressure    atmospheric pressure in hPa
     */
    void update(float temperature, float humidity, float pressure);
}
