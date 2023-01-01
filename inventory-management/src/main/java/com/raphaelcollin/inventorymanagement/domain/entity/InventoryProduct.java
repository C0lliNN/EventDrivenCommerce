package com.raphaelcollin.inventorymanagement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/* Represents the amount of product in a given inventory */
@Data
@AllArgsConstructor
public class InventoryProduct {
    String inventoryId;
    String productId;
    int quantity;
}
