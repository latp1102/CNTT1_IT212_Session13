package com.technova.smarteshop.security;

import com.technova.smarteshop.exception.CustomAccessDeniedHandler;
import com.technova.smarteshop.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 6 Configuration
 * Implements JWT-based authentication and RBAC authorization
 * 
 * Security Rules:
 * - /api/v1/public/** : Accessible to everyone (no authentication required)
 * - /api/v1/customer/** : Requires authentication with CUSTOMER role
 * - /api/v1/admin/products** : Requires authentication with STAFF or MANAGER role
 * - /api/v1/admin/** : Requires authentication with MANAGER role
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    
    /**
     * Configure security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            )
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - no authentication required
                .requestMatchers("/api/v1/public/**").permitAll()
                
                // Customer endpoints - require CUSTOMER role
                .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                
                // Admin product endpoints - require STAFF or MANAGER role
                .requestMatchers("/api/v1/admin/products/**").hasAnyRole("STAFF", "MANAGER")
                
                // Admin management endpoints - require MANAGER role only
                .requestMatchers("/api/v1/admin/users/**").hasRole("MANAGER")
                .requestMatchers("/api/v1/admin/statistics/**").hasRole("MANAGER")
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * Configure authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    /**
     * Configure authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Configure password encoder (BCrypt with cost factor 10)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
