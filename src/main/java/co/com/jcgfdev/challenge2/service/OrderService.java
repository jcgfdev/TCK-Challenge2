package co.com.jcgfdev.challenge2.service;

import co.com.jcgfdev.challenge2.model.Order;
import co.com.jcgfdev.challenge2.repository.InventoryRepository;
import co.com.jcgfdev.challenge2.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Mono<Void> placeOrder(@Valid Order order) {
        return inventoryRepository.checkInventory(order.getProductId(), order.getQuantity())
                .defaultIfEmpty(false)
                .flatMap(inStock -> {
                    if (Boolean.FALSE.equals(inStock)) {
                        return Mono.error(new RuntimeException("sin existencias"));
                    }
                    return orderRepository.save(order);
                });
    }
}
