package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.SickLeave;

import java.util.List;

public interface SickLeaveService {

    List<SickLeave> getAllSickLeaves();
    SickLeave getSickLeave (long id);
    SickLeave createSickLeave (SickLeave sickLeave);
    SickLeave updateSickLeave (SickLeave sickLeave, long id);
    void deleteSickLeave (long id);
}
