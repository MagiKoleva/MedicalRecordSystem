package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.SpecialtyRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SpecialtyRepository specialtyRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctor(long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {

        long userId = doctor.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Set<Long> specialtyIds = doctor.getSpecialties()
                .stream()
                .map(Specialty::getId)
                .collect(Collectors.toSet());

        Set<Specialty> specialties = new HashSet<>(
                this.specialtyRepository.findAllById(specialtyIds)
        );

        doctor.setUser(user);
        doctor.setUserId(user.getId());
        doctor.setSpecialties(specialties);

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor, long id) {
        return doctorRepository.findById(id)
                .map(existing -> {
                    existing.setDoctorIdentifier(doctor.getDoctorIdentifier());
                    existing.setName(doctor.getName());
                    existing.setGeneralPractitioner(doctor.isGeneralPractitioner());
                    existing.setSpecialties(doctor.getSpecialties());

                    return doctorRepository.save(existing);
                })
                .orElseGet(() ->
                        this.doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctor(long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor", id);
        }
        doctorRepository.deleteById(id);
    }
}
