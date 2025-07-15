
Create database snb_db;
 
use snb_db;
 
CREATE TABLE users (
  user_id INT IDENTITY(1,1) PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone_number VARCHAR(15)
);

INSERT INTO users (first_name, last_name, email, password, phone_number)
VALUES 
('Arjun', 'Mehta', 'arjun.mehta@example.com', 'Pass@123', '9812345678'),
('Riya', 'Sharma', 'riya.sharma@example.com', 'Riya#2024', '9876543210'),
('Kabir', 'Singh', 'kabir.singh@example.com', 'Kabir@456', '9700011223'),
('Ananya', 'Rao', 'ananya.rao@example.com', 'Ana2025!*', '9733344455');
 
-- Create Roles table
CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create UserRoles table
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),  -- Composite key ensures uniqueness
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
 
INSERT INTO roles (role_name)
VALUES 
    ('customer'),
    ('ims_admin'),
    ('pim_admin'),
    ('oms_admin'),
    ('cms_admin');
 
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1),  
(2, 2),  
(3, 3),  
(4, 4),
(1, 5);
  