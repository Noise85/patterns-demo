# Solution Notes

## Exercise 1: Shape Rendering Bridge

### Architecture

```
Shape (abstraction) ──────► Renderer (implementor)
  ↑                               ↑
  |                               |
Circle, Rectangle          VectorRenderer
                           RasterRenderer
```

### Key Points

- **Two dimensions**: Shapes and Renderers
- **Composition**: Shape delegates to Renderer
- **Extensibility**: Add shapes or renderers independently
- **No explosion**: n+m classes instead of n×m

### Implementation Pattern

```java
// Implementor
public interface Renderer {
    void renderCircle(double x, double y, double radius);
    void renderRectangle(double x, double y, double width, double height);
}

// Abstraction
public abstract class Shape {
    protected Renderer renderer;
    
    protected Shape(Renderer renderer) {
        this.renderer = renderer;
    }
    
    public abstract void draw();
}

// Refined Abstraction
public class Circle extends Shape {
    private double x, y, radius;
    
    public Circle(Renderer renderer, double x, double y, double radius) {
        super(renderer);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        renderer.renderCircle(x, y, radius);  // Delegate
    }
}
```

## Exercise 2: Notification Bridge

### Architecture

```
Notification (abstraction) ──────► MessageChannel (implementor)
       ↑                                   ↑
       |                                   |
AlertNotification              EmailChannel, SmsChannel
ReminderNotification           PushChannel
```

### Key Points

- **Message logic** in Notification hierarchy
- **Delivery mechanics** in MessageChannel hierarchy
- **Independent variation**: New messages or new channels
- **Platform abstraction**: Channels handle formatting

### Notification Implementation Pattern

```java
public abstract class Notification {
    protected MessageChannel channel;
    protected String recipient;
    
    protected Notification(MessageChannel channel, String recipient) {
        this.channel = channel;
        this.recipient = recipient;
    }
    
    public abstract void send();
}

public class AlertNotification extends Notification {
    private String alertMessage;
    
    @Override
    public void send() {
        String subject = "[ALERT] Urgent Notification";
        String body = alertMessage;
        channel.send(recipient, subject, body, NotificationPriority.HIGH);
    }
}
```

### Channel Implementation Pattern

```java
public class EmailChannel implements MessageChannel {
    @Override
    public void send(String recipient, String subject, String body, 
                     NotificationPriority priority) {
        String html = formatAsHtml(subject, body, priority);
        // Send email using email service
    }
}

public class SmsChannel implements MessageChannel {
    @Override
    public void send(String recipient, String subject, String body,
                     NotificationPriority priority) {
        String message = subject + ": " + body;
        if (message.length() > 160) {
            message = message.substring(0, 157) + "...";
        }
        // Send SMS using SMS gateway
    }
}
```

### Benefits Demonstrated

| Aspect | Without Bridge | With Bridge |
|--------|---------------|-------------|
| Classes needed (3 messages × 3 channels) | 9 classes | 6 classes |
| Adding new channel | Modify all 3 messages | Add 1 channel class |
| Adding new message type | Create 3 new classes | Add 1 message class |
| Code duplication | High | Low |

### When to Apply

Use Bridge when you answer "yes" to:
1. Do I have two or more dimensions of variation?
2. Should these dimensions change independently?
3. Is the combination count growing rapidly?
4. Do I need runtime selection of implementation?
