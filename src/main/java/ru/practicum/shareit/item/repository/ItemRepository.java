package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemRequest;

import java.util.List;

public interface ItemRepository {

    List<Item> findAll();

    List<Item> findByUserId(Long userId);

    Item findById(Long itemId);

    Item update(Long itemId, ItemRequest request);

    Item save(Item request);

    void delete(Long itemId);
}
