# Tài liệu Đặc tả Yêu cầu Phần mềm (SRS)
## Hệ thống Smart E-Shop của TechNova

**Phiên bản tài liệu:** 1.0  
**Ngày:** 2 tháng 7, 2026  
**Người chuẩn bị:** Nhóm phân tích kinh doanh có hỗ trợ AI  
**Dự án:** Smart E-Shop (Shop AI) - TechNova Bán lẻ Điện tử

---

## 1. Giới thiệu

### 1.1 Mục đích
Mục đích của tài liệu này là xác định và ghi nhận các yêu cầu chức năng và phi chức năng cho hệ thống "Smart E-Shop" của công ty TechNova. Tài liệu này đóng vai trò là cơ sở cho việc thiết kế, phát triển và kiểm thử hệ thống, đảm bảo rằng sản phẩm cuối cùng đáp ứng đầy đủ nhu cầu của khách hàng.

### 1.2 Quy ước tài liệu
- **FR-x.y:** Yêu cầu chức năng
- **NFR-x.y:** Yêu cầu phi chức năng
- **UC-x:** Trường hợp sử dụng
- **API-x:** Điểm kết nối API
- Tài liệu sử dụng định dạng Markdown
- Sơ đồ được vẽ bằng ASCII

### 1.3 Đối tượng đọc
- **Nhà phát triển (Developers):** Sử dụng để hiểu yêu cầu và triển khai hệ thống
- **Kiểm thử viên (Testers):** Sử dụng để tạo kế hoạch kiểm thử
- **Quản lý dự án (Project Managers):** Sử dụng để theo dõi tiến độ
- **Khách hàng (TechNova):** Sử dụng để xác nhận yêu cầu

### 1.4 Phạm vi dự án
Hệ thống Smart E-Shop là một nền tảng thương mại điện tử cho phép:
- Khách hàng đăng ký, đăng nhập, xem sản phẩm, thêm vào giỏ hàng và thanh toán
- Nhân viên kho quản lý sản phẩm (thêm, sửa, xóa)
- Quản lý xem thống kê doanh thu và quản lý quyền nhân viên
- Hệ thống bảo mật với phân quyền RBAC và JWT authentication
- Hỗ trợ hàng chục ngàn người truy cập đồng thời

**Ngoài phạm vi:**
- Hệ thống thanh toán trực tuyến tích hợp với ngân hàng (chưa giai đoạn 1)
- Hệ thống vận chuyển và tracking
- Chatbot AI hỗ trợ khách hàng
- Mobile app (chỉ web-based)

### 1.5 Định nghĩa và viết tắt

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

## 2. Mô tả tổng quan

### 2.1 Góc nhìn sản phẩm
Hệ thống Smart E-Shop là một ứng dụng web với kiến trúc RESTful API. Hệ thống bao gồm:
- **Frontend:** Giao diện web cho khách hàng và admin (không trong phạm vi dự án này)
- **Backend:** Máy chủ API Spring Boot
- **Cơ sở dữ liệu:** Cơ sở dữ liệu quan hệ MySQL
- **Xác thực:** Xác thực dựa trên JWT

### 2.2 Chức năng sản phẩm

#### Các chức năng chính:
1. **Quản lý người dùng:** Đăng ký, đăng nhập, phân quyền
2. **Quản lý sản phẩm:** Các thao tác CRUD cho nhân viên kho
3. **Giỏ hàng & Đặt hàng:** Quản lý giỏ hàng và thanh toán
4. **Quản lý đơn hàng:** Theo dõi trạng thái đơn hàng
5. **Thống kê:** Báo cáo doanh thu cho quản lý
6. **Quản lý quyền:** Cấp/thu hồi quyền cho nhân viên

### 2.3 Đặc điểm người dùng

| Loại người dùng | Kỹ năng | Quyền hạn |
|---------------|---------|-----------|
| **Khách hàng** | Cơ bản sử dụng web | Xem sản phẩm, mua hàng |
| **Nhân viên kho** | Cơ bản quản lý kho | Quản lý sản phẩm |
| **Quản lý** | Kỹ năng quản lý | Thống kê, quản lý nhân viên |

### 2.4 Ràng buộc

