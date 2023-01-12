package com.raphaelcollin.shippingmanagement.application.response;

import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ShippingResponse {
    int id;
    String upstreamExternalIdentifier;
    ShippingStatus status;
    LocalDateTime lastUpdated;

    public static ShippingResponse fromDomain(Shipping shipping) {
        return new ShippingResponse(
                shipping.getId(),
                shipping.getUpstreamExternalIdentifier(),
                shipping.getStatus(),
                shipping.getLastUpdated()
        );
    }
}
