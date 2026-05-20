package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.Visit;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {

    List<Visit> getAllVisits();
    Visit getVisit (long id);
    Visit createVisit (Visit visit);
    Visit updateVisit (Visit visit, long id);
    void deleteVisit (long id);

    List<Visit> getVisitsByDoctorId(long id);
    List<Visit> getVisitsByPatientId(Long id);
    List<Visit> getVisitsByPeriod(LocalDate startDate, LocalDate endDate);
    List<Visit> getVisitsByDoctorAndPeriod(Long doctorId, LocalDate startDate, LocalDate endDate);
}
