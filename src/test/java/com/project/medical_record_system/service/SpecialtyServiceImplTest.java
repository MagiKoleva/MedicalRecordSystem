package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.data.repository.SpecialtyRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.impl.SpecialtyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SpecialtyServiceImplTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyServiceImpl specialtyService;

    @Test
    void getSpecialtyReturnsSpecialtyWhenItExists() {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        Mockito.when(this.specialtyRepository.findById(1L)).thenReturn(Optional.of(specialty));

        assertThat(this.specialtyService.getSpecialty(1L).getName()).isEqualTo("Cardiology");
    }

    @Test
    void getSpecialtyThrowsWhenSpecialtyDoesNotExist() {
        Mockito.when(this.specialtyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.specialtyService.getSpecialty(99L));
    }

    @Test
    void updateSpecialtyUpdatesExistingSpecialty() {
        Specialty existing = new Specialty();
        existing.setId(1L);
        existing.setName("Old name");

        Specialty newData = new Specialty();
        newData.setName("Neurology");
        newData.setDescription("Nervous system");

        Mockito.when(this.specialtyRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(this.specialtyRepository.save(existing)).thenReturn(existing);

        Specialty result = this.specialtyService.updateSpecialty(newData, 1L);

        assertThat(result.getName()).isEqualTo("Neurology");
        assertThat(result.getDescription()).isEqualTo("Nervous system");
    }

    @Test
    void deleteSpecialtyThrowsWhenSpecialtyDoesNotExist() {
        Mockito.when(this.specialtyRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.specialtyService.deleteSpecialty(1L));
    }
}
