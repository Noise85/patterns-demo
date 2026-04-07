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
        // TODO: Register this observer with the weather station
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        // TODO: Store temperature and humidity values
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Displays current weather conditions.
     *
     * @return formatted current conditions string
     */
    public String display() {
        // TODO: Return formatted string: "Current conditions: [temp]°C and [humidity]% humidity"
        // Use String.format with %.1f for one decimal place
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
}
