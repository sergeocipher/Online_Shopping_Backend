package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.StyleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StyleDto;
import com.Avishkar.InventoryManagementSystem.dto.StyleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Style;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StyleMapper {
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "categoryId", source = "category.id")
    StyleDto toDto(Style style);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Style toEntity(StyleCreateRequest request);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Style style, StyleUpdateRequest request);
}
