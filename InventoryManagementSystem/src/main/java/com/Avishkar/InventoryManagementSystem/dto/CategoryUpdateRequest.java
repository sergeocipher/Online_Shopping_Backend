package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateRequest {
    @NotBlank
    private String name;
    private String description;
}
