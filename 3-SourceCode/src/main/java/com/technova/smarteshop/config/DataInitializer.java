package com.technova.smarteshop.config;

import com.technova.smarteshop.entity.Role;
import com.technova.smarteshop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data initializer to populate initial data
 * Creates default roles (CUSTOMER, STAFF, MANAGER) on application startup
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.count() == 0) {
            initializeRoles();
        }
    }
    
    private void initializeRoles() {
        Role customerRole = new Role();
        customerRole.setRoleName("CUSTOMER");
        customerRole.setDescription("Regular customer who can browse and purchase products");
        roleRepository.save(customerRole);
        
        Role staffRole = new Role();
        staffRole.setRoleName("STAFF");
        staffRole.setDescription("Warehouse staff who can manage products");
        roleRepository.save(staffRole);
        
        Role managerRole = new Role();
        managerRole.setRoleName("MANAGER");
        managerRole.setDescription("Manager who can view statistics and manage users");
        roleRepository.save(managerRole);
        
        System.out.println("Default roles initialized successfully");
    }
}
