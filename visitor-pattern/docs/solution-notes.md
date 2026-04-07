# Solution Notes - Visitor Pattern

## Exercise 1: File System Visitor

### Key Implementation Points

#### Double Dispatch Pattern
```java
// In File class
public void accept(FileSystemVisitor visitor) {
    visitor.visitFile(this);  // Pass 'this' to visitor
}

// In Directory class
public void accept(FileSystemVisitor visitor) {
    visitor.visitDirectory(this);
    // Then visit all children
    for (FileSystemElement child : children) {
        child.accept(visitor);
    }
}
```

The magic: `element.accept(visitor)` → `visitor.visitElement(this)` enables compile-time type resolution.

#### Traversal Control
Who controls iteration over directory children?

**Option A - Element controls** (used in this exercise):
```java
public void accept(FileSystemVisitor visitor) {
    visitor.visitDirectory(this);
    for (FileSystemElement child : children) {
        child.accept(visitor);  // Element decides traversal
    }
}
```

**Option B - Visitor controls**:
```java
public void visitDirectory(Directory directory) {
    // Visitor accesses children and decides traversal
    for (FileSystemElement child : directory.getChildren()) {
        child.accept(this);
    }
}
```

Trade-offs:
- **Element control**: Encapsulation (children private), consistent traversal
- **Visitor control**: Flexibility (each visitor can traverse differently)

#### Size Calculator Implementation
```java
private long totalSize = 0;

public void visitFile(File file) {
    totalSize += file.getSize();
}

public void visitDirectory(Directory directory) {
    // Nothing needed - children visited automatically
    // Or track directory metadata here
}
```

#### File Search Implementation
```java
private final List<File> results = new ArrayList<>();

public void visitFile(File file) {
    boolean matches = (searchPattern != null && file.getName().contains(searchPattern))
                   || (extension != null && file.getExtension().equals(extension));
    
    if (matches) {
        results.add(file);
    }
}
```

### Design Decisions

1. **Immutability**: File and Directory are immutable (no setters)
2. **Extension extraction**: Stored explicitly (could be derived from name)
3. **Search patterns**: Simple `contains()` check (could use regex)
4. **Result collection**: New list returned (defensive copy)

---

## Exercise 2: E-Commerce Order Visitor

### Key Implementation Points

#### Order Component Traversal
```java
public class Order implements OrderComponent {
    public void accept(OrderVisitor visitor) {
        visitor.visitOrder(this);
        for (OrderComponent component : components) {
            component.accept(visitor);
        }
    }
}
```

Order visits itself first, then delegates to children.

#### Price Calculator - Discount Logic
```java
public void visitDiscountCoupon(DiscountCoupon coupon) {
    BigDecimal discount;
    
    if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
        // percentage discount
        discount = subtotal.multiply(coupon.getDiscountValue())
                          .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    } else {
        // fixed amount discount (cap at subtotal)
        discount = coupon.getDiscountValue().min(subtotal);
    }
    
    discountAmount = discountAmount.add(discount);
}
```

Key: Always use `RoundingMode` with BigDecimal division!

#### Price Calculator - Tax Calculation
```java
public BigDecimal getTaxAmount() {
    BigDecimal taxableAmount = subtotal.subtract(discountAmount);
    return taxableAmount.multiply(new BigDecimal("0.08"))
                       .setScale(2, RoundingMode.HALF_UP);
}
```

Tax applies to discounted subtotal (not including shipping/gift wrap).

#### Price Calculator - Grand Total
```java
public BigDecimal getGrandTotal() {
    return subtotal
        .subtract(discountAmount)
        .add(getTaxAmount())
        .add(shippingCost)
        .add(giftWrapCost);
}
```

Order matters: discounts reduce base, tax on reduced amount, then add extras.

#### Validation - Discount Minimum Check
```java
public void visitOrder(Order order) {
    // Reset state for new validation
    errors.clear();
    lineItemCount = 0;
    currentSubtotal = BigDecimal.ZERO;
}

public void visitLineItem(LineItem item) {
    if (item.getQuantity() <= 0) {
        errors.add("Line item quantity must be positive");
    }
    lineItemCount++;
    
    BigDecimal lineTotal = item.getUnitPrice()
        .multiply(new BigDecimal(item.getQuantity()));
    currentSubtotal = currentSubtotal.add(lineTotal);
}

public void visitDiscountCoupon(DiscountCoupon coupon) {
    if (currentSubtotal.compareTo(coupon.getMinimumOrderAmount()) < 0) {
        errors.add(String.format(
            "Order subtotal $%s does not meet discount minimum $%s",
            currentSubtotal, coupon.getMinimumOrderAmount()
        ));
    }
}
```

Critical: Validate discount **after** calculating subtotal from all line items. This requires visiting line items before discount, or storing minimum and checking at end.

