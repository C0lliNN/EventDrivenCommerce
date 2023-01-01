package com.raphaelcollin.inventorymanagement.infrastructure.persistence;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.ReadOnlyProductRepository;
import com.raphaelcollin.inventorymanagement.application.synchronizer.WriteOnlyProductRepository;
import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements WriteOnlyProductRepository, ReadOnlyProductRepository {
    private final List<Product> products = new LinkedList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(final String id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public void upsertProduct(final Product product) {
        products.remove(product);
        products.add(product);
    }
}
