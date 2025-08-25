package com.dynamo.detailing.service;

import com.dynamo.detailing.entity.Booking;
import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.entity.Service;
import com.dynamo.detailing.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ServiceService serviceService;
    
    /**
     * Get all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    /**
     * Get booking by ID
     */
    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }
    
    /**
     * Create a new booking
     */
    public Booking createBooking(Booking booking) {
        // Ensure customer exists or create new one
        Customer customer = customerService.createOrGetCustomer(booking.getCustomer());
        booking.setCustomer(customer);
        
        // Set total amount based on service price
        if (booking.getTotalAmount() == null && booking.getService() != null) {
            booking.setTotalAmount(booking.getService().getPrice());
        }
        
        return bookingRepository.save(booking);
    }
    
    /**
     * Update a booking
     */
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    /**
     * Delete a booking
     */
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
    
    /**
     * Update booking status
     */
    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    /**
     * Get bookings by customer
     */
    public List<Booking> getBookingsByCustomer(Customer customer) {
        return bookingRepository.findByCustomer(customer);
    }
    
    /**
     * Get bookings by customer ID
     */
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
    
    /**
     * Get bookings by service
     */
    public List<Booking> getBookingsByService(Service service) {
        return bookingRepository.findByService(service);
    }
    
    /**
     * Get bookings by service ID
     */
    public List<Booking> getBookingsByServiceId(Long serviceId) {
        return bookingRepository.findByServiceId(serviceId);
    }
    
    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }
    
    /**
     * Get bookings by date
     */
    public List<Booking> getBookingsByDate(LocalDate bookingDate) {
        return bookingRepository.findByBookingDate(bookingDate);
    }
    
    /**
     * Get bookings between dates
     */
    public List<Booking> getBookingsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findByBookingDateBetween(startDate, endDate);
    }
    
    /**
     * Get upcoming bookings
     */
    public List<Booking> getUpcomingBookings() {
        return bookingRepository.findUpcomingBookings();
    }
    
    /**
     * Get past bookings
     */
    public List<Booking> getPastBookings() {
        return bookingRepository.findPastBookings();
    }
    
    /**
     * Get today's bookings
     */
    public List<Booking> getTodaysBookings() {
        return bookingRepository.findTodaysBookings();
    }
    
    /**
     * Get bookings by city
     */
    public List<Booking> getBookingsByCity(String city) {
        return bookingRepository.findByLocationCityIgnoreCase(city);
    }
    
    /**
     * Get bookings by state
     */
    public List<Booking> getBookingsByState(String state) {
        return bookingRepository.findByLocationStateIgnoreCase(state);
    }
    
    /**
     * Get bookings ordered by most recent
     */
    public List<Booking> getBookingsOrderedByDate() {
        return bookingRepository.findAllByOrderByCreatedDateDesc();
    }
    
    /**
     * Get bookings that need confirmation
     */
    public List<Booking> getBookingsNeedingConfirmation() {
        return bookingRepository.findBookingsNeedingConfirmation();
    }
    
    /**
     * Count bookings by status
     */
    public Long countBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }
    
    /**
     * Get bookings by date range and status
     */
    public List<Booking> getBookingsByDateRangeAndStatus(LocalDate startDate, LocalDate endDate, Booking.BookingStatus status) {
        return bookingRepository.findByDateRangeAndStatus(startDate, endDate, status);
    }
    
    /**
     * Get booking statistics for a date range
     */
    public Map<String, Object> getBookingStatistics(LocalDate startDate, LocalDate endDate) {
        List<Object[]> stats = bookingRepository.getBookingStatistics(startDate, endDate);
        Map<String, Object> result = new HashMap<>();
        
        for (Object[] stat : stats) {
            String status = stat[0].toString();
            Long count = (Long) stat[1];
            Double totalAmount = stat[2] != null ? ((Number) stat[2]).doubleValue() : 0.0;
            
            Map<String, Object> statusStats = new HashMap<>();
            statusStats.put("count", count);
            statusStats.put("totalAmount", totalAmount);
            result.put(status, statusStats);
        }
        
        return result;
    }
    
    /**
     * Get most popular services
     */
    public List<Object[]> getMostPopularServices() {
        return bookingRepository.findMostPopularServices();
    }
    
    /**
     * Get dashboard statistics
     */
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Count bookings by status
        stats.put("pending", countBookingsByStatus(Booking.BookingStatus.PENDING));
        stats.put("confirmed", countBookingsByStatus(Booking.BookingStatus.CONFIRMED));
        stats.put("inProgress", countBookingsByStatus(Booking.BookingStatus.IN_PROGRESS));
        stats.put("completed", countBookingsByStatus(Booking.BookingStatus.COMPLETED));
        stats.put("cancelled", countBookingsByStatus(Booking.BookingStatus.CANCELLED));
        
        // Today's bookings
        stats.put("todayBookings", getTodaysBookings().size());
        
        // Upcoming bookings
        stats.put("upcomingBookings", getUpcomingBookings().size());
        
        // Bookings needing confirmation
        stats.put("needingConfirmation", getBookingsNeedingConfirmation().size());
        
        return stats;
    }
    
    /**
     * Check if booking exists
     */
    public boolean bookingExists(Long bookingId) {
        return bookingRepository.existsById(bookingId);
    }
}
