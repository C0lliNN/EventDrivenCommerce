package com.raphaelcollin.ordermanagement.infrastructure.event.converter;

import com.raphaelcollin.ordermanagement.domain.event.ItemEvent;
import com.raphaelcollin.ordermanagement.kafka.Item;

public class KafkaItemEventConverter {

    public static ItemEvent convertItemToDomain(Item item) {
        return new ItemEvent(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getAvailable(),
                item.getPrice()
        );
    }

    public static Item convertDomainToKafkaItem(ItemEvent itemEvent) {
        return new Item(
                itemEvent.getId(),
                itemEvent.getName(),
                itemEvent.getQuantity(),
                itemEvent.getPrice(),
                itemEvent.isAvailable()
        );
    }
}
