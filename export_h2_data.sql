-- Export script to get data from H2 database
-- This will create INSERT statements compatible with SQL Server

-- First, let's see what we have in each table
SELECT 'Services Data:' as info;
SELECT * FROM services;

SELECT 'Customers Data:' as info;  
SELECT * FROM customers;

SELECT 'Bookings Data:' as info;
SELECT * FROM bookings;

SELECT 'Admins Data:' as info;
SELECT * FROM admins;

-- Generate INSERT statements for SQL Server
SELECT 'INSERT INTO Services (service_name, description, price, duration_minutes, is_active, created_date) VALUES (' +
       '''' + service_name + ''', ' +
       '''' + description + ''', ' +
       CAST(price AS VARCHAR) + ', ' +
       CAST(duration_minutes AS VARCHAR) + ', ' +
       CAST(is_active AS VARCHAR) + ', ' +
       '''' + FORMATDATETIME(created_date, 'yyyy-MM-dd HH:mm:ss') + ''');'
FROM services;
