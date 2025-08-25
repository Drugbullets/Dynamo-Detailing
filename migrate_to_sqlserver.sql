-- Migration script from H2 to SQL Server
-- Import customers and bookings data

USE DynamoDetailingDB;
GO

-- Clear existing data (except services and admin which we keep)
DELETE FROM Bookings;
DELETE FROM Customers;

-- Reset identity counters
DBCC CHECKIDENT ('Customers', RESEED, 0);
DBCC CHECKIDENT ('Bookings', RESEED, 0);

-- Insert customers with explicit IDs to maintain relationships
SET IDENTITY_INSERT Customers ON;

INSERT INTO Customers (customer_id, first_name, last_name, email, phone_number, address, city, state, zip_code, created_date) VALUES
(1, 'arin', 'RS', 'Arin.Work32@gmail.com', '9025032756', NULL, NULL, NULL, NULL, '2025-08-25 14:01:41.097'),
(33, 'Test', 'Customer', 'test@example.com', '+1555123456', '789 Test Rd', 'Test City', 'TS', '12345', '2025-08-25 15:33:42.842');

SET IDENTITY_INSERT Customers OFF;

-- Insert bookings with explicit IDs to maintain booking numbers
SET IDENTITY_INSERT Bookings ON;

INSERT INTO Bookings (booking_id, customer_id, service_id, booking_date, booking_time, location_address, location_city, location_state, location_zip, special_instructions, status, total_amount, created_date, updated_date) VALUES
(1, 1, 2, '2025-09-06', '11:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'wefd', 'PENDING', 40.00, '2025-08-25 14:01:41.091', '2025-08-25 14:01:41.091'),
(33, 1, 1, '2025-08-30', '13:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'fghbs', 'PENDING', 25.00, '2025-08-25 14:23:03.692', '2025-08-25 14:23:03.692'),
(65, 1, 1, '2025-09-05', '11:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'srfdgt', 'PENDING', 25.00, '2025-08-25 14:38:49.260', '2025-08-25 14:38:49.260'),
(97, 1, 3, '2025-08-29', '11:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'zsdf', 'PENDING', 80.00, '2025-08-25 15:07:44.831', '2025-08-25 15:07:44.831'),
(129, 1, 2, '2025-08-30', '12:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'QWER', 'PENDING', 40.00, '2025-08-25 15:12:33.386', '2025-08-25 15:12:33.386'),
(161, 1, 2, '2025-08-29', '10:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'sedrg', 'PENDING', 40.00, '2025-08-25 15:27:22.953', '2025-08-25 15:27:22.953'),
(193, 1, 6, '2025-08-27', '16:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'asedrf', 'PENDING', 45.00, '2025-08-25 15:31:24.073', '2025-08-25 15:31:24.073'),
(225, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 15:33:42.967', '2025-08-25 15:33:42.967'),
(226, 1, 3, '2025-09-05', '15:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'awertf', 'PENDING', 80.00, '2025-08-25 15:34:31.752', '2025-08-25 15:34:31.752'),
(257, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 15:39:12.683', '2025-08-25 15:39:12.684'),
(258, 1, 4, '2025-08-30', '10:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'ygthf', 'PENDING', 60.00, '2025-08-25 15:40:39.729', '2025-08-25 15:40:39.729'),
(289, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 16:07:43.897', '2025-08-25 16:07:43.897'),
(290, 1, 2, '2025-08-28', '16:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'awser', 'PENDING', 40.00, '2025-08-25 16:08:42.060', '2025-08-25 16:08:42.060'),
(291, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 16:11:38.284', '2025-08-25 16:11:38.284'),
(292, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 16:11:55.394', '2025-08-25 16:11:55.394'),
(293, 1, 2, '2025-08-28', '10:00:00', '113 pranavathi street sri chakara nagar', 'chennai', 'tamilnadu', '600122', 'awefa', 'PENDING', 40.00, '2025-08-25 16:12:25.949', '2025-08-25 16:12:25.949'),
(321, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 18:29:21.695', '2025-08-25 18:29:21.695'),
(322, 1, 4, '2025-08-29', '10:00:00', 'a', 'chennai', 'tamilnadu', '600122', 'dsfr', 'PENDING', 60.00, '2025-08-25 18:30:45.744', '2025-08-25 18:30:45.744'),
(353, 33, 1, '2025-08-27', '11:00:00', '789 Test Rd', 'Test City', 'TS', '12345', 'Test booking - data verification', 'CONFIRMED', 25.00, '2025-08-25 18:40:07.523', '2025-08-25 18:40:07.523');

SET IDENTITY_INSERT Bookings OFF;

-- Update identity counters to continue from current max values
DBCC CHECKIDENT ('Customers', RESEED, 33);
DBCC CHECKIDENT ('Bookings', RESEED, 353);

-- Verify the data migration
SELECT 'Migration Summary:' as info;
SELECT 'Customers: ' + CAST(COUNT(*) AS VARCHAR) as count FROM Customers;
SELECT 'Bookings: ' + CAST(COUNT(*) AS VARCHAR) as count FROM Bookings;
SELECT 'Services: ' + CAST(COUNT(*) AS VARCHAR) as count FROM Services;
SELECT 'Admins: ' + CAST(COUNT(*) AS VARCHAR) as count FROM Admins;

GO
