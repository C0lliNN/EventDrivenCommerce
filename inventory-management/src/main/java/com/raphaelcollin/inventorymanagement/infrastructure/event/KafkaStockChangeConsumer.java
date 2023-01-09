package com.raphaelcollin.inventorymanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.InventoryManagementService;
import com.raphaelcollin.inventorymanagement.domain.event.StockChangeEvent;
import com.raphaelcollin.stockmanagement.kafka.StockChange;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaStockChangeConsumer {
    private final InventoryManagementService service;

    @KafkaListener(topics = "${kafka.stock-changes-topic}", groupId = "${kafka.stock-changes-consumer-group}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload StockChange stockChange) {
        log.info("New stock change for product id: {}", stockChange.getItemId());

        StockChangeEvent event = new StockChangeEvent(stockChange.getItemId().toString(), stockChange.getQuantityToBeChanged());
        service.processStockChange(event);

        log.info("Stock change processed successfully");
    }
}
