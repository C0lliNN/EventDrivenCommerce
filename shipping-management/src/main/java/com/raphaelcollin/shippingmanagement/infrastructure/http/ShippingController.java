package com.raphaelcollin.shippingmanagement.infrastructure.http;

import com.raphaelcollin.shippingmanagement.application.ShippingManagementService;
import com.raphaelcollin.shippingmanagement.application.request.UpdateShippingRequest;
import com.raphaelcollin.shippingmanagement.application.response.ShippingResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ShippingController {
    private final ShippingManagementService shippingManagementService;

    @GetMapping("/shippings")
    public List<ShippingResponse> getShippings() {
        return shippingManagementService.getShippings();
    }

    @PutMapping("/shippings/{id}")
    public void updateShipping(@PathVariable("id") int shippingId, @RequestBody UpdateShippingRequest request) {
        shippingManagementService.updateShipping(shippingId, request);
    }
}
