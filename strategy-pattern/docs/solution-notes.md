# Solution Notes

This document provides high-level guidance for both exercises WITHOUT giving away the complete solution. Use these notes only if you're stuck.

---

## Exercise 1: Pattern in Isolation

### Key Insights

The Strategy pattern is fundamentally about **delegation** and **composition over inheritance**.

### Architecture Overview

```
PaymentContext ──────uses────> PaymentStrategy (interface)
                                      ↑
                                      │
                         ┌────────────┼────────────┐
                         │            │            │
              CreditCardPayment  PayPalPayment  BankTransferPayment
```

### Implementation Approach

**Step 1: Define the Strategy Interface**
- One method that represents the common operation
- Takes necessary parameters (amount)
- Returns a result (confirmation string)

**Step 2: Implement Concrete Strategies**
- Each strategy is a separate class
- Implement the interface method
- Encapsulate the specific algorithm/behavior
- Keep it stateless

**Step 3: Create the Context**
- Holds a reference to a strategy (composition, not inheritance)
- Provides a method to set/change the strategy
- Delegates the operation to the current strategy
- NO conditional logic (no if/else, no switch)

### Common Pitfalls

❌ **Don't** put strategy selection logic in the context
❌ **Don't** use if/else to select which strategy to call
❌ **Don't** make strategies extend a base class (use interface)
✅ **Do** make strategies stateless and independent
✅ **Do** use simple delegation from context to strategy
✅ **Do** allow strategy to be set via constructor or setter

### Testing Strategy

1. Test each strategy independently first
2. Verify the context delegates correctly
3. Test switching strategies at runtime
4. Make sure no conditional logic crept into the context

---

## Exercise 2: Real-World Simulation

### Key Insights

This exercise is about **applying Strategy pattern to a complex domain** with real-world constraints:

- Multiple strategies with different selection criteria
- Need to choose the "best" strategy when multiple apply
- Separation of concerns: strategy logic vs. selection logic
- Extensibility: adding new strategies shouldn't break existing code

### Architecture Overview

```
PricingContext ────────> PricingEngine ────uses───> PricingStrategy (interface)
                              │                            ↑
                              │                            │
                              │                  ┌─────────┼─────────────┐
                              │                  │         │             │
                              │          StandardPricing  VipMemberPricing  ...
                              │
                              └──uses──> StrategySelector (optional)
```

### Implementation Approach

**Step 1: Domain Models**
- `PricingContext`: All info needed to make pricing decisions
  - Order details (total, item count)
  - Customer info (type, is first-time buyer)
  - Temporal info (order date, active promotions)
- `PricingResult`: Output of pricing calculation
  - Original price
  - Final price
  - Strategy used (name)
  - Discount percentage/amount
- `Order`: Simple data holder or record

**Step 2: Strategy Interface**
```java
interface PricingStrategy {
    PricingResult calculatePrice(PricingContext context);
}
```

**Step 3: Concrete Strategies**
Each strategy:
- Checks if it applies (or assumes engine already filtered)
- Calculates discount based on its rules
- Returns a PricingResult with all details
- Is stateless and thread-safe

**Step 4: Strategy Selection**
The `PricingEngine` needs to:
- Determine which strategies are eligible
- Apply them (or just the best one)
- Return the result

**Two approaches**:

**Approach A: Engine selects strategies**
```java
public PricingResult calculatePrice(PricingContext context) {
    List<PricingStrategy> eligibleStrategies = findEligibleStrategies(context);
    return applyBestStrategy(eligibleStrategies, context);
}
```

**Approach B: Strategies self-select**
```java
interface PricingStrategy {
    boolean isApplicable(PricingContext context);
    PricingResult calculatePrice(PricingContext context);
}
```
Then engine iterates through all strategies, filters by `isApplicable()`, and picks the best.

**Both are valid.** Choose based on your preference.

### Design Decisions

**How to handle "best" discount?**
- Calculate results from all eligible strategies
- Compare final prices
- Return the lowest final price (best for customer)

**Where to keep available strategies?**
- Option 1: Engine holds a list of all strategies (passed in constructor)
- Option 2: Use a registry or factory to get strategies
- Option 3: Engine creates strategies on-demand (simpler for this exercise)

**Should strategies know about each other?**
- NO - each strategy should be independent
- The engine orchestrates, strategies just calculate

### Common Pitfalls

❌ **Don't** hardcode strategy selection with if/else for each strategy type (defeats the purpose)
❌ **Don't** make strategies stateful
❌ **Don't** let strategies call other strategies directly
❌ **Don't** forget edge cases (zero amounts, no applicable strategy)
✅ **Do** separate selection logic from strategy logic
✅ **Do** make it easy to add new strategies
✅ **Do** include strategy name in results for audit trail
✅ **Do** preserve original price in results

### Testing Strategy

1. **Unit test each strategy in isolation**
   - Create a context that matches the strategy's criteria
   - Verify correct discount calculation
   - Test edge cases for that strategy

2. **Test strategy selection**
   - Verify the engine picks the right strategy for given context
   - Test cases where multiple strategies apply
   - Test case where NO strategy applies (should use standard pricing)

3. **Integration tests**
   - End-to-end scenarios with realistic data
   - Verify the "best price" logic works correctly

### Extensibility Checkpoint

After implementing, ask yourself:

1. Can I add a new pricing strategy without modifying existing code? (Open/Closed Principle)
2. Can I test each strategy independently?
3. Is the strategy selection logic separate from strategy implementation?
4. Could I easily change the "best price" rule to "highest margin" instead?

If yes to all, you've successfully applied the Strategy pattern! 🎉

### Stretch Goal Hints

**Strategy Composition**:
- Create a `ComposedPricingStrategy` that holds multiple strategies
- Apply them in sequence: `price = strategy1.apply(strategy2.apply(strategy3.apply(original)))`
- Be careful about how discounts compound

**Validation**:
- Add validators that run before or after strategies
- Ensure final price >= 0
- Ensure discount <= max allowed discount
- Could use Chain of Responsibility pattern for validation pipeline

**Strategy Registry**:
- Create a `StrategyRegistry` class
- Register strategies by name: `registry.register("VIP", new VipMemberPricing())`
- Look up by name: `registry.get("VIP")`
- Useful for configuration-driven strategy selection

---

## General Strategy Pattern Lessons

### When You See This Pattern Working Well

✅ Adding new strategies is just creating a new class, no existing code changes
✅ Testing is easy - mock the strategy interface
✅ No complex conditional logic in the context/engine
✅ Each strategy is focused and follows Single Responsibility Principle
✅ Runtime strategy switching is seamless

### Red Flags You're Not Using It Correctly

🚩 If/else or switch statements to pick strategies
🚩 Strategies that know about other strategies
🚩 Context class has business logic instead of just delegating
🚩 Hard to add a new strategy without modifying multiple classes
🚩 Strategies are tightly coupled to specific data structures

---

## Further Reading

- [Refactoring.Guru: Strategy Pattern](https://refactoring.guru/design-patterns/strategy)
- [Strategy Pattern vs State Pattern](https://refactoring.guru/design-patterns/state)
- [Strategy + Factory patterns combined](https://refactoring.guru/design-patterns/factory-method)

---

**Remember**: The goal is to learn by doing. These notes guide you, but the best learning comes from struggling a bit, making mistakes, and iterating on your solution. Good luck! 🚀
