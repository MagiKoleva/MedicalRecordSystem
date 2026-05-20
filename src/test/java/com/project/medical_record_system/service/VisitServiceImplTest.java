package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.*;
import com.project.medical_record_system.data.repository.DiagnosisRepository;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.VisitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    @Test
    void createVisitSetsPaidByNzokWhenPatientHasHealthInsurance() {
        Patient patient = new Patient();
        patient.setUserId(10L);
        patient.setHealthInsurancePaid(true);

        Doctor doctor = new Doctor();
        doctor.setUserId(20L);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(30L);

        Visit visit = createVisit(patient, doctor, diagnosis);

        Mockito.when(this.patientRepository.findById(10L)).thenReturn(Optional.of(patient));
        Mockito.when(this.doctorRepository.findById(20L)).thenReturn(Optional.of(doctor));
        Mockito.when(this.diagnosisRepository.findById(30L)).thenReturn(Optional.of(diagnosis));
        Mockito.when(this.visitRepository.save(visit)).thenReturn(visit);

        Visit result = this.visitService.createVisit(visit);

        assertThat(result.getPaidBy()).isEqualTo(PaidBy.NZOK);
        assertThat(result.getPatient()).isEqualTo(patient);
        assertThat(result.getDoctor()).isEqualTo(doctor);
        assertThat(result.getDiagnosis()).isEqualTo(diagnosis);
    }

    @Test
    void createVisitSetsPaidByPatientWhenPatientHasNoHealthInsurance() {
        Patient patient = new Patient();
        patient.setUserId(10L);
        patient.setHealthInsurancePaid(false);

        Doctor doctor = new Doctor();
        doctor.setUserId(20L);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(30L);

        Visit visit = createVisit(patient, doctor, diagnosis);

        Mockito.when(this.patientRepository.findById(10L)).thenReturn(Optional.of(patient));
        Mockito.when(this.doctorRepository.findById(20L)).thenReturn(Optional.of(doctor));
        Mockito.when(this.diagnosisRepository.findById(30L)).thenReturn(Optional.of(diagnosis));
        Mockito.when(this.visitRepository.save(visit)).thenReturn(visit);

        Visit result = this.visitService.createVisit(visit);

        assertThat(result.getPaidBy()).isEqualTo(PaidBy.PATIENT);
    }

    @Test
    void createVisitThrowsWhenPatientDoesNotExist() {
        Patient patient = new Patient();
        patient.setUserId(10L);
        Doctor doctor = new Doctor();
        doctor.setUserId(20L);
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(30L);

        Visit visit = createVisit(patient, doctor, diagnosis);

        Mockito.when(this.patientRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.visitService.createVisit(visit));
    }

    @Test
    void getVisitsByDoctorIdDelegatesToRepository() {
        Mockito.when(this.visitRepository.findByDoctorUserId(20L)).thenReturn(List.of(new Visit()));

        assertThat(this.visitService.getVisitsByDoctorId(20L)).hasSize(1);
    }

    @Test
    void getVisitsByPeriodDelegatesToRepository() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(10);

        Mockito.when(this.visitRepository.findByDateBetween(start, end)).thenReturn(List.of(new Visit()));

        assertThat(this.visitService.getVisitsByPeriod(start, end)).hasSize(1);
    }

    @Test
    void deleteVisitThrowsWhenVisitDoesNotExist() {
        Mockito.when(this.visitRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.visitService.deleteVisit(1L));
    }

    private Visit createVisit(Patient patient, Doctor doctor, Diagnosis diagnosis) {
        Visit visit = new Visit();
        visit.setDate(LocalDate.now().plusDays(1));
        visit.setPrice(BigDecimal.valueOf(50));
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setDiagnosis(diagnosis);
        return visit;
    }
}
