package com.patterns.mediator.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Mediator Pattern - Simulation Exercise (Smart Home System).
 */
@DisplayName("Mediator Pattern - Smart Home Automation")
class SimulationExerciseTest {
    
    private SmartHomeHub hub;
    private SmartLight livingRoomLight;
    private SmartLight bedroomLight;
    private MotionSensor livingRoomSensor;
    private DoorLock frontDoor;
    private SecurityCamera entryCamera;
    private Thermostat thermostat;
    
    @BeforeEach
    void setUp() {
        hub = new SmartHomeHub();
        
        livingRoomLight = new SmartLight("L001", "Living Room Light", "Living Room", hub);
        bedroomLight = new SmartLight("L002", "Bedroom Light", "Bedroom", hub);
        livingRoomSensor = new MotionSensor("M001", "Living Room Sensor", "Living Room", hub);
        frontDoor = new DoorLock("D001", "Front Door", "Entrance", hub);
        entryCamera = new SecurityCamera("C001", "Entry Camera", "Entrance", hub);
        thermostat = new Thermostat("T001", "Main Thermostat", "Living Room", hub, 22);
    }
    
    @Test
    @DisplayName("Should register device with hub")
    void testRegisterDevice() {
        hub.registerDevice(livingRoomLight);
        
        assertThat(hub.getDeviceCount()).isEqualTo(1);
        assertThat(hub.getDeviceById("L001")).isEqualTo(livingRoomLight);
    }
    
    @Test
    @DisplayName("Should unregister device from hub")
    void testUnregisterDevice() {
        hub.registerDevice(livingRoomLight);
        hub.unregisterDevice(livingRoomLight);
        
        assertThat(hub.getDeviceCount()).isZero();
    }
    
    @Test
    @DisplayName("Should get devices by type")
    void testGetDevicesByType() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(bedroomLight);
        hub.registerDevice(frontDoor);
        
        var lights = hub.getDevicesByType(DeviceType.LIGHT);
        
