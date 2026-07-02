# AI Prompt Log: Backend Development (Spring Boot)
**Date:** 2026-07-02
**Phase:** Phase 2 - AI-Assisted Coding

---

## Prompt 5: Initialize Spring Boot Project with Dependencies

**Prompt:**
```
Act as a Senior Spring Boot Developer. Create a Maven pom.xml file for a Spring Boot 3.2.0 project with the following dependencies:

Required dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- MySQL Connector (mysql-connector-j)
- JWT (JJWT 0.12.3) - api, impl, jackson
- Lombok
- Spring Boot DevTools
- Spring Boot Starter Test
- Spring Security Test

Project details:
- Group ID: com.technova
- Artifact ID: smart-eshop
- Version: 1.0.0
- Name: Smart E-Shop
- Description: TechNova Smart E-Shop Backend API
- Java version: 17

Please create a complete pom.xml file with all necessary configurations.
```

---

## Prompt 6: Create Entity Classes with JPA/Hibernate

**Prompt:**
```
Act as a Senior Backend Developer. Create JPA Entity classes for the TechNova Smart E-Shop system based on the ERD diagram from the previous phase.

Entities to create:
1. Role - with id, roleName, description
2. User - with id, username, password, email, fullName, phone, createdAt, updatedAt, and Many-to-Many relationship with Role
3. Product - with id, name, description, price, stockQuantity, category, imageUrl, createdAt, updatedAt
4. Order - with id, user, totalAmount, status, orderDate, createdAt, updatedAt, and One-to-Many relationship with OrderItem
5. OrderItem - with id, order, product, quantity, unitPrice

Requirements:
- Use Jakarta Persistence (jakarta.persistence.*)
- Use Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
- Use FetchType.LAZY for all relationships to prevent N+1 queries
- Add validation annotations where appropriate
- Add @CreationTimestamp and @UpdateTimestamp for audit fields
- Add enums for RoleName, Product.Category, and Order.OrderStatus
- Add helper methods for managing relationships (addRole, removeRole, addOrderItem, removeOrderItem)
- Add detailed JavaDoc comments

Create each entity in a separate file in the entity package.
```

---

## Prompt 7: Create Repository Layer with @EntityGraph

**Prompt:**
```
Act as a Senior Backend Developer. Create Spring Data JPA Repository interfaces for all entities.

Repositories to create:
1. RoleRepository - findByRoleName, existsByRoleName
2. UserRepository - findByUsername, findByEmail, existsByUsername, existsByEmail
3. ProductRepository - findByCategory, searchByName, findByStockQuantityGreaterThan
4. OrderRepository - findById, findByUserId, findByStatus, calculateTotalRevenue, countByStatus
5. OrderItemRepository - findByOrderId, findByProductId

Requirements:
- Extend JpaRepository
- Use @EntityGraph for methods that fetch relationships to prevent N+1 queries:
  - UserRepository: Use @EntityGraph for findByUsername and findByEmail to eagerly fetch roles
  - OrderRepository: Use @EntityGraph for findById and findByUserId to eagerly fetch orderItems and user
  - OrderItemRepository: Use @EntityGraph for findByOrderId and findByProductId to eagerly fetch product and order
- Add @Query annotations for custom queries (searchByName, calculateTotalRevenue, countByStatus)
- Add detailed JavaDoc comments

Create each repository in a separate file in the repository package.
```

---

## Prompt 8: Create DTO Classes with Validation

**Prompt:**
```
Act as a Senior Backend Developer. Create DTO (Data Transfer Object) classes for the TechNova Smart E-Shop system.

DTOs to create:
1. UserDto - id, username, email, fullName, phone, roles (Set<String>)
2. RegisterRequest - username, password, email, fullName, phone with validation
3. LoginRequest - username, password with validation
4. LoginResponse - token, type, userId, username, email
5. ProductDto - id, name, description, price, stockQuantity, category, imageUrl, createdAt, updatedAt with validation
6. OrderDto - id, userId, username, totalAmount, status, orderDate, createdAt, updatedAt, orderItems (Set<OrderItemDto>)
7. OrderItemDto - id, orderId, productId, productName, productImage, quantity, unitPrice, totalPrice with validation
8. ErrorResponse - timestamp, status, error, message, path (for JSON error responses)
9. ApiResponse<T> - success, message, data (standard API response wrapper)

Requirements:
- Use Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
- Add Jakarta validation annotations (@NotBlank, @NotNull, @Email, @Size, @Min, @DecimalMin)
- Add helper static methods in ApiResponse (success, error)
- Add detailed JavaDoc comments

Create each DTO in a separate file in the dto package.
```

---

