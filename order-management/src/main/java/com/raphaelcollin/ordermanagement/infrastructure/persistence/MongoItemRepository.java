package com.raphaelcollin.ordermanagement.infrastructure.persistence;

import com.raphaelcollin.ordermanagement.application.ordermanager.ReadOnlyItemRepository;
import com.raphaelcollin.ordermanagement.application.synchronizer.WriteOnlyItemRepository;
import com.raphaelcollin.ordermanagement.domain.entity.Item;
import com.raphaelcollin.ordermanagement.infrastructure.persistence.document.ItemDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MongoItemRepository implements ReadOnlyItemRepository, WriteOnlyItemRepository {
    private final MongoTemplate template;

    @Override
    public Set<Item> findByIds(final Set<String> itemIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(itemIds));
        return template.find(query, ItemDocument.class).stream()
                .map(ItemDocument::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Item> findById(final String itemId) {
        return Optional.ofNullable(template.findById(itemId, ItemDocument.class))
                .map(ItemDocument::toDomain);
    }

    @Override
    public void upsertItem(final Item item) {
        ItemDocument document = ItemDocument.fromDomain(item);

        template.save(document);
    }
}
