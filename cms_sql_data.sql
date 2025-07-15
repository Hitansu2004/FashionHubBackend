-- Promo table
CREATE TABLE Promo (
  promo_code VARCHAR(50) PRIMARY KEY,
  promo_type VARCHAR(50),
  description VARCHAR(255),
  amount DECIMAL(10, 2),
  status VARCHAR(20)
);

-- Product_Category table
CREATE TABLE product_categories (
  id INT IDENTITY(1,1) PRIMARY KEY,
  category_id INT,
  sku VARCHAR(50),
  price DECIMAL(10,2),
  discount DECIMAL(5,2),
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (sku) REFERENCES product_attributes(sku)
);

-- Promotion_Product_Join table
CREATE TABLE Promotion_product_join (
  id INT IDENTITY(1,1) PRIMARY KEY,
  product_id INT,
  promo_code VARCHAR(50),
  FOREIGN KEY (product_id) REFERENCES products(product_id),
  FOREIGN KEY (promo_code) REFERENCES Promo(promo_code)
);

INSERT INTO Promo (promo_code, promo_type, description, amount, status)
VALUES
('PROMO2025', 'Seasonal', 'Summer sale discount', 200.00, 'Active'),
('PROMO2026', 'Festival', 'Diwali special offer', 150.00, 'Active'),
('PROMO2027', 'Clearance', 'End of season clearance', 300.00, 'Inactive');

INSERT INTO product_categories (category_id, sku, price, discount)
VALUES
(1, 'SKU-001', 999.00, 10.0),
(2, 'SKU-005', 1999.00, 5.0),
(3, 'SKU-009', 799.00, 15.0);

INSERT INTO Promotion_product_join (product_id, promo_code)
VALUES
(1, 'PROMO2025'),
(5, 'PROMO2026'),
(9, 'PROMO2027');

