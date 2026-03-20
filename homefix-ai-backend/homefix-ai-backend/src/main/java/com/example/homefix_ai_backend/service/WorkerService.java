package com.example.homefix_ai_backend.service;

import com.example.homefix_ai_backend.entity.Worker;
import com.example.homefix_ai_backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepo;

    public List<Worker> getAll() {
        return workerRepo.findAll();
    }

    public Worker create(Worker worker) {
        return workerRepo.save(worker);
    }

    public Optional<Worker> getById(Long id) {
        return workerRepo.findById(id);
    }

    public Worker updateStatus(Long id, String status) {
        Worker worker = workerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found: " + id));
        worker.setStatus(status);
        return workerRepo.save(worker);
    }

    public void delete(Long id) {
        workerRepo.deleteById(id);
    }
}