package com.project.medical_record_system.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/web/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/web/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor-dashboard";
    }

    @GetMapping("/web/patient/dashboard")
    public String patientDashboard() {
        return "patient-dashboard";
    }
}
