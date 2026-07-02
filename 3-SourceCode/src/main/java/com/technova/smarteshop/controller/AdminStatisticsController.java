package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin controller for statistics and reporting
 * Endpoints: /api/v1/admin/statistics/*
 * Requires MANAGER role only
 */
@RestController
@RequestMapping("/api/v1/admin/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class AdminStatisticsController {
    
    private final OrderRepository orderRepository;
    
    /**
     * Get revenue statistics for a date range
     * GET /api/v1/admin/statistics/revenue?start=2024-01-01&end=2024-12-31
     */
    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRevenueStatistics(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        
        LocalDateTime startDateTime = start != null 
                ? LocalDateTime.parse(start + "T00:00:00") 
                : LocalDateTime.now().minusDays(30);
        
        LocalDateTime endDateTime = end != null 
                ? LocalDateTime.parse(end + "T23:59:59") 
                : LocalDateTime.now();
        
        BigDecimal totalRevenue = orderRepository.calculateTotalRevenue(startDateTime, endDateTime)
                .orElse(BigDecimal.ZERO);
        
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("startDate", startDateTime);
        statistics.put("endDate", endDateTime);
        statistics.put("totalRevenue", totalRevenue);
        
        return ResponseEntity.ok(ApiResponse.success(statistics));
    }
    
    /**
     * Get order count by status
     * GET /api/v1/admin/statistics/orders/by-status
     */
    @GetMapping("/orders/by-status")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getOrderCountByStatus() {
        Map<String, Long> statusCounts = new HashMap<>();
        
        statusCounts.put("PENDING", orderRepository.countByStatus(com.technova.smarteshop.entity.Order.OrderStatus.PENDING));
        statusCounts.put("PAID", orderRepository.countByStatus(com.technova.smarteshop.entity.Order.OrderStatus.PAID));
        statusCounts.put("SHIPPED", orderRepository.countByStatus(com.technova.smarteshop.entity.Order.OrderStatus.SHIPPED));
        statusCounts.put("CANCELLED", orderRepository.countByStatus(com.technova.smarteshop.entity.Order.OrderStatus.CANCELLED));
        
        return ResponseEntity.ok(ApiResponse.success(statusCounts));
    }
}
