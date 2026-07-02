package com.technova.smarteshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login response containing JWT token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
}
