package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.SickLeaveRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.dto.DiagnosisCountDto;
import com.project.medical_record_system.dto.DoctorSickLeaveCountDto;
import com.project.medical_record_system.dto.MonthSickLeaveCountDto;
import com.project.medical_record_system.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private SickLeaveRepository sickLeaveRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void getPatientsByDiagnosisReturnsDistinctPatientsFromVisits() {
        Patient patient = new Patient();
        patient.setUserId(1L);

        Visit firstVisit = new Visit();
        firstVisit.setPatient(patient);

        Visit secondVisit = new Visit();
        secondVisit.setPatient(patient);

        Mockito.when(this.visitRepository.findByDiagnosisId(10L)).thenReturn(List.of(firstVisit, secondVisit));

        assertThat(this.reportService.getPatientsByDiagnosis(10L)).hasSize(1);
    }

    @Test
    void getMostCommonDiagnosisReturnsFirstOrderedResult() {
        DiagnosisCountDto flu = new DiagnosisCountDto(1L, "Flu", 3L);

        Mockito.when(this.visitRepository.findMostCommonDiagnoses()).thenReturn(List.of(flu));

        assertThat(this.reportService.getMostCommonDiagnosis().getDiagnosisName()).isEqualTo("Flu");
    }

    @Test
    void getTotalPaidByPatientsReturnsRepositorySum() {
        Mockito.when(this.visitRepository.sumPatientPaidVisits()).thenReturn(BigDecimal.valueOf(100));

        assertThat(this.reportService.getTotalPaidByPatients()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void getMonthWithMostSickLeavesReturnsFirstOrderedResult() {
        MonthSickLeaveCountDto may = new MonthSickLeaveCountDto(5, 3L);

        Mockito.when(this.sickLeaveRepository.countSickLeavesByMonth()).thenReturn(List.of(may));

        assertThat(this.reportService.getMonthWithMostSickLeaves().getMonth()).isEqualTo(5);
    }

    @Test
    void getDoctorWithMostSickLeavesReturnsFirstOrderedResult() {
        DoctorSickLeaveCountDto doctor = new DoctorSickLeaveCountDto(1L, "Dr. Ivan Ivanov", 3L);

        Mockito.when(this.sickLeaveRepository.countSickLeavesByDoctor()).thenReturn(List.of(doctor));

        assertThat(this.reportService.getDoctorWithMostSickLeaves().getDoctorName()).isEqualTo("Dr. Ivan Ivanov");
    }
}
