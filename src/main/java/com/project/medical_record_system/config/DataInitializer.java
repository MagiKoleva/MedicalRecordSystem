package com.project.medical_record_system.config;

import com.project.medical_record_system.data.entity.Role;
import com.project.medical_record_system.data.entity.RoleName;
import com.project.medical_record_system.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            if (!this.roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                this.roleRepository.save(role);
            }
        }
    }
}
