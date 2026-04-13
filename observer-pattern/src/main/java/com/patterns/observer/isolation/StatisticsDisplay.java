package com.patterns.observer.isolation;

/**
 * Display that tracks and shows temperature statistics (min, max, average).
 */
public class StatisticsDisplay implements WeatherObserver {
    
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private int numReadings = 0;
    private final WeatherSubject weatherStation;
    
    /**
     * Creates a new statistics display and registers with the weather station.
     *
     * @param weatherStation the weather station to observe
     */
    public StatisticsDisplay(WeatherSubject weatherStation) {
        this.weatherStation = weatherStation;
        this.weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.maxTemp=Math.max(temperature, this.maxTemp);
        this.minTemp=Math.min(temperature, this.minTemp);
        numReadings++;
    }
    
    /**
     * Displays temperature statistics.
     *
     * @return formatted statistics string
     */
    public String display() {
        return String.format("Avg/Max/Min temperature = %.1f/%.1f/%.1f", getAvgTemp(), maxTemp, minTemp);
    }
    
    public float getMaxTemp() {
        return maxTemp;
    }
    
    public float getMinTemp() {
        return minTemp;
    }
    
    public float getAvgTemp() {
        return (maxTemp+minTemp)/2;
    }
    
    public int getNumReadings() {
        return numReadings;
    }
}
