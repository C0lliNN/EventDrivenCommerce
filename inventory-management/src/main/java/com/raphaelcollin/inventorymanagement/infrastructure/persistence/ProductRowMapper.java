package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getString("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .additionalInfo(rs.getString("additional_info"))
                .price(rs.getDouble("price"))
                .build();
    }
}
