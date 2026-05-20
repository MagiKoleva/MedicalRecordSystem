package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.dto.DoctorRegisterDto;
import com.project.medical_record_system.dto.PatientRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(User user);
    User changeUserRole(long userId, long roleId);
    List<User> getAllUsers();
    User registerDoctor(DoctorRegisterDto dto);
    User registerPatient(PatientRegisterDto dto);
}
