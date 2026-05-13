package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.util.UpdateUtil;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static ru.practicum.shareit.user.exception.UserNotFoundException.CriteriaField.ID;

@Repository
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserRepository {
    private final UpdateUtil<User, UserRequest> updateUtil;
    private static final List<User> users = new ArrayList<>();
    private static final AtomicLong index = new AtomicLong(0);

    private static long nextId() {
        return index.getAndIncrement();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
    public User finById(Long userId) {
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(ID, userId.toString()));
    }

    @Override
    public User save(User request) {
        request.setId(nextId());
        users.add(request);
        return request;
    }

    @Override
    public void delete(Long userId) {
        boolean removed = users.removeIf(u -> u.getId().equals(userId));

        if (!removed) {
            throw new UserNotFoundException(ID, userId.toString());
        }
    }

    @Override
    public User update(Long userId, UserRequest request) throws Exception {
        User user = finById(userId);
        return updateUtil.update(user, request);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
