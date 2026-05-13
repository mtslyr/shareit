package ru.practicum.shareit.user.model.dto;

import lombok.Data;

@Data
public class UserResponse {
    Long id;
    String name;
    String email;
}
