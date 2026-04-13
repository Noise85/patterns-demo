package com.patterns.observer.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for Observer Pattern - Isolation Exercise (Weather Monitoring).
 */
@DisplayName("Observer Pattern - Weather Monitoring System")
class IsolationExerciseTest {
    
    private WeatherStation station;
    private CurrentConditionsDisplay currentDisplay;
    private StatisticsDisplay statsDisplay;
    private ForecastDisplay forecastDisplay;
    
    @BeforeEach
    void setUp() {
        station = new WeatherStation();
        currentDisplay = new CurrentConditionsDisplay(station);
        statsDisplay = new StatisticsDisplay(station);
        forecastDisplay = new ForecastDisplay(station);
    }
    
    @Test
    @DisplayName("Should register observer with station")
    void testRegisterObserver() {
        WeatherStation newStation = new WeatherStation();
        newStation.registerObserver(new CurrentConditionsDisplay(newStation));
        
        assertThat(newStation.getObserverCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should remove observer from station")
    void testRemoveObserver() {
        station.removeObserver(forecastDisplay);
        
        assertThat(station.getObserverCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should notify all observers when measurements change")
    void testNotifyAllObservers() {
        station.setMeasurements(25.0f, 60.0f, 1013.0f);
        
        assertThat(currentDisplay.getTemperature()).isEqualTo(25.0f);
        assertThat(statsDisplay.getNumReadings()).isEqualTo(1);
        assertThat(forecastDisplay.getCurrentPressure()).isEqualTo(1013.0f);
    }
    
    @Test
    @DisplayName("Should not notify removed observers")
    void testRemovedObserverNotNotified() {
        station.removeObserver(statsDisplay);
        
        station.setMeasurements(28.0f, 70.0f, 1015.0f);
        
        assertThat(statsDisplay.getNumReadings()).isZero();
        assertThat(currentDisplay.getTemperature()).isEqualTo(28.0f);
    }
    
    @Test
    @DisplayName("Should update current conditions display")
    void testCurrentConditionsDisplay() {
        station.setMeasurements(26.5f, 65.0f, 1012.0f);
        
        assertThat(currentDisplay.getTemperature()).isEqualTo(26.5f);
        assertThat(currentDisplay.getHumidity()).isEqualTo(65.0f);
        assertThat(currentDisplay.display()).contains("26.5").contains("65");
    }
    
    @Test
    @DisplayName("Should track maximum temperature")
    void testMaxTemperature() {
        station.setMeasurements(20.0f, 60.0f, 1013.0f);
        station.setMeasurements(25.0f, 65.0f, 1014.0f);
        station.setMeasurements(22.0f, 62.0f, 1013.5f);
        
        assertThat(statsDisplay.getMaxTemp()).isEqualTo(25.0f);
    }
    
    @Test
    @DisplayName("Should track minimum temperature")
    void testMinTemperature() {
        station.setMeasurements(25.0f, 60.0f, 1013.0f);
        station.setMeasurements(18.0f, 65.0f, 1014.0f);
        station.setMeasurements(22.0f, 62.0f, 1013.5f);
        
        assertThat(statsDisplay.getMinTemp()).isEqualTo(18.0f);
    }
    
    @Test
    @DisplayName("Should calculate average temperature")
    void testAverageTemperature() {
        station.setMeasurements(20.0f, 60.0f, 1013.0f);
        station.setMeasurements(24.0f, 65.0f, 1014.0f);
        station.setMeasurements(22.0f, 62.0f, 1013.5f);
        
        assertThat(statsDisplay.getAvgTemp()).isEqualTo(22.0f);
    }
    
    @Test
    @DisplayName("Should display statistics correctly")
    void testStatisticsDisplay() {
        station.setMeasurements(20.0f, 60.0f, 1013.0f);
        station.setMeasurements(24.0f, 65.0f, 1014.0f);
        
        String display = statsDisplay.display();
        
        assertThat(display).contains("22.0");  // average
        assertThat(display).contains("24.0");  // max
        assertThat(display).contains("20.0");  // min
    }
    
    @Test
    @DisplayName("Should predict improving weather when pressure rises")
    void testPressureRising() {
        station.setMeasurements(25.0f, 60.0f, 1010.0f);
        station.setMeasurements(25.0f, 60.0f, 1015.0f);
        
        assertThat(forecastDisplay.display()).isEqualTo("Improving weather!");
    }
    
    @Test
    @DisplayName("Should predict rainy weather when pressure falls")
    void testPressureFalling() {
        station.setMeasurements(25.0f, 60.0f, 1015.0f);
        station.setMeasurements(25.0f, 60.0f, 1010.0f);
        
        assertThat(forecastDisplay.display()).isEqualTo("Cooler, rainy weather");
    }
    
    @Test
    @DisplayName("Should predict same weather when pressure unchanged")
    void testPressureUnchanged() {
        station.setMeasurements(25.0f, 60.0f, 1013.0f);
        station.setMeasurements(26.0f, 65.0f, 1013.0f);
        
        assertThat(forecastDisplay.display()).isEqualTo("More of the same");
    }
    
    @Test
    @DisplayName("Should predict same weather on first reading")
    void testFirstReading() {
        WeatherStation newStation = new WeatherStation();
        ForecastDisplay newForecast = new ForecastDisplay(newStation);
        
        newStation.setMeasurements(25.0f, 60.0f, 1013.0f);
        
        assertThat(newForecast.display()).isEqualTo("More of the same");
    }
    
    @Test
    @DisplayName("Should handle multiple updates")
    void testMultipleUpdates() {
        station.setMeasurements(20.0f, 60.0f, 1010.0f);
        station.setMeasurements(22.0f, 62.0f, 1012.0f);
        station.setMeasurements(24.0f, 64.0f, 1014.0f);
        station.setMeasurements(26.0f, 66.0f, 1016.0f);
        
        assertThat(statsDisplay.getNumReadings()).isEqualTo(4);
        assertThat(statsDisplay.getAvgTemp()).isEqualTo(23.0f);
    }
    
    @Test
    @DisplayName("Should track pressure correctly")
    void testPressureTracking() {
        station.setMeasurements(25.0f, 60.0f, 1010.0f);
        assertThat(forecastDisplay.getCurrentPressure()).isEqualTo(1010.0f);
        
        station.setMeasurements(25.0f, 60.0f, 1015.0f);
        assertThat(forecastDisplay.getLastPressure()).isEqualTo(1010.0f);
        assertThat(forecastDisplay.getCurrentPressure()).isEqualTo(1015.0f);
    }
    
    @Test
    @DisplayName("Should handle observers registering after measurements")
    void testLateRegistration() {
        station.setMeasurements(20.0f, 60.0f, 1013.0f);
        
        WeatherStation newStation = new WeatherStation();
        CurrentConditionsDisplay lateDisplay = new CurrentConditionsDisplay(newStation);
        
        newStation.setMeasurements(22.0f, 65.0f, 1014.0f);
        
        assertThat(lateDisplay.getTemperature()).isEqualTo(22.0f);
    }
    
    @Test
    @DisplayName("Should handle no observers gracefully")
    void testNoObservers() {
        WeatherStation emptyStation = new WeatherStation();
        
        assertThatCode(() -> emptyStation.setMeasurements(25.0f, 60.0f, 1013.0f))
            .doesNotThrowAnyException();
    }
    
    @Test
    @DisplayName("Should not register duplicate observers")
    void testDuplicateRegistration() {
        WeatherStation newStation = new WeatherStation();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay(newStation);
        
        newStation.registerObserver(display);  // Try to register again
        
        // Should still only have one observer
        assertThat(newStation.getObserverCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should verify display formats correctly")
    void testDisplayFormats() {
        station.setMeasurements(25.5f, 67.3f, 1013.2f);
        
        assertThat(currentDisplay.display()).matches(".*\\d+\\.\\d.*");
        assertThat(statsDisplay.display()).matches(".*\\d+\\.\\d.*");
    }
    
    @Test
    @DisplayName("Should maintain state independence between displays")
    void testDisplayIndependence() {
        station.setMeasurements(20.0f, 60.0f, 1010.0f);
        
        station.removeObserver(statsDisplay);
        
        station.setMeasurements(25.0f, 65.0f, 1015.0f);
        
        // statsDisplay should still have data from first update
        assertThat(statsDisplay.getNumReadings()).isEqualTo(1);
        assertThat(statsDisplay.getMaxTemp()).isEqualTo(20.0f);
        
        // currentDisplay should have latest data
        assertThat(currentDisplay.getTemperature()).isEqualTo(25.0f);
    }
}
