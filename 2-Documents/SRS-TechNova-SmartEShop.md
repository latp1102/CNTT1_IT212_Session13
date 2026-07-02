# Software Requirements Specification (SRS)
## TechNova Smart E-Shop System

**Document Version:** 1.0  
**Date:** July 2, 2026  
**Prepared by:** AI-Assisted Business Analysis Team  
**Project:** Smart E-Shop (Shop AI) - TechNova Retail Electronics

---

## 1. Introduction

### 1.1 Purpose
Mục đích của tài liệu này là xác định và ghi nhận các yêu cầu chức năng và phi chức năng cho hệ thống "Smart E-Shop" của công ty TechNova. Tài liệu này đóng vai trò là cơ sở cho việc thiết kế, phát triển và kiểm thử hệ thống, đảm bảo rằng sản phẩm cuối cùng đáp ứng đầy đủ nhu cầu của khách hàng.

### 1.2 Document Conventions
- **FR-x.y:** Yêu cầu chức năng (Functional Requirement)
- **NFR-x.y:** Yêu cầu phi chức năng (Non-Functional Requirement)
- **UC-x:** Use Case
- **API-x:** Điểm kết nối API
- Tài liệu sử dụng định dạng Markdown
- Mã Mermaid được sử dụng cho các sơ đồ

### 1.3 Intended Audience
- **Nhà phát triển (Developers):** Sử dụng để hiểu yêu cầu và triển khai hệ thống
- **Kiểm thử viên (Testers):** Sử dụng để tạo kế hoạch kiểm thử
- **Quản lý dự án (Project Managers):** Sử dụng để theo dõi tiến độ
- **Khách hàng (TechNova):** Sử dụng để xác nhận yêu cầu

### 1.4 Project Scope
Hệ thống Smart E-Shop là một nền tảng thương mại điện tử cho phép:
- Khách hàng đăng ký, đăng nhập, xem sản phẩm, thêm vào giỏ hàng và thanh toán
- Nhân viên kho quản lý sản phẩm (thêm, sửa, xóa)
- Quản lý xem thống kê doanh thu và quản lý quyền nhân viên
- Hệ thống bảo mật với phân quyền RBAC và JWT authentication
- Hỗ trợ hàng chục ngàn người truy cập đồng thời

**Out of Scope:**
- Hệ thống thanh toán trực tuyến tích hợp với ngân hàng (chưa giai đoạn 1)
- Hệ thống vận chuyển và tracking
- Chatbot AI hỗ trợ khách hàng
- Mobile app (chỉ web-based)

### 1.5 Definitions and Acronyms

| Thuật ngữ | Định nghĩa |
|-----------|------------|
| **SRS** | Software Requirements Specification |
| **RBAC** | Role-Based Access Control |
| **JWT** | JSON Web Token |
| **API** | Application Programming Interface |
| **ERD** | Entity-Relationship Diagram |
| **N+1 Query** | Vấn đề hiệu năng khi thực hiện nhiều query lặp lại |
| **LAZY Loading** | Chiến lược tải dữ liệu khi cần thiết |
| **EntityGraph** | Cơ chế JPA để tối ưu query |

---

## 2. Overall Description

### 2.1 Product Perspective
Hệ thống Smart E-Shop là một ứng dụng web-based với kiến trúc RESTful API. Hệ thống bao gồm:
- **Frontend:** Web interface cho khách hàng và admin (không trong phạm vi dự án này)
- **Backend:** Spring Boot API server
- **Database:** MySQL relational database
- **Authentication:** JWT-based authentication

### 2.2 Product Functions

#### Chức năng chính:
1. **Quản lý người dùng:** Đăng ký, đăng nhập, phân quyền
2. **Quản lý sản phẩm:** CRUD operations cho nhân viên kho
3. **Giỏ hàng & Đặt hàng:** Quản lý giỏ hàng và thanh toán
4. **Quản lý đơn hàng:** Theo dõi trạng thái đơn hàng
5. **Thống kê:** Báo cáo doanh thu cho quản lý
6. **Quản lý quyền:** Cấp/thu hồi quyền cho nhân viên

