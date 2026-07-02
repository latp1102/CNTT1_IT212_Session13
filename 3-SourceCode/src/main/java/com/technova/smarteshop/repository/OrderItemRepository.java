package com.technova.smarteshop.repository;

import com.technova.smarteshop.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for OrderItem entity
 * Uses @EntityGraph to prevent N+1 queries when fetching product and order
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Find order items by order ID with product eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"product"})
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * Find order items by product ID with order eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"order"})
    List<OrderItem> findByProductId(Long productId);
}
