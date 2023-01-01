package com.raphaelcollin.inventorymanagement.application.inventorymanager;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.request.UpsertInventoryRequest;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.request.UpsertProductRequest;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.response.InventoryResponse;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.response.ProductResponse;
import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import com.raphaelcollin.inventorymanagement.domain.exception.EntityNotFoundException;
import com.raphaelcollin.inventorymanagement.domain.exception.InvalidRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryManagementService {
    private final WriteOnlyProductStream productStream;
    private final ReadOnlyProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::fromDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public ProductResponse getProduct(String id) {
        return productRepository.findById(id)
                .map(ProductResponse::fromDomain)
                .orElseThrow(() -> new EntityNotFoundException("Product could not be found"));
    }

    public void upsertProduct(UpsertProductRequest request) {
        validateProductRequest(request);

        ProductEvent event = ProductEvent.fromEntity(request.toDomain(), request.getCorrelationId());
        productStream.publishEvent(event);
    }

    private void validateProductRequest(UpsertProductRequest request) {
        if (request.getId() == null || request.getId().isBlank()) {
            throw new InvalidRequestException("The 'id' is required");
        }
        if (request.getCorrelationId() == null || request.getCorrelationId().isBlank()) {
            throw new InvalidRequestException("The 'correlationId' is required");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidRequestException("The 'name' is required");
        }
        if (request.getPrice() == 0.0) {
            throw new InvalidRequestException("The 'price' must be greater than zero");
        }

        // TODO: validate other fields and make sure the inventoryId is valid
    }

    public List<InventoryResponse> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(InventoryResponse::fromDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void upsertInventory(UpsertInventoryRequest request) {
        validateInventoryRequest(request);

        inventoryRepository.upsert(request.toDomain());
    }

    private void validateInventoryRequest(UpsertInventoryRequest request) {
        if (request.getId() == null || request.getId().isBlank()) {
            throw new InvalidRequestException("The 'id' is required");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidRequestException("The 'name' is required");
        }
    }
}
