# Exercise 1: Observer Pattern in Isolation

## Objective

Implement a **weather monitoring system** where multiple displays subscribe to a weather station and automatically update when weather conditions change. This exercise demonstrates the core Observer pattern: one-to-many dependency with automatic notification.

## Scenario

You're building a weather station that measures temperature, humidity, and pressure. Multiple display devices need to show this data, and each display should update automatically whenever measurements change.

**Key Constraint**: Displays should NOT poll the weather station. Instead, the weather station should push updates to all registered displays when measurements change.

## Requirements

### 1. Observer Interface

Define the observer interface:

```java
public interface WeatherObserver {
    void update(float temperature, float humidity, float pressure);
}
```

### 2. Subject Interface

Define the subject interface:

```java
public interface WeatherSubject {
    void registerObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers();
}
```

### 3. WeatherStation (ConcreteSubject)

Implement the weather station:

- **State**: Current temperature, humidity, pressure
- **Observer Management**: 
  - Maintain list of registered observers
  - `registerObserver()`: Add observer to list
  - `removeObserver()`: Remove observer from list
  - `notifyObservers()`: Call `update()` on all observers
  
- **State Change**:
  - `setMeasurements(float temp, float humidity, float pressure)`: Update state and notify observers

**Implementation Notes**:
- Store observers in a `List<WeatherObserver>`
- When measurements change, iterate through observers and call `update()` on each
- Use defensive programming (check for null, handle concurrent modification)

### 4. Concrete Observers (Display Devices)

Implement at least 3 display types:

**CurrentConditionsDisplay**:
- Shows current temperature and humidity
- Format: "Current conditions: [temp]°C and [humidity]% humidity"

**StatisticsDisplay**:
- Tracks min/max/average temperature across updates
- Format: "Avg/Max/Min temperature: [avg]/[max]/[min]"

**ForecastDisplay**:
- Predicts weather based on pressure changes
- Logic: 
  - Pressure increasing → "Improving weather!"
  - Pressure decreasing → "Cooler, rainy weather"
  - Pressure same → "More of the same"

Each display should:
- Implement `WeatherObserver` interface
- Auto-register with the weather station in constructor
- Store received data
- Provide a `display()` method to show current information

## Design Characteristics

1. **Loose Coupling**: WeatherStation knows observers only through the interface
2. **Dynamic Subscription**: Observers can register/unregister at runtime
3. **Automatic Propagation**: All observers update when subject state changes
4. **Pull Model**: This exercise uses push (data sent in `update()`), but could be modified for pull

## Expected Behavior

```java
WeatherStation station = new WeatherStation();

CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(station);
StatisticsDisplay statsDisplay = new StatisticsDisplay(station);
ForecastDisplay forecastDisplay = new ForecastDisplay(station);

// All displays automatically registered via constructor

station.setMeasurements(26.0f, 65.0f, 1013.1f);
// All three displays receive update() call and refresh their data

station.setMeasurements(28.0f, 70.0f, 1012.0f);
// All displays update again (forecast sees pressure drop)

station.removeObserver(forecastDisplay);
station.setMeasurements(25.0f, 68.0f, 1014.0f);
// Only currentDisplay and statsDisplay update (forecast unsubscribed)
```

### Display Outputs After Updates

After first measurement (26°C, 65%, 1013.1 hPa):
- Current: "Current conditions: 26.0°C and 65.0% humidity"
- Statistics: "Avg/Max/Min temperature: 26.0/26.0/26.0"
- Forecast: "More of the same" (no previous measurement to compare)

After second measurement (28°C, 70%, 1012.0 hPa):
- Current: "Current conditions: 28.0°C and 70.0% humidity"
- Statistics: "Avg/Max/Min temperature: 27.0/28.0/26.0"
- Forecast: "Cooler, rainy weather" (pressure decreased)

## Testing Focus

Your tests should verify:

1. ✅ Observers can register with subject
2. ✅ Observers can unregister from subject
3. ✅ All registered observers receive updates when subject state changes
4. ✅ Unregistered observers do NOT receive updates
5. ✅ Multiple observers receive updates in single notification cycle
6. ✅ Displays correctly update their internal state
7. ✅ Statistics display correctly tracks min/max/average
8. ✅ Forecast display correctly predicts based on pressure trends
9. ✅ Observer list can be modified during execution
10. ✅ Subject can notify with no observers registered (no crash)

## Implementation Tasks

1. ✅ Implement `WeatherObserver` interface
2. ✅ Implement `WeatherSubject` interface
3. ✅ Implement `WeatherStation` with observer management
4. ✅ Implement `CurrentConditionsDisplay`
5. ✅ Implement `StatisticsDisplay` with min/max/avg tracking
6. ✅ Implement `ForecastDisplay` with pressure trend logic
7. ✅ Handle edge cases (no observers, observer removes itself during update, duplicate registration)

## Key Takeaway

This exercise demonstrates the Observer pattern's core benefit: **automatic propagation of changes**. The WeatherStation doesn't know what displays exist or how they render data. Displays don't poll the station. Everything happens through the simple subscribe-notify mechanism, making the system easy to extend (add new displays) and maintain (displays are independent).
