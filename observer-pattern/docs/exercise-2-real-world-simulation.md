# Exercise 2: Real-World Simulation - Stock Market Trading Platform

## Objective

Build a **stock market trading platform** where investors, traders, and automated systems monitor stock price changes and react accordingly. This simulation demonstrates how the Observer pattern scales to real-world complexity with multiple observer types, filtering, and event-driven reactions.

## Business Context

You're developing a financial trading platform where:
- Stock prices fluctuate constantly
- Multiple parties need real-time updates (investors, day traders, algorithms)
- Different observers care about different events (price thresholds, volume, trends)
- Observers may trigger actions (buy/sell orders, alerts, analytics updates)
- The system must handle hundreds of stocks and thousands of observers efficiently

**Challenge**: Design a scalable observer system that supports filtering, prioritization, and complex event processing without tight coupling.

## Domain Model

### Core Components

1. **Stock (Subject)**
   - Properties: symbol, name, current price, previous price, volume, market cap
   - State changes: Price updates, volume updates
   - Behavior: Notify observers on significant changes

2. **StockExchange (Subject Manager)**
   - Maintains registry of all stocks
   - Provides stock lookup by symbol
   - Aggregates market-level events

3. **Observer Types**:
   - **Investor**: Long-term holder, tracks portfolio value
   - **DayTrader**: Short-term trader, reacts to price movements
   - **TradingBot**: Algorithmic trader with buy/sell rules
   - **PriceAlertMonitor**: Triggers alerts on price thresholds
   - **MarketAnalyzer**: Aggregates statistics across multiple stocks

### Advanced Features

**Event Filtering**:
- Observers subscribe to specific stocks (not all market activity)
- Observers filter by event type (price change, volume spike, etc.)
- Threshold-based filtering (notify only if price change > X%)

**Event Types**:
```java
public enum StockEventType {
    PRICE_INCREASE,
    PRICE_DECREASE,
    VOLUME_SPIKE,
    MARKET_OPEN,
    MARKET_CLOSE,
    TRADING_HALTED
}
```

**Rich Event Objects**:
Instead of simple `update()`, pass event objects:
```java
public record StockEvent(
    String symbol,
    StockEventType eventType,
    LocalDateTime timestamp,
    BigDecimal oldPrice,
    BigDecimal newPrice,
    long volume,
    BigDecimal priceChange,
    double percentChange
) {}
```

## Requirements

### 1. StockObserver Interface

```java
public interface StockObserver {
    void onStockUpdate(StockEvent event);
    String getObserverId();
    boolean isInterestedIn(StockEvent event);  // Filtering
}
```

### 2. Stock (ConcreteSubject)

**State**:
- `symbol`: Stock ticker (e.g., "AAPL", "GOOGL")
- `name`: Company name
- `currentPrice`: Current trading price
- `previousPrice`: Price before last update
- `volume`: Trading volume
- `observers`: List of registered observers

**Methods**:
- `registerObserver()`: Add observer
- `removeObserver()`: Remove observer
- `updatePrice(BigDecimal newPrice, long volume)`: Update price and notify
- `notifyObservers(StockEvent event)`: Notify with filtering

**Notification Logic**:
```
For each observer:
  If observer.isInterestedIn(event):
    observer.onStockUpdate(event)
```

### 3. Concrete Observers

**Investor**:
- Tracks portfolio: Map<String, Integer> holdings (symbol → shares)
- Calculates net worth when prices change
- Only interested in significant changes (>2% typically)
- Logs portfolio value changes

**DayTrader**:
- Monitors rapid price movements
- Interested in all price changes >0.5%
- Tracks opportunity count (rapid up/down movements)
- May simulate buy/sell decisions

**TradingBot**:
- Automated trading algorithm
- Buy rule: Price drops >5% below target
- Sell rule: Price rises >10% above purchase price
- Maintains transaction history

**PriceAlertMonitor**:
- User-configured price alerts
- Triggers when price crosses threshold (above/below)
- One-time or recurring alerts
- Sends notification messages

**MarketAnalyzer**:
- Observes all stocks in the exchange
- Calculates market-wide metrics (average price change, volatility)
- Identifies trends (bull/bear market indicators)

### 4. StockExchange (Central Registry)

**Responsibilities**:
- Manage collection of all Stock objects
- Provide stock lookup by symbol
- Support market-wide events (open, close)
- Aggregate observer registration across stocks

**Methods**:
- `addStock(Stock stock)`: Register new stock
- `getStock(String symbol)`: Retrieve stock by symbol
- `observeStock(String symbol, StockObserver observer)`: Convenience method
- `broadcastMarketEvent(StockEventType event)`: Notify all observers

