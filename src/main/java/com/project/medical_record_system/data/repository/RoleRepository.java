package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.Role;
import com.project.medical_record_system.data.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    boolean existsByName(RoleName name);
    Optional<Role> findByName(RoleName name);
}
