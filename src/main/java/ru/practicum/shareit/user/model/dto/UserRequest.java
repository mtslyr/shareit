package ru.practicum.shareit.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.common.validation.OnCreate;
import ru.practicum.shareit.common.validation.OnUpdate;

@Data
public class UserRequest {
    @NotNull(groups = OnCreate.class, message = "Имя должно быть указано")
    String name;

    @NotNull(groups = OnCreate.class, message = "Эл почта должна быть указана")
    @Email(groups = {OnCreate.class, OnUpdate.class})
    String email;
}
