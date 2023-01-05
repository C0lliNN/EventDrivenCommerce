package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import com.raphaelcollin.ordermanagement.domain.entity.Delivery.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
public class DeliveryDocument {
    DeliveryStatus deliveryStatus;
    LocalDateTime lastUpdated;

    public Delivery toDomain() {
        return new Delivery(deliveryStatus, lastUpdated);
    }

    public static DeliveryDocument fromDomain(Delivery delivery) {
        return new DeliveryDocument(
                delivery.getDeliveryStatus(),
                delivery.getLastUpdated()
        );
    }
}
