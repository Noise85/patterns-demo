package com.patterns.visitor.simulation;

/**
 * Represents shipping information for an order.
 */
public class ShippingInfo implements OrderComponent {
    
    private final ShippingMethod shippingMethod;
    private final String address;
    private final String city;
    private final String postalCode;
    
    /**
     * Creates new shipping information.
     *
     * @param shippingMethod shipping method selected
     * @param address       street address
     * @param city          city
     * @param postalCode    postal/zip code
     */
    public ShippingInfo(ShippingMethod shippingMethod, String address, 
                       String city, String postalCode) {
        this.shippingMethod = shippingMethod;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Call the appropriate visitor method
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
}
