package com.raphaelcollin.stockmanagement.infrastructure.persistence;

import com.raphaelcollin.stockmanagement.application.OrderRepository;
import com.raphaelcollin.stockmanagement.domain.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RedisOrderRepository implements OrderRepository {
    private final RedisTemplate<String, Order> redisTemplate;

    @Override
    public Optional<Order> findById(final String orderId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(orderId));
    }

    @Override
    public void save(final Order order) {
        redisTemplate.opsForValue().set(order.getId(), order);
    }
}
