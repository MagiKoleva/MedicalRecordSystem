package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty,Long> {
}
