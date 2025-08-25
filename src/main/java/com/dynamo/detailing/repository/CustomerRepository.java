package com.dynamo.detailing.repository;

import com.dynamo.detailing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    /**
     * Find customer by email
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Find customer by phone number
     */
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    
    /**
     * Find customers by name (first or last name containing search term)
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContaining(String name);
    
    /**
     * Find customers by city
     */
    List<Customer> findByCityIgnoreCase(String city);
    
    /**
     * Find customers by state
     */
    List<Customer> findByStateIgnoreCase(String state);
    
    /**
     * Check if email already exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if phone number already exists
     */
    boolean existsByPhoneNumber(String phoneNumber);
    
    /**
     * Find customers with bookings
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.bookings b")
    List<Customer> findCustomersWithBookings();
    
    /**
     * Find customers ordered by most recent registration
     */
    List<Customer> findAllByOrderByCreatedDateDesc();
}
