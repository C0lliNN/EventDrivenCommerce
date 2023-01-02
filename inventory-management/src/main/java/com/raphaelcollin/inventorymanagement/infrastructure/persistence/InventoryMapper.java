package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.domain.entity.Inventory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryMapper implements RowMapper<Inventory> {

    @Override
    public Inventory mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Inventory(rs.getString("id"), rs.getString("name"));
    }
}
