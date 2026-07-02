package com.technova.smarteshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product in the system
 * Categories: PHONE, LAPTOP
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name cannot be blank")
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;
    
    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity must be >= 0")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;
    
    @Column(name = "category", length = 50)
    private String category;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Enum for product categories
     */
    public enum Category {
        PHONE,
        LAPTOP
    }
}
