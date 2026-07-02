package com.technova.smarteshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user role in the system
 * Roles: CUSTOMER, STAFF, MANAGER
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "role_name", unique = true, nullable = false, length = 20)
    private String roleName;
    
    @Column(name = "description", length = 255)
    private String description;
    
    /**
     * Enum for role names
     */
    public enum RoleName {
        CUSTOMER,
        STAFF,
        MANAGER
    }
}
