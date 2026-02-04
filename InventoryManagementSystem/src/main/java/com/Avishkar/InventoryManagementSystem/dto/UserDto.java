package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Boolean isActive;
    private Long roleId;
    private Long storeId;
    private LocalDateTime createdAt;
}
