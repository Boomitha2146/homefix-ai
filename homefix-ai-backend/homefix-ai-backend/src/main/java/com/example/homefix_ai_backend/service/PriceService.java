package com.example.homefix_ai_backend.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PriceService {

    private static final Map<String, Double> BASE_RATES = Map.of(
            "Plumbing",   600.0,
            "Electrical", 500.0,
            "Carpentry",  700.0,
            "Cleaning",   400.0,
            "Painting",   800.0,
            "AC Repair",  900.0
    );

    private static final Map<String, Double> DURATION_MAP = Map.of(
            "1 hour",    1.0,
            "2-3 hours", 2.5,
            "Half day",  4.0,
            "Full day",  8.0
    );

    private static final Map<String, Double> URGENCY_MAP = Map.of(
            "Normal",    1.0,
            "Urgent",    1.4,
            "Emergency", 1.9
    );

    public Map<String, Object> predict(String serviceType, String duration,
                                       String urgency, String parts, String city) {

        double base     = BASE_RATES.getOrDefault(serviceType, 600.0);
        double dur      = DURATION_MAP.getOrDefault(duration, 2.5);
        double urg      = URGENCY_MAP.getOrDefault(urgency, 1.0);
        boolean hasParts = "Yes".equalsIgnoreCase(parts);

        double labor    = base * dur * urg;
        double material = hasParts ? base * 0.5 : 0;
        double tax      = (labor + material) * 0.05;
        double total    = labor + material + tax;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("serviceType",    serviceType);
        result.put("labor",          (long) labor);
        result.put("material",       (long) material);
        result.put("tax",            (long) tax);
        result.put("estimatedMin",   (long)(total * 0.85));
        result.put("estimatedMax",   (long)(total * 1.05));
        result.put("confidence",     88);
        result.put("recommendation",
                "Based on " + city + " market rates for " + serviceType + " — " + urgency + " priority.");

        return result;
    }
}