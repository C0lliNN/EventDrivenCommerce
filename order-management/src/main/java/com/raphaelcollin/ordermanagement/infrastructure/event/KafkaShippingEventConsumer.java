package com.raphaelcollin.ordermanagement.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.joda.time.DateTime;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaShippingEventConsumer {

    @KafkaListener(topics = "${kafka.shippings-topic}", groupId = "${kafka.shippings-consumer-group}", containerFactory = "shippingKafkaListenerContainerFactory")
    public void consume(GenericRecord record) {
        int id = (Integer) record.get("id");
        String status = (String) record.get("status");
        String orderId = (String) record.get("upstream_external_identifier");
        DateTime lastUpdated = (DateTime) record.get("last_updated");
        MDC.put("correlation_id", String.format("%d", id));
        log.info("New message received for shipping: {}, {}, {}, {}", id, status, orderId, lastUpdated.toLocalDateTime().toString());

    }
}
