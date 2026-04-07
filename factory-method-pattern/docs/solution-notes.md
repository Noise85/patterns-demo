# Solution Notes

This document provides high-level guidance for both exercises WITHOUT giving away the complete solution.

---

## Exercise 1: Pattern in Isolation

### Key Insights

Factory Method is about **delegating object creation to subclasses** while keeping the usage logic in the parent class.

### Architecture Overview

```
DocumentExporter (abstract)
  + createDocument(content): Document  [ABSTRACT - factory method]
  + export(content): String            [CONCRETE - uses factory method]
       ↓ uses
    Document (interface)
       ↑
       │
  ┌────┼────┐
  │    │    │
PdfDocument HtmlDocument MarkdownDocument

PdfExporter    HtmlExporter    MarkdownExporter
  │                 │                 │
  └─────────────────┴─────────────────┘
           override createDocument()
```

### Implementation Approach

**Step 1: Product Interface**
- Define common operations all documents share
- Methods: `getContent()`, `getFormat()`

**Step 2: Concrete Products**
- Each implements Document interface
- Each formats content differently:
  - PDF: Add "PDF: " prefix
  - HTML: Wrap in `<html><body>content</body></html>`
  - Markdown: Return as-is

**Step 3: Abstract Creator**
- Declare abstract `createDocument(String content)` method
- Implement concrete `export(String content)` that:
  1. Calls `createDocument(content)` to get a document
  2. Calls `getContent()` on the document
  3. Returns the formatted content

**Step 4: Concrete Creators**
- Each extends `DocumentExporter`
- Each overrides `createDocument()` to return its specific document type
- No other logic needed

### Common Pitfalls

❌ Making creator concrete instead of abstract
❌ Putting export logic in products instead of creator
❌ Not using the factory method in the template method
✅ Factory method returns interface type, not concrete class
✅ Creator has both abstract and concrete methods

---

## Exercise 2: Real-World Simulation

### Key Insights

This exercise demonstrates Factory Method in a production setting with:
- Validation logic specific to each channel
- Error handling without exceptions
- Extensibility for new channels

### Architecture Overview

```
NotificationService (abstract)
  + createNotification(message): Notification  [ABSTRACT]
  + sendNotification(message): DeliveryResult  [CONCRETE]
       ↓ uses
    Notification (interface)
      + send(): DeliveryResult
      + validate(): boolean
      + getChannel(): String
       ↑
       │
  ┌────┼────┬────┬────┐
  │    │    │    │    │
Email SMS Push Slack  ...

EmailService SmsService PushService SlackService
     │           │          │           │
     └───────────┴──────────┴───────────┘
            override createNotification()
```

### Implementation Approach

**Step 1: Models**
- `NotificationMessage`: recipient, subject, body, priority
- `DeliveryResult`: success, deliveryId, channel, errorMessage

**Step 2: Notification Interface**
```java
interface Notification {
    DeliveryResult send();
    boolean validate();
    String getChannel();
}
```

**Step 3: Concrete Notifications**
Each notification:
- Validates input format (email, phone, token, URL)
- Simulates sending (generate fake delivery ID)
- Returns DeliveryResult

Validation examples:
- Email: `recipient.contains("@") && recipient.contains(".")`
- SMS: `recipient.startsWith("+") && recipient.length() >= 12`
- Push: `!deviceToken.isEmpty()`
- Slack: `webhookUrl.startsWith("https://hooks.slack.com")`

**Step 4: Abstract Service**
```java
abstract class NotificationService {
    abstract Notification createNotification(NotificationMessage msg);
    
    DeliveryResult sendNotification(NotificationMessage msg) {
        Notification notification = createNotification(msg);
        if (!notification.validate()) {
            return DeliveryResult.failure(notification.getChannel(), "Validation failed");
        }
        return notification.send();
    }
}
```

**Step 5: Concrete Services**
Each service:
- Overrides `createNotification()`
- Returns channel-specific notification instance
- That's it!

### Design Decisions

**Where does validation logic go?**
- In the Notification implementation, not the service
- Each notification knows how to validate itself

**How to handle failures?**
- Return `DeliveryResult` with success=false
- Include error message for debugging
- Don't throw exceptions for business failures

**How to generate delivery IDs?**
- Simple approach: `UUID.randomUUID().toString()`
- Or: `channel + "-" + System.currentTimeMillis()`

**Should notifications be stateful?**
- Yes, they hold the message and any channel-specific config
- But they shouldn't maintain delivery state after `send()`

### Common Pitfalls

❌ Putting validation in the service instead of notification
❌ Throwing exceptions for validation failures
❌ Not using the factory method in sendNotification()
❌ Making services stateful with delivery history
✅ Each notification handles its own validation
✅ Services are just factories for notifications
✅ Use results to communicate success/failure

### Extensibility Checkpoint

After implementing, verify:
1. Can I add a new channel (e.g., Telegram) without modifying existing code?
2. Is validation channel-specific and encapsulated?
3. Does the base service enforce the validation → send flow?
4. Can I test each notification type independently?

If yes to all, you've successfully applied Factory Method! 🎉

---

## General Factory Method Lessons

### When You See This Pattern Working Well

✅ Adding new products is just creating new classes
✅ Creator doesn't know about concrete products
✅ Business logic in creator uses products through interface
✅ Subclasses customize object creation without changing usage

### Red Flags

🚩 Creator depends on all concrete product types
🚩 Factory method is never overridden
🚩 No template method that uses the factory method
🚩 Products have significantly different interfaces

---

## Further Reading

- [Refactoring.Guru: Factory Method](https://refactoring.guru/design-patterns/factory-method)
- [Factory Method vs Abstract Factory](https://refactoring.guru/design-patterns/abstract-factory)
- [Factory Method vs Template Method](https://refactoring.guru/design-patterns/template-method)
