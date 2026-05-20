package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Treatment;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.TreatmentRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.TreatmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TreatmentServiceImplTest {

    @Mock
    private TreatmentRepository treatmentRepository;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private TreatmentServiceImpl treatmentService;

    @Test
    void validateTreatmentDatesThrowsWhenEndDateIsBeforeStartDate() {
        Treatment treatment = new Treatment();
        treatment.setStartDate(LocalDate.now().plusDays(5));
        treatment.setEndDate(LocalDate.now().plusDays(2));

        assertThrows(IllegalArgumentException.class,
                () -> this.treatmentService.validateTreatmentDates(treatment));
    }

    @Test
    void createTreatmentConnectsExistingVisit() {
        Visit visit = new Visit();
        visit.setId(1L);

        Treatment treatment = new Treatment();
        treatment.setPrescribedMedication("Paracetamol");
        treatment.setStartDate(LocalDate.now().plusDays(1));
        treatment.setEndDate(LocalDate.now().plusDays(3));
        treatment.setVisit(visit);

        Mockito.when(this.visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Mockito.when(this.treatmentRepository.save(treatment)).thenReturn(treatment);

        Treatment result = this.treatmentService.createTreatment(treatment);

        assertThat(result.getVisit()).isEqualTo(visit);
        Mockito.verify(this.treatmentRepository).save(treatment);
    }

    @Test
    void createTreatmentThrowsWhenVisitDoesNotExist() {
        Visit visit = new Visit();
        visit.setId(1L);

        Treatment treatment = new Treatment();
        treatment.setVisit(visit);

        Mockito.when(this.visitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.treatmentService.createTreatment(treatment));
    }

    @Test
    void updateTreatmentUpdatesExistingTreatment() {
        Treatment existing = new Treatment();
        existing.setId(1L);

        Treatment newData = new Treatment();
        newData.setPrescribedMedication("Ibuprofen");
        newData.setDescription("Take after food");
        newData.setStartDate(LocalDate.now().plusDays(1));
        newData.setEndDate(LocalDate.now().plusDays(4));

        Mockito.when(this.treatmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(this.treatmentRepository.save(existing)).thenReturn(existing);

        Treatment result = this.treatmentService.updateTreatment(newData, 1L);

        assertThat(result.getPrescribedMedication()).isEqualTo("Ibuprofen");
        assertThat(result.getDescription()).isEqualTo("Take after food");
    }
}
