# Dynamo Detailing - Professional Auto Detailing Website

A comprehensive web application for professional auto detailing services built with Spring Boot, MS SQL Server, and Thymeleaf. Features a modern black, red, and yellow theme with mobile service booking capabilities and admin management system.

## Features

### Customer Features
- **Professional Homepage** - Modern design showcasing services and company information
- **Service Booking System** - Easy-to-use booking form with service selection, location, and appointment scheduling
- **About Page** - Comprehensive information about Dynamo Detailing services and processes
- **Mobile-Responsive Design** - Optimized for all devices

### Admin Features
- **Admin Dashboard** - Comprehensive overview with booking statistics and quick actions
- **Booking Management** - View, update, and manage all customer bookings
- **Customer Management** - Access customer information and booking history
- **Service Management** - Manage available services, pricing, and descriptions
- **Reports & Analytics** - Booking statistics and business insights
- **Secure Authentication** - Admin login with role-based access control

### Services Offered
1. **Basic Wash** ($25.00, 60 min) - Exterior wash with interior vacuum and wipe down
2. **Premium Wash** ($40.00, 90 min) - Basic wash plus tire shine and dashboard conditioning
3. **Full Detail** ($80.00, 180 min) - Complete interior and exterior detailing
4. **Interior Deep Clean** ($60.00, 120 min) - Deep cleaning of all interior surfaces
5. **Paint Correction** ($150.00, 240 min) - Professional paint correction and ceramic coating
6. **Engine Bay Cleaning** ($45.00, 75 min) - Thorough engine compartment detailing

## Technology Stack

- **Backend**: Spring Boot 3.1.0, Java 17
- **Database**: Microsoft SQL Server
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Security**: Spring Security with BCrypt password hashing
- **Build Tool**: Maven
- **Development**: Spring Boot DevTools for hot reloading

## Prerequisites

- Java 17 or higher
- Microsoft SQL Server (2019 or later recommended)
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Database Setup

1. **Install Microsoft SQL Server** if not already installed
2. **Create Database**: Run the SQL script in `database_schema.sql` to create the database and tables
3. **Update Connection**: Edit `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=DynamoDetailingDB;encrypt=false;trustServerCertificate=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Installation & Setup

1. **Clone or Download** the project files to your local machine

2. **Configure Database** connection in `application.properties`

3. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

4. **Run Database Script**:
   - Execute `database_schema.sql` in your SQL Server Management Studio
   - This creates the database, tables, and sample data

5. **Start Application**:
   ```bash
   mvn spring-boot:run
   ```

6. **Access Application**:
   - Customer Website: http://localhost:8080
   - Admin Login: http://localhost:8080/admin/login

## Default Admin Credentials

- **Username**: admin
- **Password**: admin123

*⚠️ Change these credentials in production!*

## Application Structure

```
src/
├── main/
│   ├── java/com/dynamo/detailing/
│   │   ├── config/           # Security and configuration
│   │   ├── controller/       # Web controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic
│   │   └── DynamoDetailingApplication.java
│   └── resources/
│       ├── static/          # CSS, JS, images
│       ├── templates/       # Thymeleaf templates
│       └── application.properties
└── test/                    # Unit tests
```

## Key URLs

### Customer Pages
- `/` - Homepage
- `/about` - About Us
- `/services` - Services listing
- `/book` - Booking form
- `/contact` - Contact information

### Admin Pages
- `/admin/login` - Admin login
- `/admin/dashboard` - Admin dashboard
- `/admin/bookings` - Manage bookings
- `/admin/customers` - Manage customers
- `/admin/services` - Manage services
- `/admin/schedule` - Daily schedule
- `/admin/reports` - Reports and analytics

## Database Schema

### Main Tables
- **Services** - Service definitions with pricing and duration
- **Customers** - Customer information and contact details
- **Bookings** - Appointment bookings linking customers and services
- **Admins** - Admin users for system management

### Key Relationships
- Customer → Bookings (One-to-Many)
- Service → Bookings (One-to-Many)
- Booking statuses: PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED

## Features in Detail

### Booking System
- Service selection with automatic pricing
- Customer information collection
- Service location specification
- Appointment date and time scheduling
- Special instructions field
- Email confirmation (ready for SMTP integration)

### Admin Dashboard
- Real-time booking statistics
- Today's schedule view
- Pending confirmations alert
- Quick action buttons
- Recent bookings overview

### Security
- Spring Security integration
- BCrypt password hashing
- Role-based access control
- Session management
- CSRF protection

### Mobile Responsiveness
- Responsive grid layouts
- Mobile-optimized forms
- Touch-friendly navigation
- Scalable typography

## Customization

### Theme Colors
The application uses a professional color scheme:
- **Primary Black**: #000000, #1a1a1a
- **Accent Red**: #dc3545, #c82333  
- **Highlight Yellow**: #ffc107, #e0a800

### Adding Services
1. Insert new services in the database:
```sql
INSERT INTO Services (service_name, description, price, duration_minutes) 
VALUES ('New Service', 'Description', 99.00, 120);
```

2. Services will automatically appear in booking forms and admin management

### Email Configuration
To enable email notifications, add SMTP configuration to `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Deployment

### Production Configuration
1. Update `application.properties` for production database
2. Change default admin credentials
3. Enable HTTPS
4. Configure proper logging levels
5. Set up database backups

### Environment Variables
Use environment variables for sensitive configuration:
```bash
export DB_USERNAME=production_user
export DB_PASSWORD=secure_password
export ADMIN_PASSWORD=new_admin_password
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify SQL Server is running
   - Check connection string in application.properties
   - Ensure database exists

2. **Admin Login Failed**
   - Verify admin user exists in database
   - Check password is hashed with BCrypt
   - Ensure user is active (is_active = 1)

3. **Port Already in Use**
   - Change port in application.properties:
   ```properties
   server.port=8081
   ```

### Logs
Application logs are available in the console and can be configured in `application.properties`:
```properties
logging.level.com.dynamo.detailing=DEBUG
logging.file.name=dynamo-detailing.log
```

## Support

For support or questions about this application:
- Check the code comments for implementation details
- Review the database schema for data structure
- Examine the service layer for business logic

## License

This project is created for educational/commercial purposes. Modify and use as needed for your auto detailing business.

---

**Dynamo Detailing** - Where Premium Meets Perfection 🚗✨
