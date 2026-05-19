package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Treatment;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.TreatmentRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final VisitRepository visitRepository;

    @Override
    public void validateTreatmentDates(Treatment treatment) {
        if (treatment.getStartDate() != null &&
        treatment.getEndDate() != null &&
        treatment.getEndDate().isBefore(treatment.getStartDate())) {
            throw new IllegalArgumentException("Treatment end date cannot be before start date!");
        }
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatment(long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment", id));
    }

    public Treatment createTreatment(Treatment treatment) {

        long visitId = treatment.getVisit().getId();
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", visitId));

        validateTreatmentDates(treatment);

        treatment.setVisit(visit);

        return treatmentRepository.save(treatment);
    }

    public Treatment updateTreatment(Treatment treatment, long id) {

        validateTreatmentDates(treatment);

        return treatmentRepository.findById(id)
                .map(existing -> {
                    existing.setPrescribedMedication(treatment.getPrescribedMedication());
                    existing.setDescription(treatment.getDescription());
                    existing.setStartDate(treatment.getStartDate());
                    existing.setEndDate(treatment.getEndDate());

                    return treatmentRepository.save(existing);
                })
                .orElseGet(() ->
                    this.treatmentRepository.save(treatment)
                );
    }

    public void deleteTreatment(long id) {
        if (!treatmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Treatment", id);
        }
        treatmentRepository.deleteById(id);
    }
}
