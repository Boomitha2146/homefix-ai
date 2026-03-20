package com.example.homefix_ai_backend.repository;

import com.example.homefix_ai_backend.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    // "Specialty" and "Available" must exactly match field names in Worker.java
    List<Worker> findBySpecialtyAndAvailable(String specialty, Boolean available);
}