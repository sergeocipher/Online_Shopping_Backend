package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class InventoryUpdateRequest {
    @PositiveOrZero
    private Integer quantity;
    @PositiveOrZero
    private Integer reorderLevel;
    @NotNull
    private Long storeId;
    @NotNull
    private Long variantId;
}
