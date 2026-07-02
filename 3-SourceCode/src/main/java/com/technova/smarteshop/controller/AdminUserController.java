package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.dto.UserDto;
import com.technova.smarteshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin controller for user management
 * Endpoints: /api/v1/admin/users/*
 * Requires MANAGER role only
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class AdminUserController {
    
    private final UserService userService;
    
    /**
     * Get all users
     * GET /api/v1/admin/users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    /**
     * Get user by ID
     * GET /api/v1/admin/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
    
    /**
     * Assign role to user
     * PUT /api/v1/admin/users/{id}/roles?role=MANAGER
     */
    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDto>> assignRole(
            @PathVariable Long id,
            @RequestParam String role) {
        UserDto user = userService.assignRoleToUser(id, role);
        return ResponseEntity.ok(ApiResponse.success("Role assigned successfully", user));
    }
    
    /**
     * Remove role from user
     * DELETE /api/v1/admin/users/{id}/roles?role=MANAGER
     */
    @DeleteMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDto>> removeRole(
            @PathVariable Long id,
            @RequestParam String role) {
        UserDto user = userService.removeRoleFromUser(id, role);
        return ResponseEntity.ok(ApiResponse.success("Role removed successfully", user));
    }
}
