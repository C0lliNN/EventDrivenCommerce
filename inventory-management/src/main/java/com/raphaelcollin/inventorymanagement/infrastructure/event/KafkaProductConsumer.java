package com.raphaelcollin.inventorymanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.application.synchronizer.InventorySynchronizerService;
import com.raphaelcollin.inventorymanagement.domain.event.InventoryProductEvent;
import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import com.raphaelcollin.inventorymanagement.kafka.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProductConsumer {
    private final InventorySynchronizerService service;

    @KafkaListener(topics = "${kafka.products-topic}", groupId = "${kafka.products-consumer-group}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload Product product) {
        MDC.put("correlation_id", product.getCorrelationId().toString());
        log.info("New message received for product id: {}", product.getId());

        ProductEvent event = convertKafkaMessageToDomainEvent(product);
        service.handleNewProductEvent(event);

        MDC.clear();
    }

    private ProductEvent convertKafkaMessageToDomainEvent(Product product) {
        return new ProductEvent(
                product.getId().toString(),
                product.getCorrelationId().toString(),
                product.getName().toString(),
                product.getDescription().toString(),
                product.getAdditionalInfo().toString(),
                product.getPrice(),
                product.getTotalAmountAvailable(),
                product.getIsAvailable(),
                product.getInventoryProducts().stream()
                        .map(p -> new InventoryProductEvent(p.getInventoryId().toString(), p.getQuantity()))
                        .collect(Collectors.toSet())
        );
    }
}
