package com.raphaelcollin.inventorymanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.WriteOnlyProductStream;
import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductStream implements WriteOnlyProductStream {

    @Override
    public void publishEvent(final ProductEvent event) {
        System.out.println("Publishing event for product id: " + event.getId());
    }
}
