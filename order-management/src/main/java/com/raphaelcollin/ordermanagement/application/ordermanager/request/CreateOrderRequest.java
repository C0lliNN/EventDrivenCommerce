package com.raphaelcollin.ordermanagement.application.ordermanager.request;

import com.raphaelcollin.ordermanagement.domain.entity.Customer;
import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import com.raphaelcollin.ordermanagement.domain.entity.Destination;
import com.raphaelcollin.ordermanagement.domain.entity.Order;
import com.raphaelcollin.ordermanagement.domain.entity.Payment;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class CreateOrderRequest {
    String id;
    @With
    String correlationId;
    String customerName;
    String customerEmail;
    String customerPhone;
    String destinationAddress;
    double destinationLatitude;
    double destinationLongitude;
    String destinationInstructions;
    String paymentMethod;
    Set<CreateOrderItemRequest> items;

    public Order toDomain() {
        return Order.builder()
                .id(id)
                .customer(new Customer(customerName, customerEmail, customerPhone))
                .destination(new Destination(destinationAddress, destinationLatitude, destinationLongitude, destinationInstructions))
                .payment(new Payment(paymentMethod, Payment.PaymentStatus.PENDING, LocalDateTime.now()))
                .delivery(new Delivery(Delivery.DeliveryStatus.IN_INVENTORY, LocalDateTime.now()))
                .items(items.stream().map(CreateOrderItemRequest::toDomain).collect(Collectors.toUnmodifiableSet()))
                .build();
    }
}
