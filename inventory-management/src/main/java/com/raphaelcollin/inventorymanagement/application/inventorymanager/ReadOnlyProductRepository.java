package com.raphaelcollin.inventorymanagement.application.inventorymanager;

import com.raphaelcollin.inventorymanagement.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
}
