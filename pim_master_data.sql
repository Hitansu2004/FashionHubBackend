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
('Women', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752646333/ke16ccwsficyzenx6bje.jpg', 'Trendy outfits for women'),
('Kids', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752646375/zx6yyzi9hv5pmj4v5cby.jpg', 'Fun and comfy clothes for kids');
 

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

('SKU-101', 10, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653514/utwpvwjvpb9ne3oiyt8y.png', 799.00),

('SKU-102', 10, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653514/utwpvwjvpb9ne3oiyt8y.png', 999.00),

('SKU-11', 1, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650058/zspkxxcevkfhtmwbnsef.png', 1499.00),

('SKU-111', 11, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653576/d1po3frh5zy2tigo5ugr.png', 1299.00),



('SKU-112', 11, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653576/d1po3frh5zy2tigo5ugr.png', 1299.00),

('SKU-121', 12, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r9zvbxtqchliolhozrx3.png', 599.00),

('SKU-122', 12, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r9zvbxtqchliolhozrx3.png', 799.00),

('SKU-123', 12, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r9zvbxtqchliolhozrx3.png', 699.00),

-- Kids

('SKU-131', 13, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653729/tyfsvpx5jhl59egki1ry.png', 599.00),

('SKU-132', 13, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653729/tyfsvpx5jhl59egki1ry.png', 899.00),

('SKU-141', 14, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653793/bbpqazeef5rfruocb1ah.png', 899.00),

('SKU-142', 14, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653793/bbpqazeef5rfruocb1ah.png', 1099.00),

('SKU-151', 15, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653872/b00bh88o7gvx7owm18za.png', 999.00),

('SKU-161', 16, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653919/u4ju7u88ikitocedps9o.png', 499.00),

('SKU-162', 16, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653919/u4ju7u88ikitocedps9o.png', 599.00),

('SKU-171', 17, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653987/wewpgrkevtmxje8vz60n.png', 299.00),

('SKU-172', 17, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653987/wewpgrkevtmxje8vz60n.png', 399.00),

('SKU-181', 18, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654077/wcgtrzbxypa44gxs9oah.png', 789.00),

('SKU-182', 18, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654077/wcgtrzbxypa44gxs9oah.png', 899.00),

('SKU-191', 19, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/vy0mfipuvlorephbmqfd.png', 2999.00),

('SKU-192', 19, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/vy0mfipuvlorephbmqfd.png', 3499.00),

('SKU-193', 19, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/vy0mfipuvlorephbmqfd.png', 3899.00),

('SKU-201', 20, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654246/y4vov764znykryo3s391.png', 799.00),

('SKU-202', 20, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654246/y4vov764znykryo3s391.png', 899.00),

('SKU-21', 2, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650103/w6cij78mvbsio5jgf4fx.png', 2999.00),

('SKU-211', 21, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752719321/se9sorn3ygs6d61glmes.jpg', 1499.00),

('SKU-212', 21, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752719321/se9sorn3ygs6d61glmes.jpg', 1699.00),

('SKU-213', 21, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752719321/se9sorn3ygs6d61glmes.jpg', 1999.00),

('SKU-221', 22, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654481/cbfyzhaz5sfoydurieus.png', 2499.00),

('SKU-231', 23, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654752/onh5lxxtqxecy3bgg5tm.png', 1899.00),

('SKU-232', 23, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654752/onh5lxxtqxecy3bgg5tm.png', 1399.00),

('SKU-241', 24, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654817/oywpwwmx2rwkc01kkeul.png', 199.00),

('SKU-242', 24, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654817/oywpwwmx2rwkc01kkeul.png', 199.00),

('SKU-251', 25, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654890/nyekfd6uxbpgxcyqtl2c.png', 799.00),

('SKU-252', 25, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654890/nyekfd6uxbpgxcyqtl2c.png', 899.00),

('SKU-261', 26, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654979/j4n1wkcgxwjmxiaaxh9s.png', 999.00),

('SKU-262', 26, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654979/j4n1wkcgxwjmxiaaxh9s.png', 1099.00),

('SKU-271', 27, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655039/gvlk3cjtq2dkr73kz1yo.png', 1599.00),

('SKU-281', 28, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655091/yrykqnbla6eb0zwxuse7.png', 3999.00),

('SKU-282', 28, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655091/yrykqnbla6eb0zwxuse7.png', 3999.00),

('SKU-291', 29, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752719384/fuhasudmt7f9sy3grxcc.jpg', 1099.00),

('SKU-292', 29, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752719384/fuhasudmt7f9sy3grxcc.jpg', 1099.00),

('SKU-301', 30, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655277/ay0desq9jeyq5ahn3n22.png', 1999.00),

('SKU-31', 3, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650169/ic7xmjh74ji9xl46qwat.png', 1499.00),

('SKU-401', 40, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655277/ay0desq9jeyq5ahn3n22.png', 1000.00),

('SKU-41', 4, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650220/ljo5m7xcf8vx1whkpme8.png', 1999.00),

('SKU-51', 5, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650343/kar92ffdwesh4at4sssi.png', 1999.00),

('SKU-61', 6, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650390/dnp3ssqsq1hi2jqkaiyl.png', 1999.00),

('SKU-71', 7, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650463/dabzatxfbolbfwmxcsie.png', 1799.00),

('SKU-81', 8, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653293/fojnhz2olgshfi4jevl4.png', 999.00),

('SKU-82', 8, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653293/fojnhz2olgshfi4jevl4.png', 1199.00),

('SKU-91', 9, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eegop4cpgeyv3ip5x.png', 1199.00),

('SKU-92', 9, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eegop4cpgeyv3ip5x.png', 1199.00),

('SKU-93', 9, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eegop4cpgeyv3ip5x.png', 1599.00);