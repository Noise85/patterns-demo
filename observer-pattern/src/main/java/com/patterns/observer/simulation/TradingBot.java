package com.patterns.observer.simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Automated trading algorithm with buy/sell rules.
 */
public class TradingBot implements StockObserver {
    
    private final String botId;
    private BigDecimal capital;
    private final Map<String, BigDecimal> buyRules;  // symbol -> trigger price
    private final Map<String, Position> holdings;    // symbol -> position
    private final List<String> transactionLog;
    
    /**
     * Creates a new trading bot.
     *
     * @param botId   unique bot identifier
     * @param capital starting capital
     */
    public TradingBot(String botId, BigDecimal capital) {
        this.botId = botId;
        this.capital = capital;
        this.buyRules = new HashMap<>();
        this.holdings = new HashMap<>();
        this.transactionLog = new ArrayList<>();
    }
    
    /**
     * Adds a buy rule for a stock.
     *
     * @param symbol      stock symbol
     * @param triggerPrice price at or below which to buy
     */
    public void addBuyRule(String symbol, BigDecimal triggerPrice) {
        this.buyRules.put(symbol, triggerPrice);
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        if(!this.supportsEvent(event)) {
            return;
        }
        if(event.newPrice().compareTo(buyRules.get(event.symbol())) <= 0) {
            this.executeBuy(event.symbol(), event.newPrice());
        }
        if(this.holdings.containsKey(event.symbol()) && event.priceChange().compareTo(BigDecimal.valueOf(0.1)) >= 0) {
            this.executeSell(event.symbol(), event.newPrice());
            this.holdings.remove(event.symbol());
        }
    }
    
    @Override
    public String getObserverId() {
        return "TradingBot-" + botId;
    }
    
    @Override
    public boolean supportsEvent(StockEvent event) {
        return this.buyRules.containsKey(event.symbol()) || this.holdings.containsKey(event.symbol());
    }
    
    /**
     * Executes a buy transaction.
     */
    private void executeBuy(String symbol, BigDecimal price) {
        BigDecimal shares = capital.divide(price, 2, RoundingMode.HALF_DOWN);
        if(shares.compareTo(BigDecimal.ZERO) <= 0 || this.holdings.containsKey(symbol)) {
            return;
        }
        this.capital = capital.subtract(price.multiply(shares));
        this.holdings.put(symbol, new Position(shares, price));
        this.transactionLog.add("Bought " + shares + " shares of " + symbol + " at $" + price);
    }
    
    /**
     * Executes a sell transaction.
     */
    private void executeSell(String symbol, BigDecimal price) {
        Position position = holdings.get(symbol);
        if(position == null) {
            return;
        }
        BigDecimal proceeds = position.shares.multiply(price);
        this.capital = this.capital.add(proceeds);
        this.holdings.remove(symbol);
        this.transactionLog.add("Sold " + position.shares + " shares of " + symbol + " at $" + price);
    }
    
    public BigDecimal getCapital() {
        return capital;
    }
    
    public Map<String, Position> getHoldings() {
        return new HashMap<>(holdings);
    }
    
    public List<String> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }
    
    /**
     * Record of a stock position.
     */
    public static class Position {
        public final BigDecimal shares;
        public final BigDecimal purchasePrice;
        
        public Position(BigDecimal shares, BigDecimal purchasePrice) {
            this.shares = shares;
            this.purchasePrice = purchasePrice;
        }
    }
}
