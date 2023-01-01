package com.raphaelcollin.inventorymanagement.application.inventorymanager.request;

import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import lombok.Value;

@Value
public class UpsertInventoryProductRequest {
    String inventoryId;
    int quantity;

    public InventoryProduct toDomain(String productId) {
        return new InventoryProduct(inventoryId, productId, quantity);
    }
}