## Expected Behavior

### Scenario 1: Price Alert Triggering

```java
StockExchange exchange = new StockExchange();
Stock apple = new Stock("AAPL", "Apple Inc.", new BigDecimal("150.00"));
exchange.addStock(apple);

PriceAlertMonitor alert = new PriceAlertMonitor("alert1", "AAPL", new BigDecimal("155.00"), AlertType.ABOVE);
apple.registerObserver(alert);

apple.updatePrice(new BigDecimal("156.00"), 1_000_000);
// Alert triggered: "Price alert: AAPL crossed 155.00 (now 156.00)"
```

### Scenario 2: Trading Bot Execution

```java
TradingBot bot = new TradingBot("bot1", new BigDecimal("100000")); // $100k capital
bot.addBuyRule("AAPL", new BigDecimal("145.00")); // Buy if price drops to $145

apple.registerObserver(bot);
apple.updatePrice(new BigDecimal("144.50"), 500_000);
// Bot executes buy: "TradingBot bought 100 shares of AAPL at 144.50"
```

### Scenario 3: Market-Wide Analysis

```java
MarketAnalyzer analyzer = new MarketAnalyzer();

for (Stock stock : exchange.getAllStocks()) {
    stock.registerObserver(analyzer);
}

// Multiple stocks update prices
apple.updatePrice(new BigDecimal("151.00"), 1_000_000);   // +0.67%
google.updatePrice(new BigDecimal("2850.00"), 800_000);   // +1.2%
tesla.updatePrice(new BigDecimal("695.00"), 2_000_000);   // -2.3%

analyzer.getMarketSummary();
// "Market: 3 stocks tracked, Avg change: -0.14%, Trend: NEUTRAL"
```

## Testing Focus

Your tests should verify:

1. ✅ Stock notifies observers when price changes
2. ✅ Observers can filter events (only receive relevant updates)
3. ✅ Multiple observers receive same event
4. ✅ Observer can unsubscribe and stops receiving updates
5. ✅ PriceAlertMonitor triggers at correct thresholds
6. ✅ TradingBot executes buy/sell rules correctly
7. ✅ Investor calculates portfolio value accurately
8. ✅ DayTrader tracks opportunity count
9. ✅ MarketAnalyzer aggregates market-wide statistics
10. ✅ StockEvent contains accurate price change data
11. ✅ Event timestamps are populated
12. ✅ Volume spike events trigger correctly
13. ✅ Exchange can broadcast market-wide events
14. ✅ Performance: Handling many observers efficiently

## Design Challenges

1. **Filtering**: Implement `isInterestedIn()` efficiently to avoid notifying uninterested observers
2. **Event Richness**: Include all relevant data in StockEvent to avoid circular dependencies
3. **Threading**: Consider thread-safety if prices update from multiple sources
4. **Memory Management**: Ensure observers can unsubscribe to avoid memory leaks
5. **Performance**: Optimize notification when thousands of observers exist
6. **Cascading Updates**: Handle case where observer update triggers another price change

## Implementation Tasks

1. ✅ Implement `StockObserver` interface with filtering
2. ✅ Create `StockEvent` record with comprehensive data
3. ✅ Implement `Stock` class with price tracking and notification
4. ✅ Implement `StockExchange` as central registry
5. ✅ Create `Investor` observer with portfolio tracking
6. ✅ Create `DayTrader` observer with opportunity counting
7. ✅ Create `TradingBot` observer with buy/sell rules
8. ✅ Create `PriceAlertMonitor` observer with threshold logic
9. ✅ Create `MarketAnalyzer` observer with market metrics
10. ✅ Implement event type enumeration
11. ✅ Add percentage change calculation
12. ✅ Handle edge cases (negative prices, zero volume, duplicate observers)

## Key Takeaway

This simulation demonstrates the Observer pattern at scale. Unlike the simple weather station, this system has:
- **Multiple subject types** (individual stocks + exchange)
- **Event filtering** (observers specify what they care about)
- **Rich event objects** (comprehensive state change data)
- **Action triggers** (observers don't just display; they act)
- **Aggregation** (market analyzer observes many subjects)

The pattern enables a highly decoupled architecture where adding new observer types (e.g., news sentiment analyzer, risk monitor) requires no changes to Stock or other observers.

**Production Considerations**:
- Use concurrent collections for thread-safe observer lists
- Consider event queues for asynchronous notification
- Implement priority-based notification ordering
- Add circuit breakers to prevent cascading failures
- Use weak references to prevent memory leaks in long-running systems
