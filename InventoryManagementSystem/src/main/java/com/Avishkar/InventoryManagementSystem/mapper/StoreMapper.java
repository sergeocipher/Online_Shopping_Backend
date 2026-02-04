package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.StoreCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StoreDto;
import com.Avishkar.InventoryManagementSystem.dto.StoreUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreDto toDto(Store store);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    Store toEntity(StoreCreateRequest request);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Store store, StoreUpdateRequest request);
}
