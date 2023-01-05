package com.raphaelcollin.ordermanagement.application.ordermanager;

import com.raphaelcollin.ordermanagement.application.ordermanager.request.CreateOrderRequest;
import com.raphaelcollin.ordermanagement.application.ordermanager.response.OrderResponse;
import com.raphaelcollin.ordermanagement.domain.entity.Item;
import com.raphaelcollin.ordermanagement.domain.entity.Order;
import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;
import com.raphaelcollin.ordermanagement.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderEventProducer orderEventProducer;
    private final ReadOnlyItemRepository itemRepository;
    private final ReadOnlyOrderRepository orderRepository;
    private final OrderValidatorService orderValidatorService;

    public void createOrder(CreateOrderRequest request) {
        orderValidatorService.validateCreateOrderRequest(request);

        Order order = request.toDomain();
        Set<String> itemIds = order.getItems().stream().map(Item::getId).collect(Collectors.toUnmodifiableSet());

        Set<Item> fullItems = itemRepository.findByIds(itemIds);
        order.setItems(fullItems);

        orderEventProducer.publishEvent(OrderEvent.fromEntity(order));
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public OrderResponse getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::fromDomain)
                .orElseThrow(() -> new EntityNotFoundException("order not found"));
    }
}
