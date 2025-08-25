package com.dynamo.detailing.repository;

import com.dynamo.detailing.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    /**
     * Find admin by username
     */
    Optional<Admin> findByUsername(String username);
    
    /**
     * Find admin by email
     */
    Optional<Admin> findByEmail(String email);
    
    /**
     * Find all active admins
     */
    List<Admin> findByIsActiveTrue();
    
    /**
     * Find admins by role
     */
    List<Admin> findByRoleAndIsActiveTrue(String role);
    
    /**
     * Check if username already exists
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if email already exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Find active admin by username (for authentication)
     */
    Optional<Admin> findByUsernameAndIsActiveTrue(String username);
}
