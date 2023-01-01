package com.raphaelcollin.inventorymanagement.application.synchronizer;

import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;

import java.util.List;

public class InventorySynchronizerService {
    private WriteOnlyProductRepository productRepository;

    void handleNewProductEvents(List<ProductEvent> products) {
        
    }
}
