# AI Prompt Log: Requirements Elicitation
**Date:** 2026-07-02
**Phase:** Phase 1 - Business Analysis

---

## Prompt 1: Role-Playing and Requirements Extraction

**Prompt:**
```
Act as a Senior Business Analyst with 10+ years of experience in e-commerce system analysis. 
I will provide you with raw requirements from a customer meeting. Your task is to analyze and extract:

1. Actors (all stakeholders who interact with the system)
2. Functional Requirements (FR) - what the system must do
3. Non-Functional Requirements (NFR) - how the system must perform

Raw Requirements:
"Tôi muốn một cái hệ thống web để bán hàng. Khách hàng có thể lên đó xem điện thoại, laptop, bỏ vào giỏ hàng rồi thanh toán. Cơ mà trước khi mua họ phải đăng ký tài khoản và đăng nhập nhé. Hệ thống phải bảo mật, không được để lộ thông tin khách.
Về phần nhân viên của tôi, họ cần một trang quản trị (Admin). Trong đó, nhân viên kho thì chỉ được thêm, sửa, xóa sản phẩm. Còn quản lý (Manager) thì được xem thống kê doanh thu và cấp quyền cho nhân viên khác. Khách hàng tuyệt đối không được mò vào trang của Admin. Hệ thống sau này dự kiến có hàng chục ngàn người truy cập cùng lúc nên API phải chạy nhanh, cơ sở dữ liệu không được sập."

Context:
- Customer: TechNova (retail electronics company)
- Current state: Manual management using books and Excel
- Goal: Build "Smart E-Shop (Shop AI)" to digitize entire sales process

Please provide a structured analysis in Vietnamese.
```

---

## AI Response: Requirements Analysis

### 1. ACTORS (TÁC NHÂN)

| Actor | Description | Role |
|-------|-------------|------|
| **Khách hàng (Customer)** | Người mua hàng trên hệ thống | End User |
| **Nhân viên kho (Warehouse Staff)** | Quản lý sản phẩm, tồn kho | Staff |
| **Quản lý (Manager)** | Quản lý nhân viên, xem thống kê | Admin |
| **Hệ thống (System)** | Xử lý tự động các tác vụ nền | Automated |

### 2. FUNCTIONAL REQUIREMENTS (YÊU CẦU CHỨC NĂNG)

#### FR-1: Quản lý Tài khoản & Xác thực
- **FR-1.1:** Hệ thống phải cho phép khách hàng đăng ký tài khoản mới
- **FR-1.2:** Hệ thống phải cho phép người dùng đăng nhập với username/password
- **FR-1.3:** Hệ thống phải xác thực người dùng trước khi cho phép mua hàng
- **FR-1.4:** Hệ thống phải bảo mật thông tin cá nhân của khách hàng

#### FR-2: Quản lý Sản phẩm (Dành cho Nhân viên kho)
- **FR-2.1:** Nhân viên kho có thể thêm sản phẩm mới (điện thoại, laptop)
- **FR-2.2:** Nhân viên kho có thể sửa thông tin sản phẩm
- **FR-2.3:** Nhân viên kho có thể xóa sản phẩm
- **FR-2.4:** Khách hàng có thể xem danh sách sản phẩm
- **FR-2.5:** Khách hàng có thể xem chi tiết sản phẩm

#### FR-3: Giỏ hàng & Đặt hàng
- **FR-3.1:** Khách hàng có thể thêm sản phẩm vào giỏ hàng
- **FR-3.2:** Khách hàng có thể xem giỏ hàng
- **FR-3.3:** Khách hàng có thể xóa sản phẩm khỏi giỏ hàng
- **FR-3.4:** Khách hàng có thể thanh toán đơn hàng

#### FR-4: Quản lý Người dùng (Dành cho Quản lý)
- **FR-4.1:** Quản lý có thể xem danh sách nhân viên
- **FR-4.2:** Quản lý có thể cấp quyền cho nhân viên (Staff/Manager)
- **FR-4.3:** Quản lý có thể thu hồi quyền của nhân viên

#### FR-5: Thống kê & Báo cáo (Dành cho Quản lý)
- **FR-5.1:** Quản lý có thể xem thống kê doanh thu
- **FR-5.2:** Quản lý có thể xem báo cáo bán hàng

#### FR-6: Phân quyền & Bảo mật
- **FR-6.1:** Khách hàng không được truy cập trang Admin
- **FR-6.2:** Nhân viên kho không được truy cập chức năng thống kê
- **FR-6.3:** Nhân viên kho không được cấp quyền cho nhân viên khác
- **FR-6.4:** API phải phân quyền theo vai trò (RBAC)

### 3. NON-FUNCTIONAL REQUIREMENTS (YÊU CẦU PHI CHỨC NĂNG)

#### NFR-1: Hiệu năng (Performance)
- **NFR-1.1:** Hệ thống phải hỗ trợ hàng chục ngàn người truy cập cùng lúc (concurrent users)
- **NFR-1.2:** API phải có response time < 200ms cho các request đơn giản
- **NFR-1.3:** Hệ thống phải xử lý N+1 query để tối ưu hiệu năng database

#### NFR-2: Bảo mật (Security)
- **NFR-2.1:** Hệ thống phải sử dụng JWT Token cho xác thực
- **NFR-2.2:** Mật khẩu phải được mã hóa (bcrypt/argon2)
- **NFR-2.3:** API phải trả về lỗi 401/403 dưới dạng JSON
- **NFR-2.4:** Không được lộ thông tin khách hàng (data encryption at rest)

#### NFR-3: Khả năng mở rộng (Scalability)
- **NFR-3.1:** Cơ sở dữ liệu không được sập (high availability)
- **NFR-3.2:** Hệ thống phải hỗ trợ horizontal scaling
- **NFR-3.3:** Database phải hỗ trợ connection pooling

#### NFR-4: Độ tin cậy (Reliability)
- **NFR-4.1:** Uptime phải đạt 99.9%
- **NFR-4.2:** Hệ thống phải có cơ chế rollback khi lỗi

#### NFR-5: Usability
- **NFR-5.1:** API phải tuân thủ RESTful standards
- **NFR-5.2:** API documentation phải rõ ràng (Swagger/OpenAPI)

---

## Notes from AI Interaction
- AI successfully extracted all actors from raw requirements
- AI identified implicit requirements (e.g., password encryption, connection pooling)
- AI structured requirements with unique IDs for traceability
- AI provided both Vietnamese and English terms for clarity
