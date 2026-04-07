package com.patterns.templatemethod.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Template Method Pattern - Isolation Exercise (Data Processing).
 */
@DisplayName("Template Method Pattern - Data Processing Pipeline")
class IsolationExerciseTest {
    
    private CsvDataProcessor csvProcessor;
    private JsonDataProcessor jsonProcessor;
    
    @BeforeEach
    void setUp() {
        csvProcessor = new CsvDataProcessor();
        jsonProcessor = new JsonDataProcessor();
    }
    
    // CSV Processor Tests
    
    @Test
    @DisplayName("CSV: Should parse single line")
    void testCsvParseSingleLine() {
        List<String> result = csvProcessor.process("item1");
        
        assertThat(result).containsExactly("ITEM1");
    }
    
    @Test
    @DisplayName("CSV: Should parse multiple lines")
    void testCsvParseMultipleLines() {
        List<String> result = csvProcessor.process("item1\nitem2\nitem3");
        
        assertThat(result).containsExactly("ITEM1", "ITEM2", "ITEM3");
    }
    
    @Test
    @DisplayName("CSV: Should transform to uppercase")
    void testCsvTransformToUppercase() {
        List<String> result = csvProcessor.process("hello\nworld");
        
        assertThat(result).containsExactly("HELLO", "WORLD");
    }
    
    @Test
    @DisplayName("CSV: Should save to database")
    void testCsvSaveToDatabase() {
        csvProcessor.process("data1\ndata2");
        
        assertThat(csvProcessor.getDatabase())
            .containsExactly("DATA1", "DATA2");
    }
    
    @Test
    @DisplayName("CSV: Should log after processing")
    void testCsvLogAfterProcessing() {
        csvProcessor.process("item1\nitem2\nitem3");
        
        assertThat(csvProcessor.getLog())
            .contains("Processed 3 CSV records");
    }
    
    @Test
    @DisplayName("CSV: Should trim whitespace from lines")
    void testCsvTrimWhitespace() {
        List<String> result = csvProcessor.process("  item1  \n  item2  ");
        
        assertThat(result).containsExactly("ITEM1", "ITEM2");
    }
    
    @Test
    @DisplayName("CSV: Should handle empty lines by throwing exception")
    void testCsvRejectEmptyLines() {
        assertThatThrownBy(() -> csvProcessor.process("item1\n\nitem2"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid record");
    }
    
    // JSON Processor Tests
    
    @Test
    @DisplayName("JSON: Should parse single value")
    void testJsonParseSingleValue() {
        List<String> result = jsonProcessor.process("[\"value1\"]");
        
        assertThat(result).containsExactly("value1");
    }
    
    @Test
    @DisplayName("JSON: Should parse multiple values")
    void testJsonParseMultipleValues() {
        List<String> result = jsonProcessor.process("[\"val1\",\"val2\",\"val3\"]");
        
        assertThat(result).containsExactly("val1", "val2", "val3");
    }
    
    @Test
    @DisplayName("JSON: Should not transform (shouldTransform returns false)")
    void testJsonSkipsTransformation() {
        List<String> result = jsonProcessor.process("[\"item\"]");
        
        // Should NOT be transformed (no "JSON:" prefix, original case)
        assertThat(result).containsExactly("item");
    }
    
    @Test
    @DisplayName("JSON: Should save to database")
    void testJsonSaveToDatabase() {
        jsonProcessor.process("[\"data1\",\"data2\"]");
        
        assertThat(jsonProcessor.getDatabase())
            .containsExactly("data1", "data2");
    }
    
    @Test
    @DisplayName("JSON: Should not log after processing (hook not overridden)")
    void testJsonNoLogging() {
        jsonProcessor.process("[\"item\"]");
        
        assertThat(jsonProcessor.getLog()).isEmpty();
    }
    
    @Test
    @DisplayName("JSON: Should handle values with spaces")
    void testJsonHandleSpaces() {
        List<String> result = jsonProcessor.process("[\"value 1\",\"value 2\"]");
        
        assertThat(result).containsExactly("value 1", "value 2");
    }
    
    @Test
    @DisplayName("JSON: Should trim whitespace around values")
    void testJsonTrimWhitespace() {
        List<String> result = jsonProcessor.process("[  \"item1\"  ,  \"item2\"  ]");
        
        assertThat(result).containsExactly("item1", "item2");
    }
    
    // Validation Tests
    
    @Test
    @DisplayName("Should reject null input during parsing")
    void testRejectNullInput() {
        assertThatThrownBy(() -> csvProcessor.process(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No records");
    }
    
    @Test
    @DisplayName("Should reject empty records")
    void testRejectEmptyRecords() {
        assertThatThrownBy(() -> csvProcessor.process("item1\n \nitem2"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid record");
    }
    
    // Template Method Flow Tests
    
    @Test
    @DisplayName("CSV: Should execute all steps in correct order")
    void testCsvTemplateMethodFlow() {
        csvProcessor.process("test");
        
        // Verify parse → validate → transform → save → afterProcessing
        assertThat(csvProcessor.getDatabase()).containsExactly("TEST");
        assertThat(csvProcessor.getLog()).contains("Processed 1 CSV records");
    }
    
    @Test
    @DisplayName("JSON: Should skip transformation when shouldTransform returns false")
    void testJsonSkipsTransformationStep() {
        // If transformation was applied, we'd expect "JSON:value"
        // Since it's skipped, we expect unchanged "value"
        List<String> result = jsonProcessor.process("[\"value\"]");
        
        assertThat(result).containsExactly("value");
    }
    
    @Test
    @DisplayName("CSV: Should process complete workflow")
    void testCsvCompleteWorkflow() {
        String input = "apple\nbanana\ncherry";
        
        List<String> result = csvProcessor.process(input);
        
        assertThat(result).containsExactly("APPLE", "BANANA", "CHERRY");
        assertThat(csvProcessor.getDatabase()).containsExactly("APPLE", "BANANA", "CHERRY");
        assertThat(csvProcessor.getLog()).contains("Processed 3 CSV records");
    }
    
    @Test
    @DisplayName("JSON: Should process complete workflow")
    void testJsonCompleteWorkflow() {
        String input = "[\"apple\",\"banana\",\"cherry\"]";
        
        List<String> result = jsonProcessor.process(input);
        
        assertThat(result).containsExactly("apple", "banana", "cherry");
        assertThat(jsonProcessor.getDatabase()).containsExactly("apple", "banana", "cherry");
        assertThat(jsonProcessor.getLog()).isEmpty(); // No logging hook
    }
}
