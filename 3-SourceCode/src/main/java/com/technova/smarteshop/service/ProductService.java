package com.technova.smarteshop.service;

import com.technova.smarteshop.dto.ProductDto;
import com.technova.smarteshop.entity.Product;
import com.technova.smarteshop.exception.ResourceNotFoundException;
import com.technova.smarteshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service for product operations
 * Handles CRUD operations for products
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    /**
     * Get all products with pagination
     */
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToProductDto);
    }
    
    /**
     * Get product by ID
     */
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return mapToProductDto(product);
    }
    
    /**
     * Create new product
     */
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCategory(productDto.getCategory());
        product.setImageUrl(productDto.getImageUrl());
        
        Product savedProduct = productRepository.save(product);
        return mapToProductDto(savedProduct);
    }
    
    /**
     * Update existing product
     */
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCategory(productDto.getCategory());
        product.setImageUrl(productDto.getImageUrl());
        
        Product updatedProduct = productRepository.save(product);
        return mapToProductDto(updatedProduct);
    }
    
    /**
     * Delete product
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productRepository.delete(product);
    }
    
    /**
     * Search products by name
     */
    public Page<ProductDto> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByName(keyword, pageable)
                .map(this::mapToProductDto);
    }
    
    /**
     * Get available products (stock > 0)
     */
    public Page<ProductDto> getAvailableProducts(Pageable pageable) {
        return productRepository.findByStockQuantityGreaterThan(0, pageable)
                .map(this::mapToProductDto);
    }
    
    /**
     * Map Product entity to ProductDto
     */
    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());
        return productDto;
    }
}
