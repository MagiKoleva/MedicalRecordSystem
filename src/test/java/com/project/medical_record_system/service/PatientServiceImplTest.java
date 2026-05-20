package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void validateAndGetGeneralPractitionerReturnsDoctorWhenDoctorIsGp() {
        Doctor doctor = new Doctor();
        doctor.setUserId(1L);
        doctor.setGeneralPractitioner(true);

        Mockito.when(this.doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = this.patientService.validateAndGetGeneralPractitioner(1L);

        assertThat(result).isEqualTo(doctor);
    }

    @Test
    void validateAndGetGeneralPractitionerThrowsWhenDoctorIsNotGp() {
        Doctor doctor = new Doctor();
        doctor.setUserId(1L);
        doctor.setGeneralPractitioner(false);

        Mockito.when(this.doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        assertThrows(IllegalArgumentException.class,
                () -> this.patientService.validateAndGetGeneralPractitioner(1L));
    }

    @Test
    void createPatientConnectsUserAndGeneralPractitioner() {
        User user = new User();
        user.setId(2L);

        Doctor generalPractitioner = new Doctor();
        generalPractitioner.setUserId(1L);
        generalPractitioner.setGeneralPractitioner(true);

        Patient patient = new Patient();
        patient.setUserId(2L);
        patient.setName("Maria Petrova");
        patient.setGeneralPractitioner(generalPractitioner);

        Mockito.when(this.userRepository.findById(2L)).thenReturn(Optional.of(user));
        Mockito.when(this.doctorRepository.findById(1L)).thenReturn(Optional.of(generalPractitioner));
        Mockito.when(this.patientRepository.save(patient)).thenReturn(patient);

        Patient result = this.patientService.createPatient(patient);

        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getGeneralPractitioner()).isEqualTo(generalPractitioner);
    }

    @Test
    void deletePatientThrowsWhenPatientDoesNotExist() {
        Mockito.when(this.patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.patientService.deletePatient(1L));
    }
}
