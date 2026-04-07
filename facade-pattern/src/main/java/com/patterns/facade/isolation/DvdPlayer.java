package com.patterns.facade.isolation;

/**
 * DVD player subsystem component.
 * Manages playback operations.
 */
public class DvdPlayer {
    
    private boolean isPoweredOn = false;
    private boolean isPlaying = false;
    
    /**
     * Powers on the DVD player.
     */
    public void powerOn() {
        // TODO: Implement power on logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Powers off the DVD player.
     */
    public void powerOff() {
        // TODO: Implement power off logic
        // - Stop playback if playing
        // - Power off
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Starts playing the DVD.
     *
     * @throws IllegalStateException if not powered on
     */
    public void play() {
        // TODO: Implement play logic
        // - Check if powered on
        // - Set isPlaying to true
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Stops playing the DVD.
     *
     * @throws IllegalStateException if not powered on
     */
    public void stop() {
        // TODO: Implement stop logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Ejects the DVD.
     *
     * @throws IllegalStateException if not powered on or if playing
     */
    public void eject() {
        // TODO: Implement eject logic
        // - Check if powered on
        // - Check not currently playing
        // - Eject disc
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isPoweredOn() {
        return isPoweredOn;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
}
