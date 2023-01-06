package com.raphaelcollin.ordermanagement.infrastructure.event.converter;

import com.raphaelcollin.ordermanagement.domain.entity.Delivery.DeliveryStatus;
import com.raphaelcollin.ordermanagement.domain.entity.Payment.PaymentStatus;
import com.raphaelcollin.ordermanagement.domain.event.CustomerEvent;
import com.raphaelcollin.ordermanagement.domain.event.DeliveryEvent;
import com.raphaelcollin.ordermanagement.domain.event.DestinationEvent;
import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;
import com.raphaelcollin.ordermanagement.domain.event.PaymentEvent;
import com.raphaelcollin.ordermanagement.kafka.Customer;
import com.raphaelcollin.ordermanagement.kafka.Delivery;
import com.raphaelcollin.ordermanagement.kafka.Destination;
import com.raphaelcollin.ordermanagement.kafka.Order;
import com.raphaelcollin.ordermanagement.kafka.Payment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

public class KafkaOrderEventConverter {
    public static OrderEvent convertOrderToDomain(Order order) {
        return new OrderEvent(
                order.getId(),
                order.getCorrelationId(),
                convertCustomerToDomain(order.getCustomer()),
                convertDestinationToDomain(order.getDestination()),
                convertDeliveryToDomain(order.getDelivery()),
                convertPaymentToDomain(order.getPayment()),
                order.getTotal(),
                order.getPaid(),
                order.getDelivered(),
                order.getItems().stream().map(KafkaItemEventConverter::convertItemToDomain).collect(Collectors.toSet())
        );
    }

    public static CustomerEvent convertCustomerToDomain(Customer customer) {
        return new CustomerEvent(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public static DeliveryEvent convertDeliveryToDomain(Delivery delivery) {
        return new DeliveryEvent(
                DeliveryStatus.valueOf(delivery.getDeliveryStatus()),
                LocalDateTime.ofEpochSecond(delivery.getLastUpdated(), 0, ZoneOffset.UTC),
                delivery.getDelivered()
        );
    }

    public static DestinationEvent convertDestinationToDomain(Destination destination) {
        return new DestinationEvent(
                destination.getAddress(),
                destination.getLatitude(),
                destination.getLongitude(),
                destination.getInstructions()
        );
    }

    public static PaymentEvent convertPaymentToDomain(Payment payment) {
        return new PaymentEvent(
                payment.getPaymentMethod(),
                PaymentStatus.valueOf(payment.getPaymentStatus()),
                LocalDateTime.ofEpochSecond(payment.getLastUpdated(), 0, ZoneOffset.UTC),
                payment.getPaid()
        );
    }

    public static Order convertDomainToKafkaOrder(OrderEvent order) {
        return new Order(
                order.getId(),
                order.getCorrelationId(),
                convertDomainToKafkaCustomer(order.getCustomer()),
                convertDomainToKafkaDelivery(order.getDelivery()),
                convertDomainToKafkaDestination(order.getDestination()),
                convertDomainToKafkaPayment(order.getPayment()),
                order.getTotal(),
                order.isPaid(),
                order.isDelivered(),
                order.getItems().stream().map(KafkaItemEventConverter::convertDomainToKafkaItem).collect(Collectors.toList())
        );
    }

    public static Customer convertDomainToKafkaCustomer(CustomerEvent customer) {
        return new Customer(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public static Delivery convertDomainToKafkaDelivery(DeliveryEvent delivery) {
        return new Delivery(
                delivery.getDeliveryStatus().name(),
                delivery.getLastUpdated().toEpochSecond(ZoneOffset.UTC),
                delivery.isDelivered()
        );
    }

    public static Destination convertDomainToKafkaDestination(DestinationEvent destination) {
        return new Destination(
                destination.getAddress(),
                destination.getLatitude(),
                destination.getLongitude(),
                destination.getInstructions()
        );
    }

    public static Payment convertDomainToKafkaPayment(PaymentEvent payment) {
        return new Payment(
                payment.getPaymentMethod(),
                payment.getPaymentStatus().name(),
                payment.getLastUpdated().toEpochSecond(ZoneOffset.UTC),
                payment.isPaid()
        );
    }
}
