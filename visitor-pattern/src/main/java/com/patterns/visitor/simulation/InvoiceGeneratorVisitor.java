package com.patterns.visitor.simulation;

/**
 * Visitor that generates a formatted invoice for an order.
 */
public class InvoiceGeneratorVisitor implements OrderVisitor {
    
    private final StringBuilder invoice;
    private final PriceCalculatorVisitor priceCalculator;
    
    public InvoiceGeneratorVisitor() {
        this.invoice = new StringBuilder();
        this.priceCalculator = new PriceCalculatorVisitor();
    }
    
    @Override
    public void visitOrder(Order order) {
        // TODO: Generate invoice header
        // Include order ID and customer ID
        // Also visit order with price calculator to get totals
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: Add line item to invoice
        // Format: "- ProductName (xQuantity) @ $UnitPrice = $LineTotal"
        // Also visit with price calculator
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO: Add discount information to invoice
        // Format: "Discount (CODE - TYPE): -$Amount"
        // Also visit with price calculator
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitShippingInfo(ShippingInfo shipping) {
        // TODO: Add shipping information to invoice
        // Format: "Shipping (METHOD): $Cost"
        // Also visit with price calculator
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitGiftWrap(GiftWrap giftWrap) {
        // TODO: Add gift wrap information to invoice
        // Format: "Gift Wrap: $Cost"
        // Also visit with price calculator
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the formatted invoice.
     *
     * @return invoice as string
     */
    public String getInvoice() {
        // TODO: Return the complete invoice with totals
        // Add footer with subtotal, tax, and grand total from price calculator
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
