package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Diagnosis;
import com.project.medical_record_system.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/diagnoses")
public class DiagnosisViewController {

    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getDiagnoses(Model model) {
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "diagnoses";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("diagnosis", new Diagnosis());
        return "diagnosis-form";
    }

    @PostMapping("/create")
    public String createDiagnosis(@Valid @ModelAttribute("diagnosis") Diagnosis diagnosis,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "diagnosis-form";
        }

        diagnosisService.createDiagnosis(diagnosis);
        return "redirect:/web/diagnoses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("diagnosis", diagnosisService.getDiagnosis(id));
        return "diagnosis-form";
    }

    @PostMapping("/edit/{id}")
    public String updateDiagnosis(@PathVariable long id,
                                  @Valid @ModelAttribute("diagnosis") Diagnosis diagnosis,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "diagnosis-form";
        }

        diagnosisService.updateDiagnosis(diagnosis, id);
        return "redirect:/web/diagnoses";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/web/diagnoses";
    }
}
