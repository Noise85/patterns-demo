# Solution Notes

## Exercise 1: Temperature Sensor Adapter

### Architecture

```
Client → TemperatureSensor (interface)
                ↑
                |
    FahrenheitToCelsiusAdapter
                |
    LegacyFahrenheitSensor (adaptee)
```

### Key Points

- **Adapter** implements the target interface
- **Composition** over inheritance: adapter wraps adaptee
- **Translation**: converts Fahrenheit → Celsius
- **Delegation**: passesthrough sensor ID

### Implementation Pattern

```java
public class FahrenheitToCelsiusAdapter implements TemperatureSensor {
    private final LegacyFahrenheitSensor adaptee;
    
    public FahrenheitToCelsiusAdapter(LegacyFahrenheitSensor adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public double getTemperature() {
        double fahrenheit = adaptee.readFahrenheit();
        return (fahrenheit - 32) * 5.0 / 9.0;  // Convert
    }
    
    @Override
    public String getSensorId() {
        return adaptee.getDeviceId();  // Delegate
    }
}
```

## Exercise 2: Payment Gateway Adapters

### Architecture

```
Client → PaymentProcessor (interface)
              ↑                ↑
              |                |
    StripePaymentAdapter    PayPalPaymentAdapter
              |                |
        StripeGateway      PayPalGateway
```

### Key Points

- Multiple adapters for different third-party services
- **Model translation**: app models ↔ vendor models
- **Unit conversion**: dollars ↔ cents (Stripe)
- **Format conversion**: double ↔ string (PayPal)
- **Error mapping**: vendor errors → standard results

### Stripe Adapter Pattern

```java
public class StripePaymentAdapter implements PaymentProcessor {
    private final StripeGateway gateway;
    
    @Override
    public PaymentResult charge(PaymentRequest request) {
        // 1. Convert app model to Stripe model
        StripeChargeRequest stripeReq = new StripeChargeRequest();
        stripeReq.setAmountCents((int) (request.getAmount() * 100));
        stripeReq.setCurrency(request.getCurrency());
        stripeReq.setCardToken(request.getCardToken());
        
        // 2. Call adaptee
        StripeCharge charge = gateway.createCharge(stripeReq);
        
        // 3. Convert Stripe response to app model
        return new PaymentResult(
            charge.isSuccessful(),
            charge.getChargeId(),
            charge.getMessage()
        );
    }
}
```

### Common Adapter Responsibilities

1. **Model translation**: Map between domain models
2. **Unit conversion**: Handle different units (cents/dollars, etc.)
3. **Format conversion**: String ↔ numeric, date formats
4. **Error handling**: Translate exceptions/error codes
5. **Default values**: Provide sensible defaults for optional fields

### Object vs Class Adapter

| Aspect | Object Adapter | Class Adapter |
|--------|---------------|---------------|
| Mechanism | Composition | Multiple inheritance |
| Flexibility | High (runtime swap) | Low (compile-time) |
| Java Support | ✅ | ❌ (single inheritance) |
| Recommended | **Yes** | No (use object) |