### 2.3 User Characteristics

| User Type | Kỹ năng | Quyền hạn |
|-----------|---------|-----------|
| **Khách hàng** | Cơ bản sử dụng web | Xem sản phẩm, mua hàng |
| **Nhân viên kho** | Cơ bản quản lý kho | Quản lý sản phẩm |
| **Quản lý** | Kỹ năng quản lý | Thống kê, quản lý nhân viên |

### 2.4 Constraints

- **Technical:** Sử dụng Spring Boot, MySQL, JWT
- **Security:** Phải tuân thủ các tiêu chuẩn bảo mật dữ liệu cá nhân
- **Performance:** Hỗ trợ 10,000+ concurrent users
- **Time:** Phải hoàn thành trong [thời gian dự án]

### 2.5 Assumptions and Dependencies

- **Assumptions:**
  - Khách hàng có kết nối internet ổn định
  - Database server có uptime 99.9%
  - JWT secret key được bảo mật an toàn
  
- **Dependencies:**
  - Spring Boot 3.x
  - MySQL 8.0+
  - Java 17+
  - Spring Security 6

---

## 3. Specific Requirements

### 3.1 Functional Requirements

#### FR-1: Quản lý Tài khoản & Xác thực

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-1.1** | Hệ thống phải cho phép khách hàng đăng ký tài khoản với username, password, email, full_name, phone | High |
| **FR-1.2** | Hệ thống phải cho phép người dùng đăng nhập với username/password và trả về JWT token | High |
| **FR-1.3** | Hệ thống phải xác thực JWT token trong mỗi request bảo mật | High |
| **FR-1.4** | Password phải được mã hóa sử dụng bcrypt trước khi lưu vào database | High |
| **FR-1.5** | Username và email phải là duy nhất trong hệ thống | High |

#### FR-2: Quản lý Sản phẩm (Dành cho Nhân viên kho)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-2.1** | Nhân viên kho có thể thêm sản phẩm mới với name, description, price, stock_quantity, category, image_url | High |
| **FR-2.2** | Nhân viên kho có thể sửa thông tin sản phẩm hiện có | High |
| **FR-2.3** | Nhân viên kho có thể xóa sản phẩm (soft delete) | Medium |
| **FR-2.4** | Khách hàng có thể xem danh sách sản phẩm với phân trang | High |
| **FR-2.5** | Khách hàng có thể xem chi tiết sản phẩm theo ID | High |
| **FR-2.6** | Giá sản phẩm phải > 0 | High |
| **FR-2.7** | Số lượng tồn kho phải >= 0 | High |

#### FR-3: Giỏ hàng & Đặt hàng

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-3.1** | Khách hàng có thể thêm sản phẩm vào giỏ hàng | High |
| **FR-3.2** | Khách hàng có thể xem giỏ hàng của mình | High |
| **FR-3.3** | Khách hàng có thể xóa sản phẩm khỏi giỏ hàng | High |
| **FR-3.4** | Khách hàng có thể cập nhật số lượng sản phẩm trong giỏ hàng | High |
| **FR-3.5** | Khách hàng có thể thanh toán đơn hàng | High |
| **FR-3.6** | Hệ thống phải kiểm tra stock_quantity trước khi cho phép đặt hàng | High |
| **FR-3.7** | Hệ thống phải trừ stock_quantity khi đơn hàng được tạo | High |

#### FR-4: Quản lý Người dùng (Dành cho Quản lý)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-4.1** | Quản lý có thể xem danh sách tất cả người dùng | High |
| **FR-4.2** | Quản lý có thể cấp vai trò (STAFF/MANAGER) cho người dùng | High |
| **FR-4.3** | Quản lý có thể thu hồi vai trò của người dùng | High |
| **FR-4.4** | Quản lý có thể xem chi tiết thông tin người dùng | Medium |

#### FR-5: Thống kê & Báo cáo (Dành cho Quản lý)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-5.1** | Quản lý có thể xem tổng doanh thu theo khoảng thời gian | High |
| **FR-5.2** | Quản lý có thể xem số lượng đơn hàng theo trạng thái | High |
| **FR-5.3** | Quản lý có thể xem sản phẩm bán chạy nhất | Medium |

