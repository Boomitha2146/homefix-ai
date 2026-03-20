package com.example.homefix_ai_backend.controller;

import com.example.homefix_ai_backend.entity.Worker;
import com.example.homefix_ai_backend.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workers")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    // GET /api/workers
    @GetMapping
    public List<Worker> getAll() {
        return workerService.getAll();
    }

    // GET /api/workers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getById(@PathVariable Long id) {
        return workerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/workers
    @PostMapping
    public ResponseEntity<Worker> create(@Valid @RequestBody Worker worker) {
        return ResponseEntity.ok(workerService.create(worker));
    }

    // PATCH /api/workers/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Worker> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(workerService.updateStatus(id, body.get("status")));
    }

    // DELETE /api/workers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}