package com.raphaelcollin.shippingmanagement.application;

import com.raphaelcollin.shippingmanagement.application.request.CreateShippingRequest;
import com.raphaelcollin.shippingmanagement.application.request.UpdateShippingRequest;
import com.raphaelcollin.shippingmanagement.application.response.ShippingResponse;
import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import com.raphaelcollin.shippingmanagement.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ShippingManagementService {
    private final ShippingRepository shippingRepository;

    public List<ShippingResponse> getShippings() {
        return shippingRepository.findAll().stream()
                .map(ShippingResponse::fromDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void createShipping(CreateShippingRequest request) {
        String externalUniqueIdentifier = request.getUpstreamExternalIdentifier();
        if (shippingRepository.findByUpstreamExternalIdentifier(externalUniqueIdentifier).isPresent()) {
            log.info("Shipping {} already processed, skipping it", externalUniqueIdentifier);
            return;
        }

        Shipping shipping = Shipping.builder()
                .upstreamExternalIdentifier(externalUniqueIdentifier)
                .status(ShippingStatus.IN_INVENTORY)
                .lastUpdated(LocalDateTime.now())
                .build();

        shippingRepository.save(shipping);
    }

    @Transactional
    public void updateShipping(int shippingId, UpdateShippingRequest request) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping not found"));

        shipping.setStatus(request.getStatus());
        shipping.setUpstreamExternalIdentifier(request.getUpstreamExternalIdentifier());
        shipping.setLastUpdated(LocalDateTime.now(Clock.systemUTC()));

        shippingRepository.update(shipping);
    }
}
