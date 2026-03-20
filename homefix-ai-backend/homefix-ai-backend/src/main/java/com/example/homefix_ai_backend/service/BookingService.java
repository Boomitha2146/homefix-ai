package com.example.homefix_ai_backend.service;

import com.example.homefix_ai_backend.entity.Booking;
import com.example.homefix_ai_backend.entity.Worker;
import com.example.homefix_ai_backend.repository.BookingRepository;
import com.example.homefix_ai_backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WorkerRepository workerRepository;

    // ── Fetch all (paginated) ──────────────────────────
    public Page<Booking> getAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    // ── Fetch by numeric ID ────────────────────────────
    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    // ── Fetch by booking code ──────────────────────────
    public Optional<Booking> getByCode(String code) {
        return bookingRepository.findByBookingCode(code);
    }

    // ── Create + auto-assign worker ────────────────────
    public Booking createBooking(Booking booking) {

        // 1. Generate unique booking code
        long count = bookingRepository.count() + 1;
        booking.setBookingCode(String.format("BK-%03d", count));

        // 2. Set default status
        if (booking.getStatus() == null || booking.getStatus().isBlank()) {
            booking.setStatus("Assigned");
        }

        // 3. Auto-assign best available worker by serviceType + rating
// Change this line in createBooking():
        List<Worker> workers = workerRepository.findBySpecialtyAndAvailable(
                booking.getServiceType(), true);   // true auto-boxes to Boolean — fine
        if (!workers.isEmpty()) {
            // Pick highest rated
            Worker best = workers.stream()
                    .max((a, b) -> Double.compare(
                            a.getRating() != null ? a.getRating() : 0.0,
                            b.getRating() != null ? b.getRating() : 0.0))
                    .orElse(workers.get(0));
            booking.setAssignedWorker(best);
        }

        // 4. Save to MySQL
        return bookingRepository.save(booking);
    }

    // ── Update status ──────────────────────────────────
    public Booking updateStatus(Long id, String newStatus) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    // ── Dashboard stats ────────────────────────────────
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBookings", bookingRepository.count());
        stats.put("activeJobs",    bookingRepository.countActiveJobs());
        stats.put("totalRevenue",  bookingRepository.totalRevenue());
        return stats;
    }
}