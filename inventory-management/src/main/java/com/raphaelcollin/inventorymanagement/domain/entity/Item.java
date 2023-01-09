package com.raphaelcollin.inventorymanagement.domain.entity;

public interface Item {
    double getPrice();
    int getTotalAmountAvailable();
    boolean isAvailable();
    void applyQuantityChange(int quantity);
}
