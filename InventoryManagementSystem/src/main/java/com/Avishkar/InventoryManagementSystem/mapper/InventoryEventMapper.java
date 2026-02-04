package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.InventoryEventCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryEventDto;
import com.Avishkar.InventoryManagementSystem.entity.InventoryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryEventMapper {
    @Mapping(target = "inventoryId", source = "inventory.id")
    @Mapping(target = "eventType", source = "eventType")
    InventoryEventDto toDto(InventoryEvent event);

    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    InventoryEvent toEntity(InventoryEventCreateRequest request);
}
