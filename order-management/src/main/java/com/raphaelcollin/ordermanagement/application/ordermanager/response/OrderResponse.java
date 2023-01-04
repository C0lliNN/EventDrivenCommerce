package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Order;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class OrderResponse {
    String id;
    CustomerResponse customer;
    DestinationResponse destination;
    DeliveryResponse delivery;
    PaymentResponse payment;
    double total;
    boolean paid;
    boolean delivered;
    Set<ItemResponse> items;

    public static OrderResponse fromDomain(@NonNull Order order) {
        return new OrderResponse(
                order.getId(),
                CustomerResponse.fromDomain(order.getCustomer()),
                DestinationResponse.fromDomain(order.getDestination()),
                DeliveryResponse.fromDomain(order.getDelivery()),
                PaymentResponse.fromDomain(order.getPayment()),
                order.getTotal(),
                order.isPaid(),
                order.isDelivered(),
                order.getItems().stream().map(ItemResponse::fromDomain).collect(Collectors.toUnmodifiableSet())
        );
    }
}
