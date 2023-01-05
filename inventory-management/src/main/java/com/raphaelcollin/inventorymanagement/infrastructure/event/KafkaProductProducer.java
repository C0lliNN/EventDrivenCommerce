package com.raphaelcollin.inventorymanagement.infrastructure.event;

import com.raphaelcollin.inventorymanagement.application.inventorymanager.WriteOnlyProductStream;
import com.raphaelcollin.inventorymanagement.domain.event.ProductEvent;
import com.raphaelcollin.inventorymanagement.kafka.InventoryProduct;
import com.raphaelcollin.inventorymanagement.kafka.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class KafkaProductProducer implements WriteOnlyProductStream {
    private final KafkaTemplate<String, Product> kafkaTemplate;
    private final String productsTopic;

    public KafkaProductProducer(KafkaTemplate<String, Product> kafkaTemplate, @Value("${kafka.products-topic}") String productsTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsTopic = productsTopic;
    }

    @Override
    public void publishEvent(final ProductEvent event) {
        try {
            kafkaTemplate.send(productsTopic, event.getId(), createKafkaObjectFromDomain(event)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Product createKafkaObjectFromDomain(ProductEvent event) {
        Product product = new Product();
        product.setId(event.getId());
        product.setCorrelationId(event.getCorrelationId());
        product.setName(event.getName());
        product.setDescription(event.getDescription());
        product.setAdditionalInfo(event.getAdditionalInfo());
        product.setPrice((float) event.getPrice());
        product.setTotalAmountAvailable(event.getTotalAmountAvailable());
        product.setIsAvailable(event.isAvailable());
        product.setInventoryProducts(event.getInventoryProducts().stream()
                .map(i -> new InventoryProduct(i.getInventoryId(), i.getQuantity()))
                .collect(Collectors.toUnmodifiableList())
        );
        return product;
    }
}
