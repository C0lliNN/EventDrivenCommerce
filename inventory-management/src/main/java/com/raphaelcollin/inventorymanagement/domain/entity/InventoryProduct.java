package com.raphaelcollin.inventorymanagement.domain.entity;

import lombok.Value;

/* Represents the amount of product in a given inventory */
@Value
public class InventoryProduct {
    String inventoryId;
    String productId;
    int quantity;
}
