package com.example.homefix_ai_backend.config;

import com.example.homefix_ai_backend.entity.Worker;
import com.example.homefix_ai_backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private WorkerRepository workerRepo;

    @Override
    public void run(String... args) {
        // Only seed if the table is empty
        if (workerRepo.count() == 0) {
            seed("Ravi Kumar",    "Plumbing",   "+91 98001 23456", "Chennai",   8,  4.9, 128);
            seed("Karan Patel",   "Electrical", "+91 90001 34567", "Mumbai",    5,  4.7, 96);
            seed("Suresh Nair",   "Carpentry",  "+91 88001 45678", "Kochi",     12, 4.8, 203);
            seed("Manoj Das",     "Plumbing",   "+91 77001 56789", "Kolkata",   4,  4.6, 74);
            seed("Arun Verma",    "Electrical", "+91 66001 67890", "Delhi",     3,  4.5, 55);
            seed("Pradeep Singh", "Cleaning",   "+91 55001 78901", "Bengaluru", 10, 4.9, 310);

            System.out.println("✅ HomeFix: Sample workers seeded successfully!");
        }
    }

    private void seed(String name, String specialty, String phone,
                      String city, int experience, double rating, int jobsDone) {
        Worker w = new Worker();
        w.setName(name);
        w.setSpecialty(specialty);
        w.setPhone(phone);
        w.setCity(city);
        w.setExperience(experience);
        w.setRating(rating);
        w.setJobsCompleted(jobsDone);
        w.setStatus("Available");
        workerRepo.save(w);
    }
}