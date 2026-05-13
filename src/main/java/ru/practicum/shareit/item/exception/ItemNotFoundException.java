package ru.practicum.shareit.item.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.shareit.common.exception.ApiException;

public class ItemNotFoundException extends ApiException {
    public ItemNotFoundException(Long id) {
        super("Вещь с ID = %d не найден.".formatted(id), HttpStatus.NOT_FOUND);
    }
}
