package com.patterns.facade.isolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for Exercise 1: Pattern in Isolation.
 * Tests home theater facade and subsystem components.
 */
@DisplayName("Facade Pattern - Isolation Exercise Tests")
class IsolationExerciseTest {
    
    private Projector projector;
    private SoundSystem soundSystem;
    private DvdPlayer dvdPlayer;
    private StreamingDevice streamingDevice;
    private Lights lights;
    private HomeTheaterFacade homeTheater;
    
    @BeforeEach
        // 1. Check if transaction ID exists in cache
        // 2. If yes:
        //    a. Retrieve cached result
        //    b. Add audit entry: "Idempotency: Duplicate t
    void setUp() {
        projector = new Projector();
        soundSystem = new SoundSystem();
        dvdPlayer = new DvdPlayer();
        streamingDevice = new StreamingDevice();
        lights = new Lights();
        
        homeTheater = new HomeTheaterFacade(
            projector, soundSystem, dvdPlayer, streamingDevice, lights
        );
    }
    
    // ===== Projector Tests =====
    
    @Test
    @DisplayName("Projector powers on correctly")
    void projectorPowersOn() {
        assertThat(projector.isPoweredOn()).isFalse();
        
        projector.powerOn();
        
        assertThat(projector.isPoweredOn()).isTrue();
    }
    
