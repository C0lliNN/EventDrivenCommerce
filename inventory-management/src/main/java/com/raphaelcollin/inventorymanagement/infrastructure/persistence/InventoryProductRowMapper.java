package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryProductRowMapper implements RowMapper<InventoryProduct> {

    @Override
    public InventoryProduct mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        String inventoryId = rs.getString("inventory_id");
        String productId = rs.getString("product_id");
        int quantity = rs.getInt("quantity");

        if (inventoryId == null && productId == null) {
            return null;
        }

        return new InventoryProduct(inventoryId, productId, quantity);
    }
}
