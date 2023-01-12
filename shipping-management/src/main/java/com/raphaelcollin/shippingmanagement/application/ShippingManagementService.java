package com.raphaelcollin.shippingmanagement.application;

import com.raphaelcollin.shippingmanagement.application.request.CreateShippingRequest;
import com.raphaelcollin.shippingmanagement.application.request.UpdateShippingRequest;
import com.raphaelcollin.shippingmanagement.application.response.ShippingResponse;
import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import com.raphaelcollin.shippingmanagement.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShippingManagementService {
    private final ShippingRepository shippingRepository;

    public List<ShippingResponse> getShippings() {
        return shippingRepository.findAll().stream()
                .map(ShippingResponse::fromDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public void createShipping(CreateShippingRequest request) {
        Shipping shipping = Shipping.builder()
                .upstreamExternalIdentifier(request.getUpstreamExternalIdentifier())
                .status(ShippingStatus.IN_INVENTORY)
                .lastUpdated(LocalDateTime.now())
                .build();

        shippingRepository.save(shipping);
    }

    public void updateShipping(int shippingId, UpdateShippingRequest request) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping not found"));

        shipping.setStatus(request.getStatus());
        shipping.setUpstreamExternalIdentifier(request.getUpstreamExternalIdentifier());

        shippingRepository.save(shipping);
    }
}
