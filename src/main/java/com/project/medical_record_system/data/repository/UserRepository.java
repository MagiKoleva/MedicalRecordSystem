package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.RoleName;
import com.project.medical_record_system.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    boolean existsByRoleName(RoleName roleName);
}