#### FR-6: Phân quyền & Bảo mật

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-6.1** | Khách hàng không được truy cập bất kỳ API nào trong /api/v1/admin/* | High |
| **FR-6.2** | Nhân viên kho (STAFF) chỉ được truy cập /api/v1/admin/products* | High |
| **FR-6.3** | Quản lý (MANAGER) được truy cập tất cả /api/v1/admin/* | High |
| **FR-6.4** | API /api/v1/public/* có thể truy cập bởi mọi người (không cần authentication) | High |
| **FR-6.5** | API phải trả về lỗi 401 (Unauthorized) khi token không hợp lệ | High |
| **FR-6.6** | API phải trả về lỗi 403 (Forbidden) khi user không có quyền | High |
| **FR-6.7** | Lỗi 401 và 403 phải trả về dưới dạng JSON | High |

### 3.2 Non-Functional Requirements

#### NFR-1: Hiệu năng (Performance)

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-1.1** | Hệ thống phải hỗ trợ hàng chục ngàn người truy cập cùng lúc | 10,000+ concurrent users |
| **NFR-1.2** | API response time cho các request đơn giản | < 200ms (95th percentile) |
| **NFR-1.3** | API response time cho các request phức tạp (có join) | < 500ms (95th percentile) |
| **NFR-1.4** | Hệ thống phải xử lý triệt để N+1 query problem | Sử dụng FetchType.LAZY và @EntityGraph |
| **NFR-1.5** | Database connection pooling phải được cấu hình | HikariCP với tối đa 100 connections |

#### NFR-2: Bảo mật (Security)

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-2.1** | Hệ thống phải sử dụng JWT Token cho authentication | JWT với HS256 algorithm |
| **NFR-2.2** | JWT token phải có thời gian hết hạn | 24 giờ cho access token |
| **NFR-2.3** | Mật khẩu phải được mã hóa | bcrypt với cost factor 10 |
| **NFR-2.4** | API phải trả về lỗi 401/403 dưới dạng JSON | Format: {"timestamp": "...", "status": 401, "error": "...", "message": "..."} |
| **NFR-2.5** | Không được lộ thông tin khách hàng | Sensitive data encryption at rest |
| **NFR-2.6** | JWT secret key phải được bảo mật | Lưu trong environment variable |

#### NFR-3: Khả năng mở rộng (Scalability)

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-3.1** | Cơ sở dữ liệu không được sập | High availability với replication |
| **NFR-3.2** | Hệ thống phải hỗ trợ horizontal scaling | Stateless API design |
| **NFR-3.3** | Database phải hỗ trợ connection pooling | HikariCP |

#### NFR-4: Độ tin cậy (Reliability)

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-4.1** | Uptime của hệ thống | 99.9% |
| **NFR-4.2** | Hệ thống phải có cơ chế xử lý exception | Global exception handler |

#### NFR-5: Usability

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-5.1** | API phải tuân thủ RESTful standards | HTTP methods đúng semantics |
| **NFR-5.2** | API documentation phải rõ ràng | Swagger/OpenAPI 3.0 |

### 3.3 External Interface Requirements

#### 3.3.1 User Interfaces
- Web browser interface (không trong phạm vi backend API)
- Admin dashboard interface (không trong phạm vi backend API)

#### 3.3.2 API Interfaces

**Public APIs (Không cần authentication):**
- `GET /api/v1/public/products` - Xem danh sách sản phẩm
- `GET /api/v1/public/products/{id}` - Xem chi tiết sản phẩm
- `POST /api/v1/public/auth/register` - Đăng ký tài khoản
- `POST /api/v1/public/auth/login` - Đăng nhập

**Customer APIs (Cần authentication với role CUSTOMER):**
- `GET /api/v1/customer/cart` - Xem giỏ hàng
- `POST /api/v1/customer/cart` - Thêm vào giỏ hàng
- `PUT /api/v1/customer/cart/{id}` - Cập nhật giỏ hàng
- `DELETE /api/v1/customer/cart/{id}` - Xóa khỏi giỏ hàng
- `POST /api/v1/customer/orders` - Tạo đơn hàng
- `GET /api/v1/customer/orders` - Xem đơn hàng của mình

**Admin APIs (Cần authentication với role STAFF hoặc MANAGER):**
- `POST /api/v1/admin/products` - Thêm sản phẩm (STAFF, MANAGER)
- `PUT /api/v1/admin/products/{id}` - Sửa sản phẩm (STAFF, MANAGER)
- `DELETE /api/v1/admin/products/{id}` - Xóa sản phẩm (STAFF, MANAGER)

**Manager APIs (Cần authentication với role MANAGER):**
- `GET /api/v1/admin/users` - Xem danh sách người dùng
- `PUT /api/v1/admin/users/{id}/roles` - Cấp quyền cho người dùng
- `GET /api/v1/admin/statistics/revenue` - Xem thống kê doanh thu

#### 3.3.3 Database Interfaces
- MySQL 8.0+ database
- JDBC connection via Spring Data JPA

### 3.4 System Attributes

#### 3.4.1 Availability
- Hệ thống phải có uptime 99.9%
- Downtime tối đa: 8.76 giờ/năm

#### 3.4.2 Security
- Authentication: JWT
- Authorization: RBAC
- Password encryption: bcrypt
- Data encryption: AES-256 cho sensitive data

#### 3.4.3 Maintainability
- Code phải tuân thủ Clean Code principles
- Phải có JavaDoc cho tất cả public methods
- Package structure theo Layered Architecture

---

## 4. Database Design

### 4.1 Entity-Relationship Diagram

```mermaid
erDiagram
    %% User-Role Relationship (Many-to-Many)
    USERS ||--o{ USER_ROLES : has
    ROLES ||--o{ USER_ROLES : assigned_to
    
    %% User-Order Relationship (One-to-Many)
    USERS ||--o{ ORDERS : places
    
    %% Order-Order_Item Relationship (One-to-Many)
    ORDERS ||--o{ ORDER_ITEMS : contains
    
    %% Product-Order_Item Relationship (One-to-Many)
    PRODUCTS ||--o{ ORDER_ITEMS : included_in

    USERS {
        BIGINT id PK
        VARCHAR(50) username UK "Unique"
        VARCHAR(255) password "Hashed"
        VARCHAR(100) email UK "Unique"
        VARCHAR(100) full_name
        VARCHAR(20) phone
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    ROLES {
        BIGINT id PK
        VARCHAR(20) role_name UK "CUSTOMER, STAFF, MANAGER"
        VARCHAR(255) description
    }

    USER_ROLES {
        BIGINT user_id FK
        BIGINT role_id FK
        TIMESTAMP assigned_at
    }

    PRODUCTS {
        BIGINT id PK
        VARCHAR(100) name
        TEXT description
        DECIMAL(15,2) price "Must be > 0"
        INT stock_quantity "Must be >= 0"
        VARCHAR(50) category "PHONE, LAPTOP"
        VARCHAR(500) image_url
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    ORDERS {
        BIGINT id PK
        BIGINT user_id FK
        DECIMAL(15,2) total_amount
        VARCHAR(20) status "PENDING, PAID, SHIPPED, CANCELLED"
        TIMESTAMP order_date
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    ORDER_ITEMS {
        BIGINT id PK
        BIGINT order_id FK
        BIGINT product_id FK
        INT quantity "Must be > 0"
        DECIMAL(15,2) unit_price
    }
```

### 4.2 Table Relationships

| Bảng | Quan hệ | Loại | Mô tả |
|------|---------|------|-------|
| **USERS** | USER_ROLES | One-to-Many | Một user có nhiều role |
| **ROLES** | USER_ROLES | One-to-Many | Một role được gán cho nhiều user |
| **USERS** | ORDERS | One-to-Many | Một user có nhiều order |
| **ORDERS** | ORDER_ITEMS | One-to-Many | Một order có nhiều items |
| **PRODUCTS** | ORDER_ITEMS | One-to-Many | Một product xuất hiện trong nhiều order items |

### 4.3 N+1 Query Prevention Strategy

**Problem:** Khi fetch User với Roles, nếu không cấu hình đúng, Hibernate sẽ thực hiện:
- 1 query để lấy User
- N query để lấy Roles của N users

**Solution:**
1. Sử dụng `FetchType.LAZY` cho tất cả các quan hệ
2. Sử dụng `@EntityGraph` trong Repository khi cần eager fetch
3. Ví dụ cho User-Roles:
   ```java
   @EntityGraph(attributePaths = {"roles"})
   Optional<User> findByUsername(String username);
   ```

---

## 5. Use Cases

### 5.1 Use Case Diagram

```mermaid
graph TB
    %% Actors
    Customer((Khách hàng))
    WarehouseStaff((Nhân viên kho))
    Manager((Quản lý))

    %% System Boundaries
    subgraph Customer_Portal["Customer Portal"]
        UC1[Đăng ký tài khoản]
        UC2[Đăng nhập]
        UC3[Xem danh sách sản phẩm]
        UC4[Xem chi tiết sản phẩm]
        UC5[Thêm vào giỏ hàng]
        UC6[Xem giỏ hàng]
        UC7[Xóa khỏi giỏ hàng]
        UC8[Thanh toán đơn hàng]
    end

    subgraph Admin_Portal["Admin Portal"]
        UC9[Thêm sản phẩm]
        UC10[Sửa sản phẩm]
        UC11[Xóa sản phẩm]
        UC12[Xem thống kê doanh thu]
        UC13[Xem báo cáo bán hàng]
        UC14[Xem danh sách nhân viên]
        UC15[Cấp quyền cho nhân viên]
        UC16[Thu hồi quyền nhân viên]
    end

    %% Customer Relationships
    Customer --> UC1
    Customer --> UC2
    Customer --> UC3
    Customer --> UC4
    Customer --> UC5
    Customer --> UC6
    Customer --> UC7
    Customer --> UC8

    %% Relationships between use cases
    UC5 -.-> UC3 : <<extend>>
    UC6 -.-> UC5 : <<include>>
    UC7 -.-> UC6 : <<include>>
    UC8 -.-> UC2 : <<include>>
    UC8 -.-> UC6 : <<include>>

    %% Warehouse Staff Relationships
    WarehouseStaff --> UC9
    WarehouseStaff --> UC10
    WarehouseStaff --> UC11

    %% Manager Relationships
    Manager --> UC12
    Manager --> UC13
    Manager --> UC14
    Manager --> UC15
    Manager --> UC16

    %% Manager can also do warehouse staff tasks
    Manager -.-> UC9
    Manager -.-> UC10
    Manager -.-> UC11

    %% Styling
    classDef actor fill:#f9f,stroke:#333,stroke-width:2px
    classDef usecase fill:#bbf,stroke:#333,stroke-width:1px
    classDef system fill:#dfd,stroke:#333,stroke-width:2px,stroke-dasharray: 5 5

    class Customer,WarehouseStaff,Manager actor
    class UC1,UC2,UC3,UC4,UC5,UC6,UC7,UC8,UC9,UC10,UC11,UC12,UC13,UC14,UC15,UC16 usecase
    class Customer_Portal,Admin_Portal system
```

### 5.2 Use Case Descriptions

#### UC-1: Đăng ký tài khoản
| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-1 |
| **Actor** | Khách hàng |
| **Description** | Khách hàng đăng ký tài khoản mới để sử dụng hệ thống |
| **Precondition** | Chưa có tài khoản trong hệ thống |
| **Main Flow** | 1. Khách hàng nhập username, password, email, full_name, phone<br>2. Hệ thống validate dữ liệu đầu vào<br>3. Hệ thống kiểm tra username/email duy nhất<br>4. Hệ thống mã hóa password<br>5. Hệ thống lưu user vào database với role CUSTOMER<br>6. Hệ thống trả về thông báo thành công |
| **Alternative Flow** | 3a. Username hoặc email đã tồn tại: Hệ thống trả về lỗi 400 |
| **Postcondition** | Tài khoản được tạo thành công |

#### UC-2: Đăng nhập
| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-2 |
| **Actor** | Khách hàng, Nhân viên kho, Quản lý |
| **Description** | Người dùng đăng nhập để nhận JWT token |
| **Precondition** | Đã có tài khoản trong hệ thống |
| **Main Flow** | 1. Người dùng nhập username và password<br>2. Hệ thống validate dữ liệu đầu vào<br>3. Hệ thống tìm user theo username<br>4. Hệ thống verify password<br>5. Hệ thống generate JWT token<br>6. Hệ thống trả về token |
| **Alternative Flow** | 3a. User không tồn tại: Hệ thống trả về lỗi 401<br>4a. Password sai: Hệ thống trả về lỗi 401 |
| **Postcondition** | User nhận được JWT token |

#### UC-8: Thanh toán đơn hàng
| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-8 |
| **Actor** | Khách hàng |
| **Description** | Khách hàng thanh toán giỏ hàng để tạo đơn hàng |
| **Precondition** | Đã đăng nhập, giỏ hàng có sản phẩm |
| **Main Flow** | 1. Khách hàng chọn thanh toán<br>2. Hệ thống kiểm tra stock_quantity của từng sản phẩm<br>3. Hệ thống tính total_amount<br>4. Hệ thống tạo Order với status PENDING<br>5. Hệ thống tạo Order_Items cho từng sản phẩm<br>6. Hệ thống trừ stock_quantity<br>7. Hệ thống xóa giỏ hàng<br>8. Hệ thống trả về Order ID |
| **Alternative Flow** | 2a. Sản phẩm hết hàng: Hệ thống trả về lỗi 400 |
| **Postcondition** | Đơn hàng được tạo, stock được cập nhật |

#### UC-9: Thêm sản phẩm
| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-9 |
| **Actor** | Nhân viên kho, Quản lý |
| **Description** | Thêm sản phẩm mới vào hệ thống |
| **Precondition** | Đã đăng nhập với role STAFF hoặc MANAGER |
| **Main Flow** | 1. Nhân viên nhập thông tin sản phẩm<br>2. Hệ thống validate dữ liệu (price > 0, stock >= 0)<br>3. Hệ thống lưu sản phẩm vào database<br>4. Hệ thống trả về thông báo thành công |
| **Alternative Flow** | 2a. Dữ liệu không hợp lệ: Hệ thống trả về lỗi 400 |
| **Postcondition** | Sản phẩm được thêm thành công |

#### UC-15: Cấp quyền cho nhân viên
| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-15 |
| **Actor** | Quản lý |
| **Description** | Quản lý cấp role cho nhân viên |
| **Precondition** | Đã đăng nhập với role MANAGER |
| **Main Flow** | 1. Quản lý chọn user và role<br>2. Hệ thống kiểm tra quyền của quản lý<br>3. Hệ thống thêm role cho user trong USER_ROLES<br>4. Hệ thống trả về thông báo thành công |
| **Alternative Flow** | 2a. Quản lý không có quyền: Hệ thống trả về lỗi 403 |
| **Postcondition** | User được cấp role mới |

---

## 6. Appendix

### 6.1 Traceability Matrix

| Requirement ID | Use Case | API Endpoint | Priority |
|----------------|----------|--------------|----------|
| FR-1.1 | UC-1 | POST /api/v1/public/auth/register | High |
| FR-1.2 | UC-2 | POST /api/v1/public/auth/login | High |
| FR-2.1 | UC-9 | POST /api/v1/admin/products | High |
| FR-2.2 | UC-10 | PUT /api/v1/admin/products/{id} | High |
| FR-3.5 | UC-8 | POST /api/v1/customer/orders | High |
| FR-4.2 | UC-15 | PUT /api/v1/admin/users/{id}/roles | High |
| FR-5.1 | - | GET /api/v1/admin/statistics/revenue | High |

### 6.2 References
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- JWT Specification: https://tools.ietf.org/html/rfc7519

---

**Document History**

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2026-07-02 | AI-Assisted Team | Initial version |