## Prompt 9: Configure Spring Security 6 + JWT with RBAC

**Prompt:**
```
Act as a Senior Security Engineer. Configure Spring Security 6 with JWT authentication and Role-Based Access Control (RBAC) for the TechNova Smart E-Shop system.

Classes to create:
1. JwtUtil - JWT token generation and validation
2. JwtAuthenticationFilter - Filter to intercept requests and validate JWT tokens
3. CustomUserDetailsService - Load user details for Spring Security
4. SecurityConfig - Main security configuration

Security rules:
- /api/v1/public/** - Accessible to everyone (no authentication required)
- /api/v1/customer/** - Requires authentication with CUSTOMER role
- /api/v1/admin/products/** - Requires authentication with STAFF or MANAGER role
- /api/v1/admin/users/** - Requires authentication with MANAGER role only
- /api/v1/admin/statistics/** - Requires authentication with MANAGER role only
- All other requests require authentication

Requirements:
- Use Spring Security 6 configuration (not deprecated WebSecurityConfigurerAdapter)
- Use JWT with HS256 algorithm
- Configure stateless session management
- Use BCryptPasswordEncoder with cost factor 10
- Prefix role names with "ROLE_" for Spring Security
- Use @EntityGraph from UserRepository in CustomUserDetailsService
- Add detailed JavaDoc comments

Create each class in a separate file in the security package.
```

---

## Prompt 10: Create Custom Exception Handling (401/403 JSON Responses)

**Prompt:**
```
Act as a Senior Backend Developer. Create custom exception handling for the TechNova Smart E-Shop system to return JSON error responses for 401 Unauthorized and 403 Forbidden errors.

Classes to create:
1. ResourceNotFoundException - Custom exception for resource not found scenarios
2. CustomAuthenticationEntryPoint - Returns 401 Unauthorized in JSON format
3. CustomAccessDeniedHandler - Returns 403 Forbidden in JSON format
4. GlobalExceptionHandler - Centralized exception handler for all exceptions

Requirements:
- CustomAuthenticationEntryPoint should implement AuthenticationEntryPoint
- CustomAccessDeniedHandler should implement AccessDeniedHandler
- GlobalExceptionHandler should use @RestControllerAdvice
- Handle ResourceNotFoundException, BadCredentialsException, AccessDeniedException, MethodArgumentNotValidException, and general Exception
- Return consistent JSON format with timestamp, status, error, message, and path
- Add detailed JavaDoc comments

Create each class in a separate file in the exception package.
```

---

## Prompt 11: Integrate Custom Exception Handlers into SecurityConfig

**Prompt:**
```
Update the SecurityConfig class to integrate the custom exception handlers (CustomAuthenticationEntryPoint and CustomAccessDeniedHandler).

Requirements:
- Add imports for CustomAuthenticationEntryPoint and CustomAccessDeniedHandler
- Inject CustomAuthenticationEntryPoint and CustomAccessDeniedHandler as final fields
- Configure exceptionHandling in securityFilterChain to use these handlers:
  - .exceptionHandling(exception -> exception
      .authenticationEntryPoint(customAuthenticationEntryPoint)
      .accessDeniedHandler(customAccessDeniedHandler))
```

---

## Prompt 12: Create Service Layer with Business Logic

**Prompt:**
```
Act as a Senior Backend Developer. Create Service layer classes for the TechNova Smart E-Shop system.

Services to create:
1. AuthService - register, login
2. ProductService - getAllProducts, getProductById, createProduct, updateProduct, deleteProduct, searchProducts, getAvailableProducts
3. OrderService - getOrdersByUserId, getOrderById, createOrder, updateOrderStatus
4. UserService - getAllUsers, getUserById, assignRoleToUser, removeRoleFromUser

Requirements:
- Use @Service annotation
- Use @RequiredArgsConstructor for dependency injection
- Use @Transactional for methods that modify data
- Map entities to DTOs
- Implement business logic:
  - AuthService: Assign CUSTOMER role on registration, generate JWT token on login
  - ProductService: Handle CRUD operations
  - OrderService: Check stock before creating order, update stock after order creation
  - UserService: Handle role assignment and removal
- Add detailed JavaDoc comments

Create each service in a separate file in the service package.
```

---

## Prompt 13: Create Controller Layer with RESTful Endpoints

