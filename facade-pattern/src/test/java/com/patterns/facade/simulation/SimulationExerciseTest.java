package com.patterns.facade.simulation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for Exercise 2: Real-World Simulation.
 * Tests order processing facade with complex subsystem coordination.
 */
@DisplayName("Facade Pattern - Simulation Exercise Tests")
class SimulationExerciseTest {
    
    private InventoryService inventoryService;
    private PaymentService paymentService;
    private ShippingService shippingService;
    private OrderRepository orderRepository;
    private NotificationService notificationService;
    private OrderProcessingFacade facade;
    
    private Address testAddress;
    private PaymentMethod testPayment;
    
    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        paymentService = new PaymentService();
        shippingService = new ShippingService();
        orderRepository = new OrderRepository();
        notificationService = new NotificationService();
        
        facade = new OrderProcessingFacade(
            inventoryService, paymentService, shippingService,
            orderRepository, notificationService
        );
        
        // Initialize test data
        inventoryService.initializeStock("PROD-001", 100);
        inventoryService.initializeStock("PROD-002", 50);
        inventoryService.initializeStock("PROD-003", 10);
        
        testAddress = new Address(
            "123 Main St", "Springfield", "IL", "62701", "USA"
        );
        
        testPayment = new PaymentMethod("CREDIT_CARD", "tok_valid_12345");
    }
    
    // ===== Happy Path Tests =====
    
    @Test
    @DisplayName("Process order successfully with all subsystems")
    void processOrderSuccessfully() {
        OrderRequest request = new OrderRequest(
            "ORD-001",
            "customer-123",
            List.of(new OrderItem("PROD-001", 2, 2999)),
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getOrderId()).isEqualTo("ORD-001");
        assertThat(result.getTrackingNumber()).isNotNull();
        assertThat(result.getEstimatedDelivery()).isNotNull();
        
        // Verify inventory deducted
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(98);
        
        // Verify order created
        assertThat(orderRepository.getOrder("ORD-001")).isNotNull();
        
        // Verify notification sent
        assertThat(notificationService.getSentNotifications())
            .anyMatch(n -> n.contains("confirmation") && n.contains("ORD-001"));
    }
    
    @Test
    @DisplayName("Process order with multiple items")
    void processOrderWithMultipleItems() {
        OrderRequest request = new OrderRequest(
            "ORD-002",
            "customer-456",
            List.of(
                new OrderItem("PROD-001", 3, 2999),
                new OrderItem("PROD-002", 1, 4999)
            ),
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(97);
        assertThat(inventoryService.getAvailableStock("PROD-002")).isEqualTo(49);
    }
    
    // ===== Inventory Failure Tests =====
    
    @Test
    @DisplayName("Reject order when product out of stock")
    void rejectOrderWhenOutOfStock() {
        OrderRequest request = new OrderRequest(
            "ORD-003",
            "customer-789",
            List.of(new OrderItem("PROD-003", 20, 1999)), // Only 10 available
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("stock");
        
        // Verify no inventory deducted
        assertThat(inventoryService.getAvailableStock("PROD-003")).isEqualTo(10);
        
        // Verify failure notification sent
        assertThat(notificationService.getSentNotifications())
            .anyMatch(n -> n.contains("failure") || n.contains("Failed"));
    }
    
    @Test
    @DisplayName("Reject order when one of multiple items out of stock")
    void rejectOrderWhenOneItemOutOfStock() {
        OrderRequest request = new OrderRequest(
            "ORD-004",
            "customer-101",
            List.of(
                new OrderItem("PROD-001", 2, 2999), // Available
                new OrderItem("PROD-003", 15, 1999)  // Not available
            ),
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isFalse();
        
        // Verify NO inventory deducted for either product
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(100);
        assertThat(inventoryService.getAvailableStock("PROD-003")).isEqualTo(10);
    }
    
    // ===== Payment Failure Tests =====
    
    @Test
    @DisplayName("Rollback reservation when payment fails")
    void rollbackReservationOnPaymentFailure() {
        // Payment will fail for amounts ending in 13 cents
        OrderRequest request = new OrderRequest(
            "ORD-005",
            "customer-202",
            List.of(new OrderItem("PROD-001", 1, 1313)), // Amount ends in 13
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("payment");
        
        // Verify inventory NOT deducted (reservation was released)
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(100);
        
        // Verify failure notification
        assertThat(notificationService.getSentNotifications())
            .anyMatch(n -> n.contains("failure"));
    }
    
    // ===== Idempotency Tests =====
    
    @Test
    @DisplayName("Process same order twice returns cached result")
    void idempotencyPreventsDuplicateProcessing() {
        OrderRequest request = new OrderRequest(
            "ORD-006",
            "customer-303",
            List.of(new OrderItem("PROD-001", 5, 2999)),
            testAddress,
            testPayment
        );
        
        OrderResult firstResult = facade.processOrder(request);
        OrderResult secondResult = facade.processOrder(request); // duplicate
        
        assertThat(firstResult.isSuccess()).isTrue();
        assertThat(secondResult.isSuccess()).isTrue();
        
        // Verify inventory only deducted once
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(95);
        
        // Same tracking number
        assertThat(secondResult.getTrackingNumber())
            .isEqualTo(firstResult.getTrackingNumber());
    }
    
    @Test
    @DisplayName("Different orders processed independently")
    void differentOrdersProcessedIndependently() {
        OrderRequest request1 = new OrderRequest(
            "ORD-007",
            "customer-404",
            List.of(new OrderItem("PROD-001", 2, 2999)),
            testAddress,
            testPayment
        );
        
        OrderRequest request2 = new OrderRequest(
            "ORD-008",
            "customer-404",
            List.of(new OrderItem("PROD-002", 3, 1999)),
            testAddress,
            testPayment
        );
        
        OrderResult result1 = facade.processOrder(request1);
        OrderResult result2 = facade.processOrder(request2);
        
        assertThat(result1.isSuccess()).isTrue();
        assertThat(result2.isSuccess()).isTrue();
        assertThat(result1.getOrderId()).isNotEqualTo(result2.getOrderId());
        assertThat(result1.getTrackingNumber()).isNotEqualTo(result2.getTrackingNumber());
    }
    
    // ===== Subsystem Integration Tests =====
    
    @Test
    @DisplayName("Shipping service calculates cost correctly")
    void shippingServiceCalculatesCost() {
        List<OrderItem> items = List.of(
            new OrderItem("PROD-001", 2, 2999),
            new OrderItem("PROD-002", 1, 4999)
        );
        
        long cost = shippingService.calculateShippingCost(items, testAddress);
        
        // Base 500 + (100 * 3 items) = 800 cents
        assertThat(cost).isEqualTo(800);
    }
    
    @Test
    @DisplayName("Inventory service reserves and commits correctly")
    void inventoryReservesAndCommits() {
        List<OrderItem> items = List.of(
            new OrderItem("PROD-001", 5, 2999)
        );
        
        assertThat(inventoryService.checkAvailability("PROD-001", 5)).isTrue();
        
        String reservationId = inventoryService.reserveItems("ORD-009", items);
        
        // Stock not yet deducted (just reserved)
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(100);
        
        inventoryService.commitReservation(reservationId);
        
        // Now stock is deducted
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(95);
    }
    
    @Test
    @DisplayName("Inventory service releases reservation on rollback")
    void inventoryReleasesReservation() {
        List<OrderItem> items = List.of(
            new OrderItem("PROD-002", 10, 1999)
        );
        
        String reservationId = inventoryService.reserveItems("ORD-010", items);
        inventoryService.releaseReservation(reservationId);
        
        // Stock unchanged (reservation released)
        assertThat(inventoryService.getAvailableStock("PROD-002")).isEqualTo(50);
    }
    
    @Test
    @DisplayName("Payment service authorizes and captures correctly")
    void paymentAuthorizesAndCaptures() {
        String authId = paymentService.authorizePayment(
            "customer-505", testPayment, 9999
        );
        
        assertThat(authId).isNotNull();
        
        String transactionId = paymentService.capturePayment(authId);
        
        assertThat(transactionId).isNotNull();
    }
    
    @Test
    @DisplayName("Payment service refunds transaction")
    void paymentRefundsTransaction() {
        String authId = paymentService.authorizePayment(
            "customer-606", testPayment, 5000
        );
        String transactionId = paymentService.capturePayment(authId);
        
        String refundId = paymentService.refundPayment(transactionId, 5000);
        
        assertThat(refundId).isNotNull();
    }
    
    @Test
    @DisplayName("Payment service declines suspicious amounts")
    void paymentDeclinesBlacklistedAmounts() {
        assertThatThrownBy(() -> 
            paymentService.authorizePayment("customer-707", testPayment, 1313)
        ).isInstanceOf(PaymentFailedException.class);
    }
    
    @Test
    @DisplayName("Order repository creates and retrieves orders")
    void orderRepositoryStoresOrders() {
        OrderRequest request = new OrderRequest(
            "ORD-011",
            "customer-808",
            List.of(new OrderItem("PROD-001", 1, 2999)),
            testAddress,
            testPayment
        );
        
        OrderRepository.Order order = orderRepository.createOrder(
            request, "txn-123", "TRACK-456"
        );
        
        assertThat(order.getOrderId()).isEqualTo("ORD-011");
        assertThat(order.getTrackingNumber()).isEqualTo("TRACK-456");
        
        OrderRepository.Order retrieved = orderRepository.getOrder("ORD-011");
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getOrderId()).isEqualTo("ORD-011");
    }
    
    @Test
    @DisplayName("Notification service tracks sent messages")
    void notificationServiceTracksSentMessages() {
        notificationService.clearNotifications();
        
        notificationService.sendOrderConfirmation("customer-909", "ORD-012", "TRACK-789");
        
        assertThat(notificationService.getSentNotifications())
            .hasSize(1)
            .anyMatch(n -> n.contains("customer-909") && n.contains("ORD-012"));
    }
    
    // ===== Edge Cases =====
    
    @Test
    @DisplayName("Order with exact stock amount succeeds")
    void orderWithExactStockSucceeds() {
        OrderRequest request = new OrderRequest(
            "ORD-013",
            "customer-111",
            List.of(new OrderItem("PROD-003", 10, 1999)), // Exactly available
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isTrue();
        assertThat(inventoryService.getAvailableStock("PROD-003")).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Order fails if exceeds stock by one")
    void orderFailsIfExceedsStockByOne() {
        OrderRequest request = new OrderRequest(
            "ORD-014",
            "customer-222",
            List.of(new OrderItem("PROD-003", 11, 1999)), // One more than available
            testAddress,
            testPayment
        );
        
        OrderResult result = facade.processOrder(request);
        
        assertThat(result.isSuccess()).isFalse();
    }
    
    @Test
    @DisplayName("Multiple concurrent orders handled correctly")
    void multipleConcurrentOrdersHandled() {
        OrderRequest request1 = new OrderRequest(
            "ORD-015",
            "customer-333",
            List.of(new OrderItem("PROD-001", 10, 2999)),
            testAddress,
            testPayment
        );
        
        OrderRequest request2 = new OrderRequest(
            "ORD-016",
            "customer-444",
            List.of(new OrderItem("PROD-001", 15, 2999)),
            testAddress,
            testPayment
        );
        
        OrderResult result1 = facade.processOrder(request1);
        OrderResult result2 = facade.processOrder(request2);
        
        assertThat(result1.isSuccess()).isTrue();
        assertThat(result2.isSuccess()).isTrue();
        
        // Total deducted correctly
        assertThat(inventoryService.getAvailableStock("PROD-001")).isEqualTo(75);
    }
}
