# Exercise 2: Real-World Simulation - E-Commerce Order Processing

## Objective

Build a production-grade e-commerce order processing system using the Visitor pattern. You'll implement multiple visitors for validation, pricing, invoice generation, and analytics.

## Scenario

You're working on an enterprise e-commerce platform. Orders have complex structures:
- **LineItems**: Products with quantity and unit price
- **DiscountCoupons**: Percentage-based or fixed-amount discounts
- **ShippingInfo**: Shipping method with cost calculation
- **GiftWrap**: Optional gift wrapping service

You need to perform various operations on orders:
1. **Validation**: Check inventory, discount validity, shipping address
2. **Price Calculation**: Compute subtotal, discounts, taxes, shipping, total
3. **Invoice Generation**: Create formatted invoice with all details
4. **Analytics**: Collect metrics (revenue, popular products, discount usage)

## Requirements

### 1. Order Component Hierarchy

```java
interface OrderComponent {
    void accept(OrderVisitor visitor);
}

class LineItem implements OrderComponent {
    - productId: String
    - productName: String
    - quantity: int
    - unitPrice: BigDecimal
}

class DiscountCoupon implements OrderComponent {
    - couponCode: String
    - discountType: DiscountType (PERCENTAGE, FIXED_AMOUNT)
    - discountValue: BigDecimal
    - minimumOrderAmount: BigDecimal
}

class ShippingInfo implements OrderComponent {
    - shippingMethod: ShippingMethod (STANDARD, EXPRESS, OVERNIGHT)
    - address: String
    - city: String
    - postalCode: String
}

class GiftWrap implements OrderComponent {
    - message: String
    - wrapStyle: String
    - price: BigDecimal
}

class Order implements OrderComponent {
    - orderId: String
    - customerId: String
    - components: List<OrderComponent>
    
    + addComponent(OrderComponent): void
}
```

### 2. Visitor Interface

```java
interface OrderVisitor {
    void visitOrder(Order order);
    void visitLineItem(LineItem item);
    void visitDiscountCoupon(DiscountCoupon coupon);
    void visitShippingInfo(ShippingInfo shipping);
    void visitGiftWrap(GiftWrap giftWrap);
}
```

### 3. Concrete Visitors

#### OrderValidationVisitor
- Validates all components in the order
- Checks:
  - At least one line item exists
  - All quantities are positive
  - Shipping info is complete (address, city, postal code non-empty)
  - Discount meets minimum order amount requirement
- Collects validation errors in a list
- Methods:
  - `boolean isValid()`: Returns true if no errors
  - `List<String> getErrors()`: Returns all validation errors

#### PriceCalculatorVisitor
- Calculates complete order pricing
- Computes:
  - Subtotal (sum of all line items)
  - Discount amount (applied to subtotal)
  - Shipping cost (based on method)
  - Gift wrap cost
  - Tax (applied after discounts, before shipping)
  - Grand total
- Methods:
  - `BigDecimal getSubtotal()`
  - `BigDecimal getDiscountAmount()`
  - `BigDecimal getTaxAmount()` (use 8% tax rate)
  - `BigDecimal getShippingCost()`
  - `BigDecimal getGiftWrapCost()`
  - `BigDecimal getGrandTotal()`

#### InvoiceGeneratorVisitor
- Generates formatted invoice text
- Includes:
  - Order header (order ID, customer ID)
  - Line items with quantity × price
  - Discount details (if present)
  - Subtotal
  - Tax breakdown
  - Shipping and gift wrap charges
  - Grand total
- Method:
  - `String getInvoice()`: Returns formatted invoice

#### OrderAnalyticsVisitor
- Collects business metrics
- Tracks:
  - Total items count
  - Unique products count
  - Most expensive item
  - Discount usage (yes/no)
  - Gift wrap usage (yes/no)
  - Average item price
- Methods:
  - `int getTotalItemCount()`
  - `int getUniqueProductCount()`
  - `BigDecimal getMostExpensiveItemPrice()`
  - `boolean hasDiscount()`
  - `boolean hasGiftWrap()`

## Implementation Guidelines

### Order Component Implementation

```java
public class LineItem implements OrderComponent {
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Call visitor.visitLineItem(this)
    }
}

public class Order implements OrderComponent {
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO:
        // 1. Call visitor.visitOrder(this)
        // 2. Visit all components in order
    }
}
```

### Price Calculator Logic

```java
public class PriceCalculatorVisitor implements OrderVisitor {
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal shippingCost = BigDecimal.ZERO;
    private BigDecimal giftWrapCost = BigDecimal.ZERO;
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: Calculate line total (quantity × unit price)
        // Add to subtotal
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO: Calculate discount based on type
        // PERCENTAGE: subtotal × (discountValue / 100)
        // FIXED_AMOUNT: min(discountValue, subtotal)
    }
    
    @Override
    public void visitShippingInfo(ShippingInfo shipping) {
        // TODO: Determine shipping cost based on method
        // STANDARD: $5.00
        // EXPRESS: $12.00
        // OVERNIGHT: $25.00
    }
    
    public BigDecimal getTaxAmount() {
        // TODO: Calculate tax on (subtotal - discount)
        // Tax rate: 8%
    }
    
    public BigDecimal getGrandTotal() {
        // TODO: Sum all components
        // subtotal - discount + tax + shipping + giftWrap
    }
}
```

