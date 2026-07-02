# Sơ Đồ Thực Thể - Quan Hệ (ERD)
## Hệ thống Smart E-Shop

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                           SƠ ĐỒ CƠ SỞ DỮ LIỆU                            ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌───────────────────────┐         ┌───────────────────────┐
│        USERS           │         │        ROLES           │
├───────────────────────┤         ├───────────────────────┤
│ id (PK) BIGINT        │         │ id (PK) BIGINT        │
│ username (UK) VARCHAR(50)       │ role_name (UK) VARCHAR(20) │
│ password VARCHAR(255)  │         │ description VARCHAR(255)│
│ email (UK) VARCHAR(100)│         │                        │
│ full_name VARCHAR(100) │         │                        │
│ phone VARCHAR(20)      │         │                        │
│ created_at TIMESTAMP  │         │                        │
│ updated_at TIMESTAMP  │         │                        │
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
                    │ assigned_at │
                    └─────────────┘


┌───────────────────────┐
│      PRODUCTS         │
├───────────────────────┤
│ id (PK) BIGINT        │
│ name VARCHAR(100)     │
│ description TEXT      │
│ price DECIMAL(15,2)  │
│ stock_quantity INT    │
│ category VARCHAR(50)  │
│ image_url VARCHAR(500)│
│ created_at TIMESTAMP  │
│ updated_at TIMESTAMP  │
└───────────┬───────────┘
            │ 1
            │
            │ N
            │
┌───────────▼───────────┐
│     ORDER_ITEMS       │
├───────────────────────┤
│ id (PK) BIGINT       │
│ order_id (FK) BIGINT │
│ product_id (FK) BIGINT│
│ quantity INT         │
│ unit_price DECIMAL(15,2)│
└───────────┬───────────┘
            │ N
            │
            │ 1
            │
┌───────────▼───────────┐         ┌───────────────────────┐
│       ORDERS          │         │        USERS            │
├───────────────────────┤         ├───────────────────────┤
│ id (PK) BIGINT        │◄────────│ id (PK) BIGINT        │
│ user_id (FK) BIGINT   │         │ username (UK) VARCHAR(50)│
│ total_amount DECIMAL(15,2)      │ password VARCHAR(255)  │
│ status VARCHAR(20)    │         │ email (UK) VARCHAR(100)│
│ order_date TIMESTAMP  │         │ full_name VARCHAR(100) │
│ created_at TIMESTAMP  │         │ phone VARCHAR(20)      │
│ updated_at TIMESTAMP  │         │ created_at TIMESTAMP  │
└───────────────────────┘         │ updated_at TIMESTAMP  │
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


CHI TIẾT CÁC BẢNG:
══════════════════════════════════════════════════════════════════════════════

Bảng USERS (Người dùng):
- id: Khóa chính (BIGINT)
- username: Tên đăng nhập, duy nhất (VARCHAR 50)
- password: Mật khẩu đã hash (VARCHAR 255)
- email: Email, duy nhất (VARCHAR 100)
- full_name: Họ tên đầy đủ (VARCHAR 100)
- phone: Số điện thoại (VARCHAR 20)
- created_at: Thời gian tạo (TIMESTAMP)
- updated_at: Thời gian cập nhật (TIMESTAMP)

Bảng ROLES (Vai trò):
- id: Khóa chính (BIGINT)
- role_name: Tên vai trò, duy nhất (VARCHAR 20)
  * CUSTOMER: Khách hàng
  * STAFF: Nhân viên kho
  * MANAGER: Quản lý
- description: Mô tả vai trò (VARCHAR 255)

Bảng USER_ROLES (Gán vai trò cho người dùng):
- user_id: Khóa ngoại tham chiếu USERS (BIGINT)
- role_id: Khóa ngoại tham chiếu ROLES (BIGINT)
- assigned_at: Thời gian gán (TIMESTAMP)

Bảng PRODUCTS (Sản phẩm):
- id: Khóa chính (BIGINT)
- name: Tên sản phẩm (VARCHAR 100)
- description: Mô tả sản phẩm (TEXT)
- price: Giá sản phẩm, phải > 0 (DECIMAL 15,2)
- stock_quantity: Số lượng tồn kho, phải >= 0 (INT)
- category: Danh mục sản phẩm (VARCHAR 50)
  * PHONE: Điện thoại
  * LAPTOP: Máy tính xách tay
- image_url: URL hình ảnh (VARCHAR 500)
- created_at: Thời gian tạo (TIMESTAMP)
- updated_at: Thời gian cập nhật (TIMESTAMP)

Bảng ORDERS (Đơn hàng):
- id: Khóa chính (BIGINT)
- user_id: Khóa ngoại tham chiếu USERS (BIGINT)
- total_amount: Tổng số tiền (DECIMAL 15,2)
- status: Trạng thái đơn hàng (VARCHAR 20)
  * PENDING: Chờ thanh toán
  * PAID: Đã thanh toán
  * SHIPPED: Đã giao hàng
  * CANCELLED: Đã hủy
- order_date: Ngày đặt hàng (TIMESTAMP)
- created_at: Thời gian tạo (TIMESTAMP)
- updated_at: Thời gian cập nhật (TIMESTAMP)

Bảng ORDER_ITEMS (Chi tiết đơn hàng):
- id: Khóa chính (BIGINT)
- order_id: Khóa ngoại tham chiếu ORDERS (BIGINT)
- product_id: Khóa ngoại tham chiếu PRODUCTS (BIGINT)
- quantity: Số lượng, phải > 0 (INT)
- unit_price: Đơn giá tại thời điểm đặt (DECIMAL 15,2)
```

**Chú thích:**
- PK = Primary Key (Khóa chính)
- FK = Foreign Key (Khóa ngoại)
- UK = Unique Key (Khóa duy nhất)
- 1:N = Quan hệ Một-nhiều (Một bảng có nhiều bản ghi ở bảng khác)
- N:M = Quan hệ Nhiều-nhiều (Nhiều bản ghi ở bảng này liên kết với nhiều bản ghi ở bảng kia)
- <  = Mũi tên chỉ từ bảng "Một" sang bảng "Nhiều"
- >  = Mũi tên chỉ từ bảng "Nhiều" sang bảng "Một" (trong quan hệ nhiều-nhiều)
