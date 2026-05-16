package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    public Patient createPatient(Patient patient) {

        long userId = patient.getUserId();
        long doctorId = patient.getGeneralPractitioner().getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", doctorId));

        patient.setUser(user);
        patient.setUserId(user.getId());
        patient.setGeneralPractitioner(doctor);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient, long id) {
        return patientRepository.findById(id)
                .map(existing -> {
                    existing.setName(patient.getName());
                    existing.setEgn(patient.getEgn());
                    existing.setHealthInsurancePaid(patient.isHealthInsurancePaid());
                    existing.setGeneralPractitioner(patient.getGeneralPractitioner());

                    return patientRepository.save(existing);
                })
                .orElseGet(() ->
                    this.patientRepository.save(patient)
                );
    }

    public void deletePatient(long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient", id);
        }
        patientRepository.deleteById(id);
    }
}
