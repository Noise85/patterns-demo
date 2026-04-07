package com.patterns.prototype.simulation;

/**
 * Spawner that uses a prototype registry to create game entities.
 */
public class EntitySpawner {
    private final PrototypeRegistry registry;

    public EntitySpawner(PrototypeRegistry registry) {
        this.registry = registry;
    }

    /**
     * Spawns an entity by type.
     *
     * @param entityType The type/key of the entity to spawn
     * @return Spawned entity (cloned from prototype)
     */
    public GameEntity spawn(String entityType) {
        // TODO: Use the registry to spawn (clone) an entity
        throw new UnsupportedOperationException("TODO: Implement spawn()");
    }
}
