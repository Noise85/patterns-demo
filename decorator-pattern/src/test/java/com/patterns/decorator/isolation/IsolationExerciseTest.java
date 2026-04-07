package com.patterns.decorator.isolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for Exercise 1: Pattern in Isolation.
 * Tests notification decorator composition and stacking.
 */
@DisplayName("Decorator Pattern - Isolation Exercise Tests")
class IsolationExerciseTest {
    
    @Test
    @DisplayName("BasicNotifier sends message unchanged")
    void basicNotifierSendsUnchanged() {
        Notifier notifier = new BasicNotifier();
        
        assertThat(notifier.send("Hello")).isEqualTo("Hello");
        assertThat(notifier.send("Test message")).isEqualTo("Test message");
        assertThat(notifier.send("")).isEqualTo("");
    }
    
    @Test
    @DisplayName("EncryptionDecorator encrypts with Caesar cipher")
    void encryptionDecoratorEncryptsMessage() {
        Notifier notifier = new EncryptionDecorator(new BasicNotifier());
        
        assertThat(notifier.send("ABC")).isEqualTo("ENCRYPTED[DEF]");
        assertThat(notifier.send("xyz")).isEqualTo("ENCRYPTED[abc]");
        assertThat(notifier.send("Hello")).isEqualTo("ENCRYPTED[Khoor]");
    }
    
    @Test
    @DisplayName("EncryptionDecorator wraps alphabet correctly")
    void encryptionWrapsAlphabet() {
        Notifier notifier = new EncryptionDecorator(new BasicNotifier());
        
        assertThat(notifier.send("XYZ")).isEqualTo("ENCRYPTED[ABC]");
        assertThat(notifier.send("xyz")).isEqualTo("ENCRYPTED[abc]");
    }
    
    @Test
    @DisplayName("EncryptionDecorator preserves non-letters")
    void encryptionPreservesNonLetters() {
        Notifier notifier = new EncryptionDecorator(new BasicNotifier());
        
        assertThat(notifier.send("123")).isEqualTo("ENCRYPTED[123]");
        assertThat(notifier.send("Hello, World!")).isEqualTo("ENCRYPTED[Khoor, Zruog!]");
        assertThat(notifier.send("a b c")).isEqualTo("ENCRYPTED[d e f]");
    }
    
    @Test
    @DisplayName("CompressionDecorator compresses repeated characters")
    void compressionDecoratorCompresses() {
        Notifier notifier = new CompressionDecorator(new BasicNotifier());
        
        assertThat(notifier.send("aaa")).isEqualTo("COMPRESSED[a3]");
        assertThat(notifier.send("aaabbc")).isEqualTo("COMPRESSED[a3b2c]");
        assertThat(notifier.send("abc")).isEqualTo("COMPRESSED[abc]");
    }
    
    @Test
    @DisplayName("CompressionDecorator handles complex patterns")
    void compressionHandlesComplexPatterns() {
        Notifier notifier = new CompressionDecorator(new BasicNotifier());
        
        assertThat(notifier.send("hello")).isEqualTo("COMPRESSED[hel2o]");
        assertThat(notifier.send("aabbccdd")).isEqualTo("COMPRESSED[a2b2c2d2]");
        assertThat(notifier.send("aaa bbb")).isEqualTo("COMPRESSED[a3 b3]");
    }
    
    @Test
    @DisplayName("HtmlDecorator wraps result in HTML tags")
    void htmlDecoratorWrapsInTags() {
        Notifier notifier = new HtmlDecorator(new BasicNotifier());
        
        assertThat(notifier.send("Hello"))
            .isEqualTo("<html><body>Hello</body></html>");
        assertThat(notifier.send("Test"))
            .isEqualTo("<html><body>Test</body></html>");
    }
    
    @Test
    @DisplayName("TimestampDecorator adds timestamp prefix")
    void timestampDecoratorAddsPrefix() {
        Instant fixedTime = Instant.parse("2024-01-15T10:30:00Z");
        Notifier notifier = new TimestampDecorator(new BasicNotifier(), fixedTime);
        
        assertThat(notifier.send("Hello"))
            .isEqualTo("[2024-01-15T10:30:00Z] Hello");
        assertThat(notifier.send("Test"))
            .isEqualTo("[2024-01-15T10:30:00Z] Test");
    }
    
