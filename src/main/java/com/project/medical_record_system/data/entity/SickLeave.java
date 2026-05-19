package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sick_leave")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SickLeave extends BaseEntity {

    @NotNull(message = "Start date of the sick leave must be specified!")
    @Column(nullable = false)
    private LocalDate fromDate;

    @Positive(message = "The number of sick days must be a positive number!")
    @Column(nullable = false)
    private int days;

    //@JsonBackReference
    @OneToOne(optional = false)
    @JoinColumn(name = "visit_id", unique = true)
    private Visit visit;
}
