package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Item;
import lombok.Value;

@Value
public class ItemResponse {
    String id;
    String name;
    int quantity;
    double price;
    double totalPrice;

    public static ItemResponse fromDomain(Item item) {
        if (item == null) {
            return null;
        }

        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }
}
