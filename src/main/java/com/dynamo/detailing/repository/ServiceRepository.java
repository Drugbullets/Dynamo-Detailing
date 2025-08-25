package com.dynamo.detailing.repository;

import com.dynamo.detailing.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    
    /**
     * Find all active services
     */
    List<Service> findByIsActiveTrue();
    
    /**
     * Find services by name containing the search term (case-insensitive)
     */
    List<Service> findByServiceNameContainingIgnoreCaseAndIsActiveTrue(String serviceName);
    
    /**
     * Find services within a price range
     */
    @Query("SELECT s FROM Service s WHERE s.price BETWEEN :minPrice AND :maxPrice AND s.isActive = true ORDER BY s.price ASC")
    List<Service> findByPriceRangeAndIsActiveTrue(Double minPrice, Double maxPrice);
    
    /**
     * Find services by duration range
     */
    @Query("SELECT s FROM Service s WHERE s.durationMinutes BETWEEN :minDuration AND :maxDuration AND s.isActive = true ORDER BY s.durationMinutes ASC")
    List<Service> findByDurationRangeAndIsActiveTrue(Integer minDuration, Integer maxDuration);
    
    /**
     * Find services ordered by price ascending
     */
    List<Service> findByIsActiveTrueOrderByPriceAsc();
    
    /**
     * Find services ordered by popularity (most booked first)
     */
    @Query("SELECT s FROM Service s LEFT JOIN s.bookings b WHERE s.isActive = true GROUP BY s ORDER BY COUNT(b) DESC")
    List<Service> findMostPopularServices();
}
