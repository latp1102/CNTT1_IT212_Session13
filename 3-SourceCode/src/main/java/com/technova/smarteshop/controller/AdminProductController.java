package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.dto.ProductDto;
import com.technova.smarteshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Admin controller for product management
 * Endpoints: /api/v1/admin/products/*
 * Requires STAFF or MANAGER role
 */
@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
public class AdminProductController {
    
    private final ProductService productService;
    
    /**
     * Create a new product
     * POST /api/v1/admin/products
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", createdProduct));
    }
    
    /**
     * Update an existing product
     * PUT /api/v1/admin/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
    }
    
    /**
     * Delete a product
     * DELETE /api/v1/admin/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }
}
