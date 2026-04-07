# Solution Notes: Observer Pattern

## Core Implementation Strategy

### Exercise 1: Weather Monitoring System

**WeatherStation (Subject) Implementation**:

1. **Observer List Management**:
   ```java
   private final List<WeatherObserver> observers = new ArrayList<>();
   
   public void registerObserver(WeatherObserver observer) {
       if (!observers.contains(observer)) {
           observers.add(observer);
       }
   }
   
   public void removeObserver(WeatherObserver observer) {
       observers.remove(observer);
   }
   ```

2. **State Change & Notification**:
   ```java
   public void setMeasurements(float temp, float humidity, float pressure) {
       this.temperature = temp;
       this.humidity = humidity;
       this.pressure = pressure;
       notifyObservers();
   }
   
   public void notifyObservers() {
       // Create defensive copy to avoid ConcurrentModificationException
       for (WeatherObserver observer : new ArrayList<>(observers)) {
           observer.update(temperature, humidity, pressure);
       }
   }
   ```

**Display Observers**:

1. **CurrentConditionsDisplay**:
   - Store latest temperature and humidity in fields
   - Update fields in `update()` method
   - `display()` formats: "Current conditions: {temp}°C and {humidity}% humidity"

2. **StatisticsDisplay** (More Complex):
   ```java
   private float tempSum = 0;
   private int numReadings = 0;
   private float maxTemp = Float.MIN_VALUE;
   private float minTemp = Float.MAX_VALUE;
   
   public void update(float temp, float humidity, float pressure) {
       tempSum += temp;
       numReadings++;
       
       if (temp > maxTemp) maxTemp = temp;
       if (temp < minTemp) minTemp = temp;
   }
   
   public String display() {
       float avg = tempSum / numReadings;
       return String.format("Avg/Max/Min temperature: %.1f/%.1f/%.1f", avg, maxTemp, minTemp);
   }
   ```

3. **ForecastDisplay**:
   ```java
   private float lastPressure = 0;
   private float currentPressure = 0;
   
   public void update(float temp, float humidity, float pressure) {
       lastPressure = currentPressure;
       currentPressure = pressure;
   }
   
   public String display() {
       if (lastPressure == 0) return "More of the same";
       if (currentPressure > lastPressure) return "Improving weather!";
       if (currentPressure < lastPressure) return "Cooler, rainy weather";
       return "More of the same";
   }
   ```

**Auto-Registration Pattern**:
- Displays register themselves in constructor:
  ```java
  public CurrentConditionsDisplay(WeatherSubject station) {
      this.station = station;
      station.registerObserver(this);
  }
  ```

### Exercise 2: Stock Market Platform

**Stock (Subject) Implementation**:

1. **Price Update with Event Creation**:
   ```java
   public void updatePrice(BigDecimal newPrice, long volume) {
       BigDecimal oldPrice = this.currentPrice;
       this.currentPrice = newPrice;
       this.volume = volume;
       
       BigDecimal priceChange = newPrice.subtract(oldPrice);
       double percentChange = priceChange.divide(oldPrice, 4, RoundingMode.HALF_UP)
                                          .multiply(new BigDecimal("100"))
                                          .doubleValue();
       
       StockEventType eventType = priceChange.compareTo(BigDecimal.ZERO) >= 0 
           ? StockEventType.PRICE_INCREASE 
           : StockEventType.PRICE_DECREASE;
       
       StockEvent event = new StockEvent(
           symbol, eventType, LocalDateTime.now(),
           oldPrice, newPrice, volume, priceChange, percentChange
       );
       
       notifyObservers(event);
   }
   ```

2. **Filtered Notification**:
   ```java
   private void notifyObservers(StockEvent event) {
       for (StockObserver observer : new ArrayList<>(observers)) {
           if (observer.isInterestedIn(event)) {
               observer.onStockUpdate(event);
           }
       }
   }
   ```

**Observer Filtering Examples**:

1. **Investor** (Only significant changes):
   ```java
   @Override
   public boolean isInterestedIn(StockEvent event) {
       return holdings.containsKey(event.symbol()) && 
              Math.abs(event.percentChange()) > 2.0;
   }
   ```

2. **DayTrader** (All meaningful changes):
   ```java
   @Override
   public boolean isInterestedIn(StockEvent event) {
       return Math.abs(event.percentChange()) > 0.5;
   }
   ```

3. **PriceAlertMonitor** (Threshold-based):
   ```java
   @Override
   public boolean isInterestedIn(StockEvent event) {
       if (!event.symbol().equals(targetSymbol)) return false;
       
       return (alertType == AlertType.ABOVE && event.newPrice().compareTo(threshold) >= 0) ||
              (alertType == AlertType.BELOW && event.newPrice().compareTo(threshold) <= 0);
   }
   ```