**Prompt:**
```
Act as a Senior Backend Developer. Create Controller layer classes for the TechNova Smart E-Shop system.

Controllers to create:
1. AuthController - /api/v1/public/auth/* (register, login)
2. PublicProductController - /api/v1/public/products/* (getAll, getById, search, available)
3. CustomerOrderController - /api/v1/customer/orders/* (getMyOrders, getById, createOrder)
4. AdminProductController - /api/v1/admin/products/* (create, update, delete)
5. AdminUserController - /api/v1/admin/users/* (getAll, getById, assignRole, removeRole)
6. AdminStatisticsController - /api/v1/admin/statistics/* (getRevenue, getOrderCountByStatus)

Requirements:
- Use @RestController and @RequestMapping
- Use @RequiredArgsConstructor for dependency injection
- Use @PreAuthorize for role-based authorization:
  - CustomerOrderController: @PreAuthorize("hasRole('CUSTOMER')")
  - AdminProductController: @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
  - AdminUserController: @PreAuthorize("hasRole('MANAGER')")
  - AdminStatisticsController: @PreAuthorize("hasRole('MANAGER')")
- Use @Valid for request body validation
- Return ResponseEntity<ApiResponse<T>> for consistent responses
- Add pagination support for product endpoints
- Add detailed JavaDoc comments

Create each controller in a separate file in the controller package.
```

---

## Prompt 14: Configure application.properties

**Prompt:**
```
Create an application.properties file for the TechNova Smart E-Shop system with the following configurations:

1. Application Configuration:
   - spring.application.name=smart-eshop
   - server.port=8080

2. Database Configuration (MySQL):
   - spring.datasource.url=jdbc:mysql://localhost:3306/technova_eshop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   - spring.datasource.username=root
   - spring.datasource.password=your_password_here
   - spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

3. JPA/Hibernate Configuration:
   - spring.jpa.hibernate.ddl-auto=update
   - spring.jpa.show-sql=true
   - spring.jpa.properties.hibernate.format_sql=true
   - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

4. Connection Pooling (HikariCP):
   - spring.datasource.hikari.maximum-pool-size=100
   - spring.datasource.hikari.minimum-idle=10
   - spring.datasource.hikari.connection-timeout=30000
   - spring.datasource.hikari.idle-timeout=600000
   - spring.datasource.hikari.max-lifetime=1800000

5. JWT Configuration:
   - jwt.secret=YourSuperSecretKeyForJWTTokenGenerationMustBeVeryLongAndSecure1234567890
   - jwt.expiration=86400000

6. Logging Configuration:
   - logging.level.com.technova.smarteshop=DEBUG
   - logging.level.org.springframework.security=DEBUG
   - logging.level.org.hibernate.SQL=DEBUG
   - logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

7. Jackson Configuration:
   - spring.jackson.serialization.write-dates-as-timestamps=false
   - spring.jackson.time-zone=UTC
```

---

## Prompt 15: Create Data Initializer

**Prompt:**
```
Create a DataInitializer class to populate initial data when the application starts.

Requirements:
- Implement CommandLineRunner
- Initialize default roles (CUSTOMER, STAFF, MANAGER) if they don't exist
- Use @Transactional for data operations
- Add descriptions for each role:
  - CUSTOMER: "Regular customer who can browse and purchase products"
  - STAFF: "Warehouse staff who can manage products"
  - MANAGER: "Manager who can view statistics and manage users"
- Print a message when roles are initialized
- Add detailed JavaDoc comments

Create the class in the config package.
```

---

## Prompt 16: Convert Maven to Gradle

**Prompt:**
```
Convert the Maven pom.xml file to a Gradle build.gradle file for the TechNova Smart E-Shop project.

Requirements:
- Use Gradle DSL
- Use Spring Boot plugin version 3.2.0
- Use io.spring.dependency-management plugin version 1.1.4
- Set Java version to 17
- Include all dependencies from pom.xml:
  - Spring Boot Web, Data JPA, Security, Validation
  - MySQL Connector
  - JWT (JJWT 0.12.3)
  - Lombok
  - DevTools
  - Test dependencies
- Configure Lombok with compileOnly and annotationProcessor
- Configure DevTools with developmentOnly
- Configure test dependencies with testImplementation
- Use JUnit Platform for testing
```

---

## Notes from AI Interaction
- AI successfully created all Entity classes with proper JPA annotations and FetchType.LAZY
- AI implemented @EntityGraph in repositories to prevent N+1 queries
- AI configured Spring Security 6 with JWT and RBAC according to requirements
- AI created custom exception handlers for JSON error responses
- AI integrated exception handlers into SecurityConfig
- AI implemented Service layer with business logic and transaction management
- AI created Controller layer with proper RESTful endpoints and role-based authorization
- AI configured application.properties with MySQL, JWT, and logging settings
- AI created DataInitializer to populate default roles on startup
- AI converted Maven to Gradle as requested by user
- All code includes detailed JavaDoc comments
- All DTOs include validation annotations
- All relationships use FetchType.LAZY to prevent N+1 queries
