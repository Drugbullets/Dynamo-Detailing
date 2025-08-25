package com.dynamo.detailing.config;

import com.dynamo.detailing.entity.Admin;
import com.dynamo.detailing.entity.Booking;
import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.entity.Service;
import com.dynamo.detailing.repository.AdminRepository;
import com.dynamo.detailing.repository.BookingRepository;
import com.dynamo.detailing.repository.CustomerRepository;
import com.dynamo.detailing.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Transactional
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize services if they don't exist
        if (serviceRepository.count() == 0) {
            initializeServices();
        }

        // Initialize admin user if they don't exist
        if (adminRepository.count() == 0) {
            initializeAdminUser();
        }
        
        // Initialize sample booking data 
        long bookingCount = bookingRepository.count();
        System.out.println("Current booking count: " + bookingCount);
        
        if (bookingCount == 0) {
            System.out.println("No bookings found, creating sample data...");
            initializeSampleBookings();
        } else {
            System.out.println("Found " + bookingCount + " existing bookings, skipping sample data creation.");
            // Let's create one more test booking anyway
            createTestBooking();
        }
    }

    private void initializeServices() {
        Service[] services = {
            new Service("Basic Wash", "Exterior wash with soap and water, interior vacuum and wipe down", 
                        new BigDecimal("25.00"), 60),
            new Service("Premium Wash", "Basic wash plus tire shine, dashboard conditioning, and window cleaning", 
                        new BigDecimal("40.00"), 90),
            new Service("Full Detail", "Complete interior and exterior detailing including wax and polish", 
                        new BigDecimal("80.00"), 180),
            new Service("Interior Deep Clean", "Deep cleaning of seats, carpets, dashboard, and all interior surfaces", 
                        new BigDecimal("60.00"), 120),
            new Service("Paint Correction", "Professional paint correction and ceramic coating application", 
                        new BigDecimal("150.00"), 240),
            new Service("Engine Bay Cleaning", "Thorough cleaning and detailing of engine compartment", 
                        new BigDecimal("45.00"), 75)
        };

        for (Service service : services) {
            serviceRepository.save(service);
            System.out.println("Initialized service: " + service.getServiceName());
        }
    }

    private void initializeAdminUser() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setEmail("admin@dynamodetailing.com");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole("ADMIN");
        admin.setIsActive(true);

        adminRepository.save(admin);
        System.out.println("Initialized admin user: " + admin.getUsername());
        System.out.println("Admin password: admin123");
    }
    
    private void initializeSampleBookings() {
        System.out.println("Initializing sample booking data...");
        
        try {
            // Create sample customers
            Customer customer1 = new Customer();
            customer1.setFirstName("John");
            customer1.setLastName("Doe");
            customer1.setEmail("john.doe@example.com");
            customer1.setPhoneNumber("+1234567890");
            customer1.setAddress("123 Main St");
            customer1.setCity("Springfield");
            customer1.setState("IL");
            customer1.setZipCode("62701");
            customer1 = customerRepository.save(customer1);
            System.out.println("Created customer: " + customer1.getFullName());
            
            Customer customer2 = new Customer();
            customer2.setFirstName("Jane");
            customer2.setLastName("Smith");
            customer2.setEmail("jane.smith@example.com");
            customer2.setPhoneNumber("+1987654321");
            customer2.setAddress("456 Oak Ave");
            customer2.setCity("Springfield");
            customer2.setState("IL");
            customer2.setZipCode("62702");
            customer2 = customerRepository.save(customer2);
            System.out.println("Created customer: " + customer2.getFullName());
            
            // Get services
            Service basicWash = serviceRepository.findById(1L).orElse(null);
            Service premiumWash = serviceRepository.findById(2L).orElse(null);
            Service fullDetail = serviceRepository.findById(3L).orElse(null);
            
            if (basicWash != null && premiumWash != null && fullDetail != null) {
                // Create sample bookings
                Booking booking1 = new Booking();
                booking1.setCustomer(customer1);
                booking1.setService(basicWash);
                booking1.setBookingDate(LocalDate.now().plusDays(3));
                booking1.setBookingTime(LocalTime.of(10, 0));
                booking1.setLocationAddress("123 Main St");
                booking1.setLocationCity("Springfield");
                booking1.setLocationState("IL");
                booking1.setLocationZip("62701");
                booking1.setTotalAmount(basicWash.getPrice());
                booking1.setStatus(Booking.BookingStatus.PENDING);
                booking1.setSpecialInstructions("Please call before arriving");
                booking1 = bookingRepository.save(booking1);
                System.out.println("Created booking 1 with ID: " + booking1.getBookingId());
                
                Booking booking2 = new Booking();
                booking2.setCustomer(customer2);
                booking2.setService(premiumWash);
                booking2.setBookingDate(LocalDate.now().plusDays(5));
                booking2.setBookingTime(LocalTime.of(14, 30));
                booking2.setLocationAddress("456 Oak Ave");
                booking2.setLocationCity("Springfield");
                booking2.setLocationState("IL");
                booking2.setLocationZip("62702");
                booking2.setTotalAmount(premiumWash.getPrice());
                booking2.setStatus(Booking.BookingStatus.CONFIRMED);
                booking2.setSpecialInstructions("Garage access available");
                booking2 = bookingRepository.save(booking2);
                System.out.println("Created booking 2 with ID: " + booking2.getBookingId());
                
                Booking booking3 = new Booking();
                booking3.setCustomer(customer1);
                booking3.setService(fullDetail);
                booking3.setBookingDate(LocalDate.now().plusDays(7));
                booking3.setBookingTime(LocalTime.of(9, 0));
                booking3.setLocationAddress("123 Main St");
                booking3.setLocationCity("Springfield");
                booking3.setLocationState("IL");
                booking3.setLocationZip("62701");
                booking3.setTotalAmount(fullDetail.getPrice());
                booking3.setStatus(Booking.BookingStatus.PENDING);
                booking3.setSpecialInstructions("Vehicle will be in driveway");
                booking3 = bookingRepository.save(booking3);
                System.out.println("Created booking 3 with ID: " + booking3.getBookingId());
                
                System.out.println("Successfully initialized sample booking data!");
            } else {
                System.out.println("Could not find required services for sample bookings");
            }
        } catch (Exception e) {
            System.out.println("Error creating sample bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTestBooking() {
        try {
            System.out.println("Creating additional test booking...");
            
            // Find or create a test customer
            Customer testCustomer = customerRepository.findByEmail("test@example.com").orElse(null);
            if (testCustomer == null) {
                testCustomer = new Customer();
                testCustomer.setFirstName("Test");
                testCustomer.setLastName("Customer");
                testCustomer.setEmail("test@example.com");
                testCustomer.setPhoneNumber("+1555123456");
                testCustomer.setAddress("789 Test Rd");
                testCustomer.setCity("Test City");
                testCustomer.setState("TS");
                testCustomer.setZipCode("12345");
                testCustomer = customerRepository.save(testCustomer);
                System.out.println("Created test customer: " + testCustomer.getFullName());
            }
            
            // Get first available service
            Service service = serviceRepository.findById(1L).orElse(null);
            if (service != null) {
                Booking testBooking = new Booking();
                testBooking.setCustomer(testCustomer);
                testBooking.setService(service);
                testBooking.setBookingDate(LocalDate.now().plusDays(2));
                testBooking.setBookingTime(LocalTime.of(11, 0));
                testBooking.setLocationAddress("789 Test Rd");
                testBooking.setLocationCity("Test City");
                testBooking.setLocationState("TS");
                testBooking.setLocationZip("12345");
                testBooking.setTotalAmount(service.getPrice());
                testBooking.setStatus(Booking.BookingStatus.CONFIRMED);
                testBooking.setSpecialInstructions("Test booking - data verification");
                testBooking = bookingRepository.save(testBooking);
                System.out.println("Created test booking with ID: " + testBooking.getBookingId());
            }
        } catch (Exception e) {
            System.out.println("Error creating test booking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
