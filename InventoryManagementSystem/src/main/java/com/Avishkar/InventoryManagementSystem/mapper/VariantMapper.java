package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.VariantCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.VariantDto;
import com.Avishkar.InventoryManagementSystem.dto.VariantUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VariantMapper {
    @Mapping(target = "styleId", source = "style.id")
    VariantDto toDto(Variant variant);

    @Mapping(target = "style", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Variant toEntity(VariantCreateRequest request);

    @Mapping(target = "style", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Variant variant, VariantUpdateRequest request);
}
