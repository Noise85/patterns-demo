# Exercise 2: Real-World Simulation - Smart Home Automation System

## Objective

Build a **smart home automation system** where devices coordinate through a central hub. This simulation demonstrates how the Mediator pattern handles complex interactions between multiple components in a production environment.

## Business Context

You're developing a smart home system where various devices need to coordinate their behavior:
- Motion sensors trigger lights and security cameras
- Temperature sensors adjust HVAC and notify users
- Door locks integrate with security system and lighting
- Time-based automation coordinates multiple devices
- Manual overrides affect multiple devices

**Challenge**: Devices should not directly communicate with each other. All coordination logic should be centralized in the smart home hub.

## Domain Model

### Core Components

1. **SmartHomeHub (Mediator)**
   - Coordinates all device interactions
   - Maintains device registry
   - Implements automation rules
   - Tracks system state (home/away mode)

2. **SmartDevice (Abstract Colleague)**
   - Base class for all devices
   - Holds reference to hub
   - Common properties: id, name, state
   - Notify hub of state changes

3. **Concrete Devices**:
   - **MotionSensor**: Detects motion, triggers events
   - **SmartLight**: Controls lighting, adjustable brightness
   - **Thermostat**: Manages temperature, heating/cooling modes
   - **DoorLock**: Secures entry points, locked/unlocked states
   - **SecurityCamera**: Records activity, armed/disarmed states

### System Behavior

**Automation Rules** (implemented in hub):

1. **Motion Detected** (when in away mode):
   - Turn on nearby lights at 100% brightness
   - Start recording on nearby cameras
   - Send security alert notification

2. **Motion Detected** (when in home mode):
   - Turn on nearby lights at 50% brightness
   - Cameras remain in standby mode

3. **Door Unlocked**:
   - If away mode → home mode transition
   - Turn on entry lights
   - Disarm security cameras (stop recording)
   
4. **Last Person Leaves** (manual "away mode" activation):
   - Lock all doors
   - Arm all cameras
   - Turn off all lights
   - Set thermostat to energy-saving mode (18°C / 64°F)

5. **Temperature Threshold Exceeded**:
   - Adjust thermostat automatically
   - Send notification if excessive heating/cooling needed
   - Open/close smart blinds (if implemented)

## Requirements

### 1. SmartHomeHub Interface

```java
public interface HomeAutomationMediator {
    void registerDevice(SmartDevice device);
    void unregisterDevice(SmartDevice device);
    void notify(SmartDevice sender, String event);
    void setMode(HomeMode mode);  // HOME, AWAY, NIGHT, VACATION
    HomeMode getMode();
    List<SmartDevice> getDevicesByType(DeviceType type);
    List<DeviceEvent> getEventLog();
}
```

### 2. SmartHomeHub Implementation

**Responsibilities**:
- Device registry (by ID, by type, by location)
- Event routing and coordination
- Automation rule execution
- Event logging for audit trail
- Mode management (HOME, AWAY, NIGHT, VACATION)

**Coordination Logic Examples**:

```java
// When motion sensor detects motion
if (mode == AWAY) {
    // Security scenario
    getLightsInZone(sensor.getZone()).forEach(light -> light.turnOn(100));
    getCamerasInZone(sensor.getZone()).forEach(camera -> camera.startRecording());
    logEvent("Motion detected in " + sensor.getZone() + " - Security alert");
} else {
    // Convenience scenario
    getLightsInZone(sensor.getZone()).forEach(light -> light.turnOn(50));
}
```

### 3. SmartDevice Base Class

```java
public abstract class SmartDevice {
    protected final String id;
    protected final String name;
    protected final DeviceType type;
    protected final String zone;  // "Living Room", "Bedroom", etc.
    protected final HomeAutomationMediator hub;
    protected boolean isOnline;
    
    protected void notifyHub(String event) {
        hub.notify(this, event);
    }
    
    public abstract Map<String, Object> getState();
    public abstract String getDescription();
}
```

### 4. Concrete Devices

Each device should:
- Implement state changes (turn on/off, lock/unlock, adjust settings)
- Notify hub when state changes
- Respond to commands from hub
- Maintain device-specific state (brightness, temperature, recording status)

