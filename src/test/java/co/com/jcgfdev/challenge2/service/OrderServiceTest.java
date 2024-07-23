package co.com.jcgfdev.challenge2.service;

import co.com.jcgfdev.challenge2.model.Order;
import co.com.jcgfdev.challenge2.repository.InventoryRepository;
import co.com.jcgfdev.challenge2.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_whenInventoryIsSufficient_thenOrderIsSaved() {
        Order order = new Order("productId", 5);

        when(inventoryRepository.checkInventory(order.getProductId(), order.getQuantity()))
                .thenReturn(Mono.just(true));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(orderService.placeOrder(order))
                .verifyComplete();
    }

    @Test
    void placeOrder_whenInventoryIsInsufficient_thenErrorIsThrown() {
        Order order = new Order("productId", 5);

        when(inventoryRepository.checkInventory(order.getProductId(), order.getQuantity()))
                .thenReturn(Mono.just(false));

        StepVerifier.create(orderService.placeOrder(order))
                .expectError(RuntimeException.class)
                .verify();
    }
}
