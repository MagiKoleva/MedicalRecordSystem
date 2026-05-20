package com.project.medical_record_system.config;

import com.project.medical_record_system.data.entity.Role;
import com.project.medical_record_system.data.entity.RoleName;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.RoleRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }

        if (!userRepository.existsByRoleName(RoleName.ADMIN)) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow();

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);

            userRepository.save(admin);
        }
    }
}
