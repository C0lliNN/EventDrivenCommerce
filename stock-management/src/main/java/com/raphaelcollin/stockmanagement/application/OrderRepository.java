package com.raphaelcollin.stockmanagement.application;

import com.raphaelcollin.stockmanagement.domain.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(String orderId);
    void save(Order order);
}
