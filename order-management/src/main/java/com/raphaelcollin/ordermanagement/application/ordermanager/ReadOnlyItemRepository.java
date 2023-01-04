package com.raphaelcollin.ordermanagement.application.ordermanager;

import com.raphaelcollin.ordermanagement.domain.entity.Item;

import java.util.Optional;
import java.util.Set;

public interface ReadOnlyItemRepository {
    Set<Item> findByIds(Set<String> itemIds);
    Optional<Item> findById(String itemId);
}
