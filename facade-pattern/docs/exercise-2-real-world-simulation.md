# Exercise 2: Real-World Simulation

## Objective

Build a production-grade order processing facade for an e-commerce platform that coordinates multiple complex subsystems: inventory management, payment processing, shipping, and customer notifications.

## Business Context

You're building the checkout system for an online retailer. When a customer places an order, many things must happen in the correct sequence:

1. **Inventory check**: Verify all items in stock
2. **Inventory reservation**: Reserve items to prevent overselling
3. **Payment processing**: Charge customer's payment method
4. **Order creation**: Create order record in database
5. **Shipping label generation**: Create shipping labels
6. **Shipment scheduling**: Schedule pickup with carrier
7. **Customer notification**: Send order confirmation email
8. **Inventory update**: Deduct from available stock

Each step involves a different subsystem with its own complexity. Clients shouldn't need to know all these details—they should just call `processOrder()`.

## Domain Model

### Order Request
- `orderId` (unique identifier)
- `customerId`
- `items` (list of OrderItem: productId, quantity, price)
- `shippingAddress`
- `paymentMethod`

### Order Result
- `success` (boolean)
- `orderId`
- `trackingNumber` (if successful)
- `estimatedDelivery` (date)
- `message` (success/failure details)

### OrderItem
- `productId`
- `quantity`
- `unitPrice` (in cents)

## Your Tasks

### 1. Implement Subsystem Components

Design and implement the following subsystems:

#### InventoryService
- `checkAvailability(String productId, int quantity)` → boolean
- `reserveItems(String orderId, List<OrderItem> items)` → ReservationId
- `commitReservation(String reservationId)` → void (deduct from stock)
- `releaseReservation(String reservationId)` → void (rollback)

Track inventory levels per product. Throw `InsufficientStockException` when stock unavailable.

#### PaymentService
- `authorizePayment(String customerId, PaymentMethod method, long amountCents)` → AuthorizationId
- `capturePayment(String authorizationId)` → TransactionId
- `refundPayment(String transactionId, long amountCents)` → RefundId

Simulate payment gateway interaction. Throw `PaymentFailedException` for declined payments (e.g., amounts ending in 13).

#### ShippingService
- `calculateShippingCost(List<OrderItem> items, Address address)` → long (cents)
- `generateLabel(String orderId, Address address, List<OrderItem> items)` → ShippingLabel
- `schedulePickup(ShippingLabel label)` → PickupConfirmation

Generate tracking numbers and estimated delivery dates.

#### OrderRepository
- `createOrder(OrderRequest request, String transactionId, String trackingNumber)` → Order
- `updateOrderStatus(String orderId, OrderStatus status)` → void
- `getOrder(String orderId)` → Order

Persist order data. Simulate database operations.

#### NotificationService
- `sendOrderConfirmation(String customerId, Order order)` → void
- `sendShippingNotification(String customerId, String trackingNumber)` → void
- `sendFailureNotification(String customerId, String orderId, String reason)` → void

Simulate sending emails/SMS.

### 2. Create the Facade

Create `OrderProcessingFacade`:
- Constructor accepts all subsystem services
- `processOrder(OrderRequest request)` → `OrderResult`

The facade should:
1. Orchestrate all subsystems in the correct sequence
2. Handle transactional consistency (rollback on failures)
3. Return comprehensive result with all relevant information
4. Ensure idempotency (processing same order twice is safe)

### 3. Error Handling & Rollback

If any step fails, the facade must rollback previously completed steps:
- Payment authorized but shipping failed? → Refund payment
- Items reserved but payment failed? → Release reservation
- Order halfway through? → Send failure notification to customer

### 4. Design Considerations

**Transactional Consistency**:
- Use compensation pattern for rollback
- Each subsystem should support undo operations
- Track which steps completed successfully

**Idempotency**:
- Same orderId shouldn't be processed twice
- Cache successful results
- Safe retry on transient failures

**Error Reporting**:
- Clear failure messages indicating which step failed
- Include details for customer support troubleshooting
- Log all subsystem interactions

## Example Usage

```java
// Initialize subsystems
InventoryService inventory = new InventoryService();
PaymentService payment = new PaymentService();
ShippingService shipping = new ShippingService();
OrderRepository orderRepo = new OrderRepository();
NotificationService notifications = new NotificationService();

// Create facade
OrderProcessingFacade facade = new OrderProcessingFacade(
    inventory, payment, shipping, orderRepo, notifications
);

// Process order (facade hides all complexity)
OrderRequest request = new OrderRequest(
    "ORD-1001",
    "customer-456",
    List.of(
        new OrderItem("PROD-123", 2, 2999),
        new OrderItem("PROD-456", 1, 4999)
    ),
    shippingAddress,
    paymentMethod
);

OrderResult result = facade.processOrder(request);

if (result.isSuccess()) {
    System.out.println("Order placed! Tracking: " + result.getTrackingNumber());
} else {
    System.out.println("Order failed: " + result.getMessage());
}
```

## Testing Strategy

Your implementation must handle:

1. **Happy path**: All subsystems succeed, order processed completely
2. **Inventory failure**: Out of stock → reject early, no cleanup needed
3. **Payment failure**: Reserved items → rollback reservation
4. **Shipping failure**: Payment captured → refund payment, release items
5. **Partial failures**: Verify correct compensation/rollback
6. **Idempotency**: Duplicate order ID → return cached result
7. **Multiple orders**: Process different orders independently
8. **Concurrent operations**: Thread-safe inventory reservation

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] Facade correctly orchestrates subsystem calls
- [ ] Failed operations trigger proper rollback
- [ ] Idempotency prevents duplicate processing
- [ ] Notifications sent for both success and failure cases
- [ ] Clear separation between facade and subsystems
- [ ] Production-quality error handling

## Time Estimate

**3-4 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **Saga pattern**: Implement distributed transaction coordination
2. **Retry logic**: Retry transient failures with exponential backoff
3. **Partial fulfillment**: Allow shipping available items, backorder rest
4. **Audit trail**: Log all subsystem interactions for debugging
5. **Performance optimization**: Parallelize independent operations (inventory check + shipping cost)

## Hints

- Start with subsystem interfaces, then implement simple versions
- Build the facade incrementally: happy path first, then error handling
- Use try-catch blocks to implement compensation logic
- Consider using a state machine to track order processing stages
- Keep facade thin: subsystems do the work, facade orchestrates
- Test each subsystem independently before integration
- Think about transaction boundaries and consistency guarantees
