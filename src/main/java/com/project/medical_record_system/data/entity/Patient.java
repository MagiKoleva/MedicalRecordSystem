package com.project.medical_record_system.data.entity;

import jakarta.persistence.*;
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
    private int userId;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String egn;

    private boolean healthInsurancePaid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "general_practitioner_id")
    private Doctor generalPractitioner;

    @OneToMany(mappedBy = "patient")
    private Set<Visit> visits = new HashSet<>();
}
