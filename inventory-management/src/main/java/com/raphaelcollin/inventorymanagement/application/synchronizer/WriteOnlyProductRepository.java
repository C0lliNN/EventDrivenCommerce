package com.raphaelcollin.inventorymanagement.application.synchronizer;

import com.raphaelcollin.inventorymanagement.domain.entity.Product;

public interface WriteOnlyProductRepository {
    void upsertProduct(Product product);
}