- **Kỹ thuật:** Sử dụng Spring Boot, MySQL, JWT
- **Bảo mật:** Phải tuân thủ các tiêu chuẩn bảo mật dữ liệu cá nhân
- **Hiệu năng:** Hỗ trợ 10,000+ người dùng đồng thời
- **Thời gian:** Phải hoàn thành trong [thời gian dự án]

### 2.5 Giả định và phụ thuộc

- **Giả định:**
  - Khách hàng có kết nối internet ổn định
  - Database server có uptime 99.9%
  - JWT secret key được bảo mật an toàn
  
- **Phụ thuộc:**
  - Spring Boot 3.x
  - MySQL 8.0+
  - Java 17+
  - Spring Security 6

---

## 3. Yêu cầu cụ thể

### 3.1 Yêu cầu chức năng

#### FR-1: Quản lý Tài khoản & Xác thực

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-1.1** | Hệ thống phải cho phép khách hàng đăng ký tài khoản với username, password, email, full_name, phone | Cao |
| **FR-1.2** | Hệ thống phải cho phép người dùng đăng nhập với username/password và trả về JWT token | Cao |
| **FR-1.3** | Hệ thống phải xác thực JWT token trong mỗi request bảo mật | Cao |
| **FR-1.4** | Password phải được mã hóa sử dụng bcrypt trước khi lưu vào database | Cao |
| **FR-1.5** | Username và email phải là duy nhất trong hệ thống | Cao |

#### FR-2: Quản lý Sản phẩm (Dành cho Nhân viên kho)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-2.1** | Nhân viên kho có thể thêm sản phẩm mới với name, description, price, stock_quantity, category, image_url | Cao |
| **FR-2.2** | Nhân viên kho có thể sửa thông tin sản phẩm hiện có | Cao |
| **FR-2.3** | Nhân viên kho có thể xóa sản phẩm (soft delete) | Trung bình |
| **FR-2.4** | Khách hàng có thể xem danh sách sản phẩm với phân trang | Cao |
| **FR-2.5** | Khách hàng có thể xem chi tiết sản phẩm theo ID | Cao |
| **FR-2.6** | Giá sản phẩm phải > 0 | Cao |
| **FR-2.7** | Số lượng tồn kho phải >= 0 | Cao |

#### FR-3: Giỏ hàng & Đặt hàng

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-3.1** | Khách hàng có thể thêm sản phẩm vào giỏ hàng | Cao |
| **FR-3.2** | Khách hàng có thể xem giỏ hàng của mình | Cao |
| **FR-3.3** | Khách hàng có thể xóa sản phẩm khỏi giỏ hàng | Cao |
| **FR-3.4** | Khách hàng có thể cập nhật số lượng sản phẩm trong giỏ hàng | Cao |
| **FR-3.5** | Khách hàng có thể thanh toán đơn hàng | Cao |
| **FR-3.6** | Hệ thống phải kiểm tra stock_quantity trước khi cho phép đặt hàng | Cao |
| **FR-3.7** | Hệ thống phải trừ stock_quantity khi đơn hàng được tạo | Cao |

#### FR-4: Quản lý Người dùng (Dành cho Quản lý)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-4.1** | Quản lý có thể xem danh sách tất cả người dùng | Cao |
| **FR-4.2** | Quản lý có thể cấp vai trò (STAFF/MANAGER) cho người dùng | Cao |
| **FR-4.3** | Quản lý có thể thu hồi vai trò của người dùng | Cao |
| **FR-4.4** | Quản lý có thể xem chi tiết thông tin người dùng | Trung bình |

#### FR-5: Thống kê & Báo cáo (Dành cho Quản lý)

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-5.1** | Quản lý có thể xem tổng doanh thu theo khoảng thời gian | Cao |
| **FR-5.2** | Quản lý có thể xem số lượng đơn hàng theo trạng thái | Cao |
| **FR-5.3** | Quản lý có thể xem sản phẩm bán chạy nhất | Trung bình |

#### FR-6: Phân quyền & Bảo mật