**Example - SmartLight**:
```java
public class SmartLight extends SmartDevice {
    private boolean isOn;
    private int brightness;  // 0-100
    
    public void turnOn(int brightness) {
        this.isOn = true;
        this.brightness = brightness;
        notifyHub("LIGHT_ON");
    }
    
    public void turnOff() {
        this.isOn = false;
        notifyHub("LIGHT_OFF");
    }
}
```

### 5. Supporting Types

```java
public enum HomeMode { HOME, AWAY, NIGHT, VACATION }

public enum DeviceType { 
    MOTION_SENSOR, LIGHT, THERMOSTAT, 
    DOOR_LOCK, SECURITY_CAMERA, SMOKE_DETECTOR 
}

public record DeviceEvent(
    LocalDateTime timestamp,
    String deviceId,
    String deviceName,
    String eventType,
    String description
) {}
```

## Expected Behavior

### Scenario 1: Leaving Home

```java
SmartHomeHub hub = new SmartHomeHub();

SmartLight entryLight = new SmartLight("L001", "Entry Light", "Entrance", hub);
DoorLock frontDoor = new DoorLock("D001", "Front Door", "Entrance", hub);
SecurityCamera entryCam = new SecurityCamera("C001", "Entry Camera", "Entrance", hub);

hub.registerDevice(entryLight);
hub.registerDevice(frontDoor);
hub.registerDevice(entryCam);

// User activates away mode
hub.setMode(HomeMode.AWAY);

// Hub automatically:
// - Locks front door
// - Arms camera
// - Turns off lights

assertThat(frontDoor.isLocked()).isTrue();
assertThat(entryCam.isRecording()).isTrue();
assertThat(entryLight.isOn()).isFalse();
```

### Scenario 2: Motion Detected While Away

```java
MotionSensor livingSensor = new MotionSensor("M001", "Living Room Sensor", "Living Room", hub);
hub.registerDevice(livingSensor);

hub.setMode(HomeMode.AWAY);

// Motion detected
livingSensor.detectMotion();

// Hub coordinates response:
// - Lights in living room turn on at 100%
// - Cameras in living room start recording
// - Event logged

assertThat(hub.getEventLog()).hasSizeGreaterThan(0);
```

## Testing Focus

Your tests should verify:

1. ✅ Devices register with hub successfully
2. ✅ Hub routes events from one device to relevant devices
3. ✅ Mode changes trigger appropriate automation rules
4. ✅ Motion detection triggers different responses based on mode
5. ✅ Door unlock triggers entry sequence (lights, cameras)
6. ✅ Away mode activates security measures
7. ✅ Devices can be queried by type, zone, or ID
8. ✅ Event log captures all significant actions
9. ✅ Devices don't communicate directly with each other
10. ✅ Multiple devices can respond to single event
11. ✅ Zone-based coordination (only nearby devices affected)
12. ✅ Device state queries return accurate information

## Design Challenges

1. **Zone Management**: How to group devices by physical location?
2. **Rule Complexity**: Balance flexibility with maintainability
3. **Event Logging**: What level of detail to capture?
4. **Concurrent Events**: Handle multiple devices triggering simultaneously
5. **Device Discovery**: How to add/remove devices at runtime?
6. **State Consistency**: Ensure hub's view matches device reality

## Implementation Tasks

1. ✅ Implement `HomeAutomationMediator` interface
2. ✅ Create `SmartHomeHub` with device registry and routing logic
3. ✅ Implement `SmartDevice` base class
4. ✅ Create concrete device classes (at least 4 types)
5. ✅ Implement automation rules for home/away mode transitions
6. ✅ Add zone-based device coordination
7. ✅ Implement event logging and audit trail
8. ✅ Handle edge cases (device offline, invalid events, missing devices)

## Key Takeaway

This simulation shows how the Mediator pattern scales to real-world complexity. The SmartHomeHub centralizes intricate coordination logic that would be impossible to maintain if scattered across devices. Adding new devices or automation rules only requires changes to the hub, not to existing device code.

**Production Considerations**:
- In real systems, consider publish-subscribe for scalability
- Add persistence for automation rules
- Implement device health monitoring
- Support remote access and mobile notifications
- Consider splitting hub into multiple specialized mediators for very large systems
