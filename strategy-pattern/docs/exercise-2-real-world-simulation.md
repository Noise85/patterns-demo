# Exercise 2: Real-World Simulation

## Title
Dynamic Pricing Engine for E-Commerce Platform

## Learning Objectives

By completing this exercise, you will:

- Apply the Strategy pattern to a realistic business problem
- Design for extensibility and changing requirements
- Handle multiple pricing strategies with different complexities
- Combine strategies when business logic requires it
- Make architectural decisions about strategy selection and composition
- Write production-quality code with proper separation of concerns
- Consider edge cases, validation, and maintainability

## Scenario

You are a senior engineer at an e-commerce platform. The business team wants a flexible pricing engine that can apply different pricing strategies based on:

- **Customer segments**: Regular customers, VIP members, corporate accounts
- **Time-based promotions**: Black Friday, seasonal sales, flash sales
- **Order characteristics**: Bulk discounts, first-time buyer discounts
- **Product categories**: Clearance items, premium products

The current pricing code is a mess of nested if/else statements that's hard to test and extend. Your task is to refactor it using the Strategy pattern.

The system must support:
- Multiple pricing strategies that can be applied to orders
- Strategy selection based on customer context
- Logging which strategy was applied (for analytics)
- Easy addition of new strategies without modifying existing code

## Functional Requirements

### 1. Define Domain Models

Create classes to represent:
- `Order` - Contains order total, customer type, order date, item count
- `PricingContext` - Contains all information needed for pricing decisions
- `PricingResult` - Final price, discount applied, strategy name used

### 2. Implement Pricing Strategy Interface

Define `PricingStrategy` interface with:
- Method to calculate final price given a `PricingContext`
- Returns a `PricingResult`

### 3. Implement Multiple Concrete Strategies

At minimum, implement these strategies:

- **StandardPricing**: No discounts, return original price
- **VipMemberPricing**: 15% discount for VIP members
- **BulkDiscountPricing**: 
  - 5% off for 10-49 items
  - 10% off for 50-99 items
  - 15% off for 100+ items
- **SeasonalPromotionPricing**: 20% off during promotional periods
- **FirstTimeBuyerPricing**: 10% discount for first-time buyers

### 4. Implement Pricing Engine

Create `PricingEngine` class that:
- Accepts a `PricingContext`
- Selects the appropriate strategy (or strategies)
- Applies the strategy
- Returns the `PricingResult`
- Handles the case where multiple strategies could apply (business rule: use the best discount for customer)

### 5. Strategy Selection Logic

The engine should select strategies based on:
- Customer type (VIP gets VipMemberPricing)
- Order characteristics (high quantity gets BulkDiscountPricing)
- Promotional periods (active promotion gets SeasonalPromotionPricing)
- Customer history (first purchase gets FirstTimeBuyerPricing)

**Important**: When multiple strategies apply, use the one that gives the best price for the customer.

## Non-Functional Expectations

- **Clean Architecture**: Separate concerns - domain models, strategies, selection logic
- **Extensibility**: Adding a new strategy should not require changing existing strategies or core engine logic
- **Testability**: Each strategy should be easily testable in isolation
- **Type Safety**: Use Java's type system effectively
- **Immutability**: Prefer immutable domain objects where appropriate
- **Readability**: Code should be self-documenting with clear naming

## Constraints

- No frameworks allowed (pure Java)
- Strategies must be stateless (thread-safe)
- No static methods for business logic
- Pricing calculations must handle edge cases (zero amounts, negative discounts, etc.)
- Must preserve original price in the result for audit purposes
- All monetary values should be `double` (in a real system, use `BigDecimal`, but keep it simple here)

## Starter Code Location

- Domain models: `src/main/java/com/patterns/strategy/simulation/model/`
- Strategies: `src/main/java/com/patterns/strategy/simulation/strategy/`
- Engine: `src/main/java/com/patterns/strategy/simulation/PricingEngine.java`

Look for `TODO` comments in the code.

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

The tests verify:
- Each pricing strategy calculates correctly
- The pricing engine selects the right strategy based on context
- When multiple strategies apply, the best one is chosen
- Edge cases are handled properly (zero amounts, etc.)
- The result includes strategy name for audit trail
- Original price is preserved in results

## Stretch Goals (Optional)

1. **Strategy Composition**: Implement a `CombinedPricingStrategy` that can chain multiple strategies (e.g., apply VIP discount THEN bulk discount)

2. **Strategy Caching**: Add a mechanism to cache strategy instances instead of creating new ones each time

3. **Validation**: Add validation to ensure:
   - Final price never goes negative
   - Discounts don't exceed a maximum threshold (e.g., 50%)
   - Pricing context has all required fields

4. **Strategy Registry**: Implement a registry pattern to dynamically register and lookup strategies by name

5. **Configurable Rules**: Move strategy selection rules to a configuration object instead of hardcoding them

6. **Audit Trail**: Add a list of "applied discounts" to the result showing all eligible strategies and why one was chosen

## Hints

<details>
<summary>Click to reveal hints</summary>

**Architecture**:
- Keep domain models simple - just data holders
- Strategies should only implement pricing logic, not selection logic
- The engine is responsible for strategy selection
- Consider a separate `StrategySelector` class if selection logic gets complex

**Strategy Selection**:
- You could iterate through all available strategies and pick the best one
- Or you could use a priority/rule-based selector
- Consider what happens when NO strategy applies (fallback to standard pricing)

**Testing**:
- Test each strategy in isolation first
- Then test engine's strategy selection logic
- Finally test end-to-end scenarios

**Edge Cases**:
- What if order total is $0?
- What if discount calculation results in negative price?
- What if multiple strategies have the same discount?

**Clean Code**:
- Use builder pattern for complex objects if needed
- Avoid primitive obsession - wrap values in domain types if beneficial
- Use Java records for immutable data holders (Java 21 feature)

</details>

## Real-World Considerations

In a production system, you'd also need to consider:

- **Database persistence**: Storing which strategy was applied to each order
- **A/B testing**: Randomly assigning strategies to measure effectiveness
- **External configuration**: Loading strategy rules from a database or config file
- **Monitoring**: Tracking which strategies are used most often
- **Circuit breakers**: Handling errors in strategy execution gracefully
- **Decimal precision**: Using `BigDecimal` instead of `double` for money

For this exercise, keep it focused on the Strategy pattern itself.

## What You'll Learn

This exercise simulates a real-world refactoring challenge. You'll learn:

- How to identify when Strategy pattern is the right solution
- How to migrate from conditional logic to strategies
- How to design for extensibility in business logic
- How to structure code for a complex domain
- How to make architectural tradeoffs

Good luck! 🚀
