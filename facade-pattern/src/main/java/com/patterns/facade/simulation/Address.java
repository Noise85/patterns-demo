package com.patterns.facade.simulation;

/**
 * Shipping address information.
 */
public record Address(
    String street,
    String city,
    String state,
    String zipCode,
    String country
) {
    public Address {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be blank");
        }
    }
}
