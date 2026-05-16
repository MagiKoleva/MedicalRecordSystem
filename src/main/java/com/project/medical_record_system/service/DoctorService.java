package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> getAllDoctors();
    Doctor getDoctor(long id);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor, long id);
    void deleteDoctor(long id);
}
