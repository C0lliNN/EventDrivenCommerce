package com.raphaelcollin.inventorymanagement.infrastructure.http;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.InventoryManagementService;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.request.UpsertInventoryRequest;
import com.raphaelcollin.inventorymanagement.application.inventorymanager.response.InventoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
public class InventoryController {
    private final InventoryManagementService service;

    @GetMapping("/inventories")
    public List<InventoryResponse> getAllInventories() {
        return service.getAllInventories();
    }

    @PostMapping("/inventories")
    public void upsertInventory(@RequestBody UpsertInventoryRequest request) {
        service.upsertInventory(request);
    }
}
