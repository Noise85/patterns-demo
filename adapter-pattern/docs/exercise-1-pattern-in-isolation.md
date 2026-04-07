# Exercise 1: Pattern in Isolation

## Title
Temperature Sensor Adapter

## Learning Objectives

- Understand interface incompatibility problem
- Implement object adapter pattern
- Translate method calls between interfaces
- Wrap existing classes without modification
- Practice composition over inheritance

## Scenario

Your modern smart home system expects all temperature sensors to implement `TemperatureSensor` interface returning Celsius values. You have legacy Fahrenheit sensors (`LegacyFahrenheitSensor`) that can't be modified. You need adapters to make legacy sensors work with the new system.

## Functional Requirements

### 1. Target Interface (`TemperatureSensor`)

```java
double getTemperature();  // Returns temperature in Celsius
String getSensorId();
```

### 2. Adaptee Class (`LegacyFahrenheitSensor`)

Already implemented (do not modify):
- `double readFahrenheit()` - Returns temperature in Fahrenheit
- `String getDeviceId()` - Returns sensor ID

### 3. Adapter (`FahrenheitToCelsiusAdapter`)

- Implements `TemperatureSensor`
- Wraps `LegacyFahrenheitSensor`
- Converts Fahrenheit to Celsius: `(F - 32) × 5/9`
- Delegates sensor ID retrieval

## Non-Functional Expectations

- Adapter doesn't modify adaptee
- Clean separation of concerns
- Accurate temperature conversion

## Constraints

- Use object adapter (composition, not inheritance)
- Do not modify `LegacyFahrenheitSensor`
- Adapter must implement `TemperatureSensor`

## Starter Code Location

`src/main/java/com/patterns/adapter/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- Adapter holds an instance of `LegacyFahrenheitSensor`
- In `getTemperature()`, call `adaptee.readFahrenheit()` and convert
- Formula: celsius = (fahrenheit - 32) × 5/9
- Delegate `getSensorId()` to `adaptee.getDeviceId()`
</details>
