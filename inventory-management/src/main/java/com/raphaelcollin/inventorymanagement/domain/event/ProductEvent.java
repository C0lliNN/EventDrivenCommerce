package com.raphaelcollin.inventorymanagement.domain.event;

import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class ProductEvent {
    String id;
    String correlationId;
    String name;
    String description;
    String additionalInfo;
    double price;
    Set<InventoryProductEvent> inventoryProducts;

    public Product toEntity() {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .additionalInfo(additionalInfo)
                .price(price)
                .inventoryProducts(inventoryProducts.stream().map(i -> i.toEntity(id)).collect(Collectors.toSet()))
                .build();
    }

    public static ProductEvent fromEntity(Product product, String correlationId) {
        Set<InventoryProductEvent> inventories = new HashSet<>();
        if (product.getInventoryProducts() != null) {
            inventories = product.getInventoryProducts().stream()
                    .map(InventoryProductEvent::fromEntity)
                    .collect(Collectors.toUnmodifiableSet());
        }

        return new ProductEvent(
                product.getId(),
                correlationId,
                product.getName(),
                product.getDescription(),
                product.getAdditionalInfo(),
                product.getPrice(),
                inventories
        );
    }
}
