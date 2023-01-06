package com.raphaelcollin.ordermanagement.application.synchronizer;

import com.raphaelcollin.ordermanagement.domain.event.ItemEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemSynchronizerService {
    private final WriteOnlyItemRepository itemRepository;

    public void handleNewItemEvent(ItemEvent itemEvent) {
        itemRepository.upsertItem(itemEvent.toEntity());
    }
}
