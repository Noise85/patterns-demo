package com.patterns.flyweight.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a forest of trees using the Flyweight pattern.
 * Demonstrates memory savings through tree type sharing.
 */
public class Forest {
    
    private final TreeTypeFactory typeFactory;
    private final List<TreeInstance> trees = new ArrayList<>();
    
    /**
     * Creates a forest with the specified tree type factory.
     *
     * @param typeFactory the tree type factory
     */
    public Forest(TreeTypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }
    
    /**
     * Plants a tree in the forest.
     *
     * @param species the tree species
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public void plantTree(String species, double x, double y, double z) {
        // TODO: Implement tree planting
        // 1. Get TreeType from factory
        // 2. Create TreeInstance with:
        //    - TreeType
        //    - Position (x, y, z)
        //    - Initial height: 1.0 meter
        //    - Initial health: 100
        //    - Random rotation: Math.random() * 360
        // 3. Add to trees list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Renders all trees in the forest.
     *
     * @return list of rendering descriptions
     */
    public List<String> renderAll() {
        // TODO: Implement forest rendering
        // Render each tree and collect results
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Simulates forest growth over time.
     *
     * @param deltaTime time elapsed
     */
    public void simulate(double deltaTime) {
        // TODO: Implement growth simulation
        // Call grow(deltaTime) on each tree
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Applies wind effect to all trees.
     *
     * @param windSpeed wind speed in m/s
     * @return list of sway amounts
     */
    public List<Double> applyWind(double windSpeed) {
        // TODO: Implement wind application
        // Call applyWind(windSpeed) on each tree and collect results
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the total number of trees in the forest.
     *
     * @return tree count
     */
    public int getTreeCount() {
        return trees.size();
    }
    
    /**
     * Gets the number of unique tree types in the forest.
     * Demonstrates flyweight sharing.
     *
     * @return unique type count
     */
    public int getUniqueTypeCount() {
        return typeFactory.getCacheSize();
    }
    
    /**
     * Calculates memory savings from flyweight pattern.
     *
     * @return savings description string
     */
    public String getMemorySavings() {
        // TODO: Implement memory savings calculation
        // 
        // Assumptions:
        // - TreeType (intrinsic): ~1000 bytes (model + texture data)
        // - TreeInstance (extrinsic): ~50 bytes (position, height, health, rotation)
        //
        // Without flyweight:
        // - Each tree = TreeType data + instance data = 1050 bytes
        // - Total = treeCount * 1050
        //
        // With flyweight:
        // - Shared TreeTypes = uniqueTypeCount * 1000
        // - TreeInstances = treeCount * 50
        // - Total = (uniqueTypeCount * 1000) + (treeCount * 50)
        //
        // Savings percentage = (without - with) / without * 100
        //
        // Return formatted string: "X.XX% memory saved (Y KB instead of Z KB)"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets all trees (for testing).
     *
     * @return list of tree instances
     */
    public List<TreeInstance> getTrees() {
        return new ArrayList<>(trees);
    }
}
