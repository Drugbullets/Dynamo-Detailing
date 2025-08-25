package com.dynamo.detailing.service;

import com.dynamo.detailing.entity.Customer;
import com.dynamo.detailing.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    /**
     * Get all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    /**
     * Get customer by ID
     */
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
    
    /**
     * Get customer by email
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    /**
     * Get customer by phone number
     */
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
    
    /**
     * Create a new customer or get existing one
     */
    public Customer createOrGetCustomer(Customer customer) {
        // Check if customer already exists by email
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            // Update existing customer info if needed
            Customer existing = existingCustomer.get();
            existing.setFirstName(customer.getFirstName());
            existing.setLastName(customer.getLastName());
            existing.setPhoneNumber(customer.getPhoneNumber());
            existing.setAddress(customer.getAddress());
            existing.setCity(customer.getCity());
            existing.setState(customer.getState());
            existing.setZipCode(customer.getZipCode());
            return customerRepository.save(existing);
        } else {
            return customerRepository.save(customer);
        }
    }
    
    /**
     * Update an existing customer
     */
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    /**
     * Delete a customer
     */
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
    
    /**
     * Search customers by name
     */
    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByNameContaining(name);
    }
    
    /**
     * Get customers by city
     */
    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCityIgnoreCase(city);
    }
    
    /**
     * Get customers by state
     */
    public List<Customer> getCustomersByState(String state) {
        return customerRepository.findByStateIgnoreCase(state);
    }
    
    /**
     * Check if email already exists
     */
    public boolean isEmailAlreadyExists(String email) {
        return customerRepository.existsByEmail(email);
    }
    
    /**
     * Check if phone number already exists
     */
    public boolean isPhoneNumberAlreadyExists(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
    
    /**
     * Get customers with bookings
     */
    public List<Customer> getCustomersWithBookings() {
        return customerRepository.findCustomersWithBookings();
    }
    
    /**
     * Get customers ordered by most recent registration
     */
    public List<Customer> getCustomersOrderedByRegistrationDate() {
        return customerRepository.findAllByOrderByCreatedDateDesc();
    }
    
    /**
     * Check if customer exists
     */
    public boolean customerExists(Long customerId) {
        return customerRepository.existsById(customerId);
    }
}
