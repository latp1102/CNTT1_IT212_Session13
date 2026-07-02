package com.technova.smarteshop.service;

import com.technova.smarteshop.dto.UserDto;
import com.technova.smarteshop.entity.Role;
import com.technova.smarteshop.entity.User;
import com.technova.smarteshop.exception.ResourceNotFoundException;
import com.technova.smarteshop.repository.RoleRepository;
import com.technova.smarteshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for user management operations
 * Handles user CRUD and role management
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    /**
     * Get all users
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get user by ID
     */
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return mapToUserDto(user);
    }
    
    /**
     * Assign role to user
     */
    @Transactional
    public UserDto assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role_name", roleName));
        
        // Check if user already has this role
        if (user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleName))) {
            throw new IllegalArgumentException("User already has role: " + roleName);
        }
        
        user.addRole(role);
        User updatedUser = userRepository.save(user);
        return mapToUserDto(updatedUser);
    }
    
    /**
     * Remove role from user
     */
    @Transactional
    public UserDto removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role_name", roleName));
        
        // Check if user has this role
        if (user.getRoles().stream().noneMatch(r -> r.getRoleName().equals(roleName))) {
            throw new IllegalArgumentException("User does not have role: " + roleName);
        }
        
        user.removeRole(role);
        User updatedUser = userRepository.save(user);
        return mapToUserDto(updatedUser);
    }
    
    /**
     * Map User entity to UserDto
     */
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet()));
        return userDto;
    }
}
