package com.patterns.observer.isolation;

/**
 * Display that tracks and shows temperature statistics (min, max, average).
 */
public class StatisticsDisplay implements WeatherObserver {
    
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    private final WeatherSubject weatherStation;
    
    /**
     * Creates a new statistics display and registers with the weather station.
     *
     * @param weatherStation the weather station to observe
     */
    public StatisticsDisplay(WeatherSubject weatherStation) {
        this.weatherStation = weatherStation;
        // TODO: Register this observer with the weather station
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        // TODO: Update statistics
        // 1. Add temperature to tempSum
        // 2. Increment numReadings
        // 3. Update maxTemp if temperature > current max
        // 4. Update minTemp if temperature < current min
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Displays temperature statistics.
     *
     * @return formatted statistics string
     */
    public String display() {
        // TODO: Calculate average and return formatted string
        // Format: "Avg/Max/Min temperature: [avg]/[max]/[min]"
        // Use String.format with %.1f for one decimal place
        // Calculate average as tempSum / numReadings
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public float getMaxTemp() {
        return maxTemp;
    }
    
    public float getMinTemp() {
        return minTemp;
    }
    
    public float getAvgTemp() {
        // TODO: Return average temperature (tempSum / numReadings)
        // Handle case where numReadings is 0
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public int getNumReadings() {
        return numReadings;
    }
}
