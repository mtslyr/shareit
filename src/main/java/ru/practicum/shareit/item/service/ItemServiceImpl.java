package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.exception.AccessException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemRequest;
import ru.practicum.shareit.item.model.dto.ItemResponse;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper mapper;
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<ItemResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ItemResponse> searchItems(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }

        String lowerText = text.toLowerCase();

        return getAll().stream()
                .filter(ItemResponse::getAvailable)
                .filter(i ->
                        i.getName().toLowerCase().contains(lowerText)
                                || i.getDescription().toLowerCase().contains(lowerText))
                .toList();
    }

    @Override
    public List<ItemResponse> getAllItemsByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ItemResponse getItemById(Long itemId) {
        return mapper.toResponse(repository.findById(itemId));
    }

    @Override
    public ItemResponse save(Long userId, ItemRequest request) {
        userRepository.finById(userId);

        Item item = mapper.toItem(request);
        item.setUserId(userId);
        return mapper.toResponse(
                repository.save(item));
    }

    @Override
    public ItemResponse update(Long userId, Long itemId, ItemRequest request) {

        if (!isOwner(userId, itemId)) {
            throw new AccessException();
        }

        return mapper.toResponse(
                repository.update(itemId, request));
    }

    @Override
    public void delete(Long userId, Long itemId) {
        if (!isOwner(userId, itemId)) {
            throw new AccessException();
        }

        repository.delete(itemId);
    }

    private boolean isOwner(Long userId, Long itemId) {
        return repository.findById(itemId).getUserId().equals(userId);
    }
}
