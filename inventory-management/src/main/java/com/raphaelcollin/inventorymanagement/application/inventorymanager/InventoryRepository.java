package com.raphaelcollin.inventorymanagement.application.inventorymanager;

import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;

import java.util.List;

public interface InventoryRepository {
    List<Inventory> findAll();
    void upsert(Inventory inventory);
}
