package com.patterns.prototype.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Concrete game entity representing an item.
 */
public class Item implements GameEntity {
    private final String id;
    private final String name;
    private final int value;
    private final Map<String, Object> effects;

    public Item(String id, String name, int value, Map<String, Object> effects) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.effects = new HashMap<>(effects);
    }

    @Override
    public GameEntity clone() {
        // TODO: Implement cloning with a NEW unique ID
        // Hint: Use UUID.randomUUID().toString() for new ID
        // Remember to deep copy the effects map
        throw new UnsupportedOperationException("TODO: Implement Item.clone()");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // For items, effects are the attributes
        return new HashMap<>(effects);
    }

    public int getValue() {
        return value;
    }

    public Map<String, Object> getEffects() {
        return new HashMap<>(effects);
    }
}
