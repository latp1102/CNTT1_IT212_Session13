package com.technova.smarteshop.controller;

import com.technova.smarteshop.dto.ApiResponse;
import com.technova.smarteshop.dto.ProductDto;
import com.technova.smarteshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public controller for product browsing
 * Endpoints: /api/v1/public/products/*
 * Accessible without authentication
 */
@RestController
@RequestMapping("/api/v1/public/products")
@RequiredArgsConstructor
public class PublicProductController {
    
    private final ProductService productService;
    
    /**
     * Get all products with pagination
     * GET /api/v1/public/products?page=0&size=10&sort=id,desc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ProductDto> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    /**
     * Get product by ID
     * GET /api/v1/public/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
    
    /**
     * Search products by name
     * GET /api/v1/public/products/search?keyword=iphone&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    /**
     * Get available products (stock > 0)
     * GET /api/v1/public/products/available?page=0&size=10
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAvailableProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getAvailableProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
}