        assertThat(lights).hasSize(2);
        assertThat(lights).containsExactlyInAnyOrder(livingRoomLight, bedroomLight);
    }
    
    @Test
    @DisplayName("Should get devices by zone")
    void testGetDevicesByZone() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(livingRoomSensor);
        hub.registerDevice(bedroomLight);
        
        var livingRoomDevices = hub.getDevicesByZone("Living Room");
        
        assertThat(livingRoomDevices).hasSize(2);
        assertThat(livingRoomDevices).containsExactlyInAnyOrder(livingRoomLight, livingRoomSensor);
    }
    
    @Test
    @DisplayName("Should turn on light")
    void testLightTurnOn() {
        hub.registerDevice(livingRoomLight);
        
        livingRoomLight.turnOn(75);
        
        assertThat(livingRoomLight.isOn()).isTrue();
        assertThat(livingRoomLight.getBrightness()).isEqualTo(75);
    }
    
    @Test
    @DisplayName("Should turn off light")
    void testLightTurnOff() {
        hub.registerDevice(livingRoomLight);
        livingRoomLight.turnOn(100);
        
        livingRoomLight.turnOff();
        
        assertThat(livingRoomLight.isOn()).isFalse();
        assertThat(livingRoomLight.getBrightness()).isZero();
    }
    
    @Test
    @DisplayName("Should detect motion")
    void testMotionDetection() {
        hub.registerDevice(livingRoomSensor);
        
        livingRoomSensor.detectMotion();
        
        assertThat(livingRoomSensor.isMotionDetected()).isTrue();
    }
    
    @Test
    @DisplayName("Should lock door")
    void testLockDoor() {
        hub.registerDevice(frontDoor);
        frontDoor.unlock();
        
        frontDoor.lock();
        
        assertThat(frontDoor.isLocked()).isTrue();
    }
    
    @Test
    @DisplayName("Should unlock door")
    void testUnlockDoor() {
        hub.registerDevice(frontDoor);
        
        frontDoor.unlock();
        
        assertThat(frontDoor.isLocked()).isFalse();
    }
    
    @Test
    @DisplayName("Should arm security camera")
    void testArmCamera() {
        hub.registerDevice(entryCamera);
        
        entryCamera.arm();
        
        assertThat(entryCamera.isArmed()).isTrue();
    }
    
    @Test
    @DisplayName("Should start camera recording")
    void testCameraRecording() {
        hub.registerDevice(entryCamera);
        
        entryCamera.startRecording();
        
        assertThat(entryCamera.isRecording()).isTrue();
    }
    
    @Test
    @DisplayName("Should set thermostat target temperature")
    void testSetTargetTemperature() {
        hub.registerDevice(thermostat);
        
        thermostat.setTargetTemperature(24);
        
        assertThat(thermostat.getTargetTemperature()).isEqualTo(24);
    }
    
    @Test
    @DisplayName("Should handle motion detected in home mode")
    void testMotionDetectedHomeMode() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(livingRoomSensor);
        hub.setMode(HomeMode.HOME);
        
        livingRoomSensor.detectMotion();
        
        // In HOME mode, lights turn on at reduced brightness
        assertThat(livingRoomLight.isOn()).isTrue();
        assertThat(livingRoomLight.getBrightness()).isLessThan(100);
    }
    
    @Test
    @DisplayName("Should handle motion detected in away mode")
    void testMotionDetectedAwayMode() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(livingRoomSensor);
        hub.registerDevice(entryCamera);
        hub.setMode(HomeMode.AWAY);
        
        livingRoomSensor.detectMotion();
        
        // In AWAY mode, lights turn on at full brightness and camera starts recording
        assertThat(livingRoomLight.isOn()).isTrue();
        assertThat(livingRoomLight.getBrightness()).isEqualTo(100);
    }
    
    @Test
    @DisplayName("Should trigger automation when entering away mode")
    void testAwayModeAutomation() {
        hub.registerDevice(frontDoor);
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(entryCamera);
        hub.registerDevice(thermostat);
        
        livingRoomLight.turnOn(100);
        frontDoor.unlock();
        
        hub.setMode(HomeMode.AWAY);
        
        // AWAY mode should: lock doors, turn off lights, arm cameras, set thermostat to eco
        assertThat(frontDoor.isLocked()).isTrue();
        assertThat(livingRoomLight.isOn()).isFalse();
        assertThat(entryCamera.isArmed()).isTrue();
        assertThat(thermostat.getTargetTemperature()).isLessThanOrEqualTo(18);
    }
    
    @Test
    @DisplayName("Should log device events")
    void testEventLogging() {
        hub.registerDevice(livingRoomLight);
        
        livingRoomLight.turnOn(50);
        
        assertThat(hub.getEventLog()).isNotEmpty();
        assertThat(hub.getEventLog().get(0).deviceId()).isEqualTo("L001");
    }
    
    @Test
    @DisplayName("Should get current home mode")
    void testGetMode() {
        assertThat(hub.getMode()).isEqualTo(HomeMode.HOME);
        
        hub.setMode(HomeMode.AWAY);
        
        assertThat(hub.getMode()).isEqualTo(HomeMode.AWAY);
    }
    
    @Test
    @DisplayName("Should handle door unlock triggering entry sequence")
    void testDoorUnlockEntrySequence() {
        hub.registerDevice(frontDoor);
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(entryCamera);
        hub.setMode(HomeMode.AWAY);
        
        entryCamera.arm();
        
        frontDoor.unlock();
        
        // Unlocking door should switch mode and trigger welcome sequence
        assertThat(hub.getMode()).isEqualTo(HomeMode.HOME);
        assertThat(entryCamera.isRecording()).isFalse();
    }
    
    @Test
    @DisplayName("Should return device state")
    void testDeviceState() {
        hub.registerDevice(livingRoomLight);
        livingRoomLight.turnOn(80);
        
        var state = livingRoomLight.getState();
        
        assertThat(state).containsEntry("isOn", true);
        assertThat(state).containsEntry("brightness", 80);
    }
    
    @Test
    @DisplayName("Should return device description")
    void testDeviceDescription() {
        assertThat(livingRoomLight.getDescription()).contains("Living Room");
        assertThat(frontDoor.getDescription()).contains("Door Lock");
    }
    
    @Test
    @DisplayName("Should handle zone-based light control")
    void testZoneBasedLightControl() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(bedroomLight);
        hub.registerDevice(livingRoomSensor);
        
        livingRoomSensor.detectMotion();
        
        // Only lights in same zone should turn on
        assertThat(livingRoomLight.isOn()).isTrue();
        assertThat(bedroomLight.isOn()).isFalse();
    }
    
    @Test
    @DisplayName("Should handle multiple devices in same zone")
    void testMultipleDevicesSameZone() {
        var light1 = new SmartLight("L001", "Light 1", "Living Room", hub);
        var light2 = new SmartLight("L002", "Light 2", "Living Room", hub);
        
        hub.registerDevice(light1);
        hub.registerDevice(light2);
        hub.registerDevice(livingRoomSensor);
        
        livingRoomSensor.detectMotion();
        
        assertThat(light1.isOn()).isTrue();
        assertThat(light2.isOn()).isTrue();
    }
    
    @Test
    @DisplayName("Should disarm camera when disarming")
    void testCameraDisarm() {
        hub.registerDevice(entryCamera);
        entryCamera.arm();
        entryCamera.startRecording();
        
        entryCamera.disarm();
        
        assertThat(entryCamera.isArmed()).isFalse();
        assertThat(entryCamera.isRecording()).isFalse();
    }
    
    @Test
    @DisplayName("Should set thermostat to eco mode")
    void testThermostatEcoMode() {
        hub.registerDevice(thermostat);
        
        thermostat.setEcoMode();
        
        assertThat(thermostat.getTargetTemperature()).isLessThanOrEqualTo(18);
    }
    
    @Test
    @DisplayName("Should update thermostat current temperature")
    void testThermostatTemperatureUpdate() {
        hub.registerDevice(thermostat);
        
        thermostat.updateCurrentTemperature(25);
        
        assertThat(thermostat.getCurrentTemperature()).isEqualTo(25);
    }
    
    @Test
    @DisplayName("Should adjust light brightness")
    void testAdjustBrightness() {
        hub.registerDevice(livingRoomLight);
        livingRoomLight.turnOn(50);
        
        livingRoomLight.setBrightness(75);
        
        assertThat(livingRoomLight.getBrightness()).isEqualTo(75);
        assertThat(livingRoomLight.isOn()).isTrue();
    }
    
    @Test
    @DisplayName("Should clear motion after detection")
    void testClearMotion() {
        hub.registerDevice(livingRoomSensor);
        livingRoomSensor.detectMotion();
        
        livingRoomSensor.clearMotion();
        
        assertThat(livingRoomSensor.isMotionDetected()).isFalse();
    }
    
    @Test
    @DisplayName("Should stop camera recording")
    void testStopRecording() {
        hub.registerDevice(entryCamera);
        entryCamera.startRecording();
        
        entryCamera.stopRecording();
        
        assertThat(entryCamera.isRecording()).isFalse();
    }
    
    @Test
    @DisplayName("Should verify device online status")
    void testDeviceOnlineStatus() {
        assertThat(livingRoomLight.isOnline()).isTrue();
        
        livingRoomLight.setOnline(false);
        
        assertThat(livingRoomLight.isOnline()).isFalse();
    }
    
    @Test
    @DisplayName("Should return empty list for non-existent device type")
    void testGetNonExistentDeviceType() {
        var devices = hub.getDevicesByType(DeviceType.SMOKE_DETECTOR);
        
        assertThat(devices).isEmpty();
    }
    
    @Test
    @DisplayName("Should return empty list for non-existent zone")
    void testGetNonExistentZone() {
        var devices = hub.getDevicesByZone("Garage");
        
        assertThat(devices).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle comfort mode for thermostat")
    void testThermostatComfortMode() {
        hub.registerDevice(thermostat);
        thermostat.setEcoMode();
        
        thermostat.setComfortMode();
        
        assertThat(thermostat.getTargetTemperature()).isGreaterThanOrEqualTo(20);
    }
    
    @Test
    @DisplayName("Should log registration events")
    void testRegistrationEventLogging() {
        hub.registerDevice(livingRoomLight);
        
        assertThat(hub.getEventLog())
            .isNotEmpty()
            .first()
            .satisfies(event -> {
                assertThat(event.deviceId()).isEqualTo("L001");
                assertThat(event.deviceName()).isEqualTo("Living Room Light");
            });
    }
    
    @Test
    @DisplayName("Should handle night mode")
    void testNightMode() {
        hub.registerDevice(livingRoomLight);
        hub.registerDevice(entryCamera);
        
        hub.setMode(HomeMode.NIGHT);
        
        assertThat(hub.getMode()).isEqualTo(HomeMode.NIGHT);
    }
    
    @Test
    @DisplayName("Should verify device type enumeration")
    void testDeviceTypes() {
        assertThat(DeviceType.values()).contains(
            DeviceType.MOTION_SENSOR,
            DeviceType.LIGHT,
            DeviceType.THERMOSTAT,
            DeviceType.DOOR_LOCK,
            DeviceType.SECURITY_CAMERA
        );
    }
    
    @Test
    @DisplayName("Should verify home mode enumeration")
    void testHomeModes() {
        assertThat(HomeMode.values()).contains(
            HomeMode.HOME,
            HomeMode.AWAY,
            HomeMode.NIGHT,
            HomeMode.VACATION
        );
    }
    
    @Test
    @DisplayName("Should verify device properties")
    void testDeviceProperties() {
        assertThat(livingRoomLight.getId()).isEqualTo("L001");
        assertThat(livingRoomLight.getName()).isEqualTo("Living Room Light");
        assertThat(livingRoomLight.getZone()).isEqualTo("Living Room");
        assertThat(livingRoomLight.getType()).isEqualTo(DeviceType.LIGHT);
    }
    
    @Test
    @DisplayName("Should handle device event timestamps")
    void testEventTimestamps() {
        hub.registerDevice(livingRoomLight);
        livingRoomLight.turnOn(100);
        
        var events = hub.getEventLog();
        
        assertThat(events).allSatisfy(event -> 
            assertThat(event.timestamp()).isNotNull()
        );
    }
}
