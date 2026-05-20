package com.project.medical_record_system.config;

import com.project.medical_record_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userService);

        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register/doctor", "/register/patient", "/css/**").permitAll()

                        .requestMatchers("/web/admin/**").hasAuthority("ADMIN")

                        .requestMatchers("/web/doctor/**").hasAuthority("DOCTOR")
                        .requestMatchers("/web/patient/**").hasAuthority("PATIENT")

                        .requestMatchers("/web/doctors/**").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/web/patients/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers("/web/visits/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers("/web/reports/**").hasAnyAuthority("ADMIN", "DOCTOR")

                        .requestMatchers("/api/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
