package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.dto.*;
import com.project.medical_record_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportApiController {

    private final ReportService reportService;

    @GetMapping("/patients/by-diagnosis/{diagnosisId}")
    public ResponseEntity<List<Patient>> getPatientsByDiagnosis(@PathVariable long diagnosisId) {
        return ResponseEntity.ok(reportService.getPatientsByDiagnosis(diagnosisId));
    }

    @GetMapping("/diagnoses/most-common")
    public ResponseEntity<DiagnosisCountDto> getMostCommonDiagnosis() {
        return ResponseEntity.ok(reportService.getMostCommonDiagnosis());
    }

    @GetMapping("/patients/by-general-practitioner/{doctorId}")
    public ResponseEntity<List<Patient>> getPatientsByGeneralPractitioner(@PathVariable long doctorId) {
        return ResponseEntity.ok(reportService.getPatientsByGeneralPractitioner(doctorId));
    }

    @GetMapping("/payments/patient-paid/total")
    public ResponseEntity<BigDecimal> getTotalPaidByPatients() {
        return ResponseEntity.ok(reportService.getTotalPaidByPatients());
    }

    @GetMapping("/payments/patient-paid/by-doctor")
    public ResponseEntity<List<DoctorPaymentSumDto>> getPatientPaidVisitsSumByDoctor() {
        return ResponseEntity.ok(reportService.getPatientPaidVisitsSumByDoctor());
    }

    @GetMapping("/general-practitioners/{doctorId}/patients/count")
    public ResponseEntity<Long> getPatientsCountByGeneralPractitioner(@PathVariable long doctorId) {
        return ResponseEntity.ok(reportService.getPatientsCountByGeneralPractitioner(doctorId));
    }

    @GetMapping("/doctors/visits/count")
    public ResponseEntity<List<DoctorVisitCountDto>> getVisitsCountByDoctor() {
        return ResponseEntity.ok(reportService.getVisitsCountByDoctor());
    }

    @GetMapping("/patients/{patientId}/visits")
    public ResponseEntity<List<Visit>> getPatientVisitHistory(@PathVariable long patientId) {
        return ResponseEntity.ok(reportService.getPatientVisitHistory(patientId));
    }

    @GetMapping("/visits/by-period")
    public ResponseEntity<List<Visit>> getVisitsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(reportService.getVisitsByPeriod(start, end));
    }

    @GetMapping("/visits/by-doctor-and-period")
    public ResponseEntity<List<Visit>> getVisitsByDoctorAndPeriod(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(reportService.getVisitsByDoctorAndPeriod(doctorId, startDate, endDate));
    }

    @GetMapping("/sick-leaves/month-with-most")
    public ResponseEntity<MonthSickLeaveCountDto> getMonthWithMostSickLeaves() {
        return ResponseEntity.ok(reportService.getMonthWithMostSickLeaves());
    }

    @GetMapping("/sick-leaves/doctor-with-most")
    public ResponseEntity<DoctorSickLeaveCountDto> getDoctorWithMostSickLeaves() {
        return ResponseEntity.ok(reportService.getDoctorWithMostSickLeaves());
    }
}
