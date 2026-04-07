package com.patterns.visitor.simulation;

import java.math.BigDecimal;

/**
 * Represents gift wrapping service for an order.
 */
public class GiftWrap implements OrderComponent {
    
    private final String message;
    private final String wrapStyle;
    private final BigDecimal price;
    
    /**
     * Creates new gift wrap information.
     *
     * @param message   gift message
     * @param wrapStyle wrapping style (e.g., "Premium", "Standard")
     * @param price     gift wrap price
     */
    public GiftWrap(String message, String wrapStyle, BigDecimal price) {
        this.message = message;
        this.wrapStyle = wrapStyle;
        this.price = price;
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Call the appropriate visitor method
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getWrapStyle() {
        return wrapStyle;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
}
