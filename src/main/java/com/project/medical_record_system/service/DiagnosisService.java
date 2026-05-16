package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Diagnosis;

import java.util.List;

public interface DiagnosisService {

    List<Diagnosis> getAllDiagnoses();
    Diagnosis getDiagnosis(long id);
    Diagnosis createDiagnosis(Diagnosis diagnosis);
    Diagnosis updateDiagnosis(Diagnosis diagnosis, long id);
    void deleteDiagnosis(long id);
}
