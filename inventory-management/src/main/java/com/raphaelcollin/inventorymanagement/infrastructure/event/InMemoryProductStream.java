package com.raphaelcollin.inventorymanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.WriteOnlyProductStream;
import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class InMemoryProductStream implements WriteOnlyProductStream {

    @Override
    public void publishEvent(final ProductEvent event) {
        log.info("Publishing event for product id: " + event.getId());
    }
}
