package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Doctor identifier must be provided!")
    @Column(nullable = false, unique = true)
    private String doctorIdentifier;

    @NotBlank(message = "Doctor must have a name!")
    @Pattern(regexp = "^([A-Z]).*", message = "Doctor name has to start with a capital letter!")
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
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
