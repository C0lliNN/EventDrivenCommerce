package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class ItemDocument {
    @Id
    String id;
    String name;
    int quantity;
    double price;
    boolean available;

    public Item toDomain() {
        return Item.builder()
                .id(id)
                .name(name)
                .quantity(quantity)
                .price(price)
                .available(available)
                .build();
    }

    public static ItemDocument fromDomain(Item item) {
        return new ItemDocument(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getPrice(),
                item.isAvailable()
        );
    }
}
