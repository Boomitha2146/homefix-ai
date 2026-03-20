package com.example.homefix_ai_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)   // ← ignore extra fields from frontend
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String bookingCode;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone is required")
    private String phone;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String priority = "Normal";
    private String status   = "Pending";

    @JsonFormat(pattern = "yyyy-MM-dd")   // ← correctly parses "2025-02-20" from frontend
    private LocalDate scheduledDate;

    private String scheduledTime;
    private Double estimatedPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Worker assignedWorker;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}