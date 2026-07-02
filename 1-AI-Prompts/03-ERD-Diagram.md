# AI Prompt Log: ERD Diagram Generation
**Date:** 2026-07-02
**Phase:** Phase 1 - Business Analysis

---

## Prompt 3: Generate ERD Diagram

**Prompt:**
```
Act as a Senior Database Architect. Design an Entity-Relationship Diagram (ERD) for the TechNova Smart E-Shop system using Mermaid syntax.

Core entities required:
1. Users - Store user information (customers, staff, managers)
2. Roles - Define user roles (CUSTOMER, STAFF, MANAGER)
3. Products - Store product information (phones, laptops)
4. Orders - Store order information
5. Order_Items - Store items in each order

Relationship requirements:
- User and Role: Many-to-Many (a user can have multiple roles)
- User and Order: One-to-Many (a user can have multiple orders)
- Order and Order_Item: One-to-Many (an order can have multiple items)
- Product and Order_Item: One-to-Many (a product can be in multiple order items)

For each entity, include these attributes:
- Users: id, username, password, email, full_name, phone, created_at, updated_at
- Roles: id, role_name, description
- Products: id, name, description, price, stock_quantity, category, image_url, created_at, updated_at
- Orders: id, user_id, total_amount, status, order_date, created_at, updated_at
- Order_Items: id, order_id, product_id, quantity, unit_price

Requirements:
- Use Mermaid ERD syntax (erDiagram)
- Clearly mark primary keys (PK) and foreign keys (FK)
- Show cardinality (1:N, N:N) correctly
- Include data types for attributes
```

---

## AI Response: ERD Diagram (Mermaid Code)

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
        VARCHAR(20)-status "PENDING, PAID, SHIPPED, CANCELLED"
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

---

## Notes from AI Interaction
- AI correctly identified the need for a junction table (USER_ROLES) for the Many-to-Many relationship between Users and Roles
- AI included appropriate data types (BIGINT, VARCHAR, DECIMAL, TIMESTAMP, INT, TEXT)
- AI marked Primary Keys (PK), Foreign Keys (FK), and Unique Keys (UK)
- AI added business constraints as comments (e.g., "Must be > 0", "Must be >= 0")
- AI defined enum values for status and role_name fields
- AI used proper cardinality notation (||--o{ for one-to-many, ||--|| for one-to-one)
- The ERD is normalized to 3NF (Third Normal Form)
