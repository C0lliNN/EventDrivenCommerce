package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.ReadOnlyProductRepository;
import com.raphaelcollin.inventorymanagement.application.synchronizer.WriteOnlyProductRepository;
import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JDBCProductRepository implements WriteOnlyProductRepository, ReadOnlyProductRepository {
    private final JdbcTemplate template;
    private static final String SELECT_ALL = "SELECT * FROM products LEFT JOIN inventory_products ON products.id = inventory_products.product_id";
    private static final String SELECT_ONE = "SELECT * FROM products LEFT JOIN inventory_products ON products.id = inventory_products.product_id WHERE id = ?";
    private static final String DELETE_ALL_INVENTORY_PRODUCTS = "DELETE FROM inventory_products WHERE product_id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String INSERT_INVENTORY_PRODUCT = "INSERT INTO inventory_products VALUES(?,?,?)";
    private static final String INSERT_PRODUCT = "INSERT INTO products VALUES(?,?,?,?,?)";

    private final RowMapper<Product> productMapper = new ProductRowMapper();
    private final RowMapper<InventoryProduct> inventoryProductMapper = new InventoryProductRowMapper();

    @Override
    public List<Product> findAll() {
        return template.query(SELECT_ALL, rs -> {
            List<Product> products = new LinkedList<>();
            String productId = null;
            Product currentProduct = null;
            int productIndex = 0;
            int inventoryProductIdx = 0;

            while (rs.next()) {
                // first row or when order changes
                if (currentProduct == null || !productId.equals(rs.getString("id"))) {
                    productId = rs.getString("id");
                    currentProduct = productMapper.mapRow(rs, productIndex++);
                    inventoryProductIdx = 0;
                    products.add(currentProduct);
                }

                InventoryProduct inventoryProduct = inventoryProductMapper.mapRow(rs, inventoryProductIdx++);
                if (inventoryProduct != null) {
                    currentProduct.addInventoryProduct(inventoryProduct);
                }
            }

            return products;
        });
    }

    @Override
    public Optional<Product> findById(final String id) {
        return template.query(SELECT_ONE, rs -> {
            Product product = null;
            int row = 0;
            while (rs.next()) {
                if (product == null) {
                    product = productMapper.mapRow(rs, row);
                }
                product.addInventoryProduct(inventoryProductMapper.mapRow(rs, row));
                row++;
            }

            return Optional.ofNullable(product);
        });
    }

    @Override
    public void upsertProduct(final Product product) {
        template.update(DELETE_ALL_INVENTORY_PRODUCTS, product.getId());
        template.update(DELETE_PRODUCT, product.getId());
        product.getInventoryProducts().forEach(i -> template.update(INSERT_INVENTORY_PRODUCT, i.getInventoryId(), i.getProductId(), i.getQuantity()));
        template.update(INSERT_PRODUCT, product.getId(), product.getName(), product.getDescription(), product.getAdditionalInfo(), product.getPrice());
    }
}
