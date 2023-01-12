package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class Order {
    private String id;
    private Customer customer;
    private Destination destination;
    private Delivery delivery;
    private Payment payment;
    private Set<Item> items;

    public double getTotal() {
        return getItems().stream()
                .map(Item::getTotalPrice)
                .reduce(0.0, Double::sum);
    }

    public boolean isPaid() {
        if (payment == null) {
            return false;
        }
        return payment.isPaid();
    }

    public boolean isDelivered() {
        if (delivery == null) {
            return false;
        }
        return delivery.isDelivered();
    }

    public Set<Item> getItems() {
        if (items == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(items);
    }

    public void updateDeliveryStatus(Delivery.DeliveryStatus status) {
        delivery.updateStatus(status);
    }
}