    @Test
    @DisplayName("Projector throws error when setting input while off")
    void projectorRequiresPowerForInput() {
        assertThatThrownBy(() -> projector.setInput("DVD"))
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Projector sets input when powered on")
    void projectorSetsInput() {
        projector.powerOn();
        projector.setInput("DVD");
        
        assertThat(projector.getCurrentInput()).isEqualTo("DVD");
    }
    
    @Test
    @DisplayName("Projector sets display mode")
    void projectorSetsDisplayMode() {
        projector.powerOn();
        projector.setDisplayMode("Widescreen");
        
        assertThat(projector.getDisplayMode()).isEqualTo("Widescreen");
    }
    
    // ===== Sound System Tests =====
    
    @Test
    @DisplayName("SoundSystem powers on correctly")
    void soundSystemPowersOn() {
        soundSystem.powerOn();
        
        assertThat(soundSystem.isPoweredOn()).isTrue();
    }
    
    @Test
    @DisplayName("SoundSystem sets volume within range")
    void soundSystemSetsVolume() {
        soundSystem.powerOn();
        soundSystem.setVolume(5);
        
        assertThat(soundSystem.getVolume()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("SoundSystem rejects invalid volume")
    void soundSystemRejectsInvalidVolume() {
        soundSystem.powerOn();
        
        assertThatThrownBy(() -> soundSystem.setVolume(15))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("SoundSystem sets input mode")
    void soundSystemSetsInputMode() {
        soundSystem.powerOn();
        soundSystem.setInputMode("DVD");
        
        assertThat(soundSystem.getInputMode()).isEqualTo("DVD");
    }
    
    // ===== DVD Player Tests =====
    
    @Test
    @DisplayName("DvdPlayer powers on and plays")
    void dvdPlayerPlays() {
        dvdPlayer.powerOn();
        dvdPlayer.play();
        
        assertThat(dvdPlayer.isPlaying()).isTrue();
    }
    
    @Test
    @DisplayName("DvdPlayer cannot play when powered off")
    void dvdPlayerRequiresPowerToPlay() {
        assertThatThrownBy(() -> dvdPlayer.play())
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("DvdPlayer stops playback")
    void dvdPlayerStops() {
        dvdPlayer.powerOn();
        dvdPlayer.play();
        dvdPlayer.stop();
        
        assertThat(dvdPlayer.isPlaying()).isFalse();
    }
    
    @Test
    @DisplayName("DvdPlayer cannot eject while playing")
    void dvdPlayerCannotEjectWhilePlaying() {
        dvdPlayer.powerOn();
        dvdPlayer.play();
        
        assertThatThrownBy(() -> dvdPlayer.eject())
            .isInstanceOf(IllegalStateException.class);
    }
    
    // ===== Streaming Device Tests =====
    
    @Test
    @DisplayName("StreamingDevice launches app and plays content")
    void streamingDevicePlaysContent() {
        streamingDevice.powerOn();
        streamingDevice.launchApp("Netflix");
        streamingDevice.playContent("movie-123");
        
        assertThat(streamingDevice.isPlaying()).isTrue();
        assertThat(streamingDevice.getCurrentApp()).isEqualTo("Netflix");
    }
    
    @Test
    @DisplayName("StreamingDevice cannot play without app")
    void streamingDeviceRequiresAppToPlay() {
        streamingDevice.powerOn();
        
        assertThatThrownBy(() -> streamingDevice.playContent("movie-123"))
            .isInstanceOf(IllegalStateException.class);
    }
    
    // ===== Lights Tests =====
    
    @Test
    @DisplayName("Lights turn on to full brightness")
    void lightsTurnOn() {
        lights.on();
        
        assertThat(lights.isOn()).isTrue();
        assertThat(lights.getBrightness()).isEqualTo(100);
    }
    
    @Test
    @DisplayName("Lights dim to percentage")
    void lightsDim() {
        lights.dim(10);
        
        assertThat(lights.isOn()).isTrue();
        assertThat(lights.getBrightness()).isEqualTo(10);
    }
    
    @Test
    @DisplayName("Lights reject invalid brightness")
    void lightsRejectInvalidBrightness() {
        assertThatThrownBy(() -> lights.dim(150))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    // ===== Facade Tests =====
    
    @Test
    @DisplayName("watchDvdMovie configures all subsystems correctly")
    void watchDvdMovieConfiguresSystem() {
        homeTheater.watchDvdMovie();
        
        // Verify lights dimmed
        assertThat(lights.getBrightness()).isEqualTo(10);
        
        // Verify projector configured
        assertThat(projector.isPoweredOn()).isTrue();
        assertThat(projector.getCurrentInput()).isEqualTo("DVD");
        assertThat(projector.getDisplayMode()).isEqualTo("Widescreen");
        
        // Verify sound system configured
        assertThat(soundSystem.isPoweredOn()).isTrue();
        assertThat(soundSystem.getVolume()).isEqualTo(5);
        assertThat(soundSystem.getInputMode()).isEqualTo("DVD");
        
        // Verify DVD player playing
        assertThat(dvdPlayer.isPoweredOn()).isTrue();
        assertThat(dvdPlayer.isPlaying()).isTrue();
    }
    
    @Test
    @DisplayName("watchStreamingMovie configures for streaming")
    void watchStreamingMovieConfiguresSystem() {
        homeTheater.watchStreamingMovie("Netflix", "movie-456");
        
        // Verify lights dimmed
        assertThat(lights.getBrightness()).isEqualTo(10);
        
        // Verify projector configured for streaming
        assertThat(projector.isPoweredOn()).isTrue();
        assertThat(projector.getCurrentInput()).isEqualTo("Streaming");
        
        // Verify sound system configured for streaming
        assertThat(soundSystem.isPoweredOn()).isTrue();
        assertThat(soundSystem.getInputMode()).isEqualTo("Streaming");
        
        // Verify streaming device playing
        assertThat(streamingDevice.isPoweredOn()).isTrue();
        assertThat(streamingDevice.getCurrentApp()).isEqualTo("Netflix");
        assertThat(streamingDevice.isPlaying()).isTrue();
    }
    
    @Test
    @DisplayName("endMovie restores system to initial state")
    void endMovieCleansUp() {
        homeTheater.watchDvdMovie();
        homeTheater.endMovie();
        
        // Verify all devices powered off
        assertThat(dvdPlayer.isPoweredOn()).isFalse();
        assertThat(streamingDevice.isPoweredOn()).isFalse();
        assertThat(soundSystem.isPoweredOn()).isFalse();
        assertThat(projector.isPoweredOn()).isFalse();
        
        // Verify lights restored
        assertThat(lights.isOn()).isTrue();
        assertThat(lights.getBrightness()).isEqualTo(100);
    }
    
    @Test
    @DisplayName("endMovie after streaming cleans up correctly")
    void endMovieAfterStreamingCleansUp() {
        homeTheater.watchStreamingMovie("Hulu", "show-789");
        homeTheater.endMovie();
        
        assertThat(streamingDevice.isPoweredOn()).isFalse();
        assertThat(soundSystem.isPoweredOn()).isFalse();
        assertThat(lights.getBrightness()).isEqualTo(100);
    }
    
    @Test
    @DisplayName("Subsystems can be used independently of facade")
    void subsystemsWorkIndependently() {
        // Use projector directly without facade
        projector.powerOn();
        projector.setInput("Cable");
        
        assertThat(projector.getCurrentInput()).isEqualTo("Cable");
        
        // Facade doesn't interfere
        assertThat(soundSystem.isPoweredOn()).isFalse();
    }
    
    @Test
    @DisplayName("Multiple movie sessions work correctly")
    void multipleMovieSessions() {
        // First session: DVD
        homeTheater.watchDvdMovie();
        assertThat(dvdPlayer.isPlaying()).isTrue();
        homeTheater.endMovie();
        
        // Second session: Streaming
        homeTheater.watchStreamingMovie("Amazon Prime", "movie-999");
        assertThat(streamingDevice.isPlaying()).isTrue();
        assertThat(streamingDevice.getCurrentApp()).isEqualTo("Amazon Prime");
        homeTheater.endMovie();
        
        // All clean
        assertThat(lights.getBrightness()).isEqualTo(100);
    }
}
