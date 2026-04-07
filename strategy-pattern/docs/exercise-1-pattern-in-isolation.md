# Exercise 1: Pattern in Isolation

## Title
Simple Payment Method Selection

## Learning Objectives

By completing this exercise, you will:

- Understand the core structure of the Strategy pattern
- Define a common interface for a family of algorithms
- Implement multiple concrete strategies
- Use a context class to delegate behavior to strategies
- Switch between strategies at runtime
- See how Strategy eliminates conditional logic

## Scenario

You are building a simple payment processing module for an online store. Customers can choose from three payment methods:

1. **Credit Card** - Charges the card immediately
2. **PayPal** - Redirects to PayPal for authorization
3. **Bank Transfer** - Generates bank details for manual transfer

Each payment method has a different processing flow, but they all share a common interface: they process a payment amount and return a confirmation message.

## Functional Requirements

Implement the following:

1. A `PaymentStrategy` interface with a method `String processPayment(double amount)`

2. Three concrete strategy implementations:
   - `CreditCardPayment` - Returns: `"Processed $X via Credit Card"`
   - `PayPalPayment` - Returns: `"Processed $X via PayPal"`
   - `BankTransferPayment` - Returns: `"Processed $X via Bank Transfer"`

3. A `PaymentContext` class that:
   - Holds a reference to a `PaymentStrategy`
   - Allows setting/changing the strategy
   - Delegates payment processing to the current strategy

## Non-Functional Expectations

- Keep it simple - this is about understanding the pattern mechanics
- Strategy implementations should be stateless
- No need for validation or error handling in this exercise
- Focus on the delegation pattern: context → strategy

## Constraints

- Do NOT use if/else or switch statements in the context class
- Each payment method must be a separate class
- All strategies must implement the same interface
- Amount formatting should be simple (e.g., "$100.0" is fine)

## Starter Code Location

- Interface: `src/main/java/com/patterns/strategy/isolation/PaymentStrategy.java`
- Strategies: `src/main/java/com/patterns/strategy/isolation/`
- Context: `src/main/java/com/patterns/strategy/isolation/PaymentContext.java`

Look for `TODO` comments in the code.

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

The tests verify:
- Each strategy processes payments correctly
- The context delegates to the current strategy
- Switching strategies at runtime works correctly
- No conditional logic in the context

## Stretch Goals (Optional)

1. Add a fourth payment method: `CryptocurrencyPayment`
2. Extend strategies to accept additional payment details (card number, PayPal email, etc.)
3. Add a method to get the strategy name/type from the context

## Hints

<details>
<summary>Click to reveal hints</summary>

- The `PaymentStrategy` interface should have one method that takes an amount
- Concrete strategies implement the interface and return a formatted string
- The context should have a field of type `PaymentStrategy`
- Use Java's `String.format()` for consistent formatting
- Focus on composition over inheritance

</details>

## What's Next?

Once you complete this exercise and all tests pass, move on to **Exercise 2: Real-World Simulation** where you'll apply the Strategy pattern to a production-grade pricing engine with much more complexity.
