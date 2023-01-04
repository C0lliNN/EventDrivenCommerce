package com.raphaelcollin.ordermanagement.application.ordermanager.request;

import com.raphaelcollin.ordermanagement.domain.entity.Item;
import lombok.Value;

@Value
public class CreateOrderItemRequest {
    String itemId;
    int quantity;

    public Item toDomain() {
        return Item.builder()
                .id(itemId)
                .quantity(quantity)
                .build();
    }
}
