package com.technova.smarteshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity representing an item in an order
 * Many-to-One relationship with Product and Order
 * Uses FetchType.LAZY to prevent N+1 queries
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;
}
