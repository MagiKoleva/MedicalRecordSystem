package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    //@JsonBackReference
    @ManyToOne(optional = false)
    private Patient patient;

    //@JsonBackReference
    @ManyToOne(optional = false)
    private Doctor doctor;

    //@JsonBackReference
    @ManyToOne(optional = false)
    private Diagnosis diagnosis;

    //@JsonManagedReference
    @OneToOne(mappedBy = "visit")
    @JsonIgnore
    private Treatment treatment;

    //@JsonManagedReference
    @OneToOne(mappedBy = "visit")
    @JsonIgnore
    private SickLeave  sickleave;
}
