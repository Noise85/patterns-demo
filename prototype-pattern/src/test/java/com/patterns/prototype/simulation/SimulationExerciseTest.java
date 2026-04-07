package com.patterns.prototype.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Exercise 2: Game Entity Spawner
 */
class SimulationExerciseTest {

    private PrototypeRegistry registry;
    private EntitySpawner spawner;

    @BeforeEach
    void setup() {
        registry = new PrototypeRegistry();
        spawner = new EntitySpawner(registry);
    }

    @Test
    void enemy_clone_createsIndependentCopy() {
        Enemy original = new Enemy("enemy-1", "Goblin", 100, 15,
                Map.of("speed", 5, "defense", 10));

        GameEntity clone = original.clone();

        assertThat(clone).isNotSameAs(original);
        assertThat(clone.getName()).isEqualTo("Goblin");
        assertThat(((Enemy) clone).getHealth()).isEqualTo(100);
        assertThat(((Enemy) clone).getDamage()).isEqualTo(15);
    }

    @Test
    void enemy_clone_generatesNewId() {
        Enemy original = new Enemy("enemy-1", "Orc", 150, 25, Map.of());

        GameEntity clone = original.clone();

        assertThat(clone.getId()).isNotEqualTo(original.getId());
        assertThat(clone.getId()).isNotNull().isNotEmpty();
    }

    @Test
    void item_clone_createsIndependentCopy() {
        Item original = new Item("item-1", "Health Potion", 50,
                Map.of("healing", 30, "duration", 0));

        GameEntity clone = original.clone();

        assertThat(clone).isNotSameAs(original);
        assertThat(clone.getName()).isEqualTo("Health Potion");
        assertThat(((Item) clone).getValue()).isEqualTo(50);
    }

    @Test
    void item_clone_generatesNewId() {
        Item original = new Item("item-1", "Sword", 100, Map.of("damage", 20));

        GameEntity clone = original.clone();

        assertThat(clone.getId()).isNotEqualTo(original.getId());
    }

    @Test
    void registry_registerAndRetrieve() {
        Enemy goblin = new Enemy("proto-1", "Goblin", 100, 15, Map.of());
        registry.registerPrototype("goblin", goblin);

        assertThat(registry.hasPrototype("goblin")).isTrue();
    }

    @Test
    void registry_spawnEntity_clonesPrototype() {
        Enemy prototype = new Enemy("proto-1", "Zombie", 80, 12, Map.of("undead", true));
        registry.registerPrototype("zombie", prototype);

        GameEntity spawned = registry.spawnEntity("zombie");

        assertThat(spawned).isNotSameAs(prototype);
        assertThat(spawned.getName()).isEqualTo("Zombie");
        assertThat(spawned.getId()).isNotEqualTo(prototype.getId());
    }

    @Test
    void registry_spawnMultipleTimes_createsIndependentClones() {
        Item potion = new Item("proto-2", "Mana Potion", 30, Map.of("mana", 50));
        registry.registerPrototype("mana_potion", potion);

        GameEntity spawn1 = registry.spawnEntity("mana_potion");
        GameEntity spawn2 = registry.spawnEntity("mana_potion");
        GameEntity spawn3 = registry.spawnEntity("mana_potion");

        // All spawns should be independent with different IDs
        assertThat(spawn1.getId()).isNotEqualTo(spawn2.getId());
        assertThat(spawn1.getId()).isNotEqualTo(spawn3.getId());
        assertThat(spawn2.getId()).isNotEqualTo(spawn3.getId());
        
        // But all should have the same name
        assertThat(spawn1.getName()).isEqualTo("Mana Potion");
        assertThat(spawn2.getName()).isEqualTo("Mana Potion");
        assertThat(spawn3.getName()).isEqualTo("Mana Potion");
    }

    @Test
    void registry_throwsException_forUnknownPrototype() {
        assertThatThrownBy(() -> registry.spawnEntity("unknown"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unknown");
    }

    @Test
    void registry_removePrototype() {
        Enemy dragon = new Enemy("proto-3", "Dragon", 500, 75, Map.of());
        registry.registerPrototype("dragon", dragon);

        assertThat(registry.hasPrototype("dragon")).isTrue();

        registry.removePrototype("dragon");

        assertThat(registry.hasPrototype("dragon")).isFalse();
    }

    @Test
    void registry_hasPrototype_returnsFalse_forNonExistent() {
        assertThat(registry.hasPrototype("nonexistent")).isFalse();
    }

    @Test
    void entitySpawner_spawnsUsingRegistry() {
        Enemy skeleton = new Enemy("proto-4", "Skeleton", 60, 10,
                Map.of("undead", true, "armor", 5));
        registry.registerPrototype("skeleton", skeleton);

        GameEntity spawned = spawner.spawn("skeleton");

        assertThat(spawned).isNotNull();
        assertThat(spawned.getName()).isEqualTo("Skeleton");
        assertThat(spawned.getId()).isNotEqualTo(skeleton.getId());
    }

    @Test
    void entities_deepCopyAttributes() {
        Map<String, Object> originalAttributes = Map.of("strength", 10, "agility", 15);
        Enemy enemy = new Enemy("e1", "Warrior", 200, 30, originalAttributes);

        GameEntity clone = enemy.clone();

        // Attributes should be equal but independent
        assertThat(clone.getAttributes()).isEqualTo(enemy.getAttributes());
        assertThat(clone.getAttributes()).isNotSameAs(enemy.getAttributes());
    }

    @Test
    void multiplePrototypes_canCoexist() {
        registry.registerPrototype("goblin", new Enemy("e1", "Goblin", 100, 15, Map.of()));
        registry.registerPrototype("orc", new Enemy("e2", "Orc", 150, 25, Map.of()));
        registry.registerPrototype("potion", new Item("i1", "Health Potion", 50, Map.of()));

        assertThat(registry.hasPrototype("goblin")).isTrue();
        assertThat(registry.hasPrototype("orc")).isTrue();
        assertThat(registry.hasPrototype("potion")).isTrue();

        GameEntity goblin = registry.spawnEntity("goblin");
        GameEntity orc = registry.spawnEntity("orc");
        GameEntity potion = registry.spawnEntity("potion");

        assertThat(goblin.getName()).isEqualTo("Goblin");
        assertThat(orc.getName()).isEqualTo("Orc");
        assertThat(potion.getName()).isEqualTo("Health Potion");
    }

    @Test
    void spawning_doesNotModifyStoredPrototype() {
        Enemy originalPrototype = new Enemy("proto", "Troll", 300, 40, Map.of("regeneration", 5));
        String originalId = originalPrototype.getId();
        registry.registerPrototype("troll", originalPrototype);

        // Spawn multiple times
        registry.spawnEntity("troll");
        registry.spawnEntity("troll");
        registry.spawnEntity("troll");

        // Original prototype should be unchanged
        assertThat(originalPrototype.getId()).isEqualTo(originalId);
    }
}
