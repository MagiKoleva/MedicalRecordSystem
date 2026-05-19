package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.service.DoctorService;
import com.project.medical_record_system.service.PatientService;
import com.project.medical_record_system.service.ReportService;
import com.project.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final VisitService visitService;
    private final ReportService reportService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/web/doctors")
    public String doctors(Model model) {
        model.addAttribute("doctors", this.doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/web/patients")
    public String patients(Model model) {
        model.addAttribute("patients", this.patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/web/visits")
    public String visits(Model model) {
        model.addAttribute("visits", this.visitService.getAllVisits());
        return "visits";
    }

    @GetMapping("/web/reports")
    public String reports(Model model) {
        model.addAttribute("mostCommonDiagnosis", this.reportService.getMostCommonDiagnosis());
        model.addAttribute("totalPaidByPatients", this.reportService.getTotalPaidByPatients());
        model.addAttribute("paidByDoctor", this.reportService.getPatientPaidVisitsSumByDoctor());
        model.addAttribute("visitsByDoctor", this.reportService.getVisitsCountByDoctor());
        model.addAttribute("monthWithMostSickLeaves", this.reportService.getMonthWithMostSickLeaves());
        model.addAttribute("doctorWithMostSickLeaves", this.reportService.getDoctorWithMostSickLeaves());

        return "reports";
    }
}
