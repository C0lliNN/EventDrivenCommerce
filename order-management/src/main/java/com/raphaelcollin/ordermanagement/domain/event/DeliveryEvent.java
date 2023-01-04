package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DeliveryEvent {
    Delivery.DeliveryStatus deliveryStatus;
    LocalDateTime lastUpdated;
    boolean delivered;

    public static DeliveryEvent fromEntity(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        return new DeliveryEvent(delivery.getDeliveryStatus(), delivery.getLastUpdated(), delivery.isDelivered());
    }
}
