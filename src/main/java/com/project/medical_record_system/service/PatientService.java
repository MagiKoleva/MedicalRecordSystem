package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Patient;

import java.util.List;

public interface PatientService {

    Doctor validateAndGetGeneralPractitioner(long doctorId);
    List<Patient> getAllPatients();
    Patient getPatient(long id);
    Patient createPatient(Patient patient);
    Patient updatePatient(Patient patient, long id);
    void deletePatient(long id);
}
