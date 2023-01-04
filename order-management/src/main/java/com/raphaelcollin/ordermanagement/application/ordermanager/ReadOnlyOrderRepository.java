package com.raphaelcollin.ordermanagement.application.ordermanager;

import com.raphaelcollin.ordermanagement.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyOrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String orderId);
}
