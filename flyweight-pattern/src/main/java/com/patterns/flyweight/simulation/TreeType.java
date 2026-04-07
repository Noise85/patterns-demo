package com.patterns.flyweight.simulation;

/**
 * Flyweight representing a tree species type.
 * Stores intrinsic state (shared among all trees of this species).
 * Immutable and thread-safe.
 */
public record TreeType(
    String species,
    String modelId,
    String textureSet,
    double maxHeight,      // meters
    double growthRate,     // meters per time unit
    double windResistance  // 0.0 to 1.0 (higher = more resistant)
) {
    
    /**
     * Renders a tree instance with this type.
     * Combines intrinsic state (from this type) with extrinsic state (from instance).
     *
     * @param instance the tree instance to render
     * @return rendering description for testing
     */
    public String render(TreeInstance instance) {
        // TODO: Implement rendering simulation
        // Return string describing render:
        // "{species} tree at ({x},{y},{z}) height={height}m rotation={rotation}° [model={modelId}]"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Calculates wind effect on a tree instance.
     * Uses intrinsic wind resistance and extrinsic tree height.
     *
     * @param windSpeed wind speed in m/s
     * @param instance the tree instance
     * @return sway amount in degrees
     */
    public double calculateWindEffect(double windSpeed, TreeInstance instance) {
        // TODO: Implement wind effect calculation
        // baseSway = windSpeed * (1 - windResistance)
        // heightFactor = currentHeight / maxHeight
        // return baseSway * heightFactor
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
