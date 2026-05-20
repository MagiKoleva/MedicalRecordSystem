package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Treatment;
import com.project.medical_record_system.service.TreatmentService;
import com.project.medical_record_system.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/treatments")
public class TreatmentViewController {

    private final TreatmentService treatmentService;
    private final VisitService visitService;

    @GetMapping
    public String getTreatments(Model model) {
        model.addAttribute("treatments", treatmentService.getAllTreatments());
        return "treatments";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("treatment", new Treatment());
        model.addAttribute("visits", visitService.getAllVisits());
        return "treatment-form";
    }

    @PostMapping("/create")
    public String createTreatment(
            @Valid @ModelAttribute("treatment") Treatment treatment,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getAllVisits());
            return "treatment-form";
        }

        treatmentService.createTreatment(treatment);
        return "redirect:/web/treatments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("treatment", treatmentService.getTreatment(id));
        model.addAttribute("visits", visitService.getAllVisits());
        return "treatment-form";
    }

    @PostMapping("/edit/{id}")
    public String updateTreatment(
            @PathVariable long id,
            @Valid @ModelAttribute("treatment") Treatment treatment,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getAllVisits());
            return "treatment-form";
        }

        treatmentService.updateTreatment(treatment, id);
        return "redirect:/web/treatments";
    }

    @GetMapping("/delete/{id}")
    public String deleteTreatment(@PathVariable long id) {
        treatmentService.deleteTreatment(id);
        return "redirect:/web/treatments";
    }
}
