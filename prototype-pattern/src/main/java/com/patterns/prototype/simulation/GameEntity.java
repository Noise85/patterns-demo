package com.patterns.prototype.simulation;

import java.util.Map;

/**
 * Interface for game entities that can be cloned.
 * All entities must support cloning for the prototype pattern.
 */
public interface GameEntity {
    /**
     * Creates an independent copy of this entity.
     * Cloned entities should have unique IDs.
     *
     * @return Cloned entity
     */
    GameEntity clone();

    /**
     * Gets the unique identifier for this entity.
     *
     * @return Entity ID
     */
    String getId();

    /**
     * Gets the name of this entity.
     *
     * @return Entity name
     */
    String getName();

    /**
     * Gets the attributes of this entity.
     * Returns a copy to prevent external modification.
     *
     * @return Entity attributes
     */
    Map<String, Object> getAttributes();
}
