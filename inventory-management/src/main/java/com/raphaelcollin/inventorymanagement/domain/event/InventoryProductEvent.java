package com.raphaelcollin.inventorymanagement.domain.event;

import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import lombok.Value;

@Value
public class InventoryProductEvent {
    String inventoryId;
    int quantity;

    public InventoryProduct toEntity(String productId) {
        return new InventoryProduct(inventoryId, productId, quantity);
    }

    public static InventoryProductEvent fromEntity(InventoryProduct product) {
        return new InventoryProductEvent(product.getInventoryId(), product.getQuantity());
    }
}
