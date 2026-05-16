package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
