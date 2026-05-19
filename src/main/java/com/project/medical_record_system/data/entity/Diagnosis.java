package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnosis")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Diagnosis extends BaseEntity {

    @NotBlank(message = "Diagnosis name must be provided!")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    //@JsonManagedReference
    @OneToMany(mappedBy = "diagnosis")
    private Set<Visit> visits = new HashSet<>();
}
