package com.raphaelcollin.shippingmanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.kafka.Order;
import com.raphaelcollin.shippingmanagement.application.ShippingManagementService;
import com.raphaelcollin.shippingmanagement.application.request.CreateShippingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaOrderEventConsumer {
    private final ShippingManagementService service;

    @KafkaListener(topics = "${kafka.orders-topic}", groupId = "${kafka.orders-consumer-group}", containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(Order order) {
        MDC.put("correlation_id", order.getCorrelationId());
        log.info("New message received for order id: {}", order.getId());

        service.createShipping(new CreateShippingRequest(order.getId()));

        MDC.clear();
    }
}
