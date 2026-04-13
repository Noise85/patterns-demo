package com.patterns.observer.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for Observer Pattern - Simulation Exercise (Stock Market).
 */
@DisplayName("Observer Pattern - Stock Market Trading Platform")
class SimulationExerciseTest {
    
    private StockExchange exchange;
    private Stock apple;
    private Stock google;
    
    @BeforeEach
    void setUp() {
        exchange = new StockExchange();
        apple = new Stock("AAPL", "Apple Inc.", new BigDecimal("150.00"));
        google = new Stock("GOOGL", "Google LLC", new BigDecimal("2800.00"));
        
        exchange.addStock(apple);
        exchange.addStock(google);
    }
    
    @Test
    @DisplayName("Should register observer with stock")
    void testRegisterObserver() {
        Investor investor = new Investor("Alice");
        apple.registerObserver(investor);
        
        assertThat(apple.getObserverCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should remove observer from stock")
    void testRemoveObserver() {
        Investor investor = new Investor("Alice");
        apple.registerObserver(investor);
        
        apple.removeObserver(investor);
        
        assertThat(apple.getObserverCount()).isZero();
    }
    
    @Test
    @DisplayName("Should notify observer when price changes")
    void testPriceChangeNotification() {
        DayTrader trader = new DayTrader("Bob");
        apple.registerObserver(trader);
        
        apple.updatePrice(new BigDecimal("157.50"), 1_000_000);
        
        assertThat(trader.getOpportunityCount()).isGreaterThan(0);
    }
    
    @Test
    @DisplayName("Should create stock exchange and add stocks")
    void testStockExchange() {
        assertThat(exchange.getStockCount()).isEqualTo(2);
        assertThat(exchange.getStock("AAPL")).isEqualTo(apple);
    }
    
    @Test
    @DisplayName("Should observe stock through exchange")
    void testObserveThroughExchange() {
        Investor investor = new Investor("Charlie");
        boolean success = exchange.observeStock("AAPL", investor);
        
        assertThat(success).isTrue();
        assertThat(apple.getObserverCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should track investor holdings")
    void testInvestorHoldings() {
        Investor investor = new Investor("Alice");
        investor.addHolding("AAPL", 100);
        
        assertThat(investor.getHoldings()).containsEntry("AAPL", 100);
    }
    
    @Test
    @DisplayName("Should filter events by significance for investor")
    void testInvestorInterestFilter() {
        Investor investor = new Investor("Alice");
        investor.addHolding("AAPL", 100);

        StockEvent smallChange = new StockEvent(
            "AAPL", StockEventType.PRICE_INCREASE, null,
            new BigDecimal("150.00"), new BigDecimal("150.50"),
            1000000, new BigDecimal("0.50"), 0.003333333d
        );
        
        StockEvent largeChange = new StockEvent(
            "AAPL", StockEventType.PRICE_INCREASE, null,
            new BigDecimal("150.00"), new BigDecimal("155.00"),
            2000000, new BigDecimal("5.00"), 0.033333333d
        );
        
        assertThat(investor.supportsEvent(smallChange)).isFalse();
        assertThat(investor.supportsEvent(largeChange)).isTrue();
    }
    
    @Test
    @DisplayName("Should track opportunities for day trader")
    void testDayTraderOpportunities() {
        DayTrader trader = new DayTrader("Bob");
        apple.registerObserver(trader);
        
        apple.updatePrice(new BigDecimal("160.00"), 500_000);
        apple.updatePrice(new BigDecimal("175.00"), 600_000);
        
        assertThat(trader.getOpportunityCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should filter events by threshold for day trader")
    void testDayTraderInterestFilter() {
        DayTrader trader = new DayTrader("Bob");
        
        StockEvent smallChange = new StockEvent(
            "AAPL", StockEventType.PRICE_INCREASE, null,
            new BigDecimal("150.00"), new BigDecimal("155.10"),
            1000000, new BigDecimal("5.10"), 0.034
        );
        
        StockEvent largeChange = new StockEvent(
            "AAPL", StockEventType.PRICE_INCREASE, null,
            new BigDecimal("150.00"), new BigDecimal("160.00"),
            2000000, new BigDecimal("1.00"), 0.67
        );
        
        assertThat(trader.supportsEvent(smallChange)).isFalse();
        assertThat(trader.supportsEvent(largeChange)).isTrue();
    }
    
    @Test
    @DisplayName("Should add buy rule to trading bot")
    void testTradingBotBuyRule() {
        TradingBot bot = new TradingBot("bot1", new BigDecimal("10000"));
        bot.addBuyRule("AAPL", new BigDecimal("145.00"));
        
        assertThat(bot.getCapital()).isEqualByComparingTo(new BigDecimal("10000"));
    }
    
    @Test
    @DisplayName("Should filter events for stocks with buy rules or holdings")
    void testTradingBotInterestFilter() {
        TradingBot bot = new TradingBot("bot1", new BigDecimal("10000"));
        bot.addBuyRule("AAPL", new BigDecimal("145.00"));
        
        StockEvent appleEvent = new StockEvent(
            "AAPL", StockEventType.PRICE_DECREASE, null,
            new BigDecimal("150.00"), new BigDecimal("144.00"),
            1000000, new BigDecimal("-6.00"), -4.0
        );
        
        StockEvent googleEvent = new StockEvent(
            "GOOGL", StockEventType.PRICE_INCREASE, null,
            new BigDecimal("2800.00"), new BigDecimal("2850.00"),
            500000, new BigDecimal("50.00"), 1.79
        );
        
        assertThat(bot.supportsEvent(appleEvent)).isTrue();
        assertThat(bot.supportsEvent(googleEvent)).isFalse();
    }
    
    @Test
    @DisplayName("Should trigger price alert above threshold")
    void testPriceAlertAbove() {
        PriceAlertMonitor alert = new PriceAlertMonitor("alert1", "AAPL", new BigDecimal("155.00"), AlertType.ABOVE);
        apple.registerObserver(alert);
        
        apple.updatePrice(new BigDecimal("156.00"), 1_000_000);
        
        assertThat(alert.isTriggered()).isTrue();
        assertThat(alert.getLastAlertMessage()).isNotNull();
    }
    
    @Test
    @DisplayName("Should trigger price alert below threshold")
    void testPriceAlertBelow() {
        PriceAlertMonitor alert = new PriceAlertMonitor("alert2", "AAPL", new BigDecimal("145.00"), AlertType.BELOW);
        apple.registerObserver(alert);
        
        apple.updatePrice(new BigDecimal("144.00"), 800_000);
        
        assertThat(alert.isTriggered()).isTrue();
    }
    
    @Test
    @DisplayName("Should not trigger alert before threshold crossed")
    void testAlertNotTriggeredBefore() {
        PriceAlertMonitor alert = new PriceAlertMonitor("alert3", "AAPL", new BigDecimal("160.00"), AlertType.ABOVE);
        apple.registerObserver(alert);
        
        apple.updatePrice(new BigDecimal("155.00"), 1_000_000);
        
        assertThat(alert.isTriggered()).isFalse();
    }
    
    @Test
    @DisplayName("Should track events in market analyzer")
    void testMarketAnalyzerTracking() {
        MarketAnalyzer analyzer = new MarketAnalyzer("analyzer1");
        apple.registerObserver(analyzer);
        google.registerObserver(analyzer);
        
        apple.updatePrice(new BigDecimal("151.00"), 1_000_000);
        google.updatePrice(new BigDecimal("2850.00"), 800_000);
        
        assertThat(analyzer.getTotalEventCount()).isEqualTo(2);
        assertThat(analyzer.getTrackedStockCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should calculate average percent change")
    void testAveragePercentChange() {
        MarketAnalyzer analyzer = new MarketAnalyzer("analyzer1");
        apple.registerObserver(analyzer);
        
        apple.updatePrice(new BigDecimal("153.00"), 1_000_000);  // +2%
        apple.updatePrice(new BigDecimal("156.00"), 1_200_000);  // ~1.96%
        
        double avg = analyzer.getAveragePercentChange();
        
        assertThat(avg).isGreaterThan(0);
    }
    
    @Test
    @DisplayName("Should determine bullish trend")
    void testBullishTrend() {
        MarketAnalyzer analyzer = new MarketAnalyzer("analyzer1");
        apple.registerObserver(analyzer);
        
        apple.updatePrice(new BigDecimal("153.00"), 1_000_000);  // +2%
        
        assertThat(analyzer.getTrend()).isEqualTo("BULLISH");
    }
    
    @Test
    @DisplayName("Should determine bearish trend")
    void testBearishTrend() {
        MarketAnalyzer analyzer = new MarketAnalyzer("analyzer1");
        apple.registerObserver(analyzer);
        
        apple.updatePrice(new BigDecimal("147.00"), 1_000_000);  // -2%
        
        assertThat(analyzer.getTrend()).isEqualTo("BEARISH");
    }
    
    @Test
    @DisplayName("Should verify stock event contains correct data")
    void testStockEventData() {
        final StockEvent[] capturedEvent = new StockEvent[1];
        
        StockObserver capturer = new StockObserver() {
            @Override
            public void onStockUpdate(StockEvent event) {
                capturedEvent[0] = event;
            }
            
            @Override
            public String getObserverId() {
                return "capturer";
            }
            
            @Override
            public boolean supportsEvent(StockEvent event) {
                return true;
            }
        };
        
        apple.registerObserver(capturer);
        apple.updatePrice(new BigDecimal("153.00"), 1_000_000);
        
        assertThat(capturedEvent[0]).isNotNull();
        assertThat(capturedEvent[0].symbol()).isEqualTo("AAPL");
        assertThat(capturedEvent[0].newPrice()).isEqualByComparingTo(new BigDecimal("153.00"));
        assertThat(capturedEvent[0].oldPrice()).isEqualByComparingTo(new BigDecimal("150.00"));
        assertThat(capturedEvent[0].volume()).isEqualTo(1_000_000);
        assertThat(capturedEvent[0].timestamp()).isNotNull();
    }
    
    @Test
    @DisplayName("Should calculate price change correctly")
    void testPriceChange() {
        final StockEvent[] capturedEvent = new StockEvent[1];
        
        apple.registerObserver(new StockObserver() {
            @Override
            public void onStockUpdate(StockEvent event) {
                capturedEvent[0] = event;
            }
            
            @Override
            public String getObserverId() {
                return "test";
            }
            
            @Override
            public boolean supportsEvent(StockEvent event) {
                return true;
            }
        });
        
        apple.updatePrice(new BigDecimal("153.00"), 1_000_000);
        
        assertThat(capturedEvent[0].priceChange()).isEqualByComparingTo(new BigDecimal("3.00"));
        assertThat(capturedEvent[0].percentChange()).isCloseTo(0.02, within(0.00001));
    }
    
    @Test
    @DisplayName("Should determine correct event type")
    void testEventType() {
        final StockEvent[] capturedEvent = new StockEvent[1];
        
        apple.registerObserver(new StockObserver() {
            @Override
            public void onStockUpdate(StockEvent event) {
                capturedEvent[0] = event;
            }
            
            @Override
            public String getObserverId() {
                return "test";
            }
            
            @Override
            public boolean supportsEvent(StockEvent event) {
                return true;
            }
        });
        
        apple.updatePrice(new BigDecimal("148.00"), 1_000_000);
        
        assertThat(capturedEvent[0].eventType()).isEqualTo(StockEventType.PRICE_DECREASE);
    }
    
    @Test
    @DisplayName("Should verify observer IDs are unique")
    void testObserverIds() {
        Investor investor = new Investor("Alice");
        DayTrader trader = new DayTrader("Bob");
        TradingBot bot = new TradingBot("bot1", new BigDecimal("10000"));
        
        assertThat(investor.getObserverId()).isNotEqualTo(trader.getObserverId());
        assertThat(trader.getObserverId()).isNotEqualTo(bot.getObserverId());
    }
    
    @Test
    @DisplayName("Should handle multiple observers on same stock")
    void testMultipleObservers() {
        Investor investor = new Investor("Alice");
        DayTrader trader = new DayTrader("Bob");
        
        apple.registerObserver(investor);
        apple.registerObserver(trader);
        
        assertThat(apple.getObserverCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should verify stock properties")
    void testStockProperties() {
        assertThat(apple.getSymbol()).isEqualTo("AAPL");
        assertThat(apple.getName()).isEqualTo("Apple Inc.");
        assertThat(apple.getCurrentPrice()).isEqualByComparingTo(new BigDecimal("150.00"));
    }
    
    @Test
    @DisplayName("Should verify alert type enumeration")
    void testAlertTypes() {
        assertThat(AlertType.values()).contains(AlertType.ABOVE, AlertType.BELOW);
    }
    
    @Test
    @DisplayName("Should verify stock event type enumeration")
    void testEventTypes() {
        assertThat(StockEventType.values()).contains(
            StockEventType.PRICE_INCREASE,
            StockEventType.PRICE_DECREASE,
            StockEventType.VOLUME_SPIKE
        );
    }
}
