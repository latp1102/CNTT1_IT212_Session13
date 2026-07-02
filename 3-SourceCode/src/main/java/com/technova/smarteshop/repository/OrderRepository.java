package com.technova.smarteshop.repository;

import com.technova.smarteshop.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Order entity
 * Uses @EntityGraph to prevent N+1 queries when fetching order items and user
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find order by ID with order items and user eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"orderItems", "user"})
    Optional<Order> findById(Long id);
    
    /**
     * Find all orders by user with order items eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findByUserId(Long userId);
    
    /**
     * Find orders by status
     */
    List<Order> findByStatus(Order.OrderStatus status);
    
    /**
     * Calculate total revenue for a given date range
     */
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'PAID' AND o.orderDate BETWEEN :startDate AND :endDate")
    Optional<java.math.BigDecimal> calculateTotalRevenue(@Param("startDate") java.time.LocalDateTime startDate, 
                                                          @Param("endDate") java.time.LocalDateTime endDate);
    
    /**
     * Count orders by status
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(@Param("status") Order.OrderStatus status);
}
