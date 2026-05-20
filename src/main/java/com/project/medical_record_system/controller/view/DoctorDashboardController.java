package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.data.entity.Treatment;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorDashboardController {

    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final DoctorRepository doctorRepository;
    private final VisitService visitService;
    private final TreatmentService treatmentService;
    private final SickLeaveService sickLeaveService;

    private Visit getDoctorVisitOrThrow(Long visitId, Authentication authentication) {
        Doctor doctor = doctorRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found."));

        Visit visit = visitService.getVisit(visitId);

        if (visit.getDoctor().getUserId() != (doctor.getUserId())) {
            throw new IllegalArgumentException("You can manage only your own visits.");
        }

        return visit;
    }

    @GetMapping("/web/doctor/my-visits")
    public String myDoctorVisits(Authentication authentication, Model model) {
        String username = authentication.getName();

        Doctor doctor = doctorRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found."));

        List<Visit> visits = visitService.getVisitsByDoctorId(doctor.getUserId());

        model.addAttribute("visits", visits);

        return "doctor-my-visits";
    }

    @GetMapping("/web/doctor/my-visits/create")
    public String showCreateMyVisitForm(Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());

        return "doctor-visit-form";
    }

    @PostMapping("/web/doctor/my-visits/create")
    public String createMyVisit(
            @Valid @ModelAttribute("visit") Visit visit,
            BindingResult bindingResult,
            Model model,
            Authentication authentication
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
            return "doctor-visit-form";
        }

        Doctor doctor = doctorRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found."));

        visit.setDoctor(doctor);

        visitService.createVisit(visit);

        return "redirect:/web/doctor/my-visits";
    }

    @GetMapping("/web/doctor/my-visits/{visitId}/treatment/create")
    public String showCreateTreatmentForm(
            @PathVariable Long visitId,
            Authentication authentication,
            Model model
    ) {
        Visit visit = getDoctorVisitOrThrow(visitId, authentication);

        Treatment treatment = new Treatment();
        treatment.setVisit(visit);

        model.addAttribute("treatment", treatment);
        return "doctor-treatment-form";
    }

    @PostMapping("/web/doctor/my-visits/{visitId}/treatment/create")
    public String createTreatment(
            @PathVariable Long visitId,
            @Valid @ModelAttribute("treatment") Treatment treatment,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        Visit visit = getDoctorVisitOrThrow(visitId, authentication);

        if (bindingResult.hasErrors()) {
            return "doctor-treatment-form";
        }

        treatment.setVisit(visit);
        treatmentService.createTreatment(treatment);

        return "redirect:/web/doctor/my-visits";
    }

    @GetMapping("/web/doctor/my-visits/{visitId}/sick-leave/create")
    public String showCreateSickLeaveForm(
            @PathVariable Long visitId,
            Authentication authentication,
            Model model
    ) {
        Visit visit = getDoctorVisitOrThrow(visitId, authentication);

        SickLeave sickLeave = new SickLeave();
        sickLeave.setVisit(visit);

        model.addAttribute("sickLeave", sickLeave);
        return "doctor-sick-leave-form";
    }

    @PostMapping("/web/doctor/my-visits/{visitId}/sick-leave/create")
    public String createSickLeave(
            @PathVariable Long visitId,
            @Valid @ModelAttribute("sickLeave") SickLeave sickLeave,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        Visit visit = getDoctorVisitOrThrow(visitId, authentication);

        if (bindingResult.hasErrors()) {
            return "doctor-sick-leave-form";
        }

        sickLeave.setVisit(visit);
        this.sickLeaveService.createSickLeave(sickLeave);

        return "redirect:/web/doctor/my-visits";
    }
}
