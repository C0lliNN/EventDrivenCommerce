package com.raphaelcollin.inventorymanagement.application.synchronizer;

import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventorySynchronizerService {
    private final WriteOnlyProductRepository productRepository;

    public void handleNewProductEvent(ProductEvent product) {
        productRepository.upsertProduct(product.toEntity());
    }
}
