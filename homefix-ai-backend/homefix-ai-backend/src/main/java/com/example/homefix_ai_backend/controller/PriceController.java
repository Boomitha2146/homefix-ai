package com.example.homefix_ai_backend.controller;

import com.example.homefix_ai_backend.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/price")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PriceController {

    @Autowired
    private PriceService priceService;

    // POST /api/price/predict
    // Body: { serviceType, duration, urgency, parts, city }
    @PostMapping("/predict")
    public ResponseEntity<Map<String, Object>> predict(@RequestBody Map<String, String> body) {
        Map<String, Object> result = priceService.predict(
                body.get("serviceType"),
                body.get("duration"),
                body.get("urgency"),
                body.get("parts"),
                body.get("city")
        );
        return ResponseEntity.ok(result);
    }
}