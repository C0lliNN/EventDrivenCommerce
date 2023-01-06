package com.raphaelcollin.ordermanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.application.synchronizer.OrderSynchronizerService;
import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;
import com.raphaelcollin.ordermanagement.infrastructure.event.converter.KafkaOrderEventConverter;
import com.raphaelcollin.ordermanagement.kafka.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaOrderEventConsumer {
    private final OrderSynchronizerService service;

    @KafkaListener(topics = "${kafka.orders-topic}", groupId = "${kafka.orders-consumer-group}", containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(Order order) {
        MDC.put("correlation_id", order.getCorrelationId());
        log.info("New message received for order id: {}", order.getId());

        OrderEvent orderEvent = KafkaOrderEventConverter.convertOrderToDomain(order);
        service.handleNewOrderEvent(orderEvent);

        MDC.clear();
    }
}
