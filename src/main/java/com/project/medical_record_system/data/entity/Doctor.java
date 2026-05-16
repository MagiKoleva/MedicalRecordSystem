package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private long userId;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false, unique = true)
    private String doctorIdentifier;

    @Column(nullable = false)
    private String name;

    private boolean generalPractitioner;

    //@JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "doctor_has_specialty",
            joinColumns = @JoinColumn(name = "doctor_user_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

    @OneToMany(mappedBy = "generalPractitioner")
    @JsonIgnore
    private Set<Patient> patients = new HashSet<>();

    //@JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Visit> visits = new HashSet<>();
}
