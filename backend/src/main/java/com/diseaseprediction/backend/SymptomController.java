package com.diseaseprediction.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {


@GetMapping
public List<Map<String, String>> getSymptoms() {
    return List.of(
            Map.of("id", "1", "name", "Fever"),
            Map.of("id", "2", "name", "Headache"),
            Map.of("id", "3", "name", "Cough"),
            Map.of("id", "4", "name", "Fatigue"),
            Map.of("id", "5", "name", "Sore throat")
    );
}


}
