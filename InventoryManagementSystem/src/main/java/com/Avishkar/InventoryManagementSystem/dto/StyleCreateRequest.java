package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StyleCreateRequest {
    @NotBlank
    private String name;
    private String season;
    private String imageUrl;
    @NotNull
    private Boolean isActive;
    @NotNull
    private Boolean isListedOnline;
    @NotNull
    private Long brandId;
    @NotNull
    private Long categoryId;
}
