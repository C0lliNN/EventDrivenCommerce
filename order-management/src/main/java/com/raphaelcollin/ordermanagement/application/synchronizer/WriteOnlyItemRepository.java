package com.raphaelcollin.ordermanagement.application.synchronizer;

import com.raphaelcollin.ordermanagement.domain.entity.Item;

public interface WriteOnlyItemRepository {
    void upsertItem(Item item);
}
