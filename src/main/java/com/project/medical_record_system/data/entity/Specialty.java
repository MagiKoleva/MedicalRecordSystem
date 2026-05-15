package com.project.medical_record_system.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "specialty")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Specialty extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "specialties")
    private Set<Doctor> doctors;
}
