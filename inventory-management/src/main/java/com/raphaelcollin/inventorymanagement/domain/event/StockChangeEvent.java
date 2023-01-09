package com.raphaelcollin.inventorymanagement.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockChangeEvent {
    private String productId;
    private int quantityToBeChanged;
}
