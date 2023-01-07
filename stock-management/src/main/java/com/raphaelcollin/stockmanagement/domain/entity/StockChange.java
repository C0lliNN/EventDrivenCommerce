package com.raphaelcollin.stockmanagement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockChange {
    String itemId;
    int quantityToBeChanged;
}
