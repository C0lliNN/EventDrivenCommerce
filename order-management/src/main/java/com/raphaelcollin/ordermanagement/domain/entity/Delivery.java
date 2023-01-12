package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
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

    public void updateStatus(DeliveryStatus newStatus) {
        this.deliveryStatus = newStatus;
        this.lastUpdated = LocalDateTime.now(Clock.systemUTC());
    }
}
