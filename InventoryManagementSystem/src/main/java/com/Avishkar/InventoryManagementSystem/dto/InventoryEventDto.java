package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryEventDto {
    private Long id;
    private String eventType;
    private Integer quantityChange;
    private String reason;
    private Long inventoryId;
    private LocalDateTime createdAt;
}
