package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment,Long> {
}