**TradingBot Logic**:
```java
@Override
public void onStockUpdate(StockEvent event) {
    // Check buy rules
    if (buyRules.containsKey(event.symbol())) {
        BigDecimal buyPrice = buyRules.get(event.symbol());
        if (event.newPrice().compareTo(buyPrice) <= 0 && capital.compareTo(BigDecimal.ZERO) > 0) {
            executeBuy(event.symbol(), event.newPrice());
        }
    }
    
    // Check sell rules
    if (holdings.containsKey(event.symbol())) {
        // Sell if price increased 10% above purchase price
        Position position = holdings.get(event.symbol());
        BigDecimal targetSell = position.purchasePrice.multiply(new BigDecimal("1.10"));
        if (event.newPrice().compareTo(targetSell) >= 0) {
            executeSell(event.symbol(), event.newPrice());
        }
    }
}
```

**MarketAnalyzer Aggregation**:
```java
@Override
public void onStockUpdate(StockEvent event) {
    stockEvents.computeIfAbsent(event.symbol(), k -> new ArrayList<>()).add(event);
}

public MarketSummary getMarketSummary() {
    double avgChange = stockEvents.values().stream()
        .flatMap(List::stream)
        .mapToDouble(StockEvent::percentChange)
        .average()
        .orElse(0.0);
    
    String trend = avgChange > 1.0 ? "BULLISH" : avgChange < -1.0 ? "BEARISH" : "NEUTRAL";
    
    return new MarketSummary(stockEvents.size(), avgChange, trend);
}
```

## Common Pitfalls to Avoid

1. **ConcurrentModificationException**: 
   - Problem: Observer removes itself during notification loop
   - Solution: Iterate over defensive copy: `new ArrayList<>(observers)`

2. **Memory Leaks**:
   - Problem: Observers never unsubscribe
   - Solution: Provide explicit `unsubscribe()`, or use weak references

3. **Null Checks**:
   - Always check if observer list is null/empty before notifying
   - Validate observer parameter in register/remove methods

4. **Duplicate Registration**:
   - Check `!observers.contains(observer)` before adding
   - Or use `Set<Observer>` instead of `List<Observer>`

5. **Notification During Construction**:
   - Don't notify observers in subject's constructor
   - Subject may not be fully initialized

6. **Circular Updates**:
   - Observer update triggers another subject change
   - Can cause infinite loop
   - Solution: Track notification depth, or prevent cascading

## Testing Strategy

**Exercise 1**:
- Test observer registration/removal
- Verify all observers receive updates
- Test statistics accumulation (min/max/avg)
- Test forecast pressure trend logic
- Test empty observer list (no crash)
- Test observer removing itself during update

**Exercise 2**:
- Test event filtering (observers receive only relevant events)
- Test price alert threshold triggering
- Test trading bot buy/sell execution
- Test portfolio value calculation
- Test market analyzer aggregation
- Test concurrent observer modification
- Test event object contains correct data (price change, percent, timestamp)

## Extension Ideas

**Exercise 1**:
- Add HeatIndexDisplay (combines temp and humidity)
- Implement pull model (observers query subject for data)
- Add WeatherData change history
- Support multiple weather stations

**Exercise 2**:
- Add OrderBook observer (tracks buy/sell orders)
- Implement priority-based notification (VIP observers first)
- Add event history with replay capability
- Support stock splits and dividends
- Implement async notification (CompletableFuture)
- Add circuit breaker to prevent notification storms

## Pattern Variations

1. **Event Objects**: Pass rich event objects (used in Exercise 2)
2. **Aspect-Specific Observers**: Subscribe to specific event types only
3. **Change Manager**: Intermediary to coordinate complex dependency graphs
4. **Weak References**: Prevent memory leaks in long-running systems

## Performance Considerations

- For many observers, consider:
  - Concurrent collections (`CopyOnWriteArrayList`)
  - Filtering before notification (avoid calling all observers)
  - Async notification (don't block subject on slow observers)
  - Event batching (collect multiple changes, notify once)

## Real-World Evolution

As systems grow, consider transitioning to:
- **Event Bus**: Centralized event distribution (Guava EventBus, Spring ApplicationEventPublisher)
- **Message Queue**: Distributed systems (Kafka, RabbitMQ)
- **Reactive Streams**: Backpressure handling (RxJava, Project Reactor)
- **CQRS**: Separate read/write models with event sourcing

The Observer pattern provides the foundation, but production systems often need additional infrastructure for scalability and resilience.
