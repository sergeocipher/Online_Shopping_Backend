package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.InventoryCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryDto;
import com.Avishkar.InventoryManagementSystem.dto.InventoryUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "variantId", source = "variant.id")
    InventoryDto toDto(Inventory inventory);

    @Mapping(target = "store", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Inventory toEntity(InventoryCreateRequest request);

    @Mapping(target = "store", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Inventory inventory, InventoryUpdateRequest request);
}
