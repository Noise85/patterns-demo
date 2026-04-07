package com.patterns.adapter.isolation;

/**
 * Adapter that makes LegacyFahrenheitSensor compatible with TemperatureSensor interface.
 * Converts Fahrenheit to Celsius and adapts the interface.
 */
public class FahrenheitToCelsiusAdapter implements TemperatureSensor {
    private final LegacyFahrenheitSensor adaptee;

    public FahrenheitToCelsiusAdapter(LegacyFahrenheitSensor adaptee) {
        this.adaptee = adaptee;
    }

    /**
     * Gets the temperature in Celsius by converting from Fahrenheit.
     *
     * @return Temperature in Celsius
     */
    @Override
    public double getTemperature() {
        // TODO: Read Fahrenheit from adaptee and convert to Celsius
        // Formula: celsius = (fahrenheit - 32) * 5.0 / 9.0
        throw new UnsupportedOperationException("TODO: Implement temperature conversion");
    }

    /**
     * Gets the sensor ID from the adaptee.
     *
     * @return Sensor ID
     */
    @Override
    public String getSensorId() {
        // TODO: Delegate to adaptee's getDeviceId() method
        throw new UnsupportedOperationException("TODO: Implement getSensorId()");
    }
}
