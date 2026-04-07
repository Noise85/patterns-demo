package com.patterns.flyweight.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Flyweight pattern isolation exercise (text editor character rendering).
 */
class IsolationExerciseTest {
    
    private GlyphFactory factory;
    
    @BeforeEach
    void setUp() {
        factory = new GlyphFactory();
    }
    
    @Test
    @DisplayName("CharacterGlyph renders with extrinsic state")
    void testGlyphRendering() {
        CharacterGlyph glyph = new CharacterGlyph('A', "Arial", 12, FontStyle.REGULAR);
        
        String result = glyph.render(10, 20, "black");
        
        assertThat(result)
            .contains("A")
            .contains("10")
            .contains("20")
            .contains("black")
            .contains("Arial")
            .contains("12")
            .contains("REGULAR");
    }
    
    @Test
    @DisplayName("GlyphFactory caches glyphs by intrinsic state")
    void testFactoryCaching() {
        CharacterGlyph glyph1 = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        CharacterGlyph glyph2 = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        
        assertThat(glyph1).isSameAs(glyph2);  // Same object instance
        assertThat(factory.getCacheSize()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Different characters create different glyphs")
    void testDifferentCharacters() {
        CharacterGlyph glyphA = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        CharacterGlyph glyphB = factory.getGlyph('B', "Arial", 12, FontStyle.REGULAR);
        
        assertThat(glyphA).isNotSameAs(glyphB);
        assertThat(factory.getCacheSize()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Different fonts create different glyphs")
    void testDifferentFonts() {
        CharacterGlyph arial = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        CharacterGlyph times = factory.getGlyph('A', "Times", 12, FontStyle.REGULAR);
        
        assertThat(arial).isNotSameAs(times);
        assertThat(factory.getCacheSize()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Different font sizes create different glyphs")
    void testDifferentFontSizes() {
        CharacterGlyph size12 = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        CharacterGlyph size14 = factory.getGlyph('A', "Arial", 14, FontStyle.REGULAR);
        
        assertThat(size12).isNotSameAs(size14);
        assertThat(factory.getCacheSize()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Different font styles create different glyphs")
    void testDifferentFontStyles() {
        CharacterGlyph regular = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        CharacterGlyph bold = factory.getGlyph('A', "Arial", 12, FontStyle.BOLD);
        
        assertThat(regular).isNotSameAs(bold);
        assertThat(factory.getCacheSize()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Factory tracks cache hits and misses")
    void testCacheStatistics() {
        factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);  // Miss
        factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);  // Hit
        factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);  // Hit
        factory.getGlyph('B', "Arial", 12, FontStyle.REGULAR);  // Miss
        
        assertThat(factory.getCacheMisses()).isEqualTo(2);
        assertThat(factory.getCacheHits()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("CharacterInstance delegates rendering to glyph")
    void testCharacterInstanceRendering() {
        CharacterGlyph glyph = factory.getGlyph('X', "Times", 14, FontStyle.BOLD);
        CharacterInstance instance = new CharacterInstance(glyph, 100, 200, "red");
        
        String result = instance.render();
        
        assertThat(result)
            .contains("X")
            .contains("100")
            .contains("200")
            .contains("red")
            .contains("Times")
            .contains("14")
            .contains("BOLD");
    }
    
    @Test
    @DisplayName("Multiple instances can share same glyph with different extrinsic state")
    void testMultipleInstancesSameGlyph() {
        CharacterGlyph glyph = factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        
        CharacterInstance instance1 = new CharacterInstance(glyph, 10, 20, "black");
        CharacterInstance instance2 = new CharacterInstance(glyph, 50, 20, "red");
        CharacterInstance instance3 = new CharacterInstance(glyph, 90, 20, "blue");
        
        assertThat(instance1.getGlyph()).isSameAs(glyph);
        assertThat(instance2.getGlyph()).isSameAs(glyph);
        assertThat(instance3.getGlyph()).isSameAs(glyph);
        
        assertThat(instance1.render()).contains("black");
        assertThat(instance2.render()).contains("red");
        assertThat(instance3.render()).contains("blue");
    }
    
    @Test
    @DisplayName("TextDocument adds characters efficiently")
    void testTextDocumentAddCharacter() {
        TextDocument doc = new TextDocument(factory);
        
        doc.addCharacter('H', 0, 0, "black", "Arial", 12, FontStyle.REGULAR);
        doc.addCharacter('i', 10, 0, "black", "Arial", 12, FontStyle.REGULAR);
        
        assertThat(doc.getCharacterCount()).isEqualTo(2);
        assertThat(doc.getUniqueGlyphCount()).isEqualTo(2);  // 'H' and 'i'
    }
    
    @Test
    @DisplayName("TextDocument shows memory savings from flyweight sharing")
    void testMemorySavings() {
        TextDocument doc = new TextDocument(factory);
        
        // Add 1000 'A' characters at different positions
        for (int i = 0; i < 1000; i++) {
            doc.addCharacter('A', i * 10, 0, "black", "Arial", 12, FontStyle.REGULAR);
        }
        
        assertThat(doc.getCharacterCount()).isEqualTo(1000);
        assertThat(doc.getUniqueGlyphCount()).isEqualTo(1);  // Only 1 glyph shared!
        
        double savings = doc.getMemorySavingsPercentage();
        assertThat(savings).isGreaterThan(99.0);  // 999/1000 = 99.9% savings
    }
    
    @Test
    @DisplayName("TextDocument with mixed characters shows moderate savings")
    void testMixedCharacterSavings() {
        TextDocument doc = new TextDocument(factory);
        
        String text = "AAABBBCCC";  // 3 unique characters, 9 total
        for (int i = 0; i < text.length(); i++) {
            doc.addCharacter(text.charAt(i), i * 10, 0, "black", "Arial", 12, FontStyle.REGULAR);
        }
        
        assertThat(doc.getCharacterCount()).isEqualTo(9);
        assertThat(doc.getUniqueGlyphCount()).isEqualTo(3);
        
        double savings = doc.getMemorySavingsPercentage();
        assertThat(savings).isBetween(60.0, 70.0);  // (9-3)/9 = 66.67%
    }
    
    @Test
    @DisplayName("TextDocument renders all characters")
    void testDocumentRendering() {
        TextDocument doc = new TextDocument(factory);
        
        doc.addCharacter('A', 0, 0, "black", "Arial", 12, FontStyle.REGULAR);
        doc.addCharacter('B', 10, 0, "black", "Arial", 12, FontStyle.REGULAR);
        doc.addCharacter('C', 20, 0, "red", "Times", 14, FontStyle.BOLD);
        
        var renderings = doc.render();
        
        assertThat(renderings).hasSize(3);
        assertThat(renderings.get(0)).contains("A");
        assertThat(renderings.get(1)).contains("B");
        assertThat(renderings.get(2)).contains("C").contains("red").contains("Times");
    }
    
    @Test
    @DisplayName("GlyphKey equality works correctly")
    void testGlyphKeyEquality() {
        GlyphKey key1 = new GlyphKey('A', "Arial", 12, FontStyle.REGULAR);
        GlyphKey key2 = new GlyphKey('A', "Arial", 12, FontStyle.REGULAR);
        GlyphKey key3 = new GlyphKey('B', "Arial", 12, FontStyle.REGULAR);
        
        assertThat(key1).isEqualTo(key2);
        assertThat(key1).isNotEqualTo(key3);
        assertThat(key1.hashCode()).isEqualTo(key2.hashCode());
    }
    
    @Test
    @DisplayName("CharacterGlyph is immutable")
    void testGlyphImmutability() {
        CharacterGlyph glyph = new CharacterGlyph('A', "Arial", 12, FontStyle.REGULAR);
        
        // Records are immutable - this test just verifies the fields
        assertThat(glyph.value()).isEqualTo('A');
        assertThat(glyph.fontFamily()).isEqualTo("Arial");
        assertThat(glyph.fontSize()).isEqualTo(12);
        assertThat(glyph.style()).isEqualTo(FontStyle.REGULAR);
    }
    
    @Test
    @DisplayName("Factory clearCache resets state")
    void testFactoryClear() {
        factory.getGlyph('A', "Arial", 12, FontStyle.REGULAR);
        factory.getGlyph('B', "Arial", 12, FontStyle.REGULAR);
        
        assertThat(factory.getCacheSize()).isEqualTo(2);
        
        factory.clearCache();
        
        assertThat(factory.getCacheSize()).isEqualTo(0);
        assertThat(factory.getCacheHits()).isEqualTo(0);
        assertThat(factory.getCacheMisses()).isEqualTo(0);
    }
}
