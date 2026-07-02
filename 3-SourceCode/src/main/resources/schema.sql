-- Tạo cơ sở dữ liệu technova_eshop
CREATE DATABASE IF NOT EXISTS technova_eshop;

-- Sử dụng cơ sở dữ liệu
USE technova_eshop;

-- Tạo bảng ROLES (Vai trò)
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng USERS (Người dùng)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng USER_ROLES (Gán vai trò cho người dùng)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tạo bảng PRODUCTS (Sản phẩm)
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(15,2) NOT NULL CHECK (price > 0),
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    category VARCHAR(50),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng ORDERS (Đơn hàng)
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng ORDER_ITEMS (Chi tiết đơn hàng)
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Chèn dữ liệu mẫu cho bảng ROLES
INSERT INTO roles (role_name, description) VALUES
('CUSTOMER', 'Khách hàng'),
('STAFF', 'Nhân viên kho'),
('MANAGER', 'Quản lý');

-- Chèn dữ liệu mẫu cho bảng USERS (mật khẩu đã hash với bcrypt)
INSERT INTO users (username, password, email, full_name, phone) VALUES
('customer1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'customer1@example.com', 'Nguyễn Văn A', '0901234567'),
('staff1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'staff1@example.com', 'Trần Văn B', '0902345678'),
('manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'manager1@example.com', 'Lê Văn C', '0903456789');

-- Gán vai trò cho người dùng
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- customer1 -> CUSTOMER
(2, 2), -- staff1 -> STAFF
(3, 3); -- manager1 -> MANAGER

-- Chèn dữ liệu mẫu cho bảng PRODUCTS
INSERT INTO products (name, description, price, stock_quantity, category, image_url) VALUES
('iPhone 15 Pro Max', 'iPhone 15 Pro Max với chip A17 Pro', 34990000, 50, 'PHONE', 'https://example.com/iphone15.jpg'),
('Samsung Galaxy S24 Ultra', 'Samsung Galaxy S24 Ultra với camera 200MP', 32990000, 30, 'PHONE', 'https://example.com/s24.jpg'),
('MacBook Pro 16 inch', 'MacBook Pro 16 inch M3 Max', 69990000, 20, 'LAPTOP', 'https://example.com/macbook.jpg'),
('Dell XPS 15', 'Dell XPS 15 với Intel Core i9', 45990000, 25, 'LAPTOP', 'https://example.com/dell.jpg'),
('iPad Pro 12.9 inch', 'iPad Pro 12.9 inch M2', 28990000, 40, 'TABLET', 'https://example.com/ipad.jpg');
