package com.patterns.chainofresponsibility.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Chain of Responsibility Pattern - Simulation Exercise (Support Tickets).
 */
@DisplayName("Chain of Responsibility Pattern - Support Ticket Routing")
class SimulationExerciseTest {
    
    private L1SupportHandler l1Handler;
    private L2SupportHandler l2Handler;
    private L3SupportHandler l3Handler;
    private EngineeringHandler engineeringHandler;
    private SupportHandler chain;
    private TicketAudit audit;
    
    @BeforeEach
    void setUp() {
        l1Handler = new L1SupportHandler();
        l2Handler = new L2SupportHandler();
        l3Handler = new L3SupportHandler();
        engineeringHandler = new EngineeringHandler();
        audit = new TicketAudit();
    }
    
    @Test
    @DisplayName("Should route low complexity ticket to L1")
    void testLowComplexityToL1() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T001",
            "Password reset",
            "User forgot password",
            TicketCategory.ACCOUNT,
            15,
            Priority.LOW,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L1 Support");
        assertThat(result.resolution()).contains("L1");
        assertThat(result.processingTimeMillis()).isGreaterThanOrEqualTo(0);
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESOLVED);
    }
    
    @Test
    @DisplayName("Should route medium complexity ticket to L2")
    void testMediumComplexityToL2() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T002",
            "Invoice discrepancy",
            "Charged incorrectly",
            TicketCategory.BILLING,
            45,
            Priority.MEDIUM,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L2 Support");
        assertThat(result.resolution()).contains("L2");
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESOLVED);
    }
    
    @Test
    @DisplayName("Should route high complexity ticket to L3")
    void testHighComplexityToL3() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T003",
            "API performance issue",
            "API calls timing out",
            TicketCategory.TECHNICAL,
            75,
            Priority.HIGH,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L3 Support");
        assertThat(result.resolution()).contains("L3");
    }
    
    @Test
    @DisplayName("Should route critical complexity ticket to Engineering")
    void testCriticalComplexityToEngineering() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T004",
            "Production database corruption",
            "Data integrity issue",
            TicketCategory.TECHNICAL,
            95,
            Priority.CRITICAL,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("Engineering");
        assertThat(result.resolution()).contains("Engineering");
    }
    
    @Test
    @DisplayName("Should route CRITICAL priority to Engineering regardless of complexity")
    void testCriticalPriorityOverride() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        // Low complexity but CRITICAL priority
        Ticket ticket = new Ticket(
            "T005",
            "Production outage",
            "Service down",
            TicketCategory.TECHNICAL,
            20,  // Low complexity
            Priority.CRITICAL,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("Engineering");
    }
    
    @Test
    @DisplayName("Should route BILLING category to L2 regardless of complexity")
    void testBillingCategoryOverride() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        // Low complexity but BILLING category
        Ticket ticket = new Ticket(
            "T006",
            "Billing question",
            "Need invoice explanation",
            TicketCategory.BILLING,
            10,  // Low complexity
            Priority.LOW,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L2 Support");
    }
    
    @Test
    @DisplayName("Should route GENERAL category to L1")
    void testGeneralCategoryToL1() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T007",
            "General inquiry",
            "How to use feature X?",
            TicketCategory.GENERAL,
            50,  // Medium complexity but GENERAL category
            Priority.LOW,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L1 Support");
    }
    
    @Test
    @DisplayName("Should route TECHNICAL + HIGH priority to L3")
    void testTechnicalHighPriorityToL3() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T008",
            "Important bug",
            "Feature not working for VIP customer",
            TicketCategory.TECHNICAL,
            50,  // Medium complexity
            Priority.HIGH,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isEqualTo("L3 Support");
    }
    
    @Test
    @DisplayName("Should handle boundary complexity values")
    void testBoundaryComplexityValues() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        // Exactly at L1/L2 boundary (30)
        Ticket ticket30 = new Ticket("T009", "Test", "Test", TicketCategory.GENERAL, 30, Priority.LOW, LocalDateTime.now());
        HandlingResult result30 = chain.handle(ticket30);
        assertThat(result30.handlerName()).isIn("L1 Support", "L2 Support");
        
        // Exactly at L2/L3 boundary (70)
        Ticket ticket70 = new Ticket("T010", "Test", "Test", TicketCategory.TECHNICAL, 70, Priority.MEDIUM, LocalDateTime.now());
        HandlingResult result70 = chain.handle(ticket70);
        assertThat(result70.handlerName()).isIn("L2 Support", "L3 Support");
        
        // Exactly at L3/Engineering boundary (90)
        Ticket ticket90 = new Ticket("T011", "Test", "Test", TicketCategory.TECHNICAL, 90, Priority.HIGH, LocalDateTime.now());
        HandlingResult result90 = chain.handle(ticket90);
        assertThat(result90.handlerName()).isIn("L3 Support", "Engineering");
    }
    
    @Test
    @DisplayName("Should track handler statistics")
    void testHandlerStatistics() {
        chain = l1Handler
                .setNext(l2Handler)
                .setNext(l3Handler)
                .setNext(engineeringHandler);
        
        // L1 should handle simple tickets
        chain.handle(createTicket("T1", TicketCategory.GENERAL, 10, Priority.LOW));
        assertThat(l1Handler.getHandledCount()).isEqualTo(1);
        assertThat(l1Handler.getEscalatedCount()).isEqualTo(0);
        
        // L2-level ticket should escalate from L1
        chain.handle(createTicket("T2", TicketCategory.BILLING, 50, Priority.MEDIUM));
        assertThat(l1Handler.getEscalatedCount()).isGreaterThan(0);
        assertThat(l2Handler.getHandledCount()).isGreaterThan(0);
    }
    
    @Test
    @DisplayName("Should support escalation override")
    void testEscalationOverride() {
        chain = l1Handler
                .setNext(l2Handler)
                .setNext(l3Handler);
        
        // Simple ticket that L1 could handle
        Ticket ticket = createTicket("T012", TicketCategory.ACCOUNT, 15, Priority.LOW);
        
        // Force escalation - skip L1
        HandlingResult result = chain.handleWithEscalation(ticket, true);
        
        assertThat(result.handlerName()).isNotEqualTo("L1 Support");
        assertThat(result.handlerName()).isEqualTo("L2 Support");
    }
    
    @Test
    @DisplayName("Should throw exception for unhandled ticket")
    void testUnhandledTicket() {
        // Create incomplete chain (only L1)
        chain = l1Handler;
        
        // Ticket that L1 cannot handle (complexity too high, wrong category)
        Ticket ticket = new Ticket(
            "T013",
            "Complex technical issue",
            "Advanced problem",
            TicketCategory.TECHNICAL,
            95,
            Priority.HIGH,
            LocalDateTime.now()
        );
        
        assertThatThrownBy(() -> chain.handle(ticket))
                .isInstanceOf(UnhandledTicketException.class)
                .hasMessageContaining("T013");
    }
    
    @Test
    @DisplayName("Should update ticket status during processing")
    void testTicketStatusUpdates() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = createTicket("T014", TicketCategory.GENERAL, 10, Priority.LOW);
        
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.OPEN);
        
        chain.handle(ticket);
        
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESOLVED);
    }
    
    @Test
    @DisplayName("Should assign ticket to handler who processed it")
    void testTicketAssignment() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = createTicket("T015", TicketCategory.BILLING, 40, Priority.MEDIUM);
        
        chain.handle(ticket);
        
        assertThat(ticket.getAssignedTo()).isEqualTo("L2 Support");
    }
    
    @Test
    @DisplayName("Should build priority chain correctly")
    void testPriorityChainBuilding() {
        chain = SupportChainBuilder.buildPriorityChain();
        
        assertThat(chain).isNotNull();
        assertThat(chain).isInstanceOf(EngineeringHandler.class);
        
        // Critical ticket should be handled by first handler (Engineering)
        Ticket criticalTicket = createTicket("T016", TicketCategory.TECHNICAL, 95, Priority.CRITICAL);
        HandlingResult result = chain.handle(criticalTicket);
        
        assertThat(result.handlerName()).isEqualTo("Engineering");
    }
    
    @Test
    @DisplayName("Should build custom chain")
    void testCustomChainBuilding() {
        List<SupportHandler> handlers = List.of(
            new L3SupportHandler(),
            new L1SupportHandler(),
            new EngineeringHandler()
        );
        
        chain = SupportChainBuilder.buildCustomChain(handlers);
        
        assertThat(chain).isNotNull();
        assertThat(chain).isInstanceOf(L3SupportHandler.class);
    }
    
    @Test
    @DisplayName("Should record audit trail")
    void testAuditTrail() {
        audit.recordHandling("T017", "L1 Support", true);
        audit.recordHandling("T017", "L2 Support", false);
        audit.recordHandling("T018", "Engineering", true);
        
        List<String> historyT017 = audit.getHandlerHistory("T017");
        assertThat(historyT017).hasSize(2);
        
        List<String> historyT018 = audit.getHandlerHistory("T018");
        assertThat(historyT018).hasSize(1);
    }
    
    @Test
    @DisplayName("Should calculate handler statistics from audit")
    void testAuditStatistics() {
        audit.recordHandling("T019", "L1 Support", true);
        audit.recordHandling("T020", "L1 Support", true);
        audit.recordHandling("T021", "L2 Support", true);
        audit.recordHandling("T022", "Engineering", true);
        
        var stats = audit.getHandlerStatistics();
        
        assertThat(stats).containsEntry("L1 Support", 2);
        assertThat(stats).containsEntry("L2 Support", 1);
        assertThat(stats).containsEntry("Engineering", 1);
    }
    
    @Test
    @DisplayName("Should handle empty audit history gracefully")
    void testEmptyAuditHistory() {
        List<String> history = audit.getHandlerHistory("NONEXISTENT");
        assertThat(history).isEmpty();
    }
    
    @Test
    @DisplayName("Should verify multiple tickets processing")
    void testMultipleTicketProcessing() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket t1 = createTicket("T023", TicketCategory.GENERAL, 10, Priority.LOW);
        Ticket t2 = createTicket("T024", TicketCategory.BILLING, 50, Priority.MEDIUM);
        Ticket t3 = createTicket("T025", TicketCategory.TECHNICAL, 80, Priority.HIGH);
        Ticket t4 = createTicket("T026", TicketCategory.TECHNICAL, 95, Priority.CRITICAL);
        
        HandlingResult r1 = chain.handle(t1);
        HandlingResult r2 = chain.handle(t2);
        HandlingResult r3 = chain.handle(t3);
        HandlingResult r4 = chain.handle(t4);
        
        assertThat(r1.handlerName()).isEqualTo("L1 Support");
        assertThat(r2.handlerName()).isEqualTo("L2 Support");
        assertThat(r3.handlerName()).isEqualTo("L3 Support");
        assertThat(r4.handlerName()).isEqualTo("Engineering");
    }
    
    @Test
    @DisplayName("Should measure processing time")
    void testProcessingTime() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = createTicket("T027", TicketCategory.ACCOUNT, 15, Priority.LOW);
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.processingTimeMillis()).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    @DisplayName("Should verify handler complexity ranges")
    void testHandlerComplexityRanges() {
        // Test L1 range (0-29)
        assertThat(createAndHandle(15, TicketCategory.GENERAL, Priority.LOW).handlerName())
                .isEqualTo("L1 Support");
        
        // Test L2 range (30-69)
        assertThat(createAndHandle(50, TicketCategory.BILLING, Priority.MEDIUM).handlerName())
                .isEqualTo("L2 Support");
        
        // Test L3 range (70-89)
        assertThat(createAndHandle(75, TicketCategory.TECHNICAL, Priority.HIGH).handlerName())
                .isEqualTo("L3 Support");
        
        // Test Engineering range (90-100)
        assertThat(createAndHandle(95, TicketCategory.TECHNICAL, Priority.CRITICAL).handlerName())
                .isEqualTo("Engineering");
    }
    
    @Test
    @DisplayName("Should handle ticket with all fields populated")
    void testCompleteTicketHandling() {
        chain = SupportChainBuilder.buildDefaultChain();
        
        Ticket ticket = new Ticket(
            "T028",
            "Complete test ticket",
            "This is a detailed description of the issue",
            TicketCategory.ACCOUNT,
            25,
            Priority.MEDIUM,
            LocalDateTime.now()
        );
        
        HandlingResult result = chain.handle(ticket);
        
        assertThat(result.handled()).isTrue();
        assertThat(result.handlerName()).isNotBlank();
        assertThat(result.resolution()).isNotBlank();
        assertThat(ticket.getId()).isEqualTo("T028");
        assertThat(ticket.getTitle()).isEqualTo("Complete test ticket");
        assertThat(ticket.getCategory()).isEqualTo(TicketCategory.ACCOUNT);
    }
    
    @Test
    @DisplayName("Should verify exception contains ticket information")
    void testUnhandledExceptionDetails() {
        chain = l1Handler;  // Only L1, cannot handle complex tickets
        
        Ticket ticket = createTicket("T029", TicketCategory.TECHNICAL, 95, Priority.HIGH);
        
        assertThatThrownBy(() -> chain.handle(ticket))
                .isInstanceOf(UnhandledTicketException.class)
                .hasMessageContaining("T029")
                .extracting(e -> ((UnhandledTicketException) e).getTicket())
                .isEqualTo(ticket);
    }
    
    // Helper methods
    
    private Ticket createTicket(String id, TicketCategory category, int complexity, Priority priority) {
        return new Ticket(
            id,
            "Test ticket " + id,
            "Test description",
            category,
            complexity,
            priority,
            LocalDateTime.now()
        );
    }
    
    private HandlingResult createAndHandle(int complexity, TicketCategory category, Priority priority) {
        chain = SupportChainBuilder.buildDefaultChain();
        Ticket ticket = createTicket("TEST", category, complexity, priority);
        return chain.handle(ticket);
    }
}
