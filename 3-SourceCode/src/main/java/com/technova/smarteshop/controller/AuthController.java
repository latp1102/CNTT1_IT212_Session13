package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.dto.LoginRequest;
import com.technova.smarteshop.dto.LoginResponse;
import com.technova.smarteshop.dto.RegisterRequest;
import com.technova.smarteshop.dto.UserDto;
import com.technova.smarteshop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public controller for authentication operations
 * Endpoints: /api/v1/public/auth/*
 */
@RestController
@RequestMapping("/api/v1/public/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Register a new user
     * POST /api/v1/public/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
        UserDto userDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", userDto));
    }
    
    /**
     * Login user
     * POST /api/v1/public/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));
    }
}
