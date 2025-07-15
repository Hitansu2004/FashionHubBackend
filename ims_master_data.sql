
IF DB_ID('ims_db') IS NULL
BEGIN
    CREATE DATABASE ims_db;
END
GO

USE ims_db;
GO

-- Drop order_inventory if exists (child table first)
IF OBJECT_ID('dbo.order_inventory', 'U') IS NOT NULL
    DROP TABLE dbo.order_inventory;

-- Drop inventory if exists
IF OBJECT_ID('dbo.inventory', 'U') IS NOT NULL
    DROP TABLE dbo.inventory;

-- Create inventory table
CREATE TABLE inventory (
    id INT IDENTITY(1,1) PRIMARY KEY,
    sku VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    location VARCHAR(100) NOT NULL,
    available_qty INT NOT NULL CHECK (available_qty >= 0),
    CONSTRAINT UQ_sku_category UNIQUE (sku, category_id)
);

-- Insert data into inventory
INSERT INTO inventory (sku, category_id, location, available_qty) VALUES
('SKU-001', 1, 'Warehouse A', 100),
('SKU-002', 1, 'Warehouse B', 200),
('SKU-003', 2, 'Warehouse A', 150),
('SKU-004', 2, 'Warehouse C', 50),
('SKU-005', 3, 'Warehouse B', 75);

-- Create order_inventory table
CREATE TABLE order_inventory (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    sku VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    reserved_qty INT DEFAULT 0 CHECK (reserved_qty >= 0),
    allocated_qty INT DEFAULT 0 CHECK (allocated_qty >= 0),
    is_cancelled BIT DEFAULT 0,
    CONSTRAINT FK_order_inventory 
        FOREIGN KEY (sku, category_id) 
        REFERENCES inventory(sku, category_id)
);

-- Insert data into order_inventory
INSERT INTO order_inventory (order_id, sku, category_id, reserved_qty, allocated_qty, is_cancelled) VALUES
(1001, 'SKU-001', 1, 10, 5, 0),
(1002, 'SKU-002', 1, 20, 15, 0),
(1003, 'SKU-003', 2, 5, 5, 0),
(1004, 'SKU-004', 2, 0, 10, 0),
(1005, 'SKU-005', 3, 7, 7, 0);
