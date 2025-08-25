package com.dynamo.detailing.controller;

import com.dynamo.detailing.entity.Booking;
import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.entity.Service;
import com.dynamo.detailing.service.BookingService;
import com.dynamo.detailing.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private BookingService bookingService;
    
    /**
     * Home page
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Service> services = serviceService.getAllActiveServices();
        List<Service> popularServices = serviceService.getMostPopularServices();
        
        model.addAttribute("services", services);
        model.addAttribute("popularServices", popularServices);
        model.addAttribute("pageTitle", "Dynamo Detailing - Professional Auto Detailing Services");
        
        return "index";
    }
    
    /**
     * About page
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About Us - Dynamo Detailing");
        return "about";
    }
    
    /**
     * Services page
     */
    @GetMapping("/services")
    public String services(Model model) {
        List<Service> services = serviceService.getAllActiveServices();
        model.addAttribute("services", services);
        model.addAttribute("pageTitle", "Our Services - Dynamo Detailing");
        return "services";
    }
    
    /**
     * Service details page
     */
    @GetMapping("/services/{serviceId}")
    public String serviceDetails(@PathVariable Long serviceId, Model model) {
        Optional<Service> serviceOpt = serviceService.getServiceById(serviceId);
        if (serviceOpt.isPresent() && serviceOpt.get().getIsActive()) {
            model.addAttribute("service", serviceOpt.get());
            model.addAttribute("pageTitle", serviceOpt.get().getServiceName() + " - Dynamo Detailing");
            return "service-details";
        } else {
            return "redirect:/services";
        }
    }
    
    /**
     * Booking form page
     */
    @GetMapping("/book")
    public String showBookingForm(@RequestParam(required = false) Long serviceId, Model model) {
        List<Service> services = serviceService.getAllActiveServices();
        model.addAttribute("services", services);
        
        // Create new booking with empty customer
        Booking booking = new Booking();
        booking.setCustomer(new Customer());
        
        // Pre-select service if provided
        if (serviceId != null) {
            Optional<Service> serviceOpt = serviceService.getServiceById(serviceId);
            if (serviceOpt.isPresent() && serviceOpt.get().getIsActive()) {
                booking.setService(serviceOpt.get());
            }
        }
        
        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "Book Service - Dynamo Detailing");
        
        return "booking-form";
    }
    
    /**
     * Process booking form submission
     */
    @PostMapping("/book")
    public String processBooking(@Valid @ModelAttribute("booking") Booking booking, 
                                BindingResult bindingResult,
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        
        System.out.println("Processing booking submission...");
        System.out.println("Service ID: " + (booking.getService() != null ? booking.getService().getServiceId() : "null"));
        System.out.println("Customer Name: " + (booking.getCustomer() != null ? booking.getCustomer().getFirstName() : "null"));
        System.out.println("Booking Date: " + booking.getBookingDate());
        System.out.println("Booking Time: " + booking.getBookingTime());
        
        // Validate service selection
        if (booking.getService() == null || booking.getService().getServiceId() == null) {
            bindingResult.rejectValue("service", "error.service", "Please select a service");
            System.out.println("Service validation failed");
        } else {
            // Load the full service object
            Optional<Service> serviceOpt = serviceService.getServiceById(booking.getService().getServiceId());
            if (serviceOpt.isPresent() && serviceOpt.get().getIsActive()) {
                booking.setService(serviceOpt.get());
                booking.setTotalAmount(serviceOpt.get().getPrice());
                System.out.println("Service found: " + serviceOpt.get().getServiceName());
            } else {
                bindingResult.rejectValue("service", "error.service", "Selected service is not available");
                System.out.println("Service not found or inactive");
            }
        }
        
        // Log validation errors
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors found:");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("- " + error.getDefaultMessage());
            });
            
            List<Service> services = serviceService.getAllActiveServices();
            model.addAttribute("services", services);
            model.addAttribute("pageTitle", "Book Service - Dynamo Detailing");
            model.addAttribute("errorMessage", "Please correct the errors below and try again.");
            return "booking-form";
        }
        
        try {
            // Create the booking
            System.out.println("Creating booking...");
            Booking savedBooking = bookingService.createBooking(booking);
            System.out.println("Booking created with ID: " + savedBooking.getBookingId());
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Your booking has been submitted successfully! Booking ID: " + savedBooking.getBookingId() + 
                ". We will contact you shortly to confirm your appointment.");
            
            return "redirect:/booking-confirmation/" + savedBooking.getBookingId();
            
        } catch (Exception e) {
            System.out.println("Error creating booking: " + e.getMessage());
            e.printStackTrace();
            
            model.addAttribute("errorMessage", "There was an error processing your booking: " + e.getMessage());
            List<Service> services = serviceService.getAllActiveServices();
            model.addAttribute("services", services);
            model.addAttribute("pageTitle", "Book Service - Dynamo Detailing");
            return "booking-form";
        }
    }
    
    /**
     * Booking confirmation page
     */
    @GetMapping("/booking-confirmation/{bookingId}")
    public String bookingConfirmation(@PathVariable Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            model.addAttribute("booking", bookingOpt.get());
            model.addAttribute("pageTitle", "Booking Confirmation - Dynamo Detailing");
            return "booking-confirmation";
        } else {
            return "redirect:/";
        }
    }
    
    /**
     * Contact page
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact Us - Dynamo Detailing");
        return "contact";
    }
    
    /**
     * Get service price via AJAX
     */
    @GetMapping("/api/service/{serviceId}/price")
    @ResponseBody
    public Double getServicePrice(@PathVariable Long serviceId) {
        Optional<Service> serviceOpt = serviceService.getServiceById(serviceId);
        return serviceOpt.map(service -> service.getPrice().doubleValue()).orElse(0.0);
    }
}
