package com.raphaelcollin.ordermanagement.application.synchronizer;

import com.raphaelcollin.ordermanagement.domain.entity.Order;

public interface WriteOnlyOrderRepository {
    void upsertOrder(Order order);
}
