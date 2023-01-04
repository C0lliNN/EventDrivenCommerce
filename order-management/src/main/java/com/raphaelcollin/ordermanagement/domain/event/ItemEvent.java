package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Item;
import lombok.Value;

@Value
public class ItemEvent {
    String id;
    String name;
    int quantity;
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
                item.getPrice(),
                item.getTotalPrice()
        );
    }
}
