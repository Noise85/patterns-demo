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
        if(!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }
    
    @Override
    public void removeObserver(WeatherObserver observer) {
        this.observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        this.observers.forEach(o -> o.update(temperature, humidity, pressure));
    }
    
    /**
     * Updates weather measurements and notifies observers.
     *
     * @param temperature temperature in Celsius
     * @param humidity    humidity percentage
     * @param pressure    atmospheric pressure in hPa
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.notifyObservers();
    }
    
    /**
     * Returns the number of registered observers.
     *
     * @return observer count
     */
    public int getObserverCount() {
        return this.observers.size();
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
