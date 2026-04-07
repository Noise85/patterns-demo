package com.patterns.prototype.simulation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Registry that stores prototype entities.
 * Supports registering, retrieving, and spawning entities by cloning.
 */
public class PrototypeRegistry {
    private final Map<String, GameEntity> prototypes = new ConcurrentHashMap<>();

    /**
     * Registers a prototype entity with a key.
     *
     * @param key       The key to identify this prototype
     * @param prototype The prototype entity to store
     */
    public void registerPrototype(String key, GameEntity prototype) {
        // TODO: Store the prototype in the registry
        throw new UnsupportedOperationException("TODO: Implement registerPrototype()");
    }

    /**
     * Spawns a new entity by cloning the prototype associated with the key.
     *
     * @param key The key of the prototype to clone
     * @return Cloned entity
     * @throws IllegalArgumentException if no prototype exists for the key
     */
    public GameEntity spawnEntity(String key) {
        // TODO: Get the prototype by key
        // TODO: Throw IllegalArgumentException if not found
        // TODO: Clone and return the prototype (NEVER return the stored prototype directly!)
        throw new UnsupportedOperationException("TODO: Implement spawnEntity()");
    }

    /**
     * Removes a prototype from the registry.
     *
     * @param key The key of the prototype to remove
     */
    public void removePrototype(String key) {
        // TODO: Remove the prototype from the registry
        throw new UnsupportedOperationException("TODO: Implement removePrototype()");
    }

    /**
     * Checks if a prototype exists for the given key.
     *
     * @param key The key to check
     * @return true if a prototype exists, false otherwise
     */
    public boolean hasPrototype(String key) {
        // TODO: Check if a prototype exists for the key
        throw new UnsupportedOperationException("TODO: Implement hasPrototype()");
    }
}
