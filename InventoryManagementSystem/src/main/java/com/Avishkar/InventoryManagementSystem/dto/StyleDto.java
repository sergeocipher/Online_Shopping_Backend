package com.Avishkar.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StyleDto {
    private Long id;
    private String name;
    private String season;
    private String imageUrl;
    private Boolean isActive;
    private Boolean isListedOnline;
    private Long brandId;
    private Long categoryId;
    private LocalDateTime createdAt;
}
