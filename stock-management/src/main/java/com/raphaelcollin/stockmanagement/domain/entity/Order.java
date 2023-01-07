package com.raphaelcollin.stockmanagement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Order {
    private String id;
    private String correlationId;
    private Set<Item> items;
}
