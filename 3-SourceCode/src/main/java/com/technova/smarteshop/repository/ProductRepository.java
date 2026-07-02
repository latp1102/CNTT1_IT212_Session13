package com.technova.smarteshop.repository;

import com.technova.smarteshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Find products by category with pagination
     */
    Page<Product> findByCategory(String category, Pageable pageable);
    
    /**
     * Search products by name (case-insensitive)
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchByName(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * Find products with stock quantity greater than 0
     */
    Page<Product> findByStockQuantityGreaterThan(Integer quantity, Pageable pageable);
}
