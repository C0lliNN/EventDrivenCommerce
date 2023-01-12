package com.raphaelcollin.shippingmanagement.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Shipping {
    private int id;
    private String upstreamExternalIdentifier;
    private ShippingStatus status;
    private LocalDateTime lastUpdated;

    public boolean isDelivered() {
        return status == ShippingStatus.DELIVERED;
    }
}
