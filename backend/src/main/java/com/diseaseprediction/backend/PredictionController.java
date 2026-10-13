package com.diseaseprediction.backend;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PredictionController {


@PostMapping("/predict")
public Map<String, Object> predictDisease(
        @RequestBody PredictionRequest request) {

    List<String> symptoms = request.getSymptoms();

    if (symptoms == null || symptoms.isEmpty()) {
        return Map.of(
            "message", "Please select at least one symptom."
        );
    }

    Set<String> selected = new HashSet<>();

    for (String symptom : symptoms) {
        selected.add(symptom.trim().toLowerCase());
    }

    String possibleCondition;
    String prevention;
    String doctorAdvice;

    if (selected.contains("fever")
            && selected.contains("headache")
            && selected.contains("body pain")) {

        possibleCondition = "Flu-like illness";

        prevention = "Wash hands regularly, cover coughs and sneezes, "
                + "avoid close contact with sick people, and keep "
                + "recommended vaccinations up to date.";

        doctorAdvice = "Seek medical advice if symptoms are severe, "
                + "worsening, or persistent.";

    } else if (selected.contains("cough")
            && selected.contains("sore throat")
            && selected.contains("runny nose")) {

        possibleCondition = "Common-cold-like illness";

        prevention = "Wash hands regularly, avoid touching your face "
                + "with unwashed hands, cover coughs and sneezes, "
                + "and avoid sharing personal items when ill.";

        doctorAdvice = "Seek medical advice if symptoms worsen, "
                + "persist, or you develop concerning new symptoms.";

    } else if (selected.contains("headache")
            && selected.contains("fatigue")) {

        possibleCondition = "Headache and fatigue symptom pattern";

        prevention = "Maintain regular sleep, drink enough water, "
                + "eat balanced meals, and take regular breaks. "
                + "These steps may help some causes but cannot prevent "
                + "all conditions.";

        doctorAdvice = "Consult a healthcare professional if the "
                + "symptoms continue, recur frequently, or interfere "
                + "with daily activities.";

    } else if (selected.contains("cough")) {

        possibleCondition = "Cough-related symptom pattern";

        prevention = "Avoid smoke and other known irritants, wash "
                + "hands regularly, and cover coughs and sneezes. "
                + "Prevention depends on the cause.";

        doctorAdvice = "Get medical advice if the cough persists "
                + "or worsens.";

    } else if (selected.contains("fever")) {

        possibleCondition = "Fever-related symptom pattern";

        prevention = "Wash hands regularly, practise respiratory "
                + "hygiene, and follow recommended vaccination "
                + "guidance. Prevention depends on the cause of fever.";

        doctorAdvice = "Seek medical advice if the fever is persistent, "
                + "high, worsening, or accompanied by concerning symptoms.";

    } else {

        possibleCondition = "Cause cannot be determined from these symptoms";

        prevention = "Practise good hand hygiene, maintain healthy "
                + "daily habits, and follow appropriate vaccination "
                + "guidance.";

        doctorAdvice = "Consult a healthcare professional if symptoms "
                + "persist, worsen, or concern you.";
    }

    Map<String, Object> result = new LinkedHashMap<>();

    result.put("symptoms", symptoms);
    result.put("possibleCondition", possibleCondition);
    result.put("prevention", prevention);
    result.put("doctorAdvice", doctorAdvice);
    result.put(
        "disclaimer",
        "Educational demonstration only. These rules are not a "
        + "validated AI model and cannot diagnose disease."
    );

    return result;
}

public static class PredictionRequest {

    private List<String> symptoms;

    public PredictionRequest() {
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }
}


}
