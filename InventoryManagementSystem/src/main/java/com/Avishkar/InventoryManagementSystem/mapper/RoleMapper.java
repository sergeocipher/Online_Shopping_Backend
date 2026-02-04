package com.Avishkar.InventoryManagementSystem.mapper;

import com.Avishkar.InventoryManagementSystem.dto.RoleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.RoleDto;
import com.Avishkar.InventoryManagementSystem.dto.RoleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    Role toEntity(RoleCreateRequest request);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Role role, RoleUpdateRequest request);
}