#### Invoice Generation
```java
public void visitOrder(Order order) {
    invoice.append("=====================================\n");
    invoice.append("INVOICE\n");
    invoice.append("=====================================\n");
    invoice.append(String.format("Order ID: %s\n", order.getOrderId()));
    invoice.append(String.format("Customer ID: %s\n\n", order.getCustomerId()));
    invoice.append("LINE ITEMS:\n");
}

public void visitLineItem(LineItem item) {
    BigDecimal lineTotal = item.getUnitPrice()
        .multiply(new BigDecimal(item.getQuantity()));
    invoice.append(String.format("- %s (x%d) @ $%s = $%s\n",
        item.getProductName(), item.getQuantity(), 
        item.getUnitPrice(), lineTotal));
}
```

Use StringBuilder for efficient string concatenation during traversal.

### Design Decisions

1. **BigDecimal everywhere**: Financial calculations require precision
2. **Rounding mode**: HALF_UP for standard rounding
3. **Scale**: Always 2 decimal places for currency
4. **Validation approach**: Collect all errors (don't fail fast)
5. **Component order**: Order visits itself first, then children in sequence
6. **Visitor reuse**: Stateful visitors should not be reused without reset

### Advanced Patterns

#### Visitor Composition
Chain multiple visitors:
```java
void processOrder(Order order) {
    // Validate first
    OrderValidationVisitor validator = new OrderValidationVisitor();
    order.accept(validator);
    if (!validator.isValid()) {
        throw new ValidationException(validator.getErrors());
    }
    
    // Then calculate price
    PriceCalculatorVisitor priceCalc = new PriceCalculatorVisitor();
    order.accept(priceCalc);
    
    // Then generate invoice
    InvoiceGeneratorVisitor invoiceGen = new InvoiceGeneratorVisitor();
    order.accept(invoiceGen);
}
```

#### Visitor with Parameters
Pass context to visitor:
```java
class ShippingCostVisitor implements OrderVisitor {
    private final String customerTier;  // GOLD, SILVER, BRONZE
    
    public ShippingCostVisitor(String customerTier) {
        this.customerTier = customerTier;
    }
    
    public void visitShippingInfo(ShippingInfo shipping) {
        BigDecimal baseCost = getBaseCost(shipping.getShippingMethod());
        BigDecimal discount = getTierDiscount(customerTier);
        shippingCost = baseCost.multiply(BigDecimal.ONE.subtract(discount));
    }
}
```

### Common Mistakes to Avoid

1. **Forgetting to reset state**: Visitors with mutable state must be reset between uses
2. **Wrong tax calculation**: Tax should apply to `subtotal - discount`, not just subtotal
3. **Order dependency**: Discount validation requires subtotal, so component order matters
4. **BigDecimal comparison**: Use `compareTo()`, not `equals()` (scale differs)
5. **Null handling**: Check for null components, optional fields
6. **Traversal bugs**: Forgetting to visit children in composite components

### Testing Strategy

1. **Unit test each visitor** with minimal structures
2. **Test edge cases**: empty orders, zero discounts, single items
3. **Test complex scenarios**: multiple discounts, all component types
4. **Test visitor independence**: Same order, multiple visitors
5. **Test calculation accuracy**: Verify BigDecimal precision
6. **Test validation**: All error conditions

---

## General Visitor Pattern Insights

### When to Apply
- ✅ Stable object structure, volatile operations
- ✅ Many unrelated operations on complex hierarchy
- ✅ Need to collect data from heterogeneous structures
- ✅ Operations require different types for different elements

### When to Avoid
- ❌ Object structure changes frequently (adding types breaks all visitors)
- ❌ Simple operations (overhead not justified)
- ❌ Can't modify elements to add `accept()`
- ❌ Need to preserve encapsulation strictly

### Alternatives

1. **Pattern Matching** (Java 17+): Switch expressions with type patterns
2. **Instanceof chains**: Simple but not extensible
3. **Strategy pattern**: When operation doesn't depend on element type
4. **Method overloading**: When dispatch based on compile-time types is sufficient

### Performance Considerations

- Virtual method dispatch overhead: minimal (~1 extra method call)
- Memory: Each visitor instance holds state
- Traversal cost: O(n) where n = number of elements
- Optimization: Reuse stateless visitors, cache results

### Modern Java Features

Java 17+ pattern matching can sometimes replace Visitor:
```java
// Instead of visitor
String process(OrderComponent component) {
    return switch(component) {
        case LineItem item -> "Item: " + item.getProductName();
        case DiscountCoupon c -> "Discount: " + c.getCouponCode();
        case ShippingInfo s -> "Ship to: " + s.getCity();
        default -> "Unknown";
    };
}
```

Trade-off: Less ceremony, but operations scattered across codebase.

---

## Further Reading

- **Refactoring Guru - Visitor**: https://refactoring.guru/design-patterns/visitor
- **Martin Fowler - Visitor Replacement**: On when pattern matching replaces Visitor
- **"Design Patterns" (GoF)**: Chapter on Visitor pattern
