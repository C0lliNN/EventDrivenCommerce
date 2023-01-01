package com.raphaelcollin.inventorymanagement.application.inventorymanager.response;

import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import lombok.Value;

@Value
public class InventoryProductResponse {
    String inventoryId;
    int quantity;

    public static InventoryProductResponse fromDomain(InventoryProduct inventoryProduct) {
        return new InventoryProductResponse(inventoryProduct.getInventoryId(), inventoryProduct.getQuantity());
    }
}
