package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.stream.Collectors;

@Document
@Data
@AllArgsConstructor
public class OrderDocument {
    private String id;
    private CustomerDocument customer;
    private DestinationDocument destination;
    private DeliveryDocument delivery;
    private PaymentDocument payment;
    private Set<ItemDocument> items;

    public Order toDomain() {
        return Order
                .builder()
                .id(id)
                .customer(customer.toDomain())
                .destination(destination.toDomain())
                .delivery(delivery.toDomain())
                .payment(payment.toDomain())
                .items(items.stream().map(ItemDocument::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public static OrderDocument fromDomain(Order order) {
        return new OrderDocument(
                order.getId(),
                CustomerDocument.fromDomain(order.getCustomer()),
                DestinationDocument.fromDomain(order.getDestination()),
                DeliveryDocument.fromDomain(order.getDelivery()),
                PaymentDocument.fromDomain(order.getPayment()),
                order.getItems().stream().map(ItemDocument::fromDomain).collect(Collectors.toSet())
        );
    }
}
