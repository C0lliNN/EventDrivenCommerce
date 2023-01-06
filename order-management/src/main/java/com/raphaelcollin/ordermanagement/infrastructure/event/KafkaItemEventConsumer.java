package com.raphaelcollin.ordermanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.application.synchronizer.ItemSynchronizerService;
import com.raphaelcollin.ordermanagement.domain.event.ItemEvent;
import com.raphaelcollin.ordermanagement.infrastructure.event.converter.KafkaItemEventConverter;
import com.raphaelcollin.ordermanagement.kafka.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaItemEventConsumer {
    private final ItemSynchronizerService service;

    @KafkaListener(topics = "${kafka.items-topic}", groupId = "${kafka.items-consumer-group}", containerFactory = "itemKafkaListenerContainerFactory")
    public void consume(Item item) {
        MDC.put("correlation_id", item.getCorrelationId());
        log.info("New message received for item id: {}", item.getId());

        ItemEvent itemEvent = KafkaItemEventConverter.convertItemToDomain(item);
        service.handleNewItemEvent(itemEvent);

        MDC.clear();
    }
}
