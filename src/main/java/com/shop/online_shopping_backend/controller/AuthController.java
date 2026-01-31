package com.shop.online_shopping_backend.controller;

import com.shop.online_shopping_backend.dto.ApiResponse;
import com.shop.online_shopping_backend.dto.LoginRequest;
import com.shop.online_shopping_backend.dto.RegisterRequest;
import com.shop.online_shopping_backend.model.User;
import com.shop.online_shopping_backend.service.AuthService;
import com.shop.online_shopping_backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        ApiResponse<User> response = new ApiResponse<>(true, "User registered successfully", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = authService.validateLogin(loginRequest);
        String token = jwtUtil.generateToken(user.getEmail());
        ApiResponse<String> response = new ApiResponse<>(true, "Login successful", token);
        return ResponseEntity.ok(response);
    }
}
