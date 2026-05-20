package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.service.DiagnosisService;
import com.project.medical_record_system.service.DoctorService;
import com.project.medical_record_system.service.PatientService;
import com.project.medical_record_system.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/patients")
public class PatientViewController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ReportService reportService;
    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "patients";
    }

    @GetMapping("/create")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "patient-form";
    }

//    @PostMapping("/create")
//    public String createPatient(
//            @Valid @ModelAttribute("patient") Patient patient,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("doctors", doctorService.getAllDoctors());
//            return "patient-form";
//        }
//
//        patientService.createPatient(patient);
//        return "redirect:/web/patients";
//    }

    @GetMapping("/edit/{id}")
    public String showEditPatientForm(@PathVariable long id, Model model) {
        model.addAttribute("patient", patientService.getPatient(id));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "patient-form";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(
            @PathVariable long id,
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "patient-form";
        }

        patientService.updatePatient(patient, id);
        return "redirect:/web/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return "redirect:/web/patients";
    }

    @GetMapping("/by-diagnosis")
    public String getPatientsByDiagnosis(
            @RequestParam Long diagnosisId,
            Model model
    ) {
        model.addAttribute("patients", reportService.getPatientsByDiagnosis(diagnosisId));
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "patients";
    }

    @GetMapping("/by-general-practitioner")
    public String getPatientsByGeneralPractitioner(
            @RequestParam Long doctorId,
            Model model
    ) {
        model.addAttribute("patients", this.reportService.getPatientsByGeneralPractitioner(doctorId));
        model.addAttribute("doctors", this.doctorService.getAllDoctors());
        model.addAttribute("diagnoses", this.diagnosisService.getAllDiagnoses());
        return "patients";
    }
}