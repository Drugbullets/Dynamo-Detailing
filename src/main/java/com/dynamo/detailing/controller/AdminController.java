package com.dynamo.detailing.controller;

import com.dynamo.detailing.entity.Booking;
import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.entity.Service;
import com.dynamo.detailing.service.BookingService;
import com.dynamo.detailing.service.CustomerService;
import com.dynamo.detailing.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private CustomerService customerService;
    
    /**
     * Admin login page
     */
    @GetMapping("/login")
    public String adminLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        model.addAttribute("pageTitle", "Admin Login - Dynamo Detailing");
        return "admin/login";
    }
    
    /**
     * Admin dashboard
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        Map<String, Object> stats = bookingService.getDashboardStatistics();
        List<Booking> recentBookings = bookingService.getBookingsOrderedByDate();
        List<Booking> todayBookings = bookingService.getTodaysBookings();
        List<Booking> needingConfirmation = bookingService.getBookingsNeedingConfirmation();
        
        // Limit recent bookings to 10
        if (recentBookings.size() > 10) {
            recentBookings = recentBookings.subList(0, 10);
        }
        
        model.addAttribute("stats", stats);
        model.addAttribute("recentBookings", recentBookings);
        model.addAttribute("todayBookings", todayBookings);
        model.addAttribute("needingConfirmation", needingConfirmation);
        model.addAttribute("pageTitle", "Admin Dashboard - Dynamo Detailing");
        
        return "admin/dashboard";
    }
    
    /**
     * All bookings page
     */
    @GetMapping("/bookings")
    public String allBookings(@RequestParam(value = "status", required = false) String status,
                             @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             Model model) {
        
        List<Booking> bookings;
        
        if (status != null && !status.isEmpty()) {
            try {
                Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
                bookings = bookingService.getBookingsByStatus(bookingStatus);
                model.addAttribute("selectedStatus", status);
            } catch (IllegalArgumentException e) {
                bookings = bookingService.getAllBookings();
            }
        } else if (date != null) {
            bookings = bookingService.getBookingsByDate(date);
            model.addAttribute("selectedDate", date);
        } else {
            bookings = bookingService.getBookingsOrderedByDate();
        }
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("pageTitle", "All Bookings - Dynamo Detailing Admin");
        
        return "admin/bookings";
    }
    
    /**
     * Booking details page
     */
    @GetMapping("/bookings/{bookingId}")
    public String bookingDetails(@PathVariable Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            model.addAttribute("booking", bookingOpt.get());
            model.addAttribute("pageTitle", "Booking Details - Dynamo Detailing Admin");
            return "admin/booking-details";
        } else {
            return "redirect:/admin/bookings";
        }
    }
    
    /**
     * Update booking status
     */
    @PostMapping("/bookings/{bookingId}/status")
    public String updateBookingStatus(@PathVariable Long bookingId,
                                    @RequestParam("status") String status,
                                    RedirectAttributes redirectAttributes) {
        try {
            Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
            Booking updatedBooking = bookingService.updateBookingStatus(bookingId, bookingStatus);
            
            if (updatedBooking != null) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Booking status updated to " + bookingStatus.toString().toLowerCase().replace("_", " "));
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Booking not found");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid status value");
        }
        
        return "redirect:/admin/bookings/" + bookingId;
    }
    
    /**
     * Services management page
     */
    @GetMapping("/services")
    public String manageServices(Model model) {
        List<Service> services = serviceService.getAllServices();
        model.addAttribute("services", services);
        model.addAttribute("pageTitle", "Manage Services - Dynamo Detailing Admin");
        return "admin/services";
    }
    
    /**
     * Customers page
     */
    @GetMapping("/customers")
    public String allCustomers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Customer> customers;
        
        if (search != null && !search.isEmpty()) {
            customers = customerService.searchCustomersByName(search);
            model.addAttribute("searchTerm", search);
        } else {
            customers = customerService.getCustomersOrderedByRegistrationDate();
        }
        
        model.addAttribute("customers", customers);
        model.addAttribute("pageTitle", "All Customers - Dynamo Detailing Admin");
        
        return "admin/customers";
    }
    
    /**
     * Customer details page
     */
    @GetMapping("/customers/{customerId}")
    public String customerDetails(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId);
        if (customerOpt.isPresent()) {
            List<Booking> customerBookings = bookingService.getBookingsByCustomerId(customerId);
            
            model.addAttribute("customer", customerOpt.get());
            model.addAttribute("customerBookings", customerBookings);
            model.addAttribute("pageTitle", "Customer Details - Dynamo Detailing Admin");
            
            return "admin/customer-details";
        } else {
            return "redirect:/admin/customers";
        }
    }
    
    /**
     * Reports page
     */
    @GetMapping("/reports")
    public String reports(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         Model model) {
        
        // Default to current month if no dates provided
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        Map<String, Object> statistics = bookingService.getBookingStatistics(startDate, endDate);
        List<Object[]> popularServices = bookingService.getMostPopularServices();
        List<Booking> bookingsInPeriod = bookingService.getBookingsBetweenDates(startDate, endDate);
        
        model.addAttribute("statistics", statistics);
        model.addAttribute("popularServices", popularServices);
        model.addAttribute("bookingsInPeriod", bookingsInPeriod);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pageTitle", "Reports - Dynamo Detailing Admin");
        
        return "admin/reports";
    }
    
    /**
     * Today's schedule
     */
    @GetMapping("/schedule")
    public String todaySchedule(Model model) {
        List<Booking> todayBookings = bookingService.getTodaysBookings();
        List<Booking> upcomingBookings = bookingService.getUpcomingBookings();
        
        // Limit upcoming bookings to next 7 days
        if (upcomingBookings.size() > 20) {
            upcomingBookings = upcomingBookings.subList(0, 20);
        }
        
        model.addAttribute("todayBookings", todayBookings);
        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("pageTitle", "Schedule - Dynamo Detailing Admin");
        
        return "admin/schedule";
    }
}
