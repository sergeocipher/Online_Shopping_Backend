package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryEventCreateRequest {
    @NotBlank
    private String eventType;
    @NotNull
    private Integer quantityChange;
    private String reason;
    @NotNull
    private Long inventoryId;
}
