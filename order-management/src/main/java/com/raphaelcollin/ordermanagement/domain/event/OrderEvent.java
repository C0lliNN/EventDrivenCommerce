package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Order;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class OrderEvent {
    String id;
    String correlationId;
    CustomerEvent customer;
    DestinationEvent destination;
    DeliveryEvent delivery;
    PaymentEvent payment;
    double total;
    boolean paid;
    boolean delivered;
    Set<ItemEvent> items;

    public static OrderEvent fromEntity(Order order, String correlationId) {
        return new OrderEvent(
                order.getId(),
                correlationId,
                CustomerEvent.fromEntity(order.getCustomer()),
                DestinationEvent.fromEntity(order.getDestination()),
                DeliveryEvent.fromEntity(order.getDelivery()),
                PaymentEvent.fromEntity(order.getPayment()),
                order.getTotal(),
                order.isPaid(),
                order.isDelivered(),
                order.getItems().stream().map(ItemEvent::fromEntity).collect(Collectors.toUnmodifiableSet())
        );
    }

    public Order toEntity() {
        return Order.builder()
                .id(id)
                .customer(customer.toEntity())
                .destination(destination.toEntity())
                .delivery(delivery.toEntity())
                .payment(payment.toEntity())
                .items(items.stream().map(ItemEvent::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