    @Test
    @DisplayName("Decorators can be stacked - Encryption then Compression")
    void decoratorsStackEncryptionThenCompression() {
        Notifier notifier = new CompressionDecorator(
            new EncryptionDecorator(
                new BasicNotifier()
            )
        );
        
        // "AAA" -> encrypted to "DDD" -> compressed to "D3" -> wrapped
        assertThat(notifier.send("AAA"))
            .isEqualTo("COMPRESSED[ENCRYPTED[DDD]]");
    }
    
    @Test
    @DisplayName("Decorators can be stacked - Compression then Encryption")
    void decoratorsStackCompressionThenEncryption() {
        Notifier notifier = new EncryptionDecorator(
            new CompressionDecorator(
                new BasicNotifier()
            )
        );
        
        // "AAA" -> compressed to "A3" -> encrypted to "D3" -> wrapped
        String result = notifier.send("AAA");
        assertThat(result).startsWith("ENCRYPTED[");
        assertThat(result).contains("COMPRESSED[");
    }
    
    @Test
    @DisplayName("Triple decoration - HTML wraps encrypted compressed message")
    void tripleDecorationHtmlEncryptionCompression() {
        Notifier notifier = new HtmlDecorator(
            new EncryptionDecorator(
                new CompressionDecorator(
                    new BasicNotifier()
                )
            )
        );
        
        String result = notifier.send("AAA");
        assertThat(result).startsWith("<html><body>");
        assertThat(result).endsWith("</body></html>");
        assertThat(result).contains("ENCRYPTED[");
        assertThat(result).contains("COMPRESSED[");
    }
    
    @Test
    @DisplayName("Quad decoration - Timestamp, HTML, Encryption, Compression")
    void quadDecorationAllLayers() {
        Instant fixedTime = Instant.parse("2024-01-15T10:30:00Z");
        Notifier notifier = new TimestampDecorator(
            new HtmlDecorator(
                new EncryptionDecorator(
                    new CompressionDecorator(
                        new BasicNotifier()
                    )
                )
            ),
            fixedTime
        );
        
        String result = notifier.send("AAABBC");
        assertThat(result).startsWith("[2024-01-15T10:30:00Z]");
        assertThat(result).contains("<html><body>");
        assertThat(result).contains("ENCRYPTED[");
        assertThat(result).contains("COMPRESSED[");
        assertThat(result).endsWith("</body></html>");
    }
    
    @Test
    @DisplayName("Different stacking orders produce different results")
    void differentOrdersDifferentResults() {
        // Order 1: Compress then Encrypt
        Notifier order1 = new EncryptionDecorator(
            new CompressionDecorator(
                new BasicNotifier()
            )
        );
        
        // Order 2: Encrypt then Compress
        Notifier order2 = new CompressionDecorator(
            new EncryptionDecorator(
                new BasicNotifier()
            )
        );
        
        String result1 = order1.send("AAA");
        String result2 = order2.send("AAA");
        
        assertThat(result1).isNotEqualTo(result2);
    }
    
    @Test
    @DisplayName("Decorators are immutable - multiple calls independent")
    void decoratorsAreImmutable() {
        Notifier notifier = new EncryptionDecorator(new BasicNotifier());
        
        String result1 = notifier.send("ABC");
        String result2 = notifier.send("XYZ");
        String result3 = notifier.send("ABC"); // same as first
        
        assertThat(result1).isEqualTo("ENCRYPTED[DEF]");
        assertThat(result2).isEqualTo("ENCRYPTED[ABC]");
        assertThat(result3).isEqualTo(result1); // consistent results
    }
    
    @Test
    @DisplayName("Empty message handled correctly by all decorators")
    void emptyMessageHandling() {
        Notifier encryption = new EncryptionDecorator(new BasicNotifier());
        Notifier compression = new CompressionDecorator(new BasicNotifier());
        Notifier html = new HtmlDecorator(new BasicNotifier());
        
        assertThat(encryption.send("")).isEqualTo("ENCRYPTED[]");
        assertThat(compression.send("")).isEqualTo("COMPRESSED[]");
        assertThat(html.send("")).isEqualTo("<html><body></body></html>");
    }
    
    @Test
    @DisplayName("Single character handled correctly")
    void singleCharacterHandling() {
        Notifier encryption = new EncryptionDecorator(new BasicNotifier());
        Notifier compression = new CompressionDecorator(new BasicNotifier());
        
        assertThat(encryption.send("A")).isEqualTo("ENCRYPTED[D]");
        assertThat(compression.send("A")).isEqualTo("COMPRESSED[A]"); // no compression
    }
}
