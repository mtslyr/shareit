package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.exception.EmailAlreadyUsedException;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.model.dto.UserRequest;
import ru.practicum.shareit.user.model.dto.UserResponse;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return mapper.toResponse(userRepository.finById(userId));
    }

    @Override
    public UserResponse save(UserRequest request) {
        validateUserEmail(request);
        return mapper.toResponse(
                userRepository.save(mapper.toUser(request)));
    }

    @Override
    public UserResponse update(Long userId, UserRequest request) {
        validateUserEmail(request);
        return mapper.toResponse(
                userRepository.update(userId, request));
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }

    public void validateUserEmail(UserRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException(request.getEmail());
        }
    }
}
