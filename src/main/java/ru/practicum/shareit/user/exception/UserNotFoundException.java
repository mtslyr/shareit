package ru.practicum.shareit.user.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.shareit.common.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(CriteriaField field, String value) {
        super("Пользователь с %s = %s не найден.".formatted(field.name(), value), HttpStatus.NOT_FOUND);
    }

    public enum CriteriaField {
        EMAIL, ID
    }
}
