package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    String id;
    String name;
    double price;
    boolean available;
}
