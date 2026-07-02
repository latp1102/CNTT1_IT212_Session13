package com.technova.smarteshop.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for OrderItem entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private String productImage;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
    
    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
    
    private BigDecimal totalPrice;
}
