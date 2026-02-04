package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoreCreateRequest {
    @NotBlank
    private String name;
    private String location;
    @NotNull
    private Boolean isActive;
    @NotNull
    private Boolean isPublic;
}
