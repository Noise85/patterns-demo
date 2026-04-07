package com.patterns.observer.isolation;

/**
 * Display that predicts weather based on pressure trends.
 */
public class ForecastDisplay implements WeatherObserver {
    
    private float currentPressure = 0;
    private float lastPressure = 0;
    private final WeatherSubject weatherStation;
    
    /**
     * Creates a new forecast display and registers with the weather station.
     *
     * @param weatherStation the weather station to observe
     */
    public ForecastDisplay(WeatherSubject weatherStation) {
        this.weatherStation = weatherStation;
        // TODO: Register this observer with the weather station
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        // TODO: Update pressure tracking
        // 1. Set lastPressure = currentPressure
        // 2. Set currentPressure = pressure
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Displays weather forecast based on pressure trends.
     *
     * @return forecast string
     */
    public String display() {
        // TODO: Return forecast based on pressure comparison
        // If lastPressure is 0 (no previous reading): "More of the same"
        // If currentPressure > lastPressure: "Improving weather!"
        // If currentPressure < lastPressure: "Cooler, rainy weather"
        // If currentPressure == lastPressure: "More of the same"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public float getCurrentPressure() {
        return currentPressure;
    }
    
    public float getLastPressure() {
        return lastPressure;
    }
}
