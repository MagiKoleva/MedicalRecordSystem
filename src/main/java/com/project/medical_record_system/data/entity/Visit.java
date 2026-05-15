package com.project.medical_record_system.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Visit extends BaseEntity {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaidBy paidBy;

    private String notes;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private Doctor doctor;

    @ManyToOne(optional = false)
    private Diagnosis diagnosis;

    @OneToOne(mappedBy = "visit")
    private Treatment treatment;

    @OneToOne(mappedBy = "visit")
    private SickLeave  sickleave;
}
