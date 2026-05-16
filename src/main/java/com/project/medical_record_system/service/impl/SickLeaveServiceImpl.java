package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.data.repository.SickLeaveRepository;
import com.project.medical_record_system.data.repository.VisitRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final VisitRepository visitRepository;

    public List<SickLeave> getAllSickLeaves() {
        return sickLeaveRepository.findAll();
    }

    public SickLeave getSickLeave(long id) {
        return sickLeaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SickLeave", id));
    }

    public SickLeave createSickLeave(SickLeave sickLeave) {

        long visitId = sickLeave.getVisit().getId();
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", visitId));

        sickLeave.setVisit(visit);

        return sickLeaveRepository.save(sickLeave);
    }

    public SickLeave updateSickLeave(SickLeave sickLeave, long id) {
        return sickLeaveRepository.findById(id)
                .map(existing -> {
                    existing.setFromDate(sickLeave.getFromDate());
                    existing.setDays(sickLeave.getDays());

                    return sickLeaveRepository.save(existing);
                })
                .orElseGet(() ->
                    this.sickLeaveRepository.save(sickLeave)
                );
    }

    public void deleteSickLeave(long id) {
        if (!sickLeaveRepository.existsById(id)) {
            throw new ResourceNotFoundException("SickLeave", id);
        }
        sickLeaveRepository.deleteById(id);
    }
}
