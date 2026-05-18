package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    // patients of a given general practitioner
    List<Patient> findByGeneralPractitionerUserId (long doctorUserId);

    // number of patients of a given general practitioner
    long countByGeneralPractitionerUserId (long doctorUserId);
}
