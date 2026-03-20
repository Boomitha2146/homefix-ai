package com.example.homefix_ai_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workers")
@Data
@NoArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;
    private String phone;
    private String email;

    private Double rating;
    private Integer jobsCompleted;
    private Boolean available = true;
    private String city;

    private Integer experience;   // ← fixes setExperience()
    private String status;        // ← fixes setStatus("Available")
}