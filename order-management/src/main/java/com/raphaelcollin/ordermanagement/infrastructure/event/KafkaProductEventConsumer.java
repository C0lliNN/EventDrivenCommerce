package com.raphaelcollin.ordermanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.kafka.Product;
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
public class KafkaProductEventConsumer {
    private final ItemSynchronizerService service;

    @KafkaListener(topics = "${kafka.products-topic}", groupId = "${kafka.products-consumer-group}", containerFactory = "productKafkaListenerContainerFactory")
    public void consume(Product product) {
        MDC.put("correlation_id", product.getCorrelationId());
        log.info("New message received for item id: {}", product.getId());

        Item item = new Item(
                product.getId(),
                product.getCorrelationId(),
                product.getName(),
                product.getTotalAmountAvailable(),
                product.getPrice().doubleValue(),
                product.getIsAvailable()
        );

        ItemEvent itemEvent = KafkaItemEventConverter.convertItemToDomain(item);
        service.handleNewItemEvent(itemEvent);

        MDC.clear();
    }
}
