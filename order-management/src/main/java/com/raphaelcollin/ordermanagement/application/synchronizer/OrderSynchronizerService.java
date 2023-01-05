package com.raphaelcollin.ordermanagement.application.synchronizer;

import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderSynchronizerService {
    private final WriteOnlyOrderRepository orderRepository;

    public void handleNewOrderEvent(OrderEvent event) {
        orderRepository.upsertOrder(event.toEntity());
    }
}
