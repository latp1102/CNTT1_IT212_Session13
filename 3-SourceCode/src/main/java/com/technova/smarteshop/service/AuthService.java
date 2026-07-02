package com.technova.smarteshop.service;

import com.technova.smarteshop.dto.LoginRequest;
import com.technova.smarteshop.dto.LoginResponse;
import com.technova.smarteshop.dto.RegisterRequest;
import com.technova.smarteshop.dto.UserDto;
import com.technova.smarteshop.entity.Role;
import com.technova.smarteshop.entity.User;
import com.technova.smarteshop.exception.ResourceNotFoundException;
import com.technova.smarteshop.repository.RoleRepository;
import com.technova.smarteshop.repository.UserRepository;
import com.technova.smarteshop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for authentication operations
 * Handles user registration and login
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    /**
     * Register a new user
     * Automatically assigns CUSTOMER role
     */
    @Transactional
    public UserDto register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        
        // Assign CUSTOMER role
        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role_name", "CUSTOMER"));
        user.addRole(customerRole);
        
        // Save user
        User savedUser = userRepository.save(user);
        
        return mapToUserDto(savedUser);
    }
    
    /**
     * Login user and return JWT token
     */
    public LoginResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        // Generate JWT token
        String token = jwtUtil.generateToken(request.getUsername());
        
        // Get user details
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", request.getUsername()));
        
        return new LoginResponse(token, "Bearer", user.getId(), user.getUsername(), user.getEmail());
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
                .collect(java.util.stream.Collectors.toSet()));
        return userDto;
    }
}
