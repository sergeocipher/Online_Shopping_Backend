package com.Avishkar.InventoryManagementSystem.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private Instant expiresAt;
    private String username;
}
