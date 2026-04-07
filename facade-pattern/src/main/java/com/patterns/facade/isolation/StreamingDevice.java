package com.patterns.facade.isolation;

/**
 * Streaming device subsystem component.
 * Manages streaming apps and playback.
 */
public class StreamingDevice {
    
    private boolean isPoweredOn = false;
    private String currentApp;
    private boolean isPlaying = false;
    
    /**
     * Powers on the streaming device.
     */
    public void powerOn() {
        // TODO: Implement power on logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Powers off the streaming device.
     */
    public void powerOff() {
        // TODO: Implement power off logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Launches a streaming app.
     *
     * @param appName the app to launch (e.g., "Netflix", "Hulu")
     * @throws IllegalStateException if not powered on
     */
    public void launchApp(String appName) {
        // TODO: Implement app launching
        // - Check if powered on
        // - Set currentApp
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Plays content from the current app.
     *
     * @param contentId the content identifier
     * @throws IllegalStateException if not powered on or no app launched
     */
    public void playContent(String contentId) {
        // TODO: Implement content playback
        // - Check if powered on
        // - Check if app is launched
        // - Set isPlaying to true
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Stops playback.
     *
     * @throws IllegalStateException if not powered on
     */
    public void stop() {
        // TODO: Implement stop logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isPoweredOn() {
        return isPoweredOn;
    }
    
    public String getCurrentApp() {
        return currentApp;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
}
