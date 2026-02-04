package com.Avishkar.InventoryManagementSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "isActive is required")
    private Boolean isActive;

    @NotNull(message = "Role id is required")
    private Long roleId;

    private Long storeId;
}
