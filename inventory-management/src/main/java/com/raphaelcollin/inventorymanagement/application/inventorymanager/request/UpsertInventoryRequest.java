package com.raphaelcollin.inventorymanagement.application.inventorymanager.request;

import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;
import lombok.Value;

@Value
public class UpsertInventoryRequest {
    String id;
    String name;

    public Inventory toDomain() {
        return new Inventory(id, name);
    }
}
