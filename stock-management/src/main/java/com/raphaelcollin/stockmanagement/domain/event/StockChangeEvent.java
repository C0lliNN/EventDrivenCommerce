package com.raphaelcollin.stockmanagement.domain.event;

import com.raphaelcollin.stockmanagement.domain.entity.StockChange;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockChangeEvent {
    String itemId;
    int quantityToBeChanged;

    public static StockChangeEvent fromEntity(StockChange stockChange) {
        return new StockChangeEvent(stockChange.getItemId(), stockChange.getQuantityToBeChanged());
    }
}
