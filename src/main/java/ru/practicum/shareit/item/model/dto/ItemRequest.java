package ru.practicum.shareit.item.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.common.validation.OnCreate;

@Data
public class ItemRequest {

    @NotNull(groups = OnCreate.class, message = "Наименование должно быть указано")
    @NotEmpty(groups = OnCreate.class, message = "Наименование должно быть указано")
    String name;

    @NotNull(groups = OnCreate.class, message = "Описание должно быть указано")
    @NotEmpty(groups = OnCreate.class, message = "Описание должно быть указано")
    String description;

    @NotNull(groups = OnCreate.class, message = "Доступность должна быть указана")
    Boolean available;
}
