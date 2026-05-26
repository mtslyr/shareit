package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemRequest;
import ru.practicum.shareit.item.model.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    List<ItemResponse> getAll();

    List<ItemResponse> searchItems(String text);

    List<ItemResponse> getAllItemsByUserId(Long userId);

    ItemResponse getItemById(Long itemId);

    ItemResponse save(Long userId, ItemRequest request);

    ItemResponse update(Long userId, Long itemId, ItemRequest request);

    void delete(Long userId, Long itemId);
}
