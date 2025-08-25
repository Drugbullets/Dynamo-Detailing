package com.dynamo.detailing.service;

import com.dynamo.detailing.entity.Admin;
import com.dynamo.detailing.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + username));
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
        
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPasswordHash())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(!admin.getIsActive())
                .credentialsExpired(false)
                .disabled(!admin.getIsActive())
                .build();
    }
}
