package com.raphaelcollin.inventorymanagement.application.inventorymanager;

import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;

public interface WriteOnlyProductStream {
    void publishEvent(ProductEvent event);
}
