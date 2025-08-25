-- Dynamo Detailing Database Schema
-- MS SQL Server Database

USE master;
GO

-- Create database if it doesn't exist
IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'DynamoDetailingDB')
BEGIN
    CREATE DATABASE DynamoDetailingDB;
END
GO

USE DynamoDetailingDB;
GO

-- Services Table
CREATE TABLE Services (
    service_id INT IDENTITY(1,1) PRIMARY KEY,
    service_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    duration_minutes INT NOT NULL,
    is_active BIT DEFAULT 1,
    created_date DATETIME2 DEFAULT GETDATE()
);

-- Customers Table
CREATE TABLE Customers (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    phone_number NVARCHAR(20) NOT NULL,
    address NVARCHAR(255),
    city NVARCHAR(50),
    state NVARCHAR(50),
    zip_code NVARCHAR(10),
    created_date DATETIME2 DEFAULT GETDATE()
);

-- Admins Table
CREATE TABLE Admins (
    admin_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    role NVARCHAR(20) DEFAULT 'ADMIN',
    is_active BIT DEFAULT 1,
    created_date DATETIME2 DEFAULT GETDATE()
);

-- Bookings Table
CREATE TABLE Bookings (
    booking_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    booking_date DATE NOT NULL,
    booking_time TIME NOT NULL,
    location_address NVARCHAR(255) NOT NULL,
    location_city NVARCHAR(50) NOT NULL,
    location_state NVARCHAR(50) NOT NULL,
    location_zip NVARCHAR(10) NOT NULL,
    special_instructions NVARCHAR(500),
    status NVARCHAR(20) DEFAULT 'PENDING', -- PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    total_amount DECIMAL(10,2) NOT NULL,
    created_date DATETIME2 DEFAULT GETDATE(),
    updated_date DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id)
);

-- Insert default services
INSERT INTO Services (service_name, description, price, duration_minutes) VALUES
('Basic Wash', 'Exterior wash with soap and water, interior vacuum and wipe down', 25.00, 60),
('Premium Wash', 'Basic wash plus tire shine, dashboard conditioning, and window cleaning', 40.00, 90),
('Full Detail', 'Complete interior and exterior detailing including wax and polish', 80.00, 180),
('Interior Deep Clean', 'Deep cleaning of seats, carpets, dashboard, and all interior surfaces', 60.00, 120),
('Paint Correction', 'Professional paint correction and ceramic coating application', 150.00, 240),
('Engine Bay Cleaning', 'Thorough cleaning and detailing of engine compartment', 45.00, 75);

-- Insert default admin user (password: admin123)
INSERT INTO Admins (username, password_hash, email, first_name, last_name) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@dynamodetailing.com', 'Admin', 'User');

-- Create indexes for better performance
CREATE INDEX IX_Bookings_CustomerID ON Bookings(customer_id);
CREATE INDEX IX_Bookings_ServiceID ON Bookings(service_id);
CREATE INDEX IX_Bookings_BookingDate ON Bookings(booking_date);
CREATE INDEX IX_Customers_Email ON Customers(email);

GO
