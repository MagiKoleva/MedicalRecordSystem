package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.*;
import com.project.medical_record_system.data.repository.*;
import com.project.medical_record_system.dto.DoctorRegisterDto;
import com.project.medical_record_system.dto.PatientRegisterDto;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final SpecialtyRepository specialtyRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public User createUser(User user) {
        Role role = this.roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", user.getRole().getId()));

        if (role.getName() == RoleName.ADMIN &&
                this.userRepository.existsByRoleName(RoleName.ADMIN)) {
            throw new IllegalArgumentException("Admin user already exists.");
        }

        user.setRole(role);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    public User changeUserRole(long userId, long roleId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", roleId));

        if (role.getName() == RoleName.ADMIN &&
                this.userRepository.existsByRoleName(RoleName.ADMIN)) {
            throw new IllegalArgumentException("Admin user already exists.");
        }

        user.setRole(role);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User registerDoctor(DoctorRegisterDto dto) {
        Role role = roleRepository.findByName(RoleName.DOCTOR)
                .orElseThrow(() -> new IllegalArgumentException("Role not found."));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", dto.getSpecialtyId()));

        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setUserId(savedUser.getId());
        doctor.setName(dto.getName());
        doctor.setDoctorIdentifier(dto.getDoctorIdentifier());
        doctor.setGeneralPractitioner(dto.getGeneralPractitioner());
        doctor.setSpecialties(Set.of(specialty));

        doctorRepository.save(doctor);

        return savedUser;
    }

    @Transactional
    @Override
    public User registerPatient(PatientRegisterDto dto) {
        Role role = roleRepository.findByName(RoleName.PATIENT)
                .orElseThrow(() -> new IllegalArgumentException("Role not found."));

        Doctor generalPractitioner = doctorRepository
                .findByNameAndGeneralPractitionerTrue(dto.getGeneralPractitionerName())
                .orElseThrow(() -> new IllegalArgumentException("General practitioner not found."));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(savedUser);
        patient.setUserId(savedUser.getId());
        patient.setName(dto.getName());
        patient.setEgn(dto.getEgn());
        patient.setHealthInsurancePaid(dto.getHealthInsurancePaid());
        patient.setGeneralPractitioner(generalPractitioner);

        patientRepository.save(patient);

        return savedUser;
    }
}
