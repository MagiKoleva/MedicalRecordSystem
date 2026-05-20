package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Diagnosis;
import com.project.medical_record_system.data.repository.DiagnosisRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.DiagnosisServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DiagnosisServiceImplTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @InjectMocks
    private DiagnosisServiceImpl diagnosisService;

    @Test
    void getAllDiagnosesReturnsRepositoryResult() {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName("Flu");

        Mockito.when(this.diagnosisRepository.findAll()).thenReturn(List.of(diagnosis));

        assertThat(this.diagnosisService.getAllDiagnoses()).hasSize(1);
    }

    @Test
    void getDiagnosisReturnsDiagnosisWhenItExists() {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Flu");

        Mockito.when(this.diagnosisRepository.findById(1L)).thenReturn(Optional.of(diagnosis));

        Diagnosis result = this.diagnosisService.getDiagnosis(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Flu");
    }

    @Test
    void getDiagnosisThrowsWhenDiagnosisDoesNotExist() {
        Mockito.when(this.diagnosisRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.diagnosisService.getDiagnosis(99L));
    }

    @Test
    void updateDiagnosisUpdatesExistingDiagnosis() {
        Diagnosis existing = new Diagnosis();
        existing.setId(1L);
        existing.setName("Old name");

        Diagnosis newData = new Diagnosis();
        newData.setName("New name");
        newData.setDescription("New description");

        Mockito.when(this.diagnosisRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(this.diagnosisRepository.save(existing)).thenReturn(existing);

        Diagnosis result = this.diagnosisService.updateDiagnosis(newData, 1L);

        assertThat(result.getName()).isEqualTo("New name");
        assertThat(result.getDescription()).isEqualTo("New description");
        Mockito.verify(this.diagnosisRepository).save(existing);
    }

    @Test
    void deleteDiagnosisThrowsWhenDiagnosisDoesNotExist() {
        Mockito.when(this.diagnosisRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.diagnosisService.deleteDiagnosis(1L));
    }
}
