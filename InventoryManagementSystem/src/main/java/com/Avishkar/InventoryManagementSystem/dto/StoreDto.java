package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto {
    private Long id;
    private String name;
    private String location;
    private Boolean isActive;
    private Boolean isPublic;
    private LocalDateTime createdAt;
}
