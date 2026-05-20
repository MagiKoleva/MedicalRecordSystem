package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.SpecialtyRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void createDoctorConnectsUserAndSpecialties() {
        User user = new User();
        user.setId(1L);

        Specialty specialty = new Specialty();
        specialty.setId(10L);
        specialty.setName("Cardiology");

        Doctor doctor = new Doctor();
        doctor.setUserId(1L);
        doctor.setName("Dr. Ivan Ivanov");
        doctor.setDoctorIdentifier("DOC001");
        doctor.setGeneralPractitioner(true);
        doctor.setSpecialties(Set.of(specialty));

        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(this.specialtyRepository.findAllById(Set.of(10L))).thenReturn(List.of(specialty));
        Mockito.when(this.doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = this.doctorService.createDoctor(doctor);

        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getSpecialties()).containsExactly(specialty);
    }

    @Test
    void createDoctorThrowsWhenUserDoesNotExist() {
        Doctor doctor = new Doctor();
        doctor.setUserId(1L);

        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.doctorService.createDoctor(doctor));
    }

    @Test
    void updateDoctorUpdatesExistingDoctor() {
        Doctor existing = new Doctor();
        existing.setUserId(1L);

        Doctor newData = new Doctor();
        newData.setName("Dr. Petar Petrov");
        newData.setDoctorIdentifier("DOC002");
        newData.setGeneralPractitioner(false);

        Mockito.when(this.doctorRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(this.doctorRepository.save(existing)).thenReturn(existing);

        Doctor result = this.doctorService.updateDoctor(newData, 1L);

        assertThat(result.getName()).isEqualTo("Dr. Petar Petrov");
        assertThat(result.getDoctorIdentifier()).isEqualTo("DOC002");
        assertThat(result.isGeneralPractitioner()).isFalse();
    }

    @Test
    void deleteDoctorThrowsWhenDoctorDoesNotExist() {
        Mockito.when(this.doctorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.doctorService.deleteDoctor(1L));
    }
}
