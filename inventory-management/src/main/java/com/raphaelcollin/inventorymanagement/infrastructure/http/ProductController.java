package com.raphaelcollin.inventorymanagement.infrastructure.http;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.InventoryManagementService;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.request.UpsertProductRequest;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProductController {
    private final InventoryManagementService service;

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable("id") String id) {
        return service.getProduct(id);
    }

    @PostMapping("/products")
    public void upsertProduct(@RequestBody UpsertProductRequest request) {

        service.upsertProduct(request.withCorrelationId(UUID.randomUUID().toString()));
    }
}
