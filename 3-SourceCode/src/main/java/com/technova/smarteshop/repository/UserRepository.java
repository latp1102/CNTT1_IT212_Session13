package com.technova.smarteshop.repository;

import com.technova.smarteshop.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 * Uses @EntityGraph to prevent N+1 queries when fetching roles
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by username with roles eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email with roles eagerly loaded
     * Uses @EntityGraph to prevent N+1 query problem
     */
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
    
    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
}
