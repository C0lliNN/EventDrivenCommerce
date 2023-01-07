package com.raphaelcollin.stockmanagement.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void toStockChange() {
        Item item = new Item("id1", 2);

        StockChange expected = new StockChange("id1", -2);
        StockChange actual = item.toStockChange();

        assertEquals(expected, actual);
    }
}