package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.SickLeaveRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.SickLeaveServiceImpl;
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
class SickLeaveServiceImplTest {

    @Mock
    private SickLeaveRepository sickLeaveRepository;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private SickLeaveServiceImpl sickLeaveService;

    @Test
    void createSickLeaveConnectsExistingVisit() {
        Visit visit = new Visit();
        visit.setId(1L);

        SickLeave sickLeave = new SickLeave();
        sickLeave.setFromDate(LocalDate.now());
        sickLeave.setDays(5);
        sickLeave.setVisit(visit);

        Mockito.when(this.visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Mockito.when(this.sickLeaveRepository.save(sickLeave)).thenReturn(sickLeave);

        SickLeave result = this.sickLeaveService.createSickLeave(sickLeave);

        assertThat(result.getVisit()).isEqualTo(visit);
    }

    @Test
    void createSickLeaveThrowsWhenVisitDoesNotExist() {
        Visit visit = new Visit();
        visit.setId(1L);

        SickLeave sickLeave = new SickLeave();
        sickLeave.setVisit(visit);

        Mockito.when(this.visitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.sickLeaveService.createSickLeave(sickLeave));
    }

    @Test
    void deleteSickLeaveThrowsWhenSickLeaveDoesNotExist() {
        Mockito.when(this.sickLeaveRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.sickLeaveService.deleteSickLeave(1L));
    }
}
