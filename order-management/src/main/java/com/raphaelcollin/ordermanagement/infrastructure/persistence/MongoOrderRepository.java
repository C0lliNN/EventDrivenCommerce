package com.raphaelcollin.ordermanagement.infrastructure.persistence;

import com.raphaelcollin.ordermanagement.application.ordermanager.ReadOnlyOrderRepository;
import com.raphaelcollin.ordermanagement.application.synchronizer.WriteOnlyOrderRepository;
import com.raphaelcollin.ordermanagement.domain.entity.Order;
import com.raphaelcollin.ordermanagement.infrastructure.persistence.document.OrderDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MongoOrderRepository implements ReadOnlyOrderRepository, WriteOnlyOrderRepository {
    private final MongoTemplate template;

    @Override
    public List<Order> findAll() {
        return template.findAll(OrderDocument.class).stream()
                .map(OrderDocument::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(final String orderId) {
        return Optional.ofNullable(template.findById(orderId, OrderDocument.class))
                .map(OrderDocument::toDomain);
    }

    @Override
    public void upsertOrder(final Order order) {
        OrderDocument document = OrderDocument.fromDomain(order);

        template.save(document);
    }
}
