package com.technova.smarteshop.service;

import com.technova.smarteshop.dto.OrderDto;
import com.technova.smarteshop.dto.OrderItemDto;
import com.technova.smarteshop.entity.Order;
import com.technova.smarteshop.entity.OrderItem;
import com.technova.smarteshop.entity.Product;
import com.technova.smarteshop.entity.User;
import com.technova.smarteshop.exception.ResourceNotFoundException;
import com.technova.smarteshop.repository.OrderRepository;
import com.technova.smarteshop.repository.ProductRepository;
import com.technova.smarteshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for order operations
 * Handles order creation and retrieval
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    /**
     * Get all orders for a user
     */
    public List<OrderDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get order by ID
     */
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return mapToOrderDto(order);
    }
    
    /**
     * Create a new order
     */
    @Transactional
    public OrderDto createOrder(Long userId, Set<OrderItemDto> orderItemDtos) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemDto itemDto : orderItemDtos) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemDto.getProductId()));
            
            // Check stock
            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            
            order.addOrderItem(orderItem);
            
            // Calculate item total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
            
            // Update stock
            product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
            productRepository.save(product);
        }
        
        order.setTotalAmount(totalAmount);
        
        Order savedOrder = orderRepository.save(order);
        return mapToOrderDto(savedOrder);
    }
    
    /**
     * Update order status
     */
    @Transactional
    public OrderDto updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderDto(updatedOrder);
    }
    
    /**
     * Map Order entity to OrderDto
     */
    private OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setUsername(order.getUser().getUsername());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setStatus(order.getStatus().name());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        
        Set<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toSet());
        orderDto.setOrderItems(itemDtos);
        
        return orderDto;
    }
    
    /**
     * Map OrderItem entity to OrderItemDto
     */
    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setId(orderItem.getId());
        itemDto.setOrderId(orderItem.getOrder().getId());
        itemDto.setProductId(orderItem.getProduct().getId());
        itemDto.setProductName(orderItem.getProduct().getName());
        itemDto.setProductImage(orderItem.getProduct().getImageUrl());
        itemDto.setQuantity(orderItem.getQuantity());
        itemDto.setUnitPrice(orderItem.getUnitPrice());
        itemDto.setTotalPrice(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return itemDto;
    }
}
