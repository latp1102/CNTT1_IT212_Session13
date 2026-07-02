package com.technova.smarteshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for Order entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    
    private Long id;
    private Long userId;
    private String username;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<OrderItemDto> orderItems;
}
