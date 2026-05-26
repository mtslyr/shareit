package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static ru.practicum.shareit.user.exception.UserNotFoundException.CriteriaField.ID;

@Repository
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private static final AtomicLong index = new AtomicLong(0);

    private static long nextId() {
        return index.getAndIncrement();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public User finById(Long userId) {
        User u = users.get(userId);

        if (u == null) {
            throw new UserNotFoundException(ID, userId.toString());
        }

        return u;
    }

    @Override
    public User save(User request) {
        request.setId(nextId());
        users.put(request.getId(), request);
        return request;
    }

    @Override
    public void delete(Long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException(ID, userId.toString());
        }

        users.remove(userId);
    }

    @Override
    public User update(Long userId, UserRequest request) {
        User user = finById(userId);

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
