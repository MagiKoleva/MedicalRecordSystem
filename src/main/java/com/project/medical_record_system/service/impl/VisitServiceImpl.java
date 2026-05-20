package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.*;
import com.project.medical_record_system.data.repository.DiagnosisRepository;
import com.project.medical_record_system.data.repository.DoctorRepository;
import com.project.medical_record_system.data.repository.PatientRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DiagnosisRepository diagnosisRepository;

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisit(long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", id));
    }

    public Visit createVisit(Visit visit) {
        Long patientId = visit.getPatient().getUserId();
        Long doctorId = visit.getDoctor().getUserId();
        Long diagnosisId = visit.getDiagnosis().getId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", doctorId));

        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", diagnosisId));

        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setDiagnosis(diagnosis);

        if (patient.isHealthInsurancePaid()) {
            visit.setPaidBy(PaidBy.NZOK);
        } else {
            visit.setPaidBy(PaidBy.PATIENT);
        }

        return visitRepository.save(visit);
    }

    public Visit updateVisit(Visit visit, long id) {
        return visitRepository.findById(id)
                .map(existing -> {
                    existing.setDate(visit.getDate());
                    existing.setNotes(visit.getNotes());
                    existing.setPrice(visit.getPrice());
                    existing.setDiagnosis(visit.getDiagnosis());

                    return visitRepository.save(existing);
                })
                .orElseGet(() ->
                    this.visitRepository.save(visit)
                );
    }

    public void deleteVisit(long id) {
        if (!visitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Visit", id);
        }
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> getVisitsByDoctorId(long id) {
        return visitRepository.findByDoctorUserId(id);
    }

    @Override
    public List<Visit> getVisitsByPatientId(Long id) {
        return visitRepository.findByPatientUserId(id);
    }

    @Override
    public List<Visit> getVisitsByPeriod(LocalDate startDate, LocalDate endDate) {
        return visitRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Visit> getVisitsByDoctorAndPeriod(Long doctorId, LocalDate startDate, LocalDate endDate) {
        return visitRepository.findByDoctorUserIdAndDateBetween(doctorId, startDate, endDate);
    }
}
