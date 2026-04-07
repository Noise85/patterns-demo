package com.patterns.observer.simulation;

import java.math.BigDecimal;
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
        // TODO: Add buy rule to buyRules map
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        // TODO: Execute trading logic
        // 1. Check buy rules: if price <= trigger and have capital, execute buy
        // 2. Check sell rules: if holding position and price increased 10% above purchase, sell
        // 3. Log transactions
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getObserverId() {
        return "TradingBot-" + botId;
    }
    
    @Override
    public boolean isInterestedIn(StockEvent event) {
        // TODO: Filter events
        // Interested if:
        // 1. Have a buy rule for this symbol, OR
        // 2. Currently holding this stock
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Executes a buy transaction.
     */
    private void executeBuy(String symbol, BigDecimal price) {
        // TODO: Implement buy logic
        // 1. Calculate shares to buy (use available capital)
        // 2. Deduct cost from capital
        // 3. Add position to holdings
        // 4. Log transaction
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Executes a sell transaction.
     */
    private void executeSell(String symbol, BigDecimal price) {
        // TODO: Implement sell logic
        // 1. Get position from holdings
        // 2. Calculate proceeds (shares * price)
        // 3. Add proceeds to capital
        // 4. Remove position from holdings
        // 5. Log transaction
        throw new UnsupportedOperationException("Not implemented yet");
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
        public final int shares;
        public final BigDecimal purchasePrice;
        
        public Position(int shares, BigDecimal purchasePrice) {
            this.shares = shares;
            this.purchasePrice = purchasePrice;
        }
    }
}
