package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Date of the visit must be provided!")
    @FutureOrPresent(message = "Date of visit cannot be in the past!")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Price of the visit must be specified!")
    @PositiveOrZero(message = "The price must be 0 or above!")
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaidBy paidBy;

    @Size(max = 255)
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
    private SickLeave  sickLeave;
}
