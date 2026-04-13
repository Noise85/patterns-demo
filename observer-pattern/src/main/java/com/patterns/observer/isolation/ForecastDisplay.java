package com.patterns.observer.isolation;

/**
 * Display that predicts weather based on pressure trends.
 */
public class ForecastDisplay implements WeatherObserver {
    
    private float currentPressure = 0;
    private float lastPressure = 0;
    private final WeatherSubject weatherStation;
    private float nbReadings = 0;
    
    /**
     * Creates a new forecast display and registers with the weather station.
     *
     * @param weatherStation the weather station to observe
     */
    public ForecastDisplay(WeatherSubject weatherStation) {
        this.weatherStation = weatherStation;
        this.weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.lastPressure = this.currentPressure;
        this.currentPressure = pressure;
        nbReadings++;
    }
    
    /**
     * Displays weather forecast based on pressure trends.
     *
     * @return forecast string
     */
    public String display() {
        if(nbReadings==1 || currentPressure==lastPressure) {
            return "More of the same";
        } else {
            if(currentPressure>lastPressure) {
                return "Improving weather!";
            } else {
                return "Cooler, rainy weather";
            }
        }
    }
    
    public float getCurrentPressure() {
        return currentPressure;
    }
    
    public float getLastPressure() {
        return lastPressure;
    }
}
