package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.service.SickLeaveService;
import com.project.medical_record_system.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/sick-leaves")
public class SickLeaveViewController {

    private final SickLeaveService sickLeaveService;
    private final VisitService visitService;

    @GetMapping
    public String getSickLeaves(Model model) {
        model.addAttribute("sickLeaves", sickLeaveService.getAllSickLeaves());
        return "sick-leaves";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("sickLeave", new SickLeave());
        model.addAttribute("visits", visitService.getAllVisits());
        return "sick-leave-form";
    }

    @PostMapping("/create")
    public String createSickLeave(
            @Valid @ModelAttribute("sickLeave") SickLeave sickLeave,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getAllVisits());
            return "sick-leave-form";
        }

        this.sickLeaveService.createSickLeave(sickLeave);
        return "redirect:/web/sick-leaves";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("sickLeave", sickLeaveService.getSickLeave(id));
        model.addAttribute("visits", visitService.getAllVisits());
        return "sick-leave-form";
    }

    @PostMapping("/edit/{id}")
    public String updateSickLeave(
            @PathVariable long id,
            @Valid @ModelAttribute("sickLeave") SickLeave sickLeave,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getAllVisits());
            return "sick-leave-form";
        }

        sickLeaveService.updateSickLeave(sickLeave, id);
        return "redirect:/web/sick-leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable long id) {
        sickLeaveService.deleteSickLeave(id);
        return "redirect:/web/sick-leaves";
    }
}
