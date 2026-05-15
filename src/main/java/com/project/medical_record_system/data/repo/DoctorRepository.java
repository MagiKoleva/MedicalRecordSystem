package com.project.medical_record_system.data.repo;

import com.project.medical_record_system.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
}
