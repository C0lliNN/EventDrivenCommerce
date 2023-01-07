package com.raphaelcollin.stockmanagement.application;

import com.raphaelcollin.stockmanagement.domain.entity.Item;
import com.raphaelcollin.stockmanagement.domain.entity.Order;
import com.raphaelcollin.stockmanagement.domain.event.OrderEvent;
import com.raphaelcollin.stockmanagement.domain.event.StockChangeEvent;
import com.raphaelcollin.stockmanagement.domain.exception.OrderAlreadyProcessedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockManagementService {
    private final OrderRepository orderRepository;

    public Set<StockChangeEvent> processOrder(OrderEvent orderEvent) {
        Order order = orderEvent.toEntity();

        if (orderRepository.findById(order.getId()).isPresent()) {
            throw new OrderAlreadyProcessedException(String.format("Order %s is already processed, skipping it", order.getId()));
        }

        orderRepository.save(order);

        return order.getItems().stream()
                .map(Item::toStockChange)
                .map(StockChangeEvent::fromEntity)
                .collect(Collectors.toSet());

    }
}
