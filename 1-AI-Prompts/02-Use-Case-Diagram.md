# AI Prompt Log: Use Case Diagram Generation
**Date:** 2026-07-02
**Phase:** Phase 1 - Business Analysis

---

## Prompt 2: Generate Use Case Diagram

**Prompt:**
```
Act as a Senior System Architect. Based on the requirements analysis from the previous step, generate a Mermaid code for a Use Case diagram for the TechNova Smart E-Shop system.

The system has the following actors:
1. Khách hàng (Customer)
2. Nhân viên kho (Warehouse Staff)
3. Quản lý (Manager)

Key use cases to include:
- For Customer: Đăng ký, Đăng nhập, Xem sản phẩm, Thêm vào giỏ hàng, Thanh toán
- For Warehouse Staff: Thêm sản phẩm, Sửa sản phẩm, Xóa sản phẩm
- For Manager: Xem thống kê doanh thu, Cấp quyền nhân viên, Quản lý người dùng

Requirements:
- Use Mermaid syntax
- Include <<include>> and <<extend>> relationships where appropriate
- Group use cases by system boundaries (Customer Portal, Admin Portal)
- Make the diagram clear and professional
```

---

## AI Response: Use Case Diagram (Mermaid Code)

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

---

## Notes from AI Interaction
- AI correctly identified system boundaries (Customer Portal vs Admin Portal)
- AI included appropriate <<include>> and <<extend>> relationships
- AI noted that Manager inherits permissions from Warehouse Staff
- AI added styling for better visualization
- Diagram clearly shows separation of concerns between customer and admin functions
