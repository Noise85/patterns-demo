package com.patterns.prototype.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Concrete game entity representing an enemy.
 */
public class Enemy implements GameEntity {
    private final String id;
    private final String name;
    private final int health;
    private final int damage;
    private final Map<String, Object> attributes;

    public Enemy(String id, String name, int health, int damage, Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.attributes = new HashMap<>(attributes);
    }

    @Override
    public GameEntity clone() {
        // TODO: Implement cloning with a NEW unique ID
        // Hint: Use UUID.randomUUID().toString() for new ID
        // Remember to deep copy the attributes map
        throw new UnsupportedOperationException("TODO: Implement Enemy.clone()");
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
        return new HashMap<>(attributes);
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
