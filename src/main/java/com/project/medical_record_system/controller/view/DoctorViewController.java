package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.service.DoctorService;
import com.project.medical_record_system.service.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/doctors")
public class DoctorViewController {

    private final DoctorService doctorService;
    private final SpecialtyService specialtyService;

    @GetMapping
    public String getDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/create")
    public String showCreateDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("specialties", specialtyService.getAllSpecialties());
        return "doctor-form";
    }

//    @PostMapping("/create")
//    public String createDoctor(
//            @Valid @ModelAttribute("doctor") Doctor doctor,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("specialties", specialtyService.getAllSpecialties());
//            return "doctor-form";
//        }
//
//        doctorService.createDoctor(doctor);
//        return "redirect:/web/doctors";
//    }

    @GetMapping("/edit/{id}")
    public String showEditDoctorForm(@PathVariable long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctor(id));
        model.addAttribute("specialties", specialtyService.getAllSpecialties());
        return "doctor-form";
    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(
            @PathVariable long id,
            @Valid @ModelAttribute("doctor") Doctor doctor,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties", specialtyService.getAllSpecialties());
            return "doctor-form";
        }

        doctorService.updateDoctor(doctor, id);
        return "redirect:/web/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/web/doctors";
    }
}