| ID | Mô tả | Ưu tiên |
|----|-------|---------|
| **FR-6.1** | Khách hàng không được truy cập bất kỳ API nào trong /api/v1/admin/* | Cao |
| **FR-6.2** | Nhân viên kho (STAFF) chỉ được truy cập /api/v1/admin/products* | Cao |
| **FR-6.3** | Quản lý (MANAGER) được truy cập tất cả /api/v1/admin/* | Cao |
| **FR-6.4** | API /api/v1/public/* có thể truy cập bởi mọi người (không cần authentication) | Cao |
| **FR-6.5** | API phải trả về lỗi 401 (Unauthorized) khi token không hợp lệ | Cao |
| **FR-6.6** | API phải trả về lỗi 403 (Forbidden) khi user không có quyền | Cao |
| **FR-6.7** | Lỗi 401 và 403 phải trả về dưới dạng JSON | Cao |

### 3.2 Yêu cầu phi chức năng

#### NFR-1: Hiệu năng

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-1.1** | Hệ thống phải hỗ trợ hàng chục ngàn người truy cập cùng lúc | 10,000+ concurrent users |
| **NFR-1.2** | API response time cho các request đơn giản | < 200ms (95th percentile) |
| **NFR-1.3** | API response time cho các request phức tạp (có join) | < 500ms (95th percentile) |
| **NFR-1.4** | Hệ thống phải xử lý triệt để N+1 query problem | Sử dụng FetchType.LAZY và @EntityGraph |
| **NFR-1.5** | Database connection pooling phải được cấu hình | HikariCP với tối đa 100 connections |

#### NFR-2: Bảo mật

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-2.1** | Hệ thống phải sử dụng JWT Token cho authentication | JWT với HS256 algorithm |
| **NFR-2.2** | JWT token phải có thời gian hết hạn | 24 giờ cho access token |
| **NFR-2.3** | Mật khẩu phải được mã hóa | bcrypt với cost factor 10 |
| **NFR-2.4** | API phải trả về lỗi 401/403 dưới dạng JSON | Format: {"timestamp": "...", "status": 401, "error": "...", "message": "..."} |
| **NFR-2.5** | Không được lộ thông tin khách hàng | Sensitive data encryption at rest |
| **NFR-2.6** | JWT secret key phải được bảo mật | Lưu trong environment variable |

#### NFR-3: Khả năng mở rộng

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-3.1** | Cơ sở dữ liệu không được sập | High availability với replication |
| **NFR-3.2** | Hệ thống phải hỗ trợ horizontal scaling | Stateless API design |
| **NFR-3.3** | Database phải hỗ trợ connection pooling | HikariCP |

#### NFR-4: Độ tin cậy

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-4.1** | Uptime của hệ thống | 99.9% |
| **NFR-4.2** | Hệ thống phải có cơ chế xử lý exception | Global exception handler |

#### NFR-5: Khả năng sử dụng

| ID | Mô tả | Đo lường |
|----|-------|----------|
| **NFR-5.1** | API phải tuân thủ tiêu chuẩn RESTful | Các phương thức HTTP đúng ngữ nghĩa |
| **NFR-5.2** | Tài liệu API phải rõ ràng | Swagger/OpenAPI 3.0 |

### 3.3 Yêu cầu giao diện bên ngoài

#### 3.3.1 Giao diện người dùng
- Giao diện trình duyệt web (không trong phạm vi backend API)
- Giao diện dashboard quản trị (không trong phạm vi backend API)

#### 3.3.2 Giao diện API

**API Công khai (Không cần xác thực):**
- `GET /api/v1/public/products` - Xem danh sách sản phẩm
- `GET /api/v1/public/products/{id}` - Xem chi tiết sản phẩm
- `POST /api/v1/public/auth/register` - Đăng ký tài khoản
- `POST /api/v1/public/auth/login` - Đăng nhập

**API Khách hàng (Cần xác thực với vai trò CUSTOMER):**
- `GET /api/v1/customer/cart` - Xem giỏ hàng
- `POST /api/v1/customer/cart` - Thêm vào giỏ hàng
- `PUT /api/v1/customer/cart/{id}` - Cập nhật giỏ hàng
- `DELETE /api/v1/customer/cart/{id}` - Xóa khỏi giỏ hàng
- `POST /api/v1/customer/orders` - Tạo đơn hàng
- `GET /api/v1/customer/orders` - Xem đơn hàng của mình

**API Quản trị (Cần xác thực với vai trò STAFF hoặc MANAGER):**
- `POST /api/v1/admin/products` - Thêm sản phẩm (STAFF, MANAGER)
- `PUT /api/v1/admin/products/{id}` - Sửa sản phẩm (STAFF, MANAGER)
- `DELETE /api/v1/admin/products/{id}` - Xóa sản phẩm (STAFF, MANAGER)

**API Quản lý (Cần xác thực với vai trò MANAGER):**
- `GET /api/v1/admin/users` - Xem danh sách người dùng
- `PUT /api/v1/admin/users/{id}/roles` - Cấp quyền cho người dùng
- `GET /api/v1/admin/statistics/revenue` - Xem thống kê doanh thu

#### 3.3.3 Giao diện cơ sở dữ liệu
- Cơ sở dữ liệu MySQL 8.0+
- Kết nối JDBC qua Spring Data JPA

### 3.4 Thuộc tính hệ thống

#### 3.4.1 Khả dụng
- Hệ thống phải có thời gian hoạt động 99.9%
- Thời gian ngừng hoạt động tối đa: 8.76 giờ/năm

#### 3.4.2 Bảo mật
- Xác thực: JWT
- Phân quyền: RBAC
- Mã hóa mật khẩu: bcrypt
- Mã hóa dữ liệu: AES-256 cho dữ liệu nhạy cảm

#### 3.4.3 Khả năng bảo trì
- Code phải tuân thủ các nguyên tắc Clean Code
- Phải có JavaDoc cho tất cả các phương thức công khai
- Cấu trúc package theo kiến trúc phân lớp

---

## 4. Thiết kế cơ sở dữ liệu

### 4.1 Sơ đồ thực thể - quan hệ (ERD)

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                           SƠ ĐỒ CƠ SỞ DỮ LIỆU                            ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌───────────────────────┐         ┌───────────────────────┐
│        USERS           │         │        ROLES           │
├───────────────────────┤         ├───────────────────────┤
│ id (PK)                │         │ id (PK)                │
│ username (UK)          │         │ role_name (UK)         │
│ password               │         │ description            │
│ email (UK)             │         │                        │
│ full_name              │         │                        │
│ phone                  │         │                        │
│ created_at             │         │                        │
│ updated_at             │         │                        │
└───────────┬───────────┘         └───────────┬───────────┘
            │                                 │
            │ 1                               │ 1
            │                                 │
            │ N                               │ N
            │                                 │
            └──────────────┬──────────────────┘
                           │
                    ┌──────▼──────┐
                    │ USER_ROLES │
                    ├─────────────┤
                    │ user_id(FK) │
                    │ role_id(FK) │
                    │ assigned_at│
                    └─────────────┘


┌───────────────────────┐
│      PRODUCTS         │
├───────────────────────┤
│ id (PK)               │
│ name                  │
│ description           │
│ price                 │
│ stock_quantity        │
│ category              │
│ image_url             │
│ created_at            │
│ updated_at            │
└───────────┬───────────┘
            │ 1
            │
            │ N
            │
┌───────────▼───────────┐
│     ORDER_ITEMS       │
├───────────────────────┤
│ id (PK)               │
│ order_id (FK)         │
│ product_id (FK)        │
│ quantity              │
│ unit_price            │
└───────────┬───────────┘
            │ N
            │
            │ 1
            │
┌───────────▼───────────┐         ┌───────────────────────┐
│       ORDERS          │         │        USERS            │
├───────────────────────┤         ├───────────────────────┤
│ id (PK)               │◄────────│ id (PK)                │
│ user_id (FK)          │         │ username (UK)          │
│ total_amount          │         │ password               │
│ status                │         │ email (UK)             │
│ order_date            │         │ full_name              │
│ created_at            │         │ phone                  │
│ updated_at            │         │ created_at             │
└───────────────────────┘         │ updated_at             │
                                    └───────────────────────┘


QUAN HỆ GIỮA CÁC BẢNG:
══════════════════════════════════════════════════════════════════════════════

USERS  ────<  USER_ROLES  >──── ROLES     (Quan hệ Nhiều-nhiều)
  1              N,M                  1

USERS  ────<  ORDERS                 (Quan hệ Một-nhiều)
  1               N

ORDERS ────<  ORDER_ITEMS            (Quan hệ Một-nhiều)
  1               N

PRODUCTS ──<  ORDER_ITEMS            (Quan hệ Một-nhiều)
  1               N
```

**Chú thích:**
- PK = Primary Key (Khóa chính)
- FK = Foreign Key (Khóa ngoại)
- UK = Unique Key (Khóa duy nhất)
- 1:N = Quan hệ Một-nhiều (Một bảng có nhiều bản ghi ở bảng khác)
- N:M = Quan hệ Nhiều-nhiều (Nhiều bản ghi ở bảng này liên kết với nhiều bản ghi ở bảng kia)
- <  = Mũi tên chỉ từ bảng "Một" sang bảng "Nhiều"

### 4.2 Quan hệ giữa các bảng

| Bảng | Quan hệ | Loại | Mô tả |
|------|---------|------|-------|
| **USERS** | USER_ROLES | Một-nhiều | Một user có nhiều role |
| **ROLES** | USER_ROLES | Một-nhiều | Một role được gán cho nhiều user |
| **USERS** | ORDERS | Một-nhiều | Một user có nhiều order |
| **ORDERS** | ORDER_ITEMS | Một-nhiều | Một order có nhiều items |
| **PRODUCTS** | ORDER_ITEMS | Một-nhiều | Một product xuất hiện trong nhiều order items |

### 4.3 Chiến lược ngăn chặn vấn đề N+1 Query

**Vấn đề:** Khi lấy User với Roles, nếu không cấu hình đúng, Hibernate sẽ thực hiện:
- 1 query để lấy User
- N query để lấy Roles của N users

**Giải pháp:**
1. Sử dụng `FetchType.LAZY` cho tất cả các quan hệ
2. Sử dụng `@EntityGraph` trong Repository khi cần eager fetch
3. Ví dụ cho User-Roles:
   ```java
   @EntityGraph(attributePaths = {"roles"})
   Optional<User> findByUsername(String username);
   ```

---

## 5. Use Case (Trường hợp sử dụng)

### 5.1 Sơ đồ Use Case

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                             SƠ ĐỒ USE CASE                                   ║
╚══════════════════════════════════════════════════════════════════════════════╝

╔══════════════════════════════════════════════════════════════════════════════╗
║                          CUSTOMER PORTAL                                       ║
║                         (Cổng Khách Hàng)                                     ║
╚══════════════════════════════════════════════════════════════════════════════╝

        ┌───────────────┐
        │  Khách hàng   │ ◄────────────────────────────────────────────┐
        └───────┬───────┘                                              │
                │                                                     │
                │                                                     │
    ┌───────────┼───────────┬───────────┬───────────┬───────────┐     │
    │           │           │           │           │           │     │
    ▼           ▼           ▼           ▼           ▼           │     │
┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐   │     │
│Đăng ký │ │Đăng    │ │Xem danh│ │Xem chi │ │Thêm vào│   │     │
│tài khoản│ │nhập    │ │sách    │ │tiết    │ │giỏ     │   │     │
└─────────┘ └─────────┘ │sản phẩm│ │sản phẩm│ │hàng    │   │     │
                       └─────────┘ └─────────┘ └────┬────┘   │     │
                                                     │       │     │
                                                     │       │     │
                                          ┌──────────▼────┐   │     │
                                          │Xem giỏ hàng   │   │     │
                                          └───────┬───────┘   │     │
                                                  │           │     │
                                                  │           │     │
                                          ┌───────▼───────┐   │     │
                                          │Xóa khỏi giỏ  │   │     │
                                          └───────┬───────┘   │     │
                                                  │           │     │
                                                  │           │     │
                                          ┌───────▼───────┐   │     │
                                          │Thanh toán đơn │   │     │
                                          │hàng           │   │     │
                                          └───────────────┘   │     │
                                                              │     │
                                                              │     │
╔══════════════════════════════════════════════════════════════════════════════╗
║                          ADMIN PORTAL                                          ║
║                         (Cổng Quản Trị)                                         ║
╚══════════════════════════════════════════════════════════════════════════════╝

        ┌───────────────┐
        │ Nhân viên kho  │
        └───────┬───────┘
                │
    ┌───────────┼───────────┐
    │           │           │
    ▼           ▼           ▼
┌─────────┐ ┌─────────┐ ┌─────────┐
│Thêm     │ │Sửa     │ │Xóa     │
│sản phẩm│ │sản phẩm│ │sản phẩm│
└─────────┘ └─────────┘ └─────────┘


        ┌───────────────┐
        │    Quản lý     │ ◄────────────────────────────────────────────┐
        └───────┬───────┘                                              │
                │                                                     │
    ┌───────────┼───────────┬───────────┐                           │
    │           │           │           │                           │
    ▼           ▼           ▼           │                           │
┌─────────┐ ┌─────────┐ ┌─────────┐   │                           │
│Xem     │ │Xem     │ │Xem     │   │                           │
│thống kê│ │báo cáo │ │danh    │   │                           │
│doanh thu│ │bán hàng│ │sách    │   │                           │
└─────────┘ └─────────┘ │nhân    │   │                           │
                       │viên    │   │                           │
                       └────┬────┘   │                           │
                            │        │                           │
                            ▼        │                           │
                     ┌──────────┐   │                           │
                     │Cấp quyền│   │                           │
                     │cho nhân │   │                           │
                     │viên     │   │                           │
                     └────┬─────┘   │                           │
                          │        │                           │
                          ▼        │                           │
                     ┌──────────┐   │                           │
                     │Thu hồi  │   │                           │
                     │quyền    │   │                           │
                     │nhân viên│   │                           │
                     └──────────┘   │                           │
                                    │                           │
                                    └───────────────────────────┘


╔══════════════════════════════════════════════════════════════════════════════╗
║                         QUAN HỆ ĐẶC BIỆT                                      ║
╚══════════════════════════════════════════════════════════════════════════════╝

Quản lý có thể thực hiện tất cả các tác vụ của Nhân viên kho:
  └──► Thêm sản phẩm
  └──► Sửa sản phẩm  
  └──► Xóa sản phẩm

Thanh toán đơn hàng DEPENDS ON:
  └──► Đăng nhập (bắt buộc)
  └──► Xem giỏ hàng (bắt buộc)
```

**Chú thích:**
- Các ô vuông: Use case (Trường hợp sử dụng)
- Các ô tròn: Actor (Người dùng hệ thống)
- Mũi tên: Quan hệ giữa Actor và Use case
- Các khung lớn: Phạm vi hệ thống (Customer Portal, Admin Portal)
- Quản lý có quyền mở rộng hơn Nhân viên kho (có thể làm tất cả tác vụ của nhân viên)

### 5.2 Mô tả Use Case

#### UC-1: Đăng ký tài khoản
| Trường | Mô tả |
|-------|-------------|
| **Use Case ID** | UC-1 |
| **Actor** | Khách hàng |
| **Mô tả** | Khách hàng đăng ký tài khoản mới để sử dụng hệ thống |
| **Điều kiện tiên quyết** | Chưa có tài khoản trong hệ thống |
| **Luồng chính** | 1. Khách hàng nhập username, password, email, full_name, phone<br>2. Hệ thống kiểm tra dữ liệu đầu vào<br>3. Hệ thống kiểm tra username/email duy nhất<br>4. Hệ thống mã hóa password<br>5. Hệ thống lưu user vào database với role CUSTOMER<br>6. Hệ thống trả về thông báo thành công |
| **Luồng thay thế** | 3a. Username hoặc email đã tồn tại: Hệ thống trả về lỗi 400 |
| **Điều kiện sau** | Tài khoản được tạo thành công |

#### UC-2: Đăng nhập
| Trường | Mô tả |
|-------|-------------|
| **Use Case ID** | UC-2 |
| **Actor** | Khách hàng, Nhân viên kho, Quản lý |
| **Mô tả** | Người dùng đăng nhập để nhận JWT token |
| **Điều kiện tiên quyết** | Đã có tài khoản trong hệ thống |
| **Luồng chính** | 1. Người dùng nhập username và password<br>2. Hệ thống kiểm tra dữ liệu đầu vào<br>3. Hệ thống tìm user theo username<br>4. Hệ thống xác minh password<br>5. Hệ thống tạo JWT token<br>6. Hệ thống trả về token |
| **Luồng thay thế** | 3a. User không tồn tại: Hệ thống trả về lỗi 401<br>4a. Password sai: Hệ thống trả về lỗi 401 |
| **Điều kiện sau** | User nhận được JWT token |

#### UC-8: Thanh toán đơn hàng
| Trường | Mô tả |
|-------|-------------|
| **Use Case ID** | UC-8 |
| **Actor** | Khách hàng |
| **Mô tả** | Khách hàng thanh toán giỏ hàng để tạo đơn hàng |
| **Điều kiện tiên quyết** | Đã đăng nhập, giỏ hàng có sản phẩm |
| **Luồng chính** | 1. Khách hàng chọn thanh toán<br>2. Hệ thống kiểm tra stock_quantity của từng sản phẩm<br>3. Hệ thống tính total_amount<br>4. Hệ thống tạo Order với status PENDING<br>5. Hệ thống tạo Order_Items cho từng sản phẩm<br>6. Hệ thống trừ stock_quantity<br>7. Hệ thống xóa giỏ hàng<br>8. Hệ thống trả về Order ID |
| **Luồng thay thế** | 2a. Sản phẩm hết hàng: Hệ thống trả về lỗi 400 |
| **Điều kiện sau** | Đơn hàng được tạo, stock được cập nhật |

#### UC-9: Thêm sản phẩm
| Trường | Mô tả |
|-------|-------------|
| **Use Case ID** | UC-9 |
| **Actor** | Nhân viên kho, Quản lý |
| **Mô tả** | Thêm sản phẩm mới vào hệ thống |
| **Điều kiện tiên quyết** | Đã đăng nhập với role STAFF hoặc MANAGER |
| **Luồng chính** | 1. Nhân viên nhập thông tin sản phẩm<br>2. Hệ thống kiểm tra dữ liệu (price > 0, stock >= 0)<br>3. Hệ thống lưu sản phẩm vào database<br>4. Hệ thống trả về thông báo thành công |
| **Luồng thay thế** | 2a. Dữ liệu không hợp lệ: Hệ thống trả về lỗi 400 |
| **Điều kiện sau** | Sản phẩm được thêm thành công |

#### UC-15: Cấp quyền cho nhân viên
| Trường | Mô tả |
|-------|-------------|
| **Use Case ID** | UC-15 |
| **Actor** | Quản lý |
| **Mô tả** | Quản lý cấp role cho nhân viên |
| **Điều kiện tiên quyết** | Đã đăng nhập với role MANAGER |
| **Luồng chính** | 1. Quản lý chọn user và role<br>2. Hệ thống kiểm tra quyền của quản lý<br>3. Hệ thống thêm role cho user trong USER_ROLES<br>4. Hệ thống trả về thông báo thành công |
| **Luồng thay thế** | 2a. Quản lý không có quyền: Hệ thống trả về lỗi 403 |
| **Điều kiện sau** | User được cấp role mới |

---

## 6. Phụ lục

### 6.1 Ma trận truy vết

| ID Yêu cầu | Use Case | Điểm cuối API | Ưu tiên |
|----------------|----------|--------------|----------|
| FR-1.1 | UC-1 | POST /api/v1/public/auth/register | Cao |
| FR-1.2 | UC-2 | POST /api/v1/public/auth/login | Cao |
| FR-2.1 | UC-9 | POST /api/v1/admin/products | Cao |
| FR-2.2 | UC-10 | PUT /api/v1/admin/products/{id} | Cao |
| FR-3.5 | UC-8 | POST /api/v1/customer/orders | Cao |
| FR-4.2 | UC-15 | PUT /api/v1/admin/users/{id}/roles | Cao |
| FR-5.1 | - | GET /api/v1/admin/statistics/revenue | Cao |

### 6.2 Tài liệu tham khảo
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- JWT Specification: https://tools.ietf.org/html/rfc7519

---

**Lịch sử tài liệu**

| Phiên bản | Ngày | Tác giả | Thay đổi |
|---------|------|--------|---------|
| 1.0 | 2026-07-02 | Nhóm phân tích có hỗ trợ AI | Phiên bản đầu tiên |
