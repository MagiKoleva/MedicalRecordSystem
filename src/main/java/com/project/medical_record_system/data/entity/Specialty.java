package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    //@JsonBackReference
    @ManyToMany(mappedBy = "specialties")
    @JsonIgnore
    private Set<Doctor> doctors =  new HashSet<>();
}
