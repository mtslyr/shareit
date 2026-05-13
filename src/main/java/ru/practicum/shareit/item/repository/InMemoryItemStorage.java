package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.util.UpdateUtil;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class InMemoryItemStorage implements ItemRepository {

    private final UpdateUtil<Item, ItemRequest> updateUtil;
    private static final List<Item> items = new ArrayList<>();
    private static final AtomicLong index = new AtomicLong(0);

    private static long nextId() {
        return index.getAndIncrement();
    }

    @Override
    public List<Item> findAll() {
        return List.copyOf(items);
    }

    @Override
    public List<Item> findByUserId(long userId) {
        return items.stream()
                .filter(i -> i.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Item findById(long itemId) {
        return items.stream()
                .filter(u -> u.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    @Override
    @SneakyThrows
    public Item update(Long itemId, ItemRequest request) {
        Item item = findById(itemId);
        return updateUtil.update(item, request);
    }

    @Override
    public Item save(Item request) {
        request.setId(nextId());
        items.add(request);
        return request;
    }

    @Override
    public void delete(long itemId) {
        items.removeIf(i -> i.getId().equals(itemId));
    }
}
