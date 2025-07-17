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

INSERT INTO categories (name, category_image, description)
VALUES
('Men', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752646274/jopxijpokzy3ipalz4u.jpg', 'Latest styles for men'),

('Women', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752646333/ke16cowsficyzenx6bje.jpg', 'Trendy outfits for women'),

('Kids', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752646375/zx6yzi9hv5pmj4v5cby.jpg', 'Fun and comfy clothes for kids');



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


INSERT INTO seller (
    seller_name, contact_name, email, phone_number,
    address_line1, address_line2, city, state, zip_code, country
) VALUES
(
    'TechBazaar Pvt Ltd', 'Rahul Mehra', 'rahul.mehra@techbazaar.com', '9876543210',
    'Plot 21, Software Layout', 'Opposite Infosys Campus', 'Hyderabad', 'Telangana', '500084', 'India'
),
(
    'GreenEarth Supplies', 'Anjali Sharma', 'anjali@greenearth.in', '9988776655',
    '12, Eco Street', 'Near Botanical Garden', 'Bengaluru', 'Karnataka', '560103', 'India'
),
(
    'GlobalTech Traders', 'Mohit Verma', 'mohit@globaltech.com', '9123456780',
    '8, Trade Tower', 'Sector 62, Industrial Area', 'Noida', 'Uttar Pradesh', '201301', 'India'
);


CREATE TABLE products (
  product_id INT IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(100),
  category_id INT,
  seller_id BIGINT,
  status VARCHAR(20),
  last_modified_date DATETIME DEFAULT GETDATE(),
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (seller_id) REFERENCES seller(id)

);

INSERT INTO products (name, category_id, seller_id, status, last_modified_date) VALUES
('Men Loose Solid Color Cotton Linen Shirt', 1, 2, 'InActive', '2025-07-16 12:43:52.190'),
('Mens Stylish Solid Casual shirt', 1, 2, 'InActive', '2025-07-16 12:43:55.210'),
('Mens Regular Fit Cotton Formal Shirt', 1, 2, 'InActive', '2025-07-16 12:43:56.393'),
('Full Sleeve Solid Men Jacket', 1, 2, 'InActive', '2025-07-16 12:43:59.420'),
('Men Regular Fit Hooded Shirt', 1, 2, 'InActive', '2025-07-16 12:44:01.210'),
('MENS Full Sleeve Casual Short Kurta', 1, 2, 'InActive', '2025-07-16 12:44:02.450'),
('Mens Textured Popcorn Shirts Stylish', 1, 2, 'InActive', '2025-07-16 12:44:04.547'),
('Classy Polycotton Regular Shirt for Men', 1, 2, 'InActive', '2025-07-16 12:44:06.343'),
('Mens Linen Texture Shirt', 1, 2, 'InActive', '2025-07-16 12:44:08.410'),
('Bodycon Western Dress for Women', 2, 2, 'InActive', '2025-07-16 13:43:33.577'),
('Puff Sleeve Flowy Floral Dress for women', 2, 2, 'InActive', '2025-07-16 13:43:35.770'),
('White Chiken Kari Kurti for women', 2, 2, 'InActive', '2025-07-16 13:43:37.983'),
('Stylish Women Printed Designer Casual Dress', 2, 2, 'InActive', '2025-07-16 13:43:40.377'),
('Women Solid Kurta with Trouser and Dupatta Set', 2, 2, 'InActive', '2025-07-16 13:43:42.343'),
('Womens Pure Cotton Printed with Hand Embroidery Kurti', 2, 2, 'InActive', '2025-07-16 13:47:30.810'),
('Jumpsuit for Women', 2, 2, 'InActive', '2025-07-16 13:47:34.380'),
('Long Maxi One Piece Dresses for Women', 2, 2, 'InActive', '2025-07-16 13:47:37.910'),
('Women Bodycon Pink Knee Length Dress', 2, 2, 'InActive', '2025-07-16 13:47:40.050'),
('Bhandini Printed V Neck Gown Dress for Women', 2, 2, 'InActive', '2025-07-16 13:47:42.167'),
('Linen Shirt and Pant Co ord Set for Kids', 3, 1, 'InActive', '2025-07-16 13:55:08.543'),
('Ethnic Wear Pure Cotton Dhoti Kurta Set for Kids', 3, 1, 'InActive', '2025-07-16 13:55:10.540'),
('Kids Boys Green Solid Raw Silk Dhoti Set', 3, 1, 'InActive', '2025-07-16 13:55:12.367'),
('Embellished Single Breasted Festive Blazer for Kids', 3, 1, 'InActive', '2025-07-16 14:03:31.510'),
('Kids Pajama Set Pajama Suit', 3, 1, 'InActive', '2025-07-16 14:03:31.510'),
('Girls Embroidery Gown Dress', 3, 3, 'InActive', '2025-07-16 14:04:36.693'),
('Pink dresses and frocks', 3, 2, 'InActive', '2025-07-16 14:06:06.443'),
('Kdbea Baby Girls Clothes Frock and Dress', 3, 2, 'InActive', '2025-07-16 14:06:56.907'),
('Hopscotch Girls Cotton All Over Print Lehenga Choli', 3, 2, 'InActive', '2025-07-16 14:08:19.343'),
('Aarka Girls Ethnic Wear', 3, 2, 'InActive', '2025-07-16 14:09:33.467'),
('Linen Half Sleeves Bow Applique Detail Frock', 3, 2, 'InActive', '2025-07-16 14:10:45.750');




CREATE TABLE product_attributes (
  sku VARCHAR(50) PRIMARY KEY,
  product_id INT,
  size VARCHAR(50),
  product_image VARCHAR(1000),
  price DECIMAL(10,2),
  FOREIGN KEY (product_id) REFERENCES products(product_id)

);

INSERT INTO product_attributes (sku, product_id, size, product_image, price) VALUES
('SKU-101', 10, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653514/utpwypwjpb9ne3oyt8y.png', 799.00),
('SKU-102', 10, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653514/utpwypwjpb9ne3oyt8y.png', 999.00),
('SKU-11', 1, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650058/zspkxcevkfthmvbnsef.png', 1499.00),
('SKU-111', 11, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653576/d1po3fh5zy2igo5ugr.png', 1299.00),
('SKU-112', 11, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653576/d1po3fh5zy2igo5ugr.png', 1299.00),
('SKU-121', 12, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r2vxbtxqchilohzxn3.png', 599.00),
('SKU-122', 12, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r2vxbtxqchilohzxn3.png', 799.00),
('SKU-123', 12, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653644/r2vxbtxqchilohzxn3.png', 699.00),
('SKU-131', 13, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653729/tyfsvpx5jh59egkl1ry.png', 599.00),
('SKU-132', 13, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653729/tyfsvpx5jh59egkl1ry.png', 899.00),
('SKU-141', 14, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653793/bbqpoazeef5fruocb1ah.png', 899.00),
('SKU-142', 14, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653793/bbqpoazeef5fruocb1ah.png', 1099.00),
('SKU-151', 15, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653872/bbho867gvx7owm182a.png', 999.00),
('SKU-161', 16, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653919/u4ju78u8kitcoedps9o.png', 499.00),
('SKU-162', 16, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653919/u4ju78u8kitcoedps9o.png', 599.00),
('SKU-171', 17, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653987/newgpketvmye8vz6b0n.png', 299.00),
('SKU-172', 17, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653987/newgpketvmye8vz6b0n.png', 399.00),
('SKU-181', 18, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654077/wcqtrbzypwa44gxs9oah.png', 789.00),
('SKU-182', 18, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654077/wcqtrbzypwa44gxs9oah.png', 899.00),
('SKU-191', 19, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/v0vmfpiulvorephbmqfd.png', 2999.00),
('SKU-192', 19, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/v0vmfpiulvorephbmqfd.png', 3499.00),
('SKU-193', 19, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654152/v0vmfpiulvorephbmqfd.png', 3899.00),
('SKU-201', 20, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654246/y4vow764znkryo3s391.png', 799.00),
('SKU-202', 20, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654246/y4vow764znkryo3s391.png', 899.00),
('SKU-21', 2, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650103/w6cj78mvbsio5gf4fx.png', 2999.00),
('SKU-211', 21, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654481/cbfyhaza5foyduireus.png', 1499.00),
('SKU-212', 21, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654481/cbfyhaza5foyduireus.png', 1699.00),
('SKU-213', 21, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654481/cbfyhaza5foyduireus.png', 1999.00),
('SKU-221', 22, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654481/cbfyhaza5foyduireus.png', 2499.00),
('SKU-231', 23, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654752/onh5txotxeyc3bgp5tm.png', 1899.00),
('SKU-232', 23, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654752/onh5txotxeyc3bgp5tm.png', 1399.00),
('SKU-241', 24, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654817/oywmmtx2rwkc01kkeul.png', 199.00),
('SKU-242', 24, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654817/oywmmtx2rwkc01kkeul.png', 199.00),
('SKU-251', 25, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654890/nyekfd6ubxpcyqtl2c.png', 799.00),
('SKU-252', 25, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654890/nyekfd6ubxpcyqtl2c.png', 899.00),
('SKU-261', 26, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654979/4n1wkcqwywiixaaeh9s.png', 999.00),
('SKU-262', 26, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752654979/4n1wkcqwywiixaaeh9s.png', 1099.00),
('SKU-271', 27, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655039/qvlk32tjda7k73k1yo2n.png', 1599.00),
('SKU-281', 28, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655101/rykgnkblq6eb2wxusze7.png', 3999.00),
('SKU-282', 28, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655101/rykgnkblq6eb2wxusze7.png', 3999.00),
('SKU-291', 29, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655183/njoktwsirkskwj9c3rb2e.png', 1099.00),
('SKU-292', 29, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655183/njoktwsirkskwj9c3rb2e.png', 1099.00),
('SKU-301', 30, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752655277/yo4desej9eyah53hn3n22.png', 1999.00),
('SKU-31', 3, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650169/gc7xmnj74j5k46qwat.png', 1499.00),
('SKU-41', 4, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650220/jo5m7xcf8vx1whkmpne8.png', 1999.00),
('SKU-51', 5, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650343/kar3pf4wesh4af4sssi.png', 1999.00),
('SKU-61', 6, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650390/dnp3ssqq1hj2qkaiyl.png', 1999.00),
('SKU-71', 7, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752650463/dabaztbfohbfrwxcise.png', 1799.00),
('SKU-81', 8, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653293/rojhn2olgslfni4jev4.png', 999.00),
('SKU-82', 8, 'L', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653293/rojhn2olgslfni4jev4.png', 1199.00),
('SKU-91', 9, 'XL', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eego4cpqgeyv3ip5x.png', 1199.00),
('SKU-92', 9, 'S', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eego4cpqgeyv3ip5x.png', 1199.00),
('SKU-93', 9, 'M', 'https://res.cloudinary.com/dyqknbbgl/image/upload/v1752653420/vw8eego4cpqgeyv3ip5x.png', 1599.00);