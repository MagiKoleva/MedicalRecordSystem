package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorDashboardController {

    private final DoctorRepository doctorRepository;
    private final VisitService visitService;

    @GetMapping("/web/doctor/my-visits")
    public String myDoctorVisits(Authentication authentication, Model model) {
        String username = authentication.getName();

        Doctor doctor = doctorRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found."));

        List<Visit> visits = visitService.getVisitsByDoctorId(doctor.getUserId());

        model.addAttribute("visits", visits);

        return "doctor-my-visits";
    }
}
