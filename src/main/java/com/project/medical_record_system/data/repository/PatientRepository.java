package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.dto.GeneralPractitionerPatientCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Optional<Patient> findByUserUsername(String username);

    // patients of a given general practitioner
    List<Patient> findByGeneralPractitionerUserId (long doctorUserId);

    // number of patients of a given general practitioner
    long countByGeneralPractitionerUserId (long doctorUserId);

    @Query("""
       SELECT new com.project.medical_record_system.dto.GeneralPractitionerPatientCountDto(
            p.generalPractitioner.userId,
            p.generalPractitioner.name,
            COUNT(p)
       )
       FROM Patient p
       GROUP BY p.generalPractitioner.userId, p.generalPractitioner.name
       """)
    List<GeneralPractitionerPatientCountDto> countPatientsByGeneralPractitioner();
}
