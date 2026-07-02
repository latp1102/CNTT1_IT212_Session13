package com.technova.smarteshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;
    
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
}
