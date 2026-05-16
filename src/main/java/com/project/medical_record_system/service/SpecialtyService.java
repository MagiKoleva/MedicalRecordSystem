package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Specialty;

import java.util.List;

public interface SpecialtyService {

    List<Specialty> getAllSpecialties();
    Specialty getSpecialty(long id);
    Specialty createSpecialty(Specialty specialty);
    Specialty updateSpecialty(Specialty specialty, long id);
    void deleteSpecialty(long id);
}
