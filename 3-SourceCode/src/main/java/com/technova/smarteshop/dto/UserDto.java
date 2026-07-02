package com.technova.smarteshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for User entity
 * Used for data transfer between layers
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;
    
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    private Set<String> roles;
}
