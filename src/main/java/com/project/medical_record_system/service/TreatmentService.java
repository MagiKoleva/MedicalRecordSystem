package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Treatment;

import java.util.List;

public interface TreatmentService {

    void validateTreatmentDates(Treatment treatment);
    List<Treatment> getAllTreatments();
    Treatment getTreatment (long id);
    Treatment createTreatment (Treatment treatment);
    Treatment updateTreatment (Treatment treatment, long id);
    void deleteTreatment (long id);
}
