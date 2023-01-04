package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Delivery {
    DeliveryStatus deliveryStatus;
    LocalDateTime lastUpdated;

    public enum DeliveryStatus {
        IN_INVENTORY,
        IN_FLIGHT,
        DELIVERED,
        CANCELLED
    }

    public boolean isDelivered() {
        return deliveryStatus == DeliveryStatus.DELIVERED;
    }
}
