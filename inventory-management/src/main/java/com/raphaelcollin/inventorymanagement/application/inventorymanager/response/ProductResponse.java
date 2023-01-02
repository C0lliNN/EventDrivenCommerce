package com.raphaelcollin.inventorymanagement.application.inventorymanager.response;

import com.raphaelcollin.inventorymanagement.domain.entity.Product;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class ProductResponse {
    String id;
    String name;
    String description;
    String additionalInfo;
    double price;
    int totalAmountAvailable;
    boolean isAvailable;
    Set<InventoryProductResponse> inventoryProducts;

    public static ProductResponse fromDomain(Product product) {
        Set<InventoryProductResponse> inventories = new HashSet<>();
        if (product.getInventoryProducts() != null) {
            inventories = product.getInventoryProducts().stream()
                    .map(InventoryProductResponse::fromDomain)
                    .collect(Collectors.toUnmodifiableSet());
        }

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAdditionalInfo(),
                product.getPrice(),
                product.getTotalAmountAvailable(),
                product.isAvailable(),
                inventories
        );
    }
}
