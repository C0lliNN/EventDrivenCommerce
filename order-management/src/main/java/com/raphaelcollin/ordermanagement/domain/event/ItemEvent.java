package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Item;
import lombok.Value;

@Value
public class ItemEvent {
    String id;
    String name;
    int quantity;
    boolean available;
    double price;
    double totalPrice;

    public static ItemEvent fromEntity(Item item) {
        if (item == null) {
            return null;
        }

        return new ItemEvent(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.isAvailable(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }

    public Item toEntity() {
        return Item.builder()
                .id(id)
                .name(name)
                .quantity(quantity)
                .price(price)
                .available(available)
                .build();
    }
}
