package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User finById(Long userId);

    User save(User request);

    void delete(Long userId);

    User update(Long userId, UserRequest request);

    Optional<User> findUserByEmail(String email);
}
