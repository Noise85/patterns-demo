package com.patterns.chainofresponsibility.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Chain of Responsibility Pattern - Isolation Exercise (Logging).
 */
@DisplayName("Chain of Responsibility Pattern - Logging System")
class IsolationExerciseTest {
    
    private ConsoleLogger consoleLogger;
    private FileLogger fileLogger;
    private EmailLogger emailLogger;
    private AlertLogger alertLogger;
    private LogHandler chain;
    
    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        consoleLogger = new ConsoleLogger();
        fileLogger = new FileLogger("app.log");
        emailLogger = new EmailLogger("admin@company.com");
        alertLogger = new AlertLogger("PagerDuty");
    }
    
    @Test
    @DisplayName("Should handle DEBUG message in ConsoleLogger only")
    void testDebugMessage() {
        chain = consoleLogger
                .setNext(fileLogger)
                .setNext(emailLogger)
                .setNext(alertLogger);
        
        LogMessage message = new LogMessage(LogLevel.DEBUG, "Application started", LocalDateTime.now());
        chain.handle(message);
        
        assertThat(consoleLogger.getWrittenMessages())
                .hasSize(1)
                .first()
                .asString()
                .contains("[CONSOLE]")
                .contains("[DEBUG]")
                .contains("Application started");
        
        assertThat(fileLogger.getWrittenMessages()).isEmpty();
        assertThat(emailLogger.getWrittenMessages()).isEmpty();
        assertThat(alertLogger.getWrittenMessages()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle INFO message in ConsoleLogger and FileLogger")
    void testInfoMessage() {
        chain = consoleLogger
                .setNext(fileLogger)
                .setNext(emailLogger)
                .setNext(alertLogger);
        
        LogMessage message = new LogMessage(LogLevel.INFO, "User logged in", LocalDateTime.now());
        chain.handle(message);
        
        assertThat(consoleLogger.getWrittenMessages())
                .hasSize(1)
                .first()
                .asString()
                .contains("[CONSOLE]")
                .contains("[INFO]");
        
        assertThat(fileLogger.getWrittenMessages())
                .hasSize(1)
                .first()
                .asString()
                .contains("[FILE: app.log]")
                .contains("[INFO]")
                .contains("User logged in");
        
        assertThat(emailLogger.getWrittenMessages()).isEmpty();
        assertThat(alertLogger.getWrittenMessages()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle WARN message in three handlers")
    void testWarnMessage() {
        chain = consoleLogger
                .setNext(fileLogger)
                .setNext(emailLogger)
                .setNext(alertLogger);
        
        LogMessage message = new LogMessage(LogLevel.WARN, "High memory usage", LocalDateTime.now());
        chain.handle(message);
        
        assertThat(consoleLogger.getWrittenMessages()).hasSize(1);
        assertThat(fileLogger.getWrittenMessages()).hasSize(1);
        
        assertThat(emailLogger.getWrittenMessages())
                .hasSize(1)
                .first()
                .asString()
                .contains("[EMAIL: admin@company.com]")
                .contains("[WARN]")
                .contains("High memory usage");
        
        assertThat(alertLogger.getWrittenMessages()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle ERROR message in all four handlers")
    void testErrorMessage() {
        chain = consoleLogger
                .setNext(fileLogger)
                .setNext(emailLogger)
                .setNext(alertLogger);
        
        LogMessage message = new LogMessage(LogLevel.ERROR, "Database connection failed", LocalDateTime.now());
        chain.handle(message);
        
        assertThat(consoleLogger.getWrittenMessages()).hasSize(1);
        assertThat(fileLogger.getWrittenMessages()).hasSize(1);
        assertThat(emailLogger.getWrittenMessages()).hasSize(1);
        
        assertThat(alertLogger.getWrittenMessages())
                .hasSize(1)
                .first()
                .asString()
                .contains("[ALERT: PagerDuty]")
                .contains("[ERROR]")
                .contains("Database connection failed");
    }
    
    @Test
    @DisplayName("Should build chain fluently")
    void testFluentChainBuilding() {
        chain = new ConsoleLogger()
                .setNext(new FileLogger("test.log"))
                .setNext(new EmailLogger("test@example.com"))
                .setNext(new AlertLogger("AlertService"));
        
        assertThat(chain).isNotNull();
        assertThat(chain).isInstanceOf(ConsoleLogger.class);
    }
    
    @Test
    @DisplayName("Should use LoggerChain factory")
    void testLoggerChainFactory() {
        chain = LoggerChain.buildDefaultChain();
        
        assertThat(chain).isNotNull();
        assertThat(chain).isInstanceOf(ConsoleLogger.class);
        
        LogMessage infoMessage = new LogMessage(LogLevel.INFO, "Test message", LocalDateTime.now());
        chain.handle(infoMessage);
        
        // Should not throw exception
    }
    
    @Test
    @DisplayName("Should handle multiple messages correctly")
    void testMultipleMessages() {
        chain = consoleLogger
                .setNext(fileLogger)
                .setNext(emailLogger)
                .setNext(alertLogger);
        
        chain.handle(new LogMessage(LogLevel.DEBUG, "Debug 1", LocalDateTime.now()));
        chain.handle(new LogMessage(LogLevel.INFO, "Info 1", LocalDateTime.now()));
        chain.handle(new LogMessage(LogLevel.WARN, "Warn 1", LocalDateTime.now()));
        chain.handle(new LogMessage(LogLevel.ERROR, "Error 1", LocalDateTime.now()));
        
        // All four messages should appear in console (DEBUG level handles all)
        assertThat(consoleLogger.getWrittenMessages()).hasSize(4);
        
        // INFO, WARN, ERROR should appear in file
        assertThat(fileLogger.getWrittenMessages()).hasSize(3);
        
        // WARN, ERROR should appear in email
        assertThat(emailLogger.getWrittenMessages()).hasSize(2);
        
        // Only ERROR should appear in alert
        assertThat(alertLogger.getWrittenMessages()).hasSize(1);
    }
    
    @Test
    @DisplayName("Should work with partial chain")
    void testPartialChain() {
        chain = consoleLogger.setNext(fileLogger);
        
        LogMessage errorMessage = new LogMessage(LogLevel.ERROR, "Error occurred", LocalDateTime.now());
        chain.handle(errorMessage);
        
        // Should not fail even though EmailLogger and AlertLogger are missing
        assertThat(consoleLogger.getWrittenMessages()).hasSize(1);
        assertThat(fileLogger.getWrittenMessages()).hasSize(1);
    }
    
    @Test
    @DisplayName("Should work with single handler")
    void testSingleHandler() {
        chain = consoleLogger;
        
        LogMessage message = new LogMessage(LogLevel.INFO, "Single handler test", LocalDateTime.now());
        chain.handle(message);
        
        assertThat(consoleLogger.getWrittenMessages()).hasSize(1);
    }
    
    @Test
    @DisplayName("Should preserve log message details")
    void testLogMessageDetails() {
        chain = fileLogger;
        
        String testMessage = "Detailed test message";
        LogMessage message = new LogMessage(LogLevel.INFO, testMessage, LocalDateTime.now());
        chain.handle(message);
        
        assertThat(fileLogger.getWrittenMessages())
                .first()
                .asString()
                .contains(testMessage);
    }
    
    @Test
    @DisplayName("Should verify severity filtering in handler")
    void testSeverityFiltering() {
        // Start chain at FileLogger (skipping ConsoleLogger)
        chain = fileLogger.setNext(emailLogger);
        
        // DEBUG message should not be processed by any handler in this chain
        LogMessage debugMessage = new LogMessage(LogLevel.DEBUG, "Debug message", LocalDateTime.now());
        chain.handle(debugMessage);
        
        assertThat(fileLogger.getWrittenMessages()).isEmpty();
        assertThat(emailLogger.getWrittenMessages()).isEmpty();
        
        // INFO message should be processed
        LogMessage infoMessage = new LogMessage(LogLevel.INFO, "Info message", LocalDateTime.now());
        chain.handle(infoMessage);
        
        assertThat(fileLogger.getWrittenMessages()).hasSize(1);
    }
    
    @Test
    @DisplayName("Should verify handler configuration")
    void testHandlerConfiguration() {
        assertThat(fileLogger.getFilename()).isEqualTo("app.log");
        assertThat(emailLogger.getEmailAddress()).isEqualTo("admin@company.com");
        assertThat(alertLogger.getAlertService()).isEqualTo("PagerDuty");
    }
    
    @Test
    @DisplayName("Should verify log level severity values")
    void testLogLevelSeverity() {
        assertThat(LogLevel.DEBUG.getSeverity()).isEqualTo(1);
        assertThat(LogLevel.INFO.getSeverity()).isEqualTo(2);
        assertThat(LogLevel.WARN.getSeverity()).isEqualTo(3);
        assertThat(LogLevel.ERROR.getSeverity()).isEqualTo(4);
        
        assertThat(LogLevel.ERROR.getSeverity()).isGreaterThan(LogLevel.DEBUG.getSeverity());
    }
    
    @Test
    @DisplayName("Should verify log message creation")
    void testLogMessageCreation() {
        LocalDateTime now = LocalDateTime.now();
        LogMessage message = new LogMessage(LogLevel.INFO, "Test message", now);
        
        assertThat(message.level()).isEqualTo(LogLevel.INFO);
        assertThat(message.message()).isEqualTo("Test message");
        assertThat(message.timestamp()).isEqualTo(now);
    }
}
