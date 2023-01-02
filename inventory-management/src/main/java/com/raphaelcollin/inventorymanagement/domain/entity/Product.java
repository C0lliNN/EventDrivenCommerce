package com.raphaelcollin.inventorymanagement.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Product implements Item {
    private String id;
    private String name;
    private String description;
    private String additionalInfo;
    private double price;
    private Set<InventoryProduct> inventoryProducts;

    public Set<InventoryProduct> getInventoryProducts() {
        if (inventoryProducts == null) {
            return new HashSet<>();
        }
        return new HashSet<>(inventoryProducts);
    }

    public void addInventoryProduct(InventoryProduct inventoryProduct) {
        if (inventoryProducts == null) {
            inventoryProducts = new HashSet<>();
        }
        inventoryProducts.add(inventoryProduct);
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getTotalAmountAvailable() {
        if (inventoryProducts == null) {
            return 0;
        }

        return inventoryProducts.stream()
                .map(InventoryProduct::getQuantity)
                .reduce(0, Integer::sum);
    }

    @Override
    public boolean isAvailable() {
        return getTotalAmountAvailable() > 0;
    }
}
