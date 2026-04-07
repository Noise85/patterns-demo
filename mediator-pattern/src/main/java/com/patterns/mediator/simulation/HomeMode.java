package com.patterns.mediator.simulation;

/**
 * Enumeration of home modes for automation behavior.
 */
public enum HomeMode {
    /**
     * Normal home occupation - standard automation rules.
     */
    HOME,
    
    /**
     * Home is empty - enhanced security, energy saving.
     */
    AWAY,
    
    /**
     * Night time - reduced lighting, privacy mode.
     */
    NIGHT,
    
    /**
     * Extended absence - maximum security and energy saving.
     */
    VACATION
}
