package com.patterns.flyweight.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Flyweight pattern simulation exercise (game forest rendering).
 */
class SimulationExerciseTest {
    
    private TreeTypeFactory typeFactory;
    private Forest forest;
    
    @BeforeEach
    void setUp() {
        typeFactory = new TreeTypeFactory();
        forest = new Forest(typeFactory);
    }
    
    @Test
    @DisplayName("TreeTypeFactory provides predefined oak tree type")
    void testOakTreeType() {
        TreeType oak = typeFactory.getTreeType("Oak");
        
        assertThat(oak.species()).isEqualTo("Oak");
        assertThat(oak.maxHeight()).isEqualTo(25.0);
        assertThat(oak.growthRate()).isEqualTo(0.3);
        assertThat(oak.windResistance()).isEqualTo(0.7);
        assertThat(oak.modelId()).isEqualTo("model_oak");
        assertThat(oak.textureSet()).isEqualTo("texture_oak");
    }
    
    @Test
    @DisplayName("TreeTypeFactory provides all predefined species")
    void testAllPredefinedSpecies() {
        TreeType oak = typeFactory.getTreeType("Oak");
        TreeType pine = typeFactory.getTreeType("Pine");
        TreeType birch = typeFactory.getTreeType("Birch");
        TreeType maple = typeFactory.getTreeType("Maple");
        TreeType willow = typeFactory.getTreeType("Willow");
        
        assertThat(oak.species()).isEqualTo("Oak");
        assertThat(pine.species()).isEqualTo("Pine");
        assertThat(birch.species()).isEqualTo("Birch");
        assertThat(maple.species()).isEqualTo("Maple");
        assertThat(willow.species()).isEqualTo("Willow");
        
        assertThat(typeFactory.getCacheSize()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("TreeTypeFactory caches tree types")
    void testFactoryCaching() {
        TreeType oak1 = typeFactory.getTreeType("Oak");
        TreeType oak2 = typeFactory.getTreeType("Oak");
        
        assertThat(oak1).isSameAs(oak2);  // Same instance
        assertThat(typeFactory.getCacheSize()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("TreeTypeFactory throws exception for unknown species")
    void testUnknownSpecies() {
        assertThatThrownBy(() -> typeFactory.getTreeType("Banana"))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("TreeTypeFactory calculates memory footprint")
    void testMemoryFootprint() {
        typeFactory.getTreeType("Oak");
        typeFactory.getTreeType("Pine");
        
        long footprint = typeFactory.getMemoryFootprint();
        
        assertThat(footprint).isEqualTo(2000);  // 2 types * 1000 bytes
    }
    
    @Test
    @DisplayName("TreeType renders with extrinsic state from instance")
    void testTreeTypeRendering() {
        TreeType oak = typeFactory.getTreeType("Oak");
        TreeInstance tree = new TreeInstance(oak, 100, 0, 200, 10.5, 100, 45);
        
        String rendering = oak.render(tree);
        
        assertThat(rendering)
            .contains("Oak")
            .contains("100")
            .contains("200")
            .contains("10.5")
            .contains("45")
            .contains("model_oak");
    }
    
    @Test
    @DisplayName("TreeType calculates wind effect based on resistance and height")
    void testWindEffect() {
        TreeType oak = typeFactory.getTreeType("Oak");  // windResistance = 0.7
        TreeInstance tree = new TreeInstance(oak, 0, 0, 0, 12.5, 100, 0);  // half max height
        
        double sway = oak.calculateWindEffect(10.0, tree);
        
        // baseSway = 10 * (1 - 0.7) = 3.0
        // heightFactor = 12.5 / 25 = 0.5
        // sway = 3.0 * 0.5 = 1.5
        assertThat(sway).isCloseTo(1.5, within(0.01));
    }
    
    @Test
    @DisplayName("TreeInstance grows according to type's growth rate")
    void testTreeGrowth() {
        TreeType pine = typeFactory.getTreeType("Pine");  // growthRate = 0.5
        TreeInstance tree = new TreeInstance(pine, 0, 0, 0, 1.0, 100, 0);
        
        tree.grow(10.0);  // 10 time units
        
        // increment = 0.5 * 10 = 5.0 meters
        assertThat(tree.getCurrentHeight()).isCloseTo(6.0, within(0.01));
    }
    
    @Test
    @DisplayName("TreeInstance growth respects max height")
    void testMaxHeightLimit() {
        TreeType birch = typeFactory.getTreeType("Birch");  // maxHeight = 20
        TreeInstance tree = new TreeInstance(birch, 0, 0, 0, 19.0, 100, 0);
        
        tree.grow(10.0);  // Would grow by 4 meters (0.4 * 10) but capped
        
        assertThat(tree.getCurrentHeight()).isEqualTo(20.0);
    }
    
    @Test
    @DisplayName("TreeInstance takes damage")
    void testTreeDamage() {
        TreeType oak = typeFactory.getTreeType("Oak");
        TreeInstance tree = new TreeInstance(oak, 0, 0, 0, 10, 100, 0);
        
        tree.takeDamage(30);
        
        assertThat(tree.getHealth()).isEqualTo(70);
    }
    
    @Test
    @DisplayName("TreeInstance health cannot go below zero")
    void testHealthMinimum() {
        TreeType oak = typeFactory.getTreeType("Oak");
        TreeInstance tree = new TreeInstance(oak, 0, 0, 0, 10, 50, 0);
        
        tree.takeDamage(100);
        
        assertThat(tree.getHealth()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("TreeInstance delegates rendering to type")
    void testInstanceRendering() {
        TreeType maple = typeFactory.getTreeType("Maple");
        TreeInstance tree = new TreeInstance(maple, 50, 0, 75, 15, 100, 90);
        
        String rendering = tree.render();
        
        assertThat(rendering)
            .contains("Maple")
            .contains("50")
            .contains("75")
            .contains("15")
            .contains("90");
    }
    
    @Test
    @DisplayName("TreeInstance applies wind from type")
    void testInstanceWindApplication() {
        TreeType willow = typeFactory.getTreeType("Willow");  // Low resistance
        TreeInstance tree = new TreeInstance(willow, 0, 0, 0, 10, 100, 0);
        
        double sway = tree.applyWind(20.0);
        
        assertThat(sway).isGreaterThan(0);  // Should have noticeable sway
    }
    
    @Test
    @DisplayName("Forest plants trees with shared types")
    void testPlantingTrees() {
        forest.plantTree("Oak", 100, 0, 200);
        forest.plantTree("Oak", 150, 0, 250);
        forest.plantTree("Pine", 200, 0, 300);
        
        assertThat(forest.getTreeCount()).isEqualTo(3);
        assertThat(forest.getUniqueTypeCount()).isEqualTo(2);  // Oak and Pine
    }
    
    @Test
    @DisplayName("Forest demonstrates massive memory savings")
    void testMassiveForest() {
        // Plant 10,000 oak trees
        for (int i = 0; i < 10000; i++) {
            forest.plantTree("Oak", i, 0, i);
        }
        
        assertThat(forest.getTreeCount()).isEqualTo(10000);
        assertThat(forest.getUniqueTypeCount()).isEqualTo(1);  // Only 1 TreeType!
    }
    
    @Test
    @DisplayName("Forest calculates memory savings correctly")
    void testMemorySavingsCalculation() {
        // Plant 1000 trees of same species
        for (int i = 0; i < 1000; i++) {
            forest.plantTree("Pine", i, 0, i);
        }
        
        String savings = forest.getMemorySavings();
        
        // Should show high percentage savings
        assertThat(savings)
            .contains("%")
            .contains("memory saved");
        
        // Extract percentage (crude check - should be > 95%)
        assertThat(savings).matches(".*9[0-9]\\.[0-9]+%.*");
    }
    
    @Test
    @DisplayName("Forest with multiple species shows sharing per species")
    void testMultiSpeciesSharing() {
        for (int i = 0; i < 100; i++) {
            forest.plantTree("Oak", i, 0, 0);
        }
        for (int i = 0; i < 100; i++) {
            forest.plantTree("Pine", i, 0, 100);
        }
        for (int i = 0; i < 100; i++) {
            forest.plantTree("Birch", i, 0, 200);
        }
        
        assertThat(forest.getTreeCount()).isEqualTo(300);
        assertThat(forest.getUniqueTypeCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Forest renders all trees")
    void testForestRendering() {
        forest.plantTree("Oak", 0, 0, 0);
        forest.plantTree("Pine", 10, 0, 10);
        forest.plantTree("Birch", 20, 0, 20);
        
        List<String> renderings = forest.renderAll();
        
        assertThat(renderings).hasSize(3);
        assertThat(renderings.get(0)).contains("Oak");
        assertThat(renderings.get(1)).contains("Pine");
        assertThat(renderings.get(2)).contains("Birch");
    }
    
    @Test
    @DisplayName("Forest simulates growth for all trees")
    void testForestGrowthSimulation() {
        forest.plantTree("Oak", 0, 0, 0);
        forest.plantTree("Oak", 10, 0, 10);
        
        List<TreeInstance> treesBefore = forest.getTrees();
        double initialHeight = treesBefore.get(0).getCurrentHeight();
        
        forest.simulate(5.0);
        
        List<TreeInstance> treesAfter = forest.getTrees();
        assertThat(treesAfter.get(0).getCurrentHeight()).isGreaterThan(initialHeight);
        assertThat(treesAfter.get(1).getCurrentHeight()).isGreaterThan(initialHeight);
    }
    
    @Test
    @DisplayName("Forest applies wind to all trees")
    void testForestWindApplication() {
        forest.plantTree("Oak", 0, 0, 0);
        forest.plantTree("Willow", 10, 0, 10);
        
        List<Double> sways = forest.applyWind(15.0);
        
        assertThat(sways).hasSize(2);
        // Willow has lower wind resistance, should sway more
        assertThat(sways.get(1)).isGreaterThan(sways.get(0));
    }
    
    @Test
    @DisplayName("Different species have different wind responses")
    void testSpeciesWindDifferences() {
        TreeType oak = typeFactory.getTreeType("Oak");      // windResistance = 0.7
        TreeType willow = typeFactory.getTreeType("Willow"); // windResistance = 0.3
        
        TreeInstance oakTree = new TreeInstance(oak, 0, 0, 0, 10, 100, 0);
        TreeInstance willowTree = new TreeInstance(willow, 0, 0, 0, 10, 100, 0);
        
        double oakSway = oak.calculateWindEffect(20.0, oakTree);
        double willowSway = willow.calculateWindEffect(20.0, willowTree);
        
        assertThat(willowSway).isGreaterThan(oakSway);
    }
    
    @Test
    @DisplayName("Taller trees within same species sway more")
    void testHeightEffectOnWind() {
        TreeType pine = typeFactory.getTreeType("Pine");
        
        TreeInstance shortTree = new TreeInstance(pine, 0, 0, 0, 5, 100, 0);
        TreeInstance tallTree = new TreeInstance(pine, 0, 0, 0, 30, 100, 0);
        
        double shortSway = pine.calculateWindEffect(15.0, shortTree);
        double tallSway = pine.calculateWindEffect(15.0, tallTree);
        
        assertThat(tallSway).isGreaterThan(shortSway);
    }
    
    @Test
    @DisplayName("TreeInstance stores extrinsic state correctly")
    void testExtrinsicStateStorage() {
        TreeType maple = typeFactory.getTreeType("Maple");
        TreeInstance tree = new TreeInstance(maple, 123, 456, 789, 15.5, 85, 180);
        
        assertThat(tree.getX()).isEqualTo(123);
        assertThat(tree.getY()).isEqualTo(456);
        assertThat(tree.getZ()).isEqualTo(789);
        assertThat(tree.getCurrentHeight()).isEqualTo(15.5);
        assertThat(tree.getHealth()).isEqualTo(85);
        assertThat(tree.getRotation()).isEqualTo(180);
        assertThat(tree.getType()).isSameAs(maple);
    }
    
    @Test
    @DisplayName("Factory clearCache resets state")
    void testFactoryClear() {
        typeFactory.getTreeType("Oak");
        typeFactory.getTreeType("Pine");
        
        assertThat(typeFactory.getCacheSize()).isEqualTo(2);
        
        typeFactory.clearCache();
        
        assertThat(typeFactory.getCacheSize()).isEqualTo(0);
        assertThat(typeFactory.getMemoryFootprint()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Large forest stress test - 100,000 trees")
    void testLargeForestPerformance() {
        // Stress test with 100,000 trees
        for (int i = 0; i < 100000; i++) {
            String species = switch(i % 5) {
                case 0 -> "Oak";
                case 1 -> "Pine";
                case 2 -> "Birch";
                case 3 -> "Maple";
                default -> "Willow";
            };
            forest.plantTree(species, i, 0, i);
        }
        
        assertThat(forest.getTreeCount()).isEqualTo(100000);
        assertThat(forest.getUniqueTypeCount()).isEqualTo(5);  // Only 5 TreeType objects!
        
        // Verify memory savings is significant
        String savings = forest.getMemorySavings();
        assertThat(savings).contains("99.");  // Should be 99.x% savings
    }
    
    @Test
    @DisplayName("TreeType is immutable")
    void testTreeTypeImmutability() {
        TreeType oak = typeFactory.getTreeType("Oak");
        
        // Records are immutable - verify fields
        assertThat(oak.species()).isEqualTo("Oak");
        assertThat(oak.maxHeight()).isEqualTo(25.0);
        assertThat(oak.growthRate()).isEqualTo(0.3);
        assertThat(oak.windResistance()).isEqualTo(0.7);
    }
    
    @Test
    @DisplayName("Planted trees start with correct initial state")
    void testInitialTreeState() {
        forest.plantTree("Oak", 100, 0, 200);
        
        List<TreeInstance> trees = forest.getTrees();
        TreeInstance tree = trees.get(0);
        
        assertThat(tree.getCurrentHeight()).isEqualTo(1.0);  // Initial height
        assertThat(tree.getHealth()).isEqualTo(100);
        assertThat(tree.getRotation()).isBetween(0.0, 360.0);  // Random rotation
    }
}
