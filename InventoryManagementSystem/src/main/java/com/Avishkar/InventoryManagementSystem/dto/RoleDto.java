package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
