package co.com.jcgfdev.challenge2.repository;

import reactor.core.publisher.Mono;

public interface InventoryRepository {
    Mono<Boolean> checkInventory(String productId, Integer quantity);
}
