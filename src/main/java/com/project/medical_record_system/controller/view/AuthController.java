package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.RoleName;
import com.project.medical_record_system.dto.DoctorRegisterDto;
import com.project.medical_record_system.dto.PatientRegisterDto;
import com.project.medical_record_system.service.SpecialtyService;
import com.project.medical_record_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SpecialtyService specialtyService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register/doctor")
    public String showDoctorRegisterForm(Model model) {
        model.addAttribute("doctorRegisterDto", new DoctorRegisterDto());
        model.addAttribute("specialties", specialtyService.getAllSpecialties());
        return "register-doctor";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(
            @Valid @ModelAttribute("doctorRegisterDto") DoctorRegisterDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties", specialtyService.getAllSpecialties());
            return "register-doctor";
        }

        try {
            this.userService.registerDoctor(dto);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("specialties", specialtyService.getAllSpecialties());
            return "register-doctor";
        }

        return "redirect:/login";
    }

    @GetMapping("/register/patient")
    public String showPatientRegisterForm(Model model) {
        model.addAttribute("patientRegisterDto", new PatientRegisterDto());
        return "register-patient";
    }

    @PostMapping("/register/patient")
    public String registerPatient(
            @Valid @ModelAttribute("patientRegisterDto") PatientRegisterDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register-patient";
        }

        try {
            userService.registerPatient(dto);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register-patient";
        }

        return "redirect:/login";
    }
}
