package com.raphaelcollin.ordermanagement.application.ordermanager;

import com.raphaelcollin.ordermanagement.application.ordermanager.request.CreateOrderItemRequest;
import com.raphaelcollin.ordermanagement.application.ordermanager.request.CreateOrderRequest;
import com.raphaelcollin.ordermanagement.domain.entity.Item;
import com.raphaelcollin.ordermanagement.domain.exception.InvalidRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderValidatorService {
    private final ReadOnlyItemRepository itemRepository;

    public void validateCreateOrderRequest(CreateOrderRequest request) {
        if (request.getId() == null || request.getId().isBlank()) {
            throw new InvalidRequestException("id is required");
        }
        if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
            throw new InvalidRequestException("customerName is required");
        }
        if (request.getCustomerEmail() == null || request.getCustomerEmail().isBlank()) {
            throw new InvalidRequestException("customerEmail is required");
        }
        if (request.getDestinationAddress() == null || request.getDestinationAddress().isBlank()) {
            throw new InvalidRequestException("destinationAddress is required");
        }
        if (request.getPaymentMethod() == null || request.getPaymentMethod().isBlank()) {
            throw new InvalidRequestException("paymentMethod is required");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidRequestException("at least one item is required");
        }

        Map<String, CreateOrderItemRequest> createOrderItemsById = request.getItems().stream()
                .collect(Collectors.toUnmodifiableMap(CreateOrderItemRequest::getItemId, item -> item));

        Map<String, Item> itemsById = itemRepository.findByIds(createOrderItemsById.keySet()).stream()
                .collect(Collectors.toUnmodifiableMap(Item::getId, item -> item));

        for (String itemId : createOrderItemsById.keySet()) {
            if (!itemsById.containsKey(itemId)) {
                throw new InvalidRequestException(String.format("The item id '%s' does not exit", itemId));
            }

            Item item = itemsById.get(itemId);
            if(!item.isQuantityAvailable(createOrderItemsById.get(itemId).getQuantity())) {
                throw new InvalidRequestException(String.format("The item id '%s' is not available for the requested quantity", itemId));
            }
        }
    }
}
