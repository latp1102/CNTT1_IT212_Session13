package com.technova.smarteshop.exception;

import com.technova.smarteshop.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom Access Denied Handler
 * Returns 403 Forbidden error in JSON format instead of default HTML
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, 
                      HttpServletResponse response, 
                      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpServletResponse.SC_FORBIDDEN,
                "Forbidden",
                accessDeniedException.getMessage(),
                request.getRequestURI()
        );
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
