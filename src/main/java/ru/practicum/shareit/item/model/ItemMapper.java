package ru.practicum.shareit.item.model;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.model.dto.ItemRequest;
import ru.practicum.shareit.item.model.dto.ItemResponse;
import ru.practicum.shareit.item.model.dto.ItemWithBookingDates;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    Item toItem(ItemRequest request);

    ItemResponse toResponse(Item item);

    ItemWithBookingDates toResponseWithDates(Item item);

}
