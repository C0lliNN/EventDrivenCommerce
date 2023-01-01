package com.raphaelcollin.inventorymanagement.application.inventorymanager.response;

import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;
import lombok.Value;

@Value
public class InventoryResponse {
    String id;
    String name;

    public static InventoryResponse fromDomain(Inventory inventory) {
        return new InventoryResponse(inventory.getId(), inventory.getName());
    }
}
