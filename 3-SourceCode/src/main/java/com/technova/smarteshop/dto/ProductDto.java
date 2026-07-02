package com.technova.smarteshop.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Product entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    
    private Long id;
    
    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String name;
    
    private String description;
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    
    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity must be >= 0")
    private Integer stockQuantity;
    
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;
    
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
