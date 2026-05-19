package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/visits")
public class VisitViewController {

    private final VisitService visitService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getVisits(Model model) {
        model.addAttribute("visits", visitService.getAllVisits());
        return "visits";
    }

    @GetMapping("/create")
    public String showCreateVisitForm(Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "visit-form";
    }

    @PostMapping("/create")
    public String createVisit(
            @Valid @ModelAttribute("visit") Visit visit,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
            return "visit-form";
        }

        visitService.createVisit(visit);
        return "redirect:/web/visits";
    }

    @GetMapping("/edit/{id}")
    public String showEditVisitForm(@PathVariable long id, Model model) {
        model.addAttribute("visit", visitService.getVisit(id));
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "visit-form";
    }

    @PostMapping("/edit/{id}")
    public String updateVisit(
            @PathVariable long id,
            @Valid @ModelAttribute("visit") Visit visit,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
            return "visit-form";
        }

        visitService.updateVisit(visit, id);
        return "redirect:/web/visits";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable long id) {
        visitService.deleteVisit(id);
        return "redirect:/web/visits";
    }
}