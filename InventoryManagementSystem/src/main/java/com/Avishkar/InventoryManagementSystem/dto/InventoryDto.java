package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private Long id;
    private Integer quantity;
    private Integer reorderLevel;
    private Long storeId;
    private Long variantId;
    private LocalDateTime updatedAt;
}
