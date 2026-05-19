package com.project.medical_record_system.service.impl;

import com.project.medical_record_system.data.entity.Role;
import com.project.medical_record_system.data.entity.RoleName;
import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.RoleRepository;
import com.project.medical_record_system.data.repository.UserRepository;
import com.project.medical_record_system.exceptions.ResourceNotFoundException;
import com.project.medical_record_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public User createUser(User user) {
        Role role = this.roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", user.getRole().getId()));

        if (role.getName() == RoleName.ADMIN &&
                this.userRepository.existsByRoleName(RoleName.ADMIN)) {
            throw new IllegalArgumentException("Admin user already exists.");
        }

        user.setRole(role);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    public User changeUserRole(long userId, long roleId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", roleId));

        if (role.getName() == RoleName.ADMIN &&
                this.userRepository.existsByRoleName(RoleName.ADMIN)) {
            throw new IllegalArgumentException("Admin user already exists.");
        }

        user.setRole(role);
        return this.userRepository.save(user);
    }
}
