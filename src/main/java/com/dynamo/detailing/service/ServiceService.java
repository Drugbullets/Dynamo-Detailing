package com.dynamo.detailing.service;

import com.dynamo.detailing.entity.Service;
import com.dynamo.detailing.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceService {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    /**
     * Get all active services
     */
    public List<Service> getAllActiveServices() {
        return serviceRepository.findByIsActiveTrue();
    }
    
    /**
     * Get all services (including inactive)
     */
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    
    /**
     * Get service by ID
     */
    public Optional<Service> getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId);
    }
    
    /**
     * Create a new service
     */
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }
    
    /**
     * Update an existing service
     */
    public Service updateService(Service service) {
        return serviceRepository.save(service);
    }
    
    /**
     * Delete a service (soft delete by setting isActive to false)
     */
    public void deleteService(Long serviceId) {
        Optional<Service> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isPresent()) {
            Service service = serviceOpt.get();
            service.setIsActive(false);
            serviceRepository.save(service);
        }
    }
    
    /**
     * Search services by name
     */
    public List<Service> searchServicesByName(String serviceName) {
        return serviceRepository.findByServiceNameContainingIgnoreCaseAndIsActiveTrue(serviceName);
    }
    
    /**
     * Get services by price range
     */
    public List<Service> getServicesByPriceRange(Double minPrice, Double maxPrice) {
        return serviceRepository.findByPriceRangeAndIsActiveTrue(minPrice, maxPrice);
    }
    
    /**
     * Get services by duration range
     */
    public List<Service> getServicesByDurationRange(Integer minDuration, Integer maxDuration) {
        return serviceRepository.findByDurationRangeAndIsActiveTrue(minDuration, maxDuration);
    }
    
    /**
     * Get services ordered by price
     */
    public List<Service> getServicesOrderedByPrice() {
        return serviceRepository.findByIsActiveTrueOrderByPriceAsc();
    }
    
    /**
     * Get most popular services
     */
    public List<Service> getMostPopularServices() {
        return serviceRepository.findMostPopularServices();
    }
    
    /**
     * Check if service exists and is active
     */
    public boolean isServiceActiveAndExists(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .map(Service::getIsActive)
                .orElse(false);
    }
}
