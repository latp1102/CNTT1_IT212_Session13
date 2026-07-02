package com.technova.smarteshop.repository;

import com.technova.smarteshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find role by role name
     */
    Optional<Role> findByRoleName(String roleName);
    
    /**
     * Check if role name exists
     */
    boolean existsByRoleName(String roleName);
}
