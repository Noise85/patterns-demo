package com.patterns.visitor.simulation;

import java.math.BigDecimal;

/**
 * Represents a line item in an order.
 */
public class LineItem implements OrderComponent {
    
    private final String productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPrice;
    
    /**
     * Creates a new line item.
     *
     * @param productId   unique product identifier
     * @param productName product name
     * @param quantity    quantity ordered
     * @param unitPrice   price per unit
     */
    public LineItem(String productId, String productName, int quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Call the appropriate visitor method
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    /**
     * Calculates the total price for this line item.
     *
     * @return quantity × unit price
     */
    public BigDecimal getLineTotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
