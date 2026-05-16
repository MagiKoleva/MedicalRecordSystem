package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Diagnosis;
import com.project.medical_record_system.data.repository.DiagnosisRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    @Override
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis getDiagnosis(long id) {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", id));
    }

    @Override
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis updateDiagnosis(Diagnosis diagnosis, long id) {
        return this.diagnosisRepository.findById(id)
                .map(existing -> {
                    existing.setName(diagnosis.getName());
                    existing.setDescription(diagnosis.getDescription());
                    return this.diagnosisRepository.save(existing);
                })
                .orElseGet(() ->
                        this.diagnosisRepository.save(diagnosis)
                );
    }

    @Override
    public void deleteDiagnosis(long id) {
        if (!diagnosisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Diagnosis", id);
        }
        diagnosisRepository.deleteById(id);
    }
}
