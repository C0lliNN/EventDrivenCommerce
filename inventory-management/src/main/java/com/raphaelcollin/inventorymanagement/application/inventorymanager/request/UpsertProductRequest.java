package com.raphaelcollin.inventorymanagement.application.inventorymanager.request;

import com.raphaelcollin.inventorymanagement.domain.entity.InventoryProduct;
import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import lombok.Value;
import lombok.With;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class UpsertProductRequest {
    String id;
    @With
    String correlationId;
    String name;
    String description;
    String additionalInfo;
    double price;
    Set<UpsertInventoryProductRequest> inventoryProducts;

    public Product toDomain() {
        Set<InventoryProduct> inventories = new HashSet<>();
        if (inventoryProducts != null) {
            inventories = inventoryProducts.stream()
                    .map(i -> i.toDomain(id))
                    .collect(Collectors.toSet());
        }

        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .additionalInfo(additionalInfo)
                .price(price)
                .inventoryProducts(inventories)
                .build();
    }
}
