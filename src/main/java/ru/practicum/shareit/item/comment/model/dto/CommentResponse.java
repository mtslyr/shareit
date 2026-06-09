package ru.practicum.shareit.item.comment.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    Long id;
    Long userId;
    Long itemId;
    String text;
    String authorName;
    LocalDateTime created;
}
