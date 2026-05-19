package com.project.medical_record_system.service;

import com.project.medical_record_system.data.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(User user);
    User changeUserRole(long userId, long roleId);
}
