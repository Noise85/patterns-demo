package com.patterns.observer.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete subject that maintains weather measurements and notifies observers of changes.
 */
public class WeatherStation implements WeatherSubject {
    
    private final List<WeatherObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherStation() {
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(WeatherObserver observer) {
        // TODO: Add observer to list if not already registered
        // Check if observer is not null and not already in list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void removeObserver(WeatherObserver observer) {
        // TODO: Remove observer from list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void notifyObservers() {
        // TODO: Notify all registered observers
        // Create defensive copy of observers list to avoid ConcurrentModificationException
        // Iterate through copy and call update() on each observer
        // Pass current temperature, humidity, and pressure values
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Updates weather measurements and notifies observers.
     *
     * @param temperature temperature in Celsius
     * @param humidity    humidity percentage
     * @param pressure    atmospheric pressure in hPa
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        // TODO: Update internal state and notify observers
        // 1. Set this.temperature, this.humidity, this.pressure
        // 2. Call notifyObservers()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of registered observers.
     *
     * @return observer count
     */
    public int getObserverCount() {
        // TODO: Return size of observers list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Getters for current measurements (useful for pull model if needed)
    
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    public float getPressure() {
        return pressure;
    }
}
