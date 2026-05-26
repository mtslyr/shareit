package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class InMemoryItemStorage implements ItemRepository {
    private static final Map<Long, Item> items = new HashMap<>();
    private static final AtomicLong index = new AtomicLong(0);

    private static Long nextId() {
        return index.getAndIncrement();
    }

    @Override
    public List<Item> findAll() {
        return List.copyOf(items.values());
    }

    @Override
    public List<Item> findByUserId(Long userId) {
        return items.values().stream()
                .filter(i -> i.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Item findById(Long itemId) {
        Item i = items.get(itemId);

        if (i == null) {
            throw new ItemNotFoundException(itemId);
        }

        return i;
    }

    @Override
    public Item update(Long itemId, ItemRequest request) {
        Item item = findById(itemId);

        if (request.getName() != null) {
            item.setName(request.getName());
        }

        if (request.getAvailable() != null) {
            item.setAvailable(request.getAvailable());
        }

        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }

        return item;
    }

    @Override
    public Item save(Item request) {
        request.setId(nextId());
        items.put(request.getId(), request);
        return request;
    }

    @Override
    public void delete(Long itemId) {
        items.remove(itemId);
    }
}
