package com.raphaelcollin.stockmanagement.domain.event;

import com.raphaelcollin.stockmanagement.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemEvent {
    private String id;
    private int quantity;

    public Item toEntity() {
        return new Item(id, quantity);
    }
}
