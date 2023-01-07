package com.raphaelcollin.stockmanagement.domain.event;

import com.raphaelcollin.stockmanagement.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class OrderEvent {
    private String id;
    private String correlationId;
    private Set<ItemEvent> items;

    public Order toEntity() {
        return new Order(
                id,
                correlationId,
                items.stream().map(ItemEvent::toEntity).collect(Collectors.toUnmodifiableSet())
        );
    }
}
