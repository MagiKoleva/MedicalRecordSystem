package com.project.medical_record_system.data.repo;

import com.project.medical_record_system.data.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit,Integer> {
}
