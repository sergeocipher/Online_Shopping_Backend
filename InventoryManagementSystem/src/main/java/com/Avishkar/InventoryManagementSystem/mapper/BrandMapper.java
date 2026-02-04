package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.BrandCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.BrandDto;
import com.Avishkar.InventoryManagementSystem.dto.BrandUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(Brand brand);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    Brand toEntity(BrandCreateRequest request);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Brand brand, BrandUpdateRequest request);
}
