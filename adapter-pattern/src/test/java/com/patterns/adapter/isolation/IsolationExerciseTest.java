package com.patterns.adapter.isolation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for Exercise 1: Temperature Sensor Adapter
 */
class IsolationExerciseTest {

    @Test
    void adapter_implementsTargetInterface() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-1", 32.0);
        FahrenheitToCelsiusAdapter adapter = new FahrenheitToCelsiusAdapter(legacy);

        assertThat(adapter).isInstanceOf(TemperatureSensor.class);
    }

    @Test
    void getTemperature_convertsFreezingPoint() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-1", 32.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        double celsius = adapter.getTemperature();

        assertThat(celsius).isCloseTo(0.0, within(0.01));
    }

    @Test
    void getTemperature_convertsBoilingPoint() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-2", 212.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        double celsius = adapter.getTemperature();

        assertThat(celsius).isCloseTo(100.0, within(0.01));
    }

    @Test
    void getTemperature_convertsRoomTemperature() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-3", 68.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        double celsius = adapter.getTemperature();

        assertThat(celsius).isCloseTo(20.0, within(0.01));
    }

    @Test
    void getTemperature_convertsNegativeTemperature() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-4", -40.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        double celsius = adapter.getTemperature();

        assertThat(celsius).isCloseTo(-40.0, within(0.01));
    }

    @Test
    void getSensorId_delegatesToAdaptee() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("LEGACY-123", 75.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        String sensorId = adapter.getSensorId();

        assertThat(sensorId).isEqualTo("LEGACY-123");
    }

    @Test
    void adapter_reflectsAdapteeChanges() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("sensor-5", 32.0);
        TemperatureSensor adapter = new FahrenheitToCelsiusAdapter(legacy);

        assertThat(adapter.getTemperature()).isCloseTo(0.0, within(0.01));

        legacy.setTemperature(212.0);

        assertThat(adapter.getTemperature()).isCloseTo(100.0, within(0.01));
    }

    @Test
    void multipleAdapters_canWrapSameAdaptee() {
        LegacyFahrenheitSensor legacy = new LegacyFahrenheitSensor("shared-sensor", 77.0);

        TemperatureSensor adapter1 = new FahrenheitToCelsiusAdapter(legacy);
        TemperatureSensor adapter2 = new FahrenheitToCelsiusAdapter(legacy);

        double temp1 = adapter1.getTemperature();
        double temp2 = adapter2.getTemperature();

        assertThat(temp1).isCloseTo(25.0, within(0.1));
        assertThat(temp2).isCloseTo(25.0, within(0.1));
        assertThat(temp1).isEqualTo(temp2);
    }
}
