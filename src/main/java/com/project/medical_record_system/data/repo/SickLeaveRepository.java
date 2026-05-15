package com.project.medical_record_system.data.repo;

import com.project.medical_record_system.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SickLeaveRepository extends JpaRepository<SickLeave,Integer> {
}
