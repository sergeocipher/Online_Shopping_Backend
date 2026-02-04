package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VariantDto {
    private Long id;
    private String sku;
    private String size;
    private String color;
    private String barcodeValue;
    private String qrCodeValue;
    private Boolean isActive;
    private Long styleId;
    private LocalDateTime createdAt;
}