### Validation Visitor Logic

```java
public class OrderValidationVisitor implements OrderVisitor {
    private final List<String> errors;
    private int lineItemCount;
    private BigDecimal currentSubtotal;
    private BigDecimal minimumForDiscount;
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: 
        // 1. Check quantity > 0
        // 2. Increment line item count
        // 3. Add to current subtotal for discount validation
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO:
        // Check if subtotal meets minimumOrderAmount
        // Add error if not met
    }
    
    @Override
    public void visitOrder(Order order) {
        // TODO: Reset state for new order validation
    }
}
```

## Example Usage

```java
// Build complex order
Order order = new Order("ORD-12345", "CUST-789");

order.addComponent(new LineItem("PROD-1", "Laptop", 1, new BigDecimal("999.99")));
order.addComponent(new LineItem("PROD-2", "Mouse", 2, new BigDecimal("29.99")));
order.addComponent(new DiscountCoupon("SAVE20", DiscountType.PERCENTAGE, 
                                       new BigDecimal("20"), new BigDecimal("500")));
order.addComponent(new ShippingInfo(ShippingMethod.EXPRESS, "123 Main St", 
                                    "Springfield", "12345"));
order.addComponent(new GiftWrap("Happy Birthday!", "Premium", new BigDecimal("9.99")));

// Validate order
OrderValidationVisitor validator = new OrderValidationVisitor();
order.accept(validator);
if (!validator.isValid()) {
    System.out.println("Validation errors: " + validator.getErrors());
}

// Calculate pricing
PriceCalculatorVisitor priceCalc = new PriceCalculatorVisitor();
order.accept(priceCalc);
System.out.println("Subtotal: $" + priceCalc.getSubtotal());
System.out.println("Discount: -$" + priceCalc.getDiscountAmount());
System.out.println("Tax: $" + priceCalc.getTaxAmount());
System.out.println("Shipping: $" + priceCalc.getShippingCost());
System.out.println("Total: $" + priceCalc.getGrandTotal());

// Generate invoice
InvoiceGeneratorVisitor invoiceGen = new InvoiceGeneratorVisitor();
order.accept(invoiceGen);
System.out.println(invoiceGen.getInvoice());

// Collect analytics
OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
order.accept(analytics);
System.out.println("Total items: " + analytics.getTotalItemCount());
System.out.println("Has discount: " + analytics.hasDiscount());
```

## Key Learning Points

1. **Multiple Visitors**: Same structure, different operations
2. **Stateful Visitors**: Accumulating data during traversal
3. **Validation Pattern**: Collecting errors vs. throwing exceptions
4. **Financial Calculations**: Using BigDecimal correctly
5. **Composite Pattern Integration**: Order contains other components

## Testing

The test suite verifies:
- ✅ Validation detects missing line items
- ✅ Validation checks quantity constraints
- ✅ Validation enforces discount minimum order amount
- ✅ Price calculation computes correct subtotal
- ✅ Percentage discount applied correctly
- ✅ Fixed-amount discount capped at subtotal
- ✅ Tax calculated on discounted amount
- ✅ Shipping cost determined by method
- ✅ Grand total includes all components
- ✅ Invoice contains all required sections
- ✅ Analytics tracks items and products correctly
- ✅ Multiple visitors work on same order

## Design Considerations

### Visitor State Management
Each visitor maintains its own state. For thread safety:
- Create new visitor instance per operation
- Don't share visitor instances across threads

### Discount Validation Timing
Discount minimum order amount must be checked **after** visiting all line items. This requires:
- Visiting order components in correct sequence
- Or post-processing in `visitOrder()`

### Invoice Formatting
Invoice should be human-readable:
```
=====================================
INVOICE
=====================================
Order ID: ORD-12345
Customer ID: CUST-789

LINE ITEMS:
- Laptop (x1) @ $999.99 = $999.99
- Mouse (x2) @ $29.99 = $59.98

Subtotal: $1,059.97
Discount (SAVE20 - 20%): -$211.99
Tax (8%): $67.84
Shipping (EXPRESS): $12.00
Gift Wrap: $9.99
=====================================
TOTAL: $937.81
=====================================
```

## Challenge Questions

1. How would you add a new component type (e.g., `WarrantyExtension`) without breaking existing visitors?
2. How would you implement conditional discounts (e.g., "buy 2 get 1 free")?
3. Could you combine multiple visitors into a pipeline? How?
4. How would you handle circular dependencies (e.g., discount depends on shipping, shipping depends on discount)?

## Common Pitfalls

- ❌ Calculating discount before visiting all line items
- ❌ Applying tax to wrong base amount (include vs exclude shipping)
- ❌ Not resetting visitor state between uses
- ❌ Mutating order components during visitation
- ❌ Forgetting to visit nested components in Order
