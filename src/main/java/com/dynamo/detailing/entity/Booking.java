package com.dynamo.detailing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
    
    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date must be today or in the future")
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
    
    @NotNull(message = "Booking time is required")
    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;
    
    @NotBlank(message = "Location address is required")
    @Size(max = 255, message = "Address must be less than 255 characters")
    @Column(name = "location_address", nullable = false, length = 255)
    private String locationAddress;
    
    @NotBlank(message = "Location city is required")
    @Size(max = 50, message = "City must be less than 50 characters")
    @Column(name = "location_city", nullable = false, length = 50)
    private String locationCity;
    
    @NotBlank(message = "Location state is required")
    @Size(max = 50, message = "State must be less than 50 characters")
    @Column(name = "location_state", nullable = false, length = 50)
    private String locationState;
    
    @NotBlank(message = "Location zip code is required")
    @Size(max = 10, message = "Zip code must be less than 10 characters")
    @Column(name = "location_zip", nullable = false, length = 10)
    private String locationZip;
    
    @Size(max = 500, message = "Special instructions must be less than 500 characters")
    @Column(name = "special_instructions", length = 500)
    private String specialInstructions;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private BookingStatus status = BookingStatus.PENDING;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();
    
    // Enum for booking status
    public enum BookingStatus {
        PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    // Constructors
    public Booking() {}
    
    public Booking(Customer customer, Service service, LocalDate bookingDate, LocalTime bookingTime,
                   String locationAddress, String locationCity, String locationState, String locationZip,
                   BigDecimal totalAmount) {
        this.customer = customer;
        this.service = service;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.locationAddress = locationAddress;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.locationZip = locationZip;
        this.totalAmount = totalAmount;
    }
    
    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Service getService() {
        return service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public LocalTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public String getLocationAddress() {
        return locationAddress;
    }
    
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
    
    public String getLocationCity() {
        return locationCity;
    }
    
    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }
    
    public String getLocationState() {
        return locationState;
    }
    
    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }
    
    public String getLocationZip() {
        return locationZip;
    }
    
    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingStatus status) {
        this.status = status;
        this.updatedDate = LocalDateTime.now();
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public String getFullLocation() {
        return locationAddress + ", " + locationCity + ", " + locationState + " " + locationZip;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
