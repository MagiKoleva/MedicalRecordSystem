package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.SickLeaveRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.dto.*;
import com.project.medical_record_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final SickLeaveRepository  sickleaveRepository;

    @Override
    public List<Patient> getPatientsByDiagnosis(long diagnosisId) {
        return visitRepository.findByDiagnosisId(diagnosisId)
                .stream()
                .map(Visit::getPatient)
                .distinct()
                .toList();
    }

    @Override
    public DiagnosisCountDto getMostCommonDiagnosis() {
        return visitRepository.findMostCommonDiagnoses()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Patient> getPatientsByGeneralPractitioner(long doctorId) {
        return patientRepository.findByGeneralPractitionerUserId(doctorId);
    }

    @Override
    public BigDecimal getTotalPaidByPatients() {
        return visitRepository.sumPatientPaidVisits();
    }

    @Override
    public List<DoctorPaymentSumDto> getPatientPaidVisitsSumByDoctor() {
        return visitRepository.sumPatientPaidVisitsByDoctor();
    }

    @Override
    public long getPatientsCountByGeneralPractitioner(long doctorId) {
        return patientRepository.countByGeneralPractitionerUserId(doctorId);
    }

    @Override
    public List<DoctorVisitCountDto> getVisitsCountByDoctor() {
        return visitRepository.countVisitsByDoctor();
    }

    @Override
    public List<Visit> getPatientVisitHistory(long patientId) {
        return visitRepository.findByPatientUserId(patientId);
    }

    @Override
    public List<Visit> getVisitsByPeriod(LocalDate start, LocalDate end) {
        return visitRepository.findByDateBetween(start, end);
    }

    @Override
    public List<Visit> getVisitsByDoctorAndPeriod(long doctorId, LocalDate start, LocalDate end) {
        return visitRepository.findByDoctorUserIdAndDateBetween(doctorId, start, end);
    }

    @Override
    public MonthSickLeaveCountDto getMonthWithMostSickLeaves() {
        return sickleaveRepository.countSickLeavesByMonth()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public DoctorSickLeaveCountDto getDoctorWithMostSickLeaves() {
        return sickleaveRepository.countSickLeavesByDoctor()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<GeneralPractitionerPatientCountDto> getPatientsCountByEachGeneralPractitioner() {
        return patientRepository.countPatientsByGeneralPractitioner();
    }
}
