package com.Avishkar.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VariantCreateRequest {
    @NotBlank
    private String sku;
    @NotBlank
    private String size;
    @NotBlank
    private String color;
    private String barcodeValue;
    private String qrCodeValue;
    @NotNull
    private Boolean isActive;
    @NotNull
    private Long styleId;
}
