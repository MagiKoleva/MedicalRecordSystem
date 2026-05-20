package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.*;
import com.project.medical_record_system.data.repository.*;
import com.project.medical_record_system.dto.DoctorRegisterDto;
import com.project.medical_record_system.dto.PatientRegisterDto;
import com.project.medical_record_system.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsernameReturnsUserWhenItExists() {
        User user = new User();
        user.setUsername("doctor1");

        Mockito.when(this.userRepository.findByUsername("doctor1")).thenReturn(user);

        assertThat(this.userService.loadUserByUsername("doctor1")).isEqualTo(user);
    }

    @Test
    void loadUserByUsernameThrowsWhenUserDoesNotExist() {
        Mockito.when(this.userRepository.findByUsername("missing")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername("missing"));
    }

    @Test
    void registerDoctorCreatesUserAndDoctorProfile() {
        Role doctorRole = new Role();
        doctorRole.setName(RoleName.DOCTOR);

        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        User savedUser = new User();
        savedUser.setId(10L);
        savedUser.setUsername("doctor1");
        savedUser.setRole(doctorRole);

        DoctorRegisterDto dto = new DoctorRegisterDto();
        dto.setUsername("doctor1");
        dto.setPassword("doctor123");
        dto.setName("Dr. Ivan Ivanov");
        dto.setDoctorIdentifier("DOC001");
        dto.setGeneralPractitioner(true);
        dto.setSpecialtyId(1L);

        Mockito.when(this.roleRepository.findByName(RoleName.DOCTOR)).thenReturn(Optional.of(doctorRole));
        Mockito.when(this.passwordEncoder.encode("doctor123")).thenReturn("encoded");
        Mockito.when(this.userRepository.save(any(User.class))).thenReturn(savedUser);
        Mockito.when(this.specialtyRepository.findById(1L)).thenReturn(Optional.of(specialty));
        Mockito.when(this.doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = this.userService.registerDoctor(dto);

        assertThat(result.getUsername()).isEqualTo("doctor1");
        Mockito.verify(this.doctorRepository).save(any(Doctor.class));
    }

    @Test
    void registerPatientCreatesUserAndPatientProfile() {
        Role patientRole = new Role();
        patientRole.setName(RoleName.PATIENT);

        Doctor generalPractitioner = new Doctor();
        generalPractitioner.setUserId(1L);
        generalPractitioner.setName("Dr. Ivan Ivanov");
        generalPractitioner.setGeneralPractitioner(true);

        User savedUser = new User();
        savedUser.setId(20L);
        savedUser.setUsername("patient1");
        savedUser.setRole(patientRole);

        PatientRegisterDto dto = new PatientRegisterDto();
        dto.setUsername("patient1");
        dto.setPassword("patient123");
        dto.setName("Maria Petrova");
        dto.setEgn("1234567890");
        dto.setHealthInsurancePaid(true);
        dto.setGeneralPractitionerName("Dr. Ivan Ivanov");

        Mockito.when(this.roleRepository.findByName(RoleName.PATIENT)).thenReturn(Optional.of(patientRole));
        Mockito.when(this.doctorRepository.findByNameAndGeneralPractitionerTrue("Dr. Ivan Ivanov"))
                .thenReturn(Optional.of(generalPractitioner));
        Mockito.when(this.passwordEncoder.encode("patient123")).thenReturn("encoded");
        Mockito.when(this.userRepository.save(any(User.class))).thenReturn(savedUser);
        Mockito.when(this.patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = this.userService.registerPatient(dto);

        assertThat(result.getUsername()).isEqualTo("patient1");
        Mockito.verify(this.patientRepository).save(any(Patient.class));
    }

    @Test
    void registerPatientThrowsWhenGeneralPractitionerDoesNotExist() {
        Role patientRole = new Role();
        patientRole.setName(RoleName.PATIENT);

        PatientRegisterDto dto = new PatientRegisterDto();
        dto.setGeneralPractitionerName("Missing doctor");

        Mockito.when(this.roleRepository.findByName(RoleName.PATIENT)).thenReturn(Optional.of(patientRole));
        Mockito.when(this.doctorRepository.findByNameAndGeneralPractitionerTrue("Missing doctor"))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> this.userService.registerPatient(dto));
    }
}
