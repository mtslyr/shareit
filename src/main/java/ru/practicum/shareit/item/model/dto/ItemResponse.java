package ru.practicum.shareit.item.model.dto;

import lombok.Data;

@Data
public class ItemResponse {
    Long id;
    String name;
    String description;
    Boolean available;
    Integer shareCount;
}
