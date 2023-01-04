package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DeliveryResponse {
    Delivery.DeliveryStatus deliveryStatus;
    LocalDateTime lastUpdated;
    boolean delivered;

    public static DeliveryResponse fromDomain(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        return new DeliveryResponse(delivery.getDeliveryStatus(), delivery.getLastUpdated(), delivery.isDelivered());
    }
}
