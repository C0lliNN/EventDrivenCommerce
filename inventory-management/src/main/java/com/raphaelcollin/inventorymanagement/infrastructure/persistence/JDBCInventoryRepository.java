package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.InventoryRepository;
import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class JDBCInventoryRepository implements InventoryRepository {
    private final JdbcTemplate template;

    @Override
    public List<Inventory> findAll() {
        return template.query("SELECT * FROM inventories", new InventoryMapper());
    }

    @Override
    public void upsert(final Inventory inventory) {
        template.update("DELETE FROM inventories WHERE id = ?", inventory.getId());
        template.update("INSERT INTO inventories VALUES(?, ?)", inventory.getId(), inventory.getName());
    }
}
