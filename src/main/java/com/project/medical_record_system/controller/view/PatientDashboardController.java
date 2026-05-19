package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientDashboardController {

    private final PatientRepository patientRepository;
    private final VisitService visitService;

    @GetMapping("/web/patient/my-visits")
    public String myPatientVisits(Authentication authentication, Model model) {
        String username = authentication.getName();

        Patient patient = this.patientRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Patient profile not found."));

        List<Visit> visits = this.visitService.getVisitsByPatientId(patient.getUserId());

        model.addAttribute("visits", visits);

        return "patient-my-visits";
    }
}
