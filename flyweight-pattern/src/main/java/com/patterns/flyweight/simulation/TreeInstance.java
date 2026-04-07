package com.patterns.flyweight.simulation;

/**
 * Represents a specific tree instance in the game world.
 * Holds reference to shared TreeType (intrinsic state)
 * and stores extrinsic state (position, current height, health, rotation).
 */
public class TreeInstance {
    
    private final TreeType type;
    private final double x;
    private final double y;
    private final double z;
    private double currentHeight;
    private int health;  // 0-100
    private double rotation;  // degrees
    
    /**
     * Creates a tree instance.
     *
     * @param type the shared tree type
     * @param x the x coordinate
     * @param y the y coordinate (typically ground level)
     * @param z the z coordinate
     * @param initialHeight the initial height
     * @param health the initial health (0-100)
     * @param rotation the initial rotation angle
     */
    public TreeInstance(TreeType type, double x, double y, double z,
                       double initialHeight, int health, double rotation) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.currentHeight = initialHeight;
        this.health = health;
        this.rotation = rotation;
    }
    
    /**
     * Renders this tree instance.
     * Delegates to TreeType with extrinsic state.
     *
     * @return rendering description
     */
    public String render() {
        // TODO: Implement rendering delegation
        // Delegate to type.render(this)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Grows the tree over time.
     * Growth rate comes from TreeType (intrinsic state).
     *
     * @param deltaTime time elapsed
     */
    public void grow(double deltaTime) {
        // TODO: Implement growth logic
        // increment = type.growthRate() * deltaTime
        // currentHeight = min(currentHeight + increment, type.maxHeight())
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Applies damage to the tree.
     *
     * @param damage amount of damage (0-100)
     */
    public void takeDamage(int damage) {
        // TODO: Implement damage logic
        // health = max(0, health - damage)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Calculates wind sway for this tree.
     * Uses TreeType's wind resistance and this tree's current height.
     *
     * @param windSpeed wind speed in m/s
     * @return sway angle in degrees
     */
    public double applyWind(double windSpeed) {
        // TODO: Implement wind effect
        // Delegate to type.calculateWindEffect(windSpeed, this)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Getters
    
    public TreeType getType() {
        return type;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public double getCurrentHeight() {
        return currentHeight;
    }
    
    public int getHealth() {
        return health;
    }
    
    public double getRotation() {
        return rotation;
    }
}
