package com.raphaelcollin.stockmanagement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String id;
    private int quantity;

    public StockChange toStockChange() {
        return new StockChange(id, -quantity);
    }
}
