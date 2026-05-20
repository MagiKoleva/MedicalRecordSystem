package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByUserUsername(String username);
    Optional<Doctor> findByNameAndGeneralPractitionerTrue(String name);
}
