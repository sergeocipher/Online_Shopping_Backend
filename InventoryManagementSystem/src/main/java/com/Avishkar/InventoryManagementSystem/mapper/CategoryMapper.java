package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.CategoryCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.CategoryDto;
import com.Avishkar.InventoryManagementSystem.dto.CategoryUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    Category toEntity(CategoryCreateRequest request);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Category category, CategoryUpdateRequest request);
}
