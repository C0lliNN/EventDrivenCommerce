package com.raphaelcollin.stockmanagement.infrastructure.event;

import com.raphaelcollin.ordermanagement.kafka.Order;
import com.raphaelcollin.stockmanagement.application.StockManagementService;
import com.raphaelcollin.stockmanagement.domain.event.ItemEvent;
import com.raphaelcollin.stockmanagement.domain.event.OrderEvent;
import com.raphaelcollin.stockmanagement.domain.event.StockChangeEvent;
import com.raphaelcollin.stockmanagement.domain.exception.OrderAlreadyProcessedException;
import com.raphaelcollin.stockmanagement.kafka.StockChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderStreamProcessor {

    @Value("${kafka.topic.orders}")
    private String ordersTopic;

    @Value("${kafka.topic.stock-changes}")
    private String stockChangesTopic;

    private final Serde<String> stringSerde = Serdes.String();
    private final Serde<Order> orderSerde;
    private final Serde<StockChange> stockChangeSerde;
    private final StockManagementService service;

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream(ordersTopic, Consumed.with(stringSerde, orderSerde))
                .peek((id, order) -> log.info("Consuming order {}", id))
                .mapValues(this::mapOrderToEvent)
                .mapValues(orderEvent -> {
                    try {
                        return service.processOrder(orderEvent);
                    } catch (OrderAlreadyProcessedException e) {
                        log.info("Not consuming order {} because it was already processed", orderEvent.getId());
                        return Collections.<StockChangeEvent>emptySet();
                    }
                })
                .flatMapValues(events -> events)
                .mapValues(this::mapEventToStockChange)
                .to(stockChangesTopic, Produced.with(stringSerde, stockChangeSerde));
    }

    private OrderEvent mapOrderToEvent(Order order) {
        return new OrderEvent(
                order.getId(),
                order.getCorrelationId(),
                order.getItems().stream()
                    .map(i -> new ItemEvent(i.getId(), i.getQuantity()))
                    .collect(Collectors.toSet())
        );
    }

    private StockChange mapEventToStockChange(StockChangeEvent event) {
        return new StockChange(event.getItemId(), event.getQuantityToBeChanged());
    }
}
