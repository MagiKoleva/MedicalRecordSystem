package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Patient> getPatientsByDiagnosis(long diagnosisId);
    DiagnosisCountDto getMostCommonDiagnosis();
    List<Patient> getPatientsByGeneralPractitioner(long doctorId);
    BigDecimal getTotalPaidByPatients();
    List<DoctorPaymentSumDto> getPatientPaidVisitsSumByDoctor();
    long getPatientsCountByGeneralPractitioner(long doctorId);
    List<DoctorVisitCountDto> getVisitsCountByDoctor();
    List<Visit> getPatientVisitHistory(long patientId);
    List<Visit> getVisitsByPeriod(LocalDate start, LocalDate end);
    List<Visit> getVisitsByDoctorAndPeriod(long doctorId, LocalDate start, LocalDate end);
    MonthSickLeaveCountDto getMonthWithMostSickLeaves();
    DoctorSickLeaveCountDto getDoctorWithMostSickLeaves();
    List<GeneralPractitionerPatientCountDto> getPatientsCountByEachGeneralPractitioner();
}
