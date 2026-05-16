package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.data.repository.SpecialtyRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty getSpecialty(long id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", id));
    }

    @Override
    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty updateSpecialty(Specialty specialty, long id) {
        return specialtyRepository.findById(id)
                .map(existing -> {
                    existing.setName(specialty.getName());
                    existing.setDescription(specialty.getDescription());
                    return specialtyRepository.save(existing);
                })
                .orElseGet(() ->
                        this.specialtyRepository.save(specialty)
                );
    }

    @Override
    public void deleteSpecialty(long id) {
        if (!specialtyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Specialty", id);
        }
        specialtyRepository.deleteById(id);
    }
}
