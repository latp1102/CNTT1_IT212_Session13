package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.dto.OrderDto;
import com.technova.smarteshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Customer controller for order operations
 * Endpoints: /api/v1/customer/orders/*
 * Requires CUSTOMER role
 */
@RestController
@RequestMapping("/api/v1/customer/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerOrderController {
    
    private final OrderService orderService;
    
    /**
     * Get current user's orders
     * GET /api/v1/customer/orders
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getMyOrders(Authentication authentication) {
        // In a real application, you would extract userId from JWT token
        // For simplicity, we assume username is the identifier
        String username = authentication.getName();
        // TODO: Implement userId extraction from JWT
        // For now, this is a placeholder
        List<OrderDto> orders = orderService.getOrdersByUserId(1L); // Placeholder
        return ResponseEntity.ok(ApiResponse.success(orders));
    }
    
    /**
     * Get order by ID
     * GET /api/v1/customer/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }
    
    /**
     * Create a new order
     * POST /api/v1/customer/orders
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @Valid @RequestBody Set<com.technova.smarteshop.dto.OrderItemDto> orderItems,
            Authentication authentication) {
        
        // In a real application, you would extract userId from JWT token
        // For simplicity, we assume username is the identifier
        String username = authentication.getName();
        // TODO: Implement userId extraction from JWT
        // For now, this is a placeholder
        OrderDto order = orderService.createOrder(1L, orderItems); // Placeholder
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
    }
}
