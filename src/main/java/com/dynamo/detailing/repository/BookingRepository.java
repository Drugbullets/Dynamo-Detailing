package com.dynamo.detailing.repository;

import com.dynamo.detailing.entity.Booking;
import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    /**
     * Find bookings by customer
     */
    List<Booking> findByCustomer(Customer customer);
    
    /**
     * Find bookings by service
     */
    List<Booking> findByService(Service service);
    
    /**
     * Find bookings by status
     */
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    /**
     * Find bookings by date
     */
    List<Booking> findByBookingDate(LocalDate bookingDate);
    
    /**
     * Find bookings between dates
     */
    List<Booking> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find upcoming bookings (future dates)
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate >= CURRENT_DATE ORDER BY b.bookingDate ASC, b.bookingTime ASC")
    List<Booking> findUpcomingBookings();
    
    /**
     * Find past bookings (historical dates)
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate < CURRENT_DATE ORDER BY b.bookingDate DESC, b.bookingTime DESC")
    List<Booking> findPastBookings();
    
    /**
     * Find today's bookings
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate = CURRENT_DATE ORDER BY b.bookingTime ASC")
    List<Booking> findTodaysBookings();
    
    /**
     * Find bookings by customer ID
     */
    @Query("SELECT b FROM Booking b WHERE b.customer.customerId = :customerId ORDER BY b.bookingDate DESC")
    List<Booking> findByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Find bookings by service ID
     */
    @Query("SELECT b FROM Booking b WHERE b.service.serviceId = :serviceId ORDER BY b.bookingDate DESC")
    List<Booking> findByServiceId(@Param("serviceId") Long serviceId);
    
    /**
     * Find bookings by city
     */
    List<Booking> findByLocationCityIgnoreCase(String city);
    
    /**
     * Find bookings by state
     */
    List<Booking> findByLocationStateIgnoreCase(String state);
    
    /**
     * Find bookings ordered by most recent
     */
    List<Booking> findAllByOrderByCreatedDateDesc();
    
    /**
     * Find bookings that need confirmation (pending status and upcoming)
     */
    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING' AND b.bookingDate >= CURRENT_DATE ORDER BY b.bookingDate ASC")
    List<Booking> findBookingsNeedingConfirmation();
    
    /**
     * Count bookings by status
     */
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    Long countByStatus(@Param("status") Booking.BookingStatus status);
    
    /**
     * Find bookings by date range and status
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate AND b.status = :status ORDER BY b.bookingDate ASC")
    List<Booking> findByDateRangeAndStatus(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate, 
                                          @Param("status") Booking.BookingStatus status);
    
    /**
     * Get booking statistics for date range
     */
    @Query("SELECT b.status, COUNT(b), SUM(b.totalAmount) FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate GROUP BY b.status")
    List<Object[]> getBookingStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * Find most popular services (by booking count)
     */
    @Query("SELECT s.serviceName, COUNT(b) FROM Booking b JOIN b.service s GROUP BY s.serviceName ORDER BY COUNT(b) DESC")
    List<Object[]> findMostPopularServices();
}
