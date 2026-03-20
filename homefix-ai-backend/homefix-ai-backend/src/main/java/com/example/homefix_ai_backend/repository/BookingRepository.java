package com.example.homefix_ai_backend.repository;

import com.example.homefix_ai_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingCode(String bookingCode);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'In Progress' OR b.status = 'Assigned' OR b.status = 'En Route'")
    long countActiveJobs();

    @Query("SELECT COALESCE(SUM(b.estimatedPrice), 0) FROM Booking b WHERE b.status = 'Completed'")
    double totalRevenue();
}