package com.raphaelcollin.ordermanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.application.ordermanager.OrderEventProducer;
import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;
import com.raphaelcollin.ordermanagement.infrastructure.event.converter.KafkaOrderEventConverter;
import com.raphaelcollin.ordermanagement.kafka.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaOrderEventProducer implements OrderEventProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String ordersTopic;

    public KafkaOrderEventProducer(final KafkaTemplate<String, Order> kafkaTemplate,
                                   @Value("${kafka.orders-topic}") final  String ordersTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.ordersTopic = ordersTopic;
    }

    @Override
    public void publishEvent(final OrderEvent event) {
        Order order = KafkaOrderEventConverter.convertDomainToKafkaOrder(event);

        try {
            kafkaTemplate.send(ordersTopic, order.getId(), order).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
