package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.InventoryRepository;
import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryInventoryRepository implements InventoryRepository {
    private final List<Inventory> inventories = new ArrayList<>();

    @Override
    public List<Inventory> findAll() {
        return inventories;
    }

    @Override
    public void upsert(final Inventory inventory) {
        inventories.remove(inventory);
        inventories.add(inventory);
    }
}
