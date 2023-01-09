package com.raphaelcollin.inventorymanagement.domain.entity;

import com.raphaelcollin.inventorymanagement.domain.exception.InvalidQuantityException;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Iterator;
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

    @Override
    public void applyQuantityChange(int quantity) {
        if (getInventoryProducts().isEmpty()) {
            throw new IllegalStateException("The inventory products must not be empty");
        }

        if (quantity >= 0) {
            InventoryProduct inventoryProduct = inventoryProducts.stream().findFirst().orElseThrow();
            inventoryProduct.setQuantity(inventoryProduct.getQuantity() + quantity);
            return;
        }

        if (Math.abs(quantity) > getTotalAmountAvailable()) {
            throw new InvalidQuantityException(String.format("The maximum about available is %d, cannot decrement %d", getTotalAmountAvailable(), Math.abs(quantity)));
        }

        // TODO: This can probably be simplified a lot
        Iterator<InventoryProduct> iterator = inventoryProducts.iterator();
        while (iterator.hasNext() && quantity < 0) {
            InventoryProduct inventoryProduct = iterator.next();
            if (Math.abs(quantity) > inventoryProduct.getQuantity()) {
                quantity += Math.abs(inventoryProduct.getQuantity());
                inventoryProduct.setQuantity(0);
            } else {
                inventoryProduct.setQuantity(inventoryProduct.getQuantity() + quantity);
                quantity = 0;
            }
        }
    }
}
