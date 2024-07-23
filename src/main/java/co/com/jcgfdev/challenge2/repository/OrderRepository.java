package co.com.jcgfdev.challenge2.repository;

import co.com.jcgfdev.challenge2.model.Order;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<Void> save(Order order);
}
