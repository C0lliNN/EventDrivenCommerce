package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    String id;
    String name;
    int quantity;
    double price;
    boolean available;

    public boolean isQuantityAvailable(int quantity) {
        return this.quantity >= quantity;
    }

    public double getTotalPrice() {
        return quantity * price;
    }
}
