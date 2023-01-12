package com.raphaelcollin.ordermanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.application.ordermanager.OrderService;
import com.raphaelcollin.ordermanagement.application.ordermanager.request.UpdateDeliveryRequest;
import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaShippingEventConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "${kafka.shippings-topic}", groupId = "${kafka.shippings-consumer-group}", containerFactory = "shippingKafkaListenerContainerFactory")
    public void consume(GenericRecord record) {
        int id = (Integer) record.get("id");
        MDC.put("correlation_id", String.format("%d", id));

        log.info("New message received for shipping id: {}", id);

        String orderId = (String) record.get("upstream_external_identifier");
        Delivery.DeliveryStatus status = Delivery.DeliveryStatus.valueOf((String) record.get("status"));

        UpdateDeliveryRequest request = new UpdateDeliveryRequest(orderId, status);

        orderService.handleDeliveryUpdate(request);
    }
}
