package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.service.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/specialties")
public class SpecialtyViewController {

    private final SpecialtyService specialtyService;

    @GetMapping
    public String getSpecialties(Model model) {
        model.addAttribute("specialties", specialtyService.getAllSpecialties());
        return "specialties";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("specialty", new Specialty());
        return "specialty-form";
    }

    @PostMapping("/create")
    public String createSpecialty(@Valid @ModelAttribute("specialty") Specialty specialty,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "specialty-form";
        }

        specialtyService.createSpecialty(specialty);
        return "redirect:/web/specialties";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("specialty", specialtyService.getSpecialty(id));
        return "specialty-form";
    }

    @PostMapping("/edit/{id}")
    public String updateSpecialty(@PathVariable long id,
                                  @Valid @ModelAttribute("specialty") Specialty specialty,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "specialty-form";
        }

        specialtyService.updateSpecialty(specialty, id);
        return "redirect:/web/specialties";
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecialty(@PathVariable long id) {
        specialtyService.deleteSpecialty(id);
        return "redirect:/web/specialties";
    }
}