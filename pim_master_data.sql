For pim_db

Create database pim_db;

USE pim_db;

-- Drop tables in reverse dependency order

DROP TABLE IF EXISTS product_category;

DROP TABLE IF EXISTS product_attributes;

DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS category;

-- 1. Create category table

CREATE TABLE categories (
  category_id INT IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(100),
  category_image VARCHAR(1000),
  description VARCHAR(255)

);

CREATE TABLE seller (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    seller_name VARCHAR(100),
    contact_name VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100)
);



INSERT INTO seller (name, contact_name, email, phone, address_lane1, address_lane2, city, state, zipcode, country)
VALUES
('FashionHub', 'Ravi Kumar', 'ravi@fashionhub.com', '9876543210', 'Street 21', 'Building A', 'Mumbai', 'Maharashtra', '400001', 'India'),
('StyleNation', 'Pooja Mehra', 'pooja@stylenation.com', '9123456780', 'Sector 11', 'Tower B', 'Delhi', 'Delhi', '110001', 'India'),
('UrbanTrendz', 'Siddharth Roy', 'sid@urbantrendz.com', '9988776655', 'MG Road', 'Shop No. 5', 'Bangalore', 'Karnataka', '560001', 'India');

 

INSERT INTO categories (name, category_image, description)
VALUES
('Men', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372783/men_l7kf5f.png', 'Latest styles for men'),
('Women', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372782/women_ilhqk4.jpg', 'Trendy outfits for women'),
('Kids', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372781/kids_vsvx7y.png', 'Fun and comfy clothes for kids');
 

-- 2. Create product table

CREATE TABLE products (
  product_id INT IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(100),
  category_id INT,
  seller_id INT,
  status VARCHAR(20),
  last_modified_date DATETIME DEFAULT GETDATE(),
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (seller_id) REFERENCES seller(id)

);


-- 3. Create product_attributes table

CREATE TABLE product_attributes (
  sku VARCHAR(50) PRIMARY KEY,
  product_id INT,
  size VARCHAR(50),
  product_image VARCHAR(1000),
  price DECIMAL(10,2),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);
 

-- ? INSERTS must come after all tables are created

-- Insert into category

-- Insert into product

INSERT INTO products (name, category_id, seller_id, status)

VALUES

-- Men's Products (seller_id = 1)

('Men Cotton Shirt', 1, 1, 'Active'),

('Men Casual Shirt', 1, 1, 'Active'),

('Men Winter Jacket', 1, 1, 'Active'),

('Men Leather Jacket', 1, 1, 'Active'),

-- Women's Products (seller_id = 2)

('Women Kurti Combo', 2, 2, 'Active'),

('Women Blazer', 2, 2, 'Active'),

( 'Women Winter Wear', 2, 2, 'Active'),

( 'Women Crop Jacket', 2, 2, 'Active'),

-- Kids' Products (seller_id = 3)

( 'Kids Casual T-shirt', 3, 3, 'Active'),

('Kids Party Frock', 3, 3, 'Active'),

( 'Kids Hoodie', 3, 3, 'Active'),

( 'Kids Jeans Set', 3, 3, 'Active');
 


INSERT INTO product_attributes (sku, product_id, size, product_image, price)

VALUES

-- Men's

('SKU-001', 1, 'M', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372936/1_badv5r.jpg', 999.00),

('SKU-002', 2, 'S', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372937/2_v7ptnq.jpg', 1599.00),

('SKU-003', 3, 'L', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372938/3_f5hlge.webp', 1299.00),

('SKU-004', 4, 'XL', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372939/4_lzhllm.webp', 2499.00),

-- Women's

('SKU-005', 5, 'M', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372940/5_b72fpn.webp', 1999.00),

('SKU-006', 6, 'L', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372941/6_blpmoz.webp', 2999.00),

('SKU-007', 7, 'XL', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372942/7_nhuzqm.webp', 2699.00),

('SKU-008', 8, 'S', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372944/8_doudap.webp', 1599.00),

-- Kids

('SKU-009', 9, 'S', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372945/9_lsbe7p.jpg', 799.00),

('SKU-010', 10, 'M', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372947/10_yfxkqp.jpg', 1199.00),

('SKU-011', 11, 'L', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372948/11_txttse.jpg', 1399.00),

('SKU-012', 12, 'XL', 'https://res.cloudinary.com/dtkfwzsis/image/upload/v1751372950/12_wp9z53.jpg', 1599.00); 