package com.patterns.flyweight.simulation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flyweight factory for tree types.
 * Manages cache of tree species and provides predefined tree data.
 */
public class TreeTypeFactory {
    
    private final Map<String, TreeType> treeTypes = new ConcurrentHashMap<>();
    
    // Predefined tree species data
    private static final Map<String, TreeTypeData> SPECIES_DATA = Map.of(
        "Oak", new TreeTypeData("model_oak", "texture_oak", 25.0, 0.3, 0.7),
        "Pine", new TreeTypeData("model_pine", "texture_pine", 35.0, 0.5, 0.4),
        "Birch", new TreeTypeData("model_birch", "texture_birch", 20.0, 0.4, 0.6),
        "Maple", new TreeTypeData("model_maple", "texture_maple", 30.0, 0.35, 0.65),
        "Willow", new TreeTypeData("model_willow", "texture_willow", 15.0, 0.25, 0.3)
    );
    
    /**
     * Gets or creates a tree type for the specified species.
     *
     * @param species the tree species name
     * @return shared TreeType instance
     * @throws IllegalArgumentException if species is unknown
     */
    public TreeType getTreeType(String species) {
        // TODO: Implement flyweight factory logic
        // 1. Check if species exists in SPECIES_DATA
        // 2. If not, throw IllegalArgumentException
        // 3. Use computeIfAbsent to get/create TreeType from cache
        // 4. Create TreeType using data from SPECIES_DATA
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of unique tree types loaded.
     *
     * @return cache size
     */
    public int getCacheSize() {
        return treeTypes.size();
    }
    
    /**
     * Estimates memory footprint of loaded tree types.
     * Assumes ~1000 bytes per TreeType (model + texture data).
     *
     * @return estimated bytes
     */
    public long getMemoryFootprint() {
        // TODO: Implement memory estimation
        // Return cacheSize * 1000 (bytes per tree type)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Clears the cache (for testing).
     */
    public void clearCache() {
        treeTypes.clear();
    }
    
    /**
     * Internal record for species data.
     */
    private record TreeTypeData(
        String modelId,
        String textureSet,
        double maxHeight,
        double growthRate,
        double windResistance
    ) {}
}
