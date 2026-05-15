package com.project.medical_record_system.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {

    @Id
    private int userId;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false, unique = true)
    private String doctorIdentifier;

    @Column(nullable = false)
    private String name;

    private boolean generalPractitioner;

    @ManyToMany
    @JoinTable(
            name = "doctor_has_specialty",
            joinColumns = @JoinColumn(name = "doctor_user_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

    @OneToMany(mappedBy = "generalPractitioner")
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<Visit> visits = new HashSet<>();
}
