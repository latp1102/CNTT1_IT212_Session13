package com.technova.smarteshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an order in the system
 * One-to-Many relationship with OrderItem
 * Uses FetchType.LAZY to prevent N+1 queries
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * One-to-Many relationship with OrderItem
     * Uses FetchType.LAZY to prevent N+1 queries
     * Use @EntityGraph in Repository when eager fetching is needed
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    
    /**
     * Helper method to add an item to the order
     */
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    
    /**
     * Helper method to remove an item from the order
     */
    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
    
    /**
     * Enum for order status
     */
    public enum OrderStatus {
        PENDING,
        PAID,
        SHIPPED,
        CANCELLED
    }
}
