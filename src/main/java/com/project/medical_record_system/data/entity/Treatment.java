package com.project.medical_record_system.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "treatment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Treatment extends BaseEntity {

    private String prescribedMedication;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "visit_id", unique = true)
    private Visit visit;
}
