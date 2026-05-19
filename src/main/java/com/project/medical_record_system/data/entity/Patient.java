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
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Patient {

    @Id
    private long userId;

    @OneToOne
    @MapsId
    private User user;

    @NotBlank(message = "Patient must have a name!")
    @Pattern(regexp = "^([A-Z]).*", message = "Patient name has to start with a capital letter!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "EGN must be specified!")
    @Pattern(regexp = "\\d{10}", message = "EGN must contain exactly 10 digits!")
    @Column(nullable = false, unique = true, length = 10)
    private String egn;

    @NotNull
    private boolean healthInsurancePaid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "general_practitioner_id", nullable = false)
    private Doctor generalPractitioner;

    //@JsonManagedReference
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Visit> visits = new HashSet<>();
}
