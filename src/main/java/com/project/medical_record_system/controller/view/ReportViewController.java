package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/reports")
public class ReportViewController {

    private final ReportService reportService;

    @GetMapping
    public String reports(Model model) {
        model.addAttribute("mostCommonDiagnosis", reportService.getMostCommonDiagnosis());
        model.addAttribute("totalPaidByPatients", reportService.getTotalPaidByPatients());
        model.addAttribute("paidByDoctor", reportService.getPatientPaidVisitsSumByDoctor());
        model.addAttribute("visitsByDoctor", reportService.getVisitsCountByDoctor());
        model.addAttribute("monthWithMostSickLeaves", reportService.getMonthWithMostSickLeaves());
        model.addAttribute("doctorWithMostSickLeaves", reportService.getDoctorWithMostSickLeaves());

        return "reports";
    }
}