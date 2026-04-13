package com.patterns.observer.isolation;

/**
 * Display that shows current temperature and humidity conditions.
 */
public class CurrentConditionsDisplay implements WeatherObserver {
    
    private float temperature;
    private float humidity;
    private final WeatherSubject weatherStation;
    
    /**
     * Creates a new current conditions display and registers with the weather station.
     *
     * @param weatherStation the weather station to observe
     */
    public CurrentConditionsDisplay(WeatherSubject weatherStation) {
        this.weatherStation = weatherStation;
        this.weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    /**
     * Displays current weather conditions.
     *
     * @return formatted current conditions string
     */
    public String display() {
        return String.format("Current conditions: %.2f°C and %.2f humidity", this.temperature, this.humidity);
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
}
