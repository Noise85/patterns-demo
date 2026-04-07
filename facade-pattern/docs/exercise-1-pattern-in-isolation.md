# Exercise 1: Pattern in Isolation

## Objective

Implement the Facade pattern for a home theater system that simplifies complex subsystem interactions into a single, easy-to-use interface for common operations like watching a movie.

## Scenario

You have a sophisticated home theater setup with multiple components:

- **Projector**: Powers on/off, sets input, adjusts display mode
- **Sound System**: Powers on/off, sets volume, selects input mode (DVD, streaming, etc.)
- **DVD Player**: Powers on/off, plays, stops, ejects disc
- **Streaming Device**: Powers on/off, launches app, plays content
- **Lights**: Dims, brightens, turns on/off

Each component has its own interface and must be operated in the correct sequence. For example, watching a DVD movie requires:
1. Dim lights to 10%
2. Power on projector
3. Set projector input to DVD
4. Set projector to widescreen mode
5. Power on sound system
6. Set sound system volume to 5
7. Set sound system input to DVD
8. Power on DVD player
9. Play DVD

This is tedious and error-prone. A facade should simplify this to: `homeTheater.watchMovie()`

## Your Tasks

### 1. Implement Subsystem Components

Create the following subsystem classes:

**Projector**:
- `powerOn()`, `powerOff()`
- `setInput(String input)` - accepts "DVD", "Streaming", "Cable"
- `setDisplayMode(String mode)` - accepts "Widescreen", "Standard"

**SoundSystem**:
- `powerOn()`, `powerOff()`
- `setVolume(int level)` - 0-10
- `setInputMode(String mode)` - accepts "DVD", "Streaming", "Cable"

**DvdPlayer**:
- `powerOn()`, `powerOff()`
- `play()`, `stop()`, `eject()`

**StreamingDevice**:
- `powerOn()`, `powerOff()`
- `launchApp(String appName)`
- `playContent(String contentId)`

**Lights**:
- `on()`, `off()`
- `dim(int percentage)` - 0-100

### 2. Create the Facade

Create `HomeTheaterFacade`:
- Constructor accepts all subsystem components
- `watchDvdMovie()` - Orchestrates all steps to watch a DVD
- `watchStreamingMovie(String appName, String contentId)` - Sets up for streaming
- `endMovie()` - Cleanup: stop playback, power off, restore lights

### 3. State Tracking

Each subsystem should track its state (e.g., power status, current settings) so tests can verify correct sequences.

### 4. Key Requirements

- Facade methods should call subsystems in the correct order
- Each subsystem should be independently usable (facade is optional)
- Subsystems should throw exceptions for invalid operations (e.g., play() when powered off)
- State should be consistent throughout operations

## Example Usage

```java
// Create subsystems
Projector projector = new Projector();
SoundSystem soundSystem = new SoundSystem();
DvdPlayer dvdPlayer = new DvdPlayer();
StreamingDevice streamingDevice = new StreamingDevice();
Lights lights = new Lights();

// Create facade
HomeTheaterFacade homeTheater = new HomeTheaterFacade(
    projector, soundSystem, dvdPlayer, streamingDevice, lights
);

// Simple movie watching (facade hides complexity)
homeTheater.watchDvdMovie();
// ... enjoy movie ...
homeTheater.endMovie();

// Streaming
homeTheater.watchStreamingMovie("Netflix", "movie-12345");
homeTheater.endMovie();

// Direct subsystem access still possible for advanced usage
projector.setDisplayMode("Standard");
soundSystem.setVolume(8);
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Subsystem independence**: Each component works standalone
2. **Facade orchestration**: Correct sequence of subsystem calls
3. **State management**: Components track state accurately
4. **Error handling**: Invalid operations throw appropriate exceptions
5. **Cleanup**: `endMovie()` restores system to initial state

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] Each subsystem can be used independently
- [ ] Facade correctly orchestrates subsystem calls
- [ ] State is tracked and validated
- [ ] Code is clean and well-documented

## Time Estimate

**60-90 minutes** for a developer familiar with object composition.

## Hints

- Each subsystem should maintain state (powered on/off, current settings)
- Check preconditions (e.g., can't play if not powered on)
- Facade methods should be high-level and hide implementation details
- Consider using StringBuilder or List to track operation sequences for testing
- Think about the natural order of operations (lights first? projector before sound?)